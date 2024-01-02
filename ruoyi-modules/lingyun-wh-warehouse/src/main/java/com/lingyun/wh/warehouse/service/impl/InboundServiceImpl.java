package com.lingyun.wh.warehouse.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import com.lingyun.wh.goods.api.domain.Stock;
import com.lingyun.wh.order.api.RemotePurchaseOrderService;
import com.lingyun.wh.order.api.domain.PurchaseDetails;
import com.lingyun.wh.order.api.domain.PurchaseOrder;
import com.lingyun.wh.warehouse.domain.*;
import com.lingyun.wh.warehouse.service.*;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lingyun.wh.warehouse.mapper.InboundMapper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * 入库管理Service业务层处理
 *
 * @author Tang
 * @date 2023-12-18
 */
@Service
public class InboundServiceImpl implements IInboundService {

    private static final Logger log = LoggerFactory.getLogger(InboundServiceImpl.class);
    @Autowired
    RemoteEncodeRuleService remoteEncodeRuleService;
    @Autowired
    private InboundMapper inboundMapper;
    @Autowired
    private IStockService stockService;
    @Autowired
    private RemotePurchaseOrderService remotePurchaseOrderService;
    @Autowired
    private IWareHouseService wareHouseService;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private IInventorySheetService inventorySheetService;

    /**
     * 查询入库管理
     *
     * @param inid 入库 ID
     * @return 入库管理
     */
    @Override
    public Inbound selectInboundByInid(String inid) {
        Inbound inbound = new Inbound();
        inbound.setInid(inid);
        List<Inbound> inbounds = inboundMapper.selectInboundList(inbound);
        return inbounds != null && inbounds.size() > 0 ? inbounds.get(0) : null;
    }

    /**
     * 查询入库管理列表
     *
     * @param inbound 入库管理
     * @return 入库管理
     */
    @Override
    public List<Inbound> selectInboundList(Inbound inbound) {
        return inboundMapper.selectInboundList(inbound);
    }

