package com.lingyun.wh.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.goods.api.domain.Stock;
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
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lingyun.wh.warehouse.mapper.OutboundMapper;
import org.springframework.util.Assert;

/**
 * 出库管理Service业务层处理
 *
 * @author Tang
 * @date 2023-12-26
 */
@Service
public class OutboundServiceImpl implements IOutboundService {

    private static final Logger log = LoggerFactory.getLogger(InboundServiceImpl.class);

    @Autowired
    RemoteEncodeRuleService remoteEncodeRuleService;
    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private IInventorySheetService inventorySheetService;
    @Autowired
    private IWareHouseService wareHouseService;
    @Autowired
    private IStockService stockService;

    /**
     * 查询出库管理
     *
     * @param outId 出库管理主键
     * @return 出库管理
     */
    @Override
    public Outbound selectOutboundByOutId(String outId) {
        return outboundMapper.selectOutboundByOutId(outId);
    }

    /**
     * 查询出库管理列表
     *
     * @param outbound 出库管理
     * @return 出库管理
     */
    @Override
    public List<Outbound> selectOutboundList(Outbound outbound) {
        return outboundMapper.selectOutboundList(outbound);
    }

    /**
     * 查询出库明细列表
     *
     * @param params 查询参数
     * @return 出库管理集合
     */
    @Override
    public List<OutboundDetails<?>> selectOutboundDetails(Map<String, Object> params) {
        List<OutboundDetails<?>> detailsList = new ArrayList<>();
        String outType = MapUtils.getString(params, "outType");
        if (StringUtils.isEmpty(outType)) {
            throw new RuntimeException("情选择出库类型");
        }
        switch (outType) {
            case "transfer_outbound":
                selectTransferDetails(detailsList, params);
                break;
            case "sales_outbound":
                break;
            case "inventory_loss_outbound":
                break;
            case "outbound_return":
                break;
            case "other_outbound":
                break;
        }
        return detailsList;
    }

    /**
     * 封装调拨明细
     *
     * @param params
     * @return
     */
    public List<OutboundDetails<?>> selectTransferDetails(List<OutboundDetails<?>> ods, Map<String, Object> params) {
        List<OutboundDetails> outboundDetails = outboundMapper.selectOutboundDetailsList(params);
        if (outboundDetails == null || outboundDetails.isEmpty()) {
            return null;
        }
        String[] tdids = outboundDetails.stream().map(OutboundDetails::getOdid).toArray(String[]::new);
        params.put("tdids", tdids);
        List<TransferDetails> transferDetails = transferService.getTransferDetails(params);
        if (transferDetails != null && !transferDetails.isEmpty() && outboundDetails != null && !outboundDetails.isEmpty()) {
            for (int i = 0; i < outboundDetails.size(); i++) {
                for (int j = 0; j < transferDetails.size(); j++) {
                    OutboundDetails<TransferDetails> od = outboundDetails.get(i);
                    TransferDetails ts = transferDetails.get(j);
                    if (Objects.equals(ts.getTdId(), od.getOdid())) {
                        od.setOrderDetails(ts);
                        ods.add(od);
                        break;
                    }
                }
            }
        }
        return ods;
    }

    /**
     * 新增出库管理
     *
     * @param outbound 出库管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOutbound(Outbound outbound) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.OUTBOUND_ORDER, 1, SecurityConstants.INNER);
        // System.out.println("res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertOutbound 获取编码失败");
            throw new RuntimeException("获取编码失败");
        }
        outbound.setOutCode(res.getData()[0]);
        outbound.setCreateTime(nowDate);
        outbound.setCreateBy(userId);
        outbound.setUpdateTime(nowDate);
        outbound.setUpdateBy(userId);
        // 使用数据字段【单据审核状态（doc_review_status）】
        outbound.setStatus("pending_review");
        int rows = outboundMapper.insertOutbound(outbound);
        insertOutboundDetails(outbound);
        R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.OUTBOUND_ORDER, 1, SecurityConstants.INNER);
        if (r.getCode() != 200) {
            throw new RuntimeException("编号迭代失败");
        }
        return rows;
    }

    /**
     * 出库审核
     *
     * @param outbound 出库对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reviewOutbound(Outbound outbound) {
        String status = outbound.getStatus();
        Outbound outboundOriginal = selectOutboundByOutId(outbound.getOutId());
        // 判断仓库是否为正常
        Assert.isTrue(!wareHouseService.warehouseLocked(outboundOriginal.getWid()), "出库库仓库已锁定，无法进行出库操作");
        Assert.isTrue(Objects.equals(outboundOriginal.getStatus(), "pending_review"), "禁止修改已审核的订单");
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        // 设置审核人
        outbound.setReviewer(userId);
        outbound.setReviewer(userId);
        outbound.setReviewerTime(nowDate);
        String inType = outboundOriginal.getOutType();
        outboundOriginal.setOutId(outbound.getOutId());
        outboundOriginal.setOutType(inType);
        outboundOriginal.setComments(outbound.getComments());
        outboundOriginal.setStatus(status);
        if (Objects.equals(outbound.getStatus(), "done")) {
            switch (inType) {
                // 调拨出库
                case "transfer_outbound":
                    reviewTransferOutbound(outboundOriginal);
                    break;
                case "inventory_loss_outbound":
                    reviewLossOutbound(outboundOriginal);
                    break;
                default:
                    throw new RuntimeException("未知出库类型");
            }
        } else {
            // TODO 审核被驳回，对应申请的调拨单和盘点单修改的计划数量是否要被重新改回
        }
        System.out.println("reviewInbound outbound: " + outbound);
        return outboundMapper.updateOutbound(outbound);
    }

    /**
     * 审核调拨出库
     *
     * @param outbound
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewTransferOutbound(Outbound outbound) {
        // 审核通过，减库存真实，修改调拨单状态和已出库数量
        List<OutboundDetails> outboundDetails = outbound.getOutboundDetailsList();
        // 获取调拨明细 id
        String[] tdIds = outboundDetails.stream().map(e -> e.getOdid()).toArray(String[]::new);
        Assert.isTrue(tdIds != null && tdIds.length > 0, "出库失败，相关调拨订单出现错误！！");
        String tid = outbound.getOrderId();
        Map<String, Object> params = new HashMap<>();
        params.put("tdids", tdIds);
        Transfer transfer = transferService.getTransferById(tid, params);
        Assert.notNull(transfer, "相关调拨单不存在，或者审核状态以改变！");
        List<TransferDetails> transferDetails = transfer.getTransferDetailsList();
        Assert.notNull(transferDetails == null || transferDetails.isEmpty(), "相关调拨单明细不存在！");
        // 修改调拨已出库数量
        List<Stock> stocks = new ArrayList<>();
        for (TransferDetails td : transferDetails) {
            for (OutboundDetails od : outboundDetails) {
                if (Objects.equals(td.getTdId(), od.getOdid())) {
                    // 调拨数量
                    BigDecimal tq = td.getQuantity();
                    // 订单已出库库数量
                    BigDecimal qs = td.getQuantityShipped();
                    // 订单未出库数量
                    BigDecimal diff = tq.subtract(qs);
                    // 本次出库库数量
                    BigDecimal qo = od.getOutboundQuantity();
                    int res = qo.compareTo(diff);
                    Assert.isTrue(res <= 0, "出库数量出错，请修改后在审核！");
                    // 当前已出库数量
                    BigDecimal as = qs.add(qo);
                    td.setQuantityShipped(as);
                    Stock stock = new Stock();
                    stock.setGid(od.getGoodsId());
                    stock.setSlid(od.getSlid());
                    stock.setUnit(od.getUnit());
                    stock.setWid(outbound.getWid());
                    stock.setItemQuantity(qo.negate());
                    stocks.add(stock);
                    break;
                }
            }
        }
        // 修改出库
        stockService.batchAddStock(stocks);
        transfer.setOutStatus("partial_outbound");
        // 修改调拨订单出库数量
        transferService.updateTransferInOutbound(transfer);
        return true;
    }

    /**
     * 审核盘亏出库
     *
     * @param outbound
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewLossOutbound(Outbound outbound) {
        // 审核通过，减库存真实，修改盘点单状态和已出库数量
        List<OutboundDetails> outboundDetails = outbound.getOutboundDetailsList();
        // 获取盘点明细 id
        String[] isdIds = outboundDetails.stream().map(e -> e.getOdid()).toArray(String[]::new);
        Assert.isTrue(isdIds != null && isdIds.length > 0, "出库失败，相关调拨订单出现错误！！");
        InventorySheet inventorySheet = inventorySheetService.selectInventoryById(outbound.getOrderId(), isdIds);
        Assert.notNull(inventorySheet, "相关盘点单不存在！");
        List<InventoryDetails> inventoryDetails = inventorySheet.getInventoryDetailsList();
        Assert.isTrue(!(inventoryDetails == null || inventoryDetails.isEmpty()), "相关调拨单明细不存在！");
        // 修改调拨已出库数量
        List<Stock> stocks = new ArrayList<>();
        for (InventoryDetails id : inventoryDetails) {
            for (OutboundDetails od : outboundDetails) {
                if (Objects.equals(id.getIsdId(), od.getOdid())) {
                    // 盘点盘亏数量，取相反数（在数据字库中，负数为盘亏、正数为盘盈）
                    BigDecimal tq = id.getProfitLossQuantity().negate();
                    // 订单已出库库数量
                    BigDecimal qs = id.getQuantityShipped();
                    // 订单还未出库数量
                    BigDecimal diff = tq.subtract(qs);
                    // 本次出库库数量
                    BigDecimal qo = od.getOutboundQuantity();
                    int res = qo.compareTo(diff);
                    Assert.isTrue(res <= 0, "出库数量出错，请修改后在审核！");
                    // 当前已出库数量
                    BigDecimal as = qs.add(qo);
                    id.setQuantityShipped(as);
                    Stock stock = new Stock();
                    stock.setGid(od.getGoodsId());
                    stock.setSlid(od.getSlid());
                    stock.setUnit(od.getUnit());
                    stock.setWid(outbound.getWid());
                    stock.setItemQuantity(qo.negate());
                    stocks.add(stock);
                    break;
                }
            }
        }
        // 修改出库
        stockService.batchAddStock(stocks);
        inventorySheet.setOutStatus("partial_outbound");
        // 修改盘点订单已出库数量
        inventorySheetService.updateInventoryStatus(inventorySheet);
        return true;
    }

    /**
     * 修改出库管理
     *
     * @param outbound 出库管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOutbound(Outbound outbound) {
        Outbound outboundOriginal = selectOutboundByOutId(outbound.getOutId());
        Assert.isTrue(Objects.equals(outboundOriginal.getStatus(), "pending_review"), "禁止修改已审核的订单");
        outbound.setOutType(outboundOriginal.getOutType());
        outbound.setOutCode(outboundOriginal.getOutCode());
        outbound.setUpdateTime(DateUtils.getNowDate());
        outbound.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        // 删除原有的出库明细
        outboundMapper.deleteOutboundDetailsByOutId(outboundOriginal.getOutId());
        insertOutboundDetails(outbound);
        return outboundMapper.updateOutbound(outbound);
    }

    /**
     * 批量删除出库管理
     *
     * @param outIds 需要删除的出库管理主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOutboundByOutIds(String[] outIds) {
        outboundMapper.deleteOutboundDetailsByOutIds(outIds);
        return outboundMapper.deleteOutboundByOutIds(outIds);
    }

    /**
     * 删除出库管理信息
     *
     * @param outId 出库管理主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOutboundByOutId(String outId) {
        outboundMapper.deleteOutboundDetailsByOutId(outId);
        return outboundMapper.deleteOutboundByOutId(outId);
    }

    /**
     * 新增出库管理明细信息
     *
     * @param outbound 出库管理对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertOutboundDetails(Outbound outbound) {
        List<OutboundDetails> outboundDetailsList = outbound.getOutboundDetailsList();
        String outId = outbound.getOutId();
        String userId = outbound.getUpdateBy();
        Date nowDate = outbound.getUpdateTime();
        if (StringUtils.isNotNull(outboundDetailsList)) {
            List<OutboundDetails> list = new ArrayList<>();
            for (OutboundDetails outboundDetails : outboundDetailsList) {
                outboundDetails.setCreateBy(userId);
                outboundDetails.setCreateTime(nowDate);
                outboundDetails.setUpdateBy(userId);
                outboundDetails.setUpdateTime(nowDate);
                outboundDetails.setOutId(outId);
                list.add(outboundDetails);
            }
            if (!list.isEmpty()) {
                outboundMapper.batchOutboundDetails(list);
            }
        }
    }
}