    /**
     * 新增入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertInbound(Inbound inbound) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.INBOUND_ORDER, 1, SecurityConstants.INNER);
        // System.out.println("res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertInbound 获取编码失败");
            throw new RuntimeException("获取编码失败");
        }
        inbound.setStatus("pending_review");
        inbound.setInCode(res.getData()[0]);
        inbound.setCreateTime(nowDate);
        inbound.setCreateBy(userId);
        inbound.setUpdateBy(userId);
        inbound.setUpdateTime(nowDate);
        int rows = inboundMapper.insertInbound(inbound);
        insertInboundDetails(inbound);
        R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.INBOUND_ORDER, 1, SecurityConstants.INNER);
        if (r.getCode() != 200) {
            throw new RuntimeException("编号迭代失败");
        }
        return rows;
    }

    /**
     * 修改入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateInbound(Inbound inbound) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        inbound.setUpdateBy(userId);
        inbound.setUpdateTime(nowDate);
        int rows = inboundMapper.deleteInboundDetailsByInId(inbound.getInid());
        insertInboundDetails(inbound);
        return inboundMapper.updateInbound(inbound);
    }

    /**
     * 入库审核
     *
     * @param inbound 入库对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewInbound(Inbound inbound) {
        String status = inbound.getStatus();
        Assert.isTrue(StringUtils.isNotEmpty(status), "请选择审核结果");
        Inbound inboundOriginal = selectInboundByInid(inbound.getInid());
        // 判断仓库是否为正常
        Assert.isTrue(!wareHouseService.warehouseLocked(inboundOriginal.getWid()), "入库库仓库已锁定，无法进行入库操作");
        Assert.isTrue(Objects.equals(inboundOriginal.getStatus(), "pending_review"), "禁止修改已审核的订单");
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        // 设置审核人
        inbound.setReviewer(userId);
        inbound.setReviewer(userId);
        inbound.setReviewerTime(nowDate);
        String inType = inboundOriginal.getInType();
        inboundOriginal.setInid(inbound.getInid());
        inboundOriginal.setComments(inbound.getComments());
        inboundOriginal.setStatus(status);
        // 审核通过进行增加库存
        if (Objects.equals(inbound.getStatus(), "is_done")) {
            switch (inType) {
                // 进货入库
                case "purchasing_warehousing":
                    return reviewPurchaseInbound(inboundOriginal);
                // 关联进货订单
                case "inventory_plus":
                    return reviewInventoryInbound(inboundOriginal);
                // 关联进货订单
                case "transfer_receipt":
                    return reviewTransferInbound(inboundOriginal);
            }
        }
        System.out.println("reviewInbound inbound: " + inboundOriginal);
        return inboundMapper.updateInboundStatus(inboundOriginal) > 0;
    }

    /**
     * 进货入库审核
     *
     * @param inbound
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewPurchaseInbound(Inbound inbound) {
        String inid = inbound.getInid();
        String userId = inbound.getUpdateBy();
        Date nowDate = inbound.getUpdateTime();
        // 审核通过进行增加库存
        List<InboundDetails> inboundDetails = inbound.getInboundDetails();
        String[] dids = (inboundDetails.stream().map(e -> e.getOdid()).toArray(String[]::new));
        if (!StringUtils.isNumeric(inid) || dids == null || dids.length == 0) {
            throw new RuntimeException("审核参数错误");
        }
        R<PurchaseOrder> calibra = remotePurchaseOrderService.getCalibraData(inbound.getOrderId(), dids);
        if (calibra.getCode() != 200) {
            throw new RuntimeException("审核数据校验失败");
        }
        PurchaseOrder purchaseOrder = calibra.getData();
        List<PurchaseDetails> purchaseDetails = purchaseOrder.getPurchaseDetailsList();
        if (purchaseOrder == null || purchaseDetails == null || purchaseDetails.size() != dids.length) {
            throw new RuntimeException("审核数据校验失败");
        }
        List<Stock> stocks = new ArrayList<>();
        // BigDecimal allNotInStock = BigDecimal.ZERO;
        for (PurchaseDetails pd : purchaseDetails) {
            for (InboundDetails id : inboundDetails) {
                if (Objects.equals(pd.getPdId(), id.getOdid())) {
                    // 进货数量
                    BigDecimal pq = pd.getPurchaseQuantity();
                    // 订单已入库数量
                    BigDecimal rq = pd.getReceivedQuantity();
                    // 订单未入库数量
                    BigDecimal diff = pq.subtract(rq);
                    // 本次入库数量
                    BigDecimal qi = id.getQuantityInbound();
                    int res = qi.compareTo(diff);
                    Assert.isTrue(res <= 0, "入库数量不足");
                    BigDecimal as = rq.add(qi);
                    pd.setReceivedQuantity(as);
                    // 未入库数量
                    id.setUnreceivedQuantity(pq.subtract(as));
                    // allNotInStock = allNotInStock.add(id.getUnreceivedQuantity());
                    Stock stock = new Stock();
                    stock.setGid(id.getGoodsId());
                    stock.setSlid(id.getSlid());
                    stock.setUnit(id.getUnit());
                    stock.setWid(inbound.getWid());
                    stock.setCreateBy(userId);
                    stock.setCreateTime(nowDate);
                    stock.setUpdateBy(userId);
                    stock.setUpdateTime(nowDate);
                    stock.setItemQuantity(qi);
                    stock.setNumberPlans(qi);
                    stocks.add(stock);
                    break;
                }
            }
        }
        stockService.batchAddStock(stocks);
        R<Boolean> r = remotePurchaseOrderService.updateStatus(purchaseOrder, SecurityConstants.INNER);
        if (r.getCode() != 200) {
            throw new RuntimeException(r.getMsg());
        }
        return true;
    }

    /**
     * 盘点入库审核
     *
     * @param inbound
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewInventoryInbound(Inbound inbound) {
        String inid = inbound.getInid();
        String userId = inbound.getUpdateBy();
        Date nowDate = inbound.getUpdateTime();
        List<InboundDetails> inboundDetails = inbound.getInboundDetails();
        String[] dids = (inboundDetails.stream().map(e -> e.getOdid()).toArray(String[]::new));
        Assert.isTrue(dids != null && dids.length > 0, "入库失败，相关调拨订单出现错误！！");
        InventorySheet inventorySheet = inventorySheetService.selectInventoryById(inbound.getOrderId(), dids);
        Assert.notNull(inventorySheet, "相关盘点单不存在！");
        List<InventoryDetails> inventoryDetails = inventorySheet.getInventoryDetailsList();
        Assert.isTrue(!(inventoryDetails == null || inventoryDetails.isEmpty()), "相关调拨单明细不存在！");
        List<Stock> stocks = new ArrayList<>();
        // BigDecimal allNotInStock = BigDecimal.ZERO;
        for (InventoryDetails inventory : inventoryDetails) {
            for (InboundDetails ind : inboundDetails) {
                if (Objects.equals(inventory.getIsdId(), ind.getOdid())) {
                    // 盘盈数量
                    BigDecimal pq = inventory.getProfitLossQuantity();
                    // 订单已入库数量
                    BigDecimal rq = inventory.getReceivedQuantity();
                    // 订单未入库数量
                    BigDecimal diff = pq.subtract(rq);
                    // 本次入库数量
                    BigDecimal qi = ind.getQuantityInbound();
                    int res = qi.compareTo(diff);
                    Assert.isTrue(res <= 0, "入库数量不足");
                    BigDecimal as = rq.add(qi);
                    inventory.setReceivedQuantity(as);
                    Stock stock = new Stock();
                    stock.setGid(ind.getGoodsId());
                    stock.setSlid(ind.getSlid());
                    stock.setUnit(ind.getUnit());
                    stock.setWid(inbound.getWid());
                    stock.setCreateBy(userId);
                    stock.setCreateTime(nowDate);
                    stock.setUpdateBy(userId);
                    stock.setUpdateTime(nowDate);
                    stock.setItemQuantity(qi);
                    stock.setNumberPlans(qi);
                    stocks.add(stock);
                    break;
                }
            }
        }
        inventorySheet.setInStatus("partial_warehousing");
        stockService.batchAddStock(stocks);
        inventorySheetService.updateInventoryStatus(inventorySheet);
        return true;
    }

    /**
     * 调拨入库审核
     *
     * @param inbound
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewTransferInbound(Inbound inbound) {
        // 审核通过，减库存真实，修改调拨单状态和已出库数量
        List<InboundDetails> inboundDetails = inbound.getInboundDetails();
        // 获取调拨明细 id
        String[] tdIds = inboundDetails.stream().map(e -> e.getOdid()).toArray(String[]::new);
        Assert.isTrue(tdIds != null && tdIds.length > 0, "出库失败，相关调拨订单出现错误！！");
        String tid = inbound.getOrderId();
        Map<String, Object> params = new HashMap<>();
        params.put("tdids", tdIds);
        Transfer transfer = transferService.getTransferById(tid, params);
        Assert.notNull(transfer, "相关调拨单不存在，或者审核状态以改变！");
        List<TransferDetails> transferDetails = transfer.getTransferDetailsList();
        Assert.notNull(transferDetails == null || transferDetails.isEmpty(), "相关调拨单明细不存在！");
        // 修改调拨已出库数量
        List<Stock> stocks = new ArrayList<>();
        for (TransferDetails td : transferDetails) {
            for (InboundDetails id : inboundDetails) {
                if (Objects.equals(td.getTdId(), id.getOdid())) {
                    // 调拨数量
                    BigDecimal tq = td.getQuantity();
                    // 订单已入库库数量
                    BigDecimal qs = td.getReceivedQuantity();
                    // 订单未入库数量
                    BigDecimal diff = tq.subtract(qs);
                    // 本次入库库数量
                    BigDecimal qo = id.getQuantityInbound();
                    int res = qo.compareTo(diff);
                    Assert.isTrue(res <= 0, "出库数量出错，请修改后在审核！");
                    // 当前已出库数量
                    BigDecimal as = qs.add(qo);
                    td.setReceivedQuantity(as);
                    Stock stock = new Stock();
                    stock.setGid(id.getGoodsId());
                    stock.setSlid(id.getSlid());
                    stock.setUnit(id.getUnit());
                    stock.setWid(inbound.getWid());
                    stock.setItemQuantity(qo.negate());
                    stocks.add(stock);
                    break;
                }
            }
        }
        // 修改出库
        stockService.batchAddStock(stocks);
        transfer.setOutStatus("partial_warehousing");
        // 修改调拨订单出库数量
        transferService.updateTransferInOutbound(transfer);
        return true;
    }

    /**
     * 批量删除入库管理
     *
     * @param inids 需要删除的入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundByInids(String[] inids) {
        inboundMapper.deleteInboundDetailsByInIds(inids);
        return inboundMapper.deleteInboundByInids(inids);
    }

    /**
     * 删除入库管理信息
     *
     * @param inid 入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundByInid(String inid) {
        inboundMapper.deleteInboundDetailsByInId(inid);
        return inboundMapper.deleteInboundByInid(inid);
    }

    /**
     * 新增 OR 修改 入库管理明细信息
     *
     * @param inbound 入库管理对象
     */
    @Transactional(rollbackFor = SQLException.class)
    public void insertInboundDetails(Inbound inbound) {
        List<InboundDetails> inboundDetailsList = inbound.getInboundDetails();
        String inId = inbound.getInid();
        String userId = inbound.getUpdateBy();
        Date nowDate = inbound.getUpdateTime();
        Assert.notEmpty(inboundDetailsList, "入库明细信息不能为空");
        List<InboundDetails> list = new ArrayList<>();
        for (InboundDetails id : inboundDetailsList) {
            id.setInid(inId);
            id.setCreateBy(userId);
            id.setCreateTime(nowDate);
            id.setUpdateBy(userId);
            id.setUpdateTime(nowDate);
            list.add(id);
        }
        inboundMapper.batchInboundDetails(list);
    }
}