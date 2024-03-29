package com.lingyun.wh.warehouse.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import com.lingyun.wh.goods.api.RemoteGoodsService;
import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.service.IInventorySheetService;
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

import java.util.stream.Stream;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lingyun.wh.warehouse.domain.TransferDetails;
import com.lingyun.wh.warehouse.mapper.TransferMapper;
import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.service.ITransferService;
import org.springframework.util.Assert;

import javax.validation.Validator;

/**
 * 调拨单Service业务层处理
 *
 * @author LIJin
 * @date 2023-12-18
 */
@Service
public class TransferServiceImpl implements ITransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);
    @Autowired
    protected Validator validator;
    @Autowired
    private TransferMapper transferMapper;
    @Autowired
    private RemoteEncodeRuleService remoteEncodeRuleService;

    @Autowired
    private RemoteGoodsService remoteGoodsService;

    /**
     * 查询调拨单
     *
     * @param tid 调拨单主键
     * @return 调拨单
     */
    @Override
    public Transfer transferByTid(String tid) {
        return transferMapper.transferByTid(tid);
    }

    /**
     * 查询调拨单列表
     *
     * @param transfer 调拨单
     * @return 调拨单
     */
    @Override
    public List<Transfer> selectTransferList(Transfer transfer) {
        return transferMapper.selectTransferList(transfer);
    }

    /**
     * 调拨出入库，订单查询
     *
     * @param map
     * @return
     */
    @Override
    public List<Transfer> getTransferList(Map<String, Object> map) {
        return transferMapper.getTransferList(map);
    }

    @Override
    public Transfer getTransferById(String tfId, Map<String, Object> params) {
        params.put("tid", tfId);
        List<Transfer> transferList = getTransferList(params);
        if (transferList == null || transferList.size() == 0) {
            return null;
        }
        Transfer transfer = transferList.get(0);
        List<TransferDetails> transferDetails = getTransferDetails(params);
        transfer.setTransferDetailsList(transferDetails);
        return transfer;
    }

    /**
     * 调拨出入库，订单明细查询
     *
     * @param map
     * @return
     */
    @Override
    public List<TransferDetails> getTransferDetails(Map<String, Object> map) {
        // List<TransferDetails> transferDetails = transferMapper.getTransferDetails(map);
        // String[] gids = transferDetails.stream().map(TransferDetails::getGoods).toArray(String[]::new);
        // R<ArrayList<Goods>> res = remoteGoodsService.getInfoByIds(gids, map);
        // if(res.getCode() != 200){
        //     throw new RuntimeException("获取商品信息失败");
        // }
        // Stream<Goods> goodsList = res.getData().stream();
        return transferMapper.getTransferDetails(map);
    }

    /**
     * 新增调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertTransfer(Transfer transfer) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        transfer.setCreateTime(nowDate);
        transfer.setCreateBy(userId);
        transfer.setUpdateTime(nowDate);
        transfer.setUpdateBy(userId);
        transfer.setDocStatus("pending_review");
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.TRANSFER_ORDER, 1, SecurityConstants.INNER);
//        System.out.println("调拨编码获取 res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertGoodsType 调拨编码获取失败");
            throw new RuntimeException("调拨编码获取失败");
        }
        transfer.setTdCode(res.getData()[0]);
        System.out.println("transfer:" + transfer);
        String wId = "0";
        List<TransferDetails> t = transfer.getTransferDetailsList();
        if (t != null && t.size() > 0) {
            for (TransferDetails transferDetails : t) {
                String slid = transferDetails.getSl_id();// 库位id
                // 盘点数量
                BigDecimal quantity = transferDetails.getQuantity();
                // 获取货品id
                String gId = transferDetails.getG_id();
                transferMapper.changePlanNums(quantity, transfer.getOutWId(), slid, gId);
            }
        }
        int rows = transferMapper.insertTransfer(transfer);
        if (rows > 0) {
            R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.TRANSFER_ORDER, 1, SecurityConstants.INNER);
            System.out.println("流水号迭代 Res: " + r);
            transfer.setTid(transfer.getTid());// 获取刚插入调拨单的自增id
            insertTransferDetails(transfer);// 调拨明细
            if (r == null || r.getCode() != 200) {
                log.error("insertWareHouse 流水号迭代失败");
                throw new RuntimeException("调拨单数据插入失败");
            }
        }
        return rows;
    }

    /**
     * 新增调拨明细信息
     *
     * @param transfer 调拨单对象
     */
    @Transactional(rollbackFor = SQLException.class)
    public void insertTransferDetails(Transfer transfer) {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        List<TransferDetails> transferDetailsList = transfer.getTransferDetailsList();
        String tid = transfer.getTid();
        if (StringUtils.isNotNull(transferDetailsList)) {
            List<TransferDetails> list = new ArrayList<TransferDetails>();
            for (TransferDetails transferDetails : transferDetailsList) {
                transferDetails.setTid(tid);
                transferDetails.setCreateBy(uid);
                transferDetails.setCreateTime(nowDate);
                transferDetails.setUpdateBy(uid);
                transferDetails.setUpdateTime(nowDate);
                list.add(transferDetails);
            }
            if (list.size() > 0) {
                transferMapper.batchTransferDetails(list);
            }
        }
    }


    /*根据调拨仓库id查询所有库位*/
    @Override
    public List<StorageLocation> location(String w_id) {
        return transferMapper.location(w_id);
    }

    /*查询调入仓库货品库存

     */
    @Override
    public int InItemQuantity(String indWid, String g_id) {
        return transferMapper.InItemQuantity(indWid, g_id);
    }


//    // 查询调入仓库某库位下的某货品货品计划数量
//    @Override
//    public int numberPlansIn(String w_id, String sl_id, String g_id) {
//        return transferMapper.numberPlansIn(w_id, sl_id, g_id);
//    }


//    //查询调入仓库货品的库位(调入仓库id 货品id)
//    @Override
//    public List<StorageLocation> getLocation(String w_id, String g_id) {
//        return transferMapper.getLocation(w_id, g_id);
//    }

    /**
     * 修改调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTransfer(Transfer transfer) {
//        System.out.println(transfer.getTransferDetailsList()+"[][][][][");
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        transfer.setUpdateBy(uid);
        transfer.setUpdateTime(nowDate);
        Map<String, Object> map = new HashMap<>();
        map.put("outWId", transfer.getOutWId());
        map.put("tId", transfer.getTid());
        for (TransferDetails td : transfer.getTransferDetailsList()) {
            map.put("gId", td.getGoods().getGId());
            map.put("slId", td.getLocation().getSlId());
            List<Map<String, Object>> plansAndTquantity = transferMapper.getPlansAndTquantity(map);
            for (TransferDetails tds : transfer.getTransferDetailsList()) {
                for (Map<String, Object> pt : plansAndTquantity) {
                    BigDecimal tQuantity = (BigDecimal) pt.get("t_quantity");
                    if (tQuantity != tds.getQuantity() && tds.getTdId().equals(pt.get("td_id").toString())) {
                        BigDecimal result = tds.getQuantity().subtract(tQuantity);
//                        System.out.println("tds========================="+tds);
//                        System.out.println(result+"****"+transfer.getOutWId()+"****"+ tds.getStock().getSlid()+"****"+ tds.getGoods().getGId());
                        transferMapper.changePlanNums(result, transfer.getOutWId(), tds.getStock().getSlid(), tds.getGoods().getGId());
                    }
                }
            }
        }

        List<TransferDetails> tf = transfer.getTransferDetailsList();
//        System.out.println("tf=====================/////////////////*******************"+tf);
        List<TransferDetails> addList = new ArrayList<>();
        List<TransferDetails> updateList = new ArrayList<>();
        for (TransferDetails details : tf) {

            if (details.getTdId() == null || details.getTdId() == "") {
                // 新增
                System.out.println("新增" + details);
                addList.add(details);
            } else {
                System.out.println("修改" + details);
                details.setUpdateBy(uid);
                details.setUpdateTime(nowDate);
                updateList.add(details);
            }
        }
        transfer.setTransferDetailsList(addList);
        insertTransferDetails(transfer);
        System.out.println("updateList=====================" + updateList);
        transferMapper.updateTransferDetailsList(updateList);
        transferMapper.updateTransfer(transfer);

        return 1;
    }

    /**
     * 批量删除调拨单
     *
     * @param tids 需要删除的调拨单主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteTransferByTids(String[] tids) {
        transferMapper.deleteTransferDetailsByTIds(tids);// 删除调拨明细
        return transferMapper.deleteTransferByTids(tids);// 删除调拨单
    }

    /**
     * 修改调拨单出入库状态、及明细中的出入库数量
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Override
    public boolean updateTransferInOutbound(Transfer transfer) {
        List<TransferDetails> transferDetails = transfer.getTransferDetailsList();
        for (TransferDetails details : transferDetails) {
            int i = transferMapper.updateTransferDetails(details);
            Assert.isTrue(i != 0, "调拨明细单更新失败");
        }
        Map<String, Boolean> status = getOutInboundStatus(transfer.getTid());
        Boolean allShipped = MapUtils.getBoolean(status, "allShipped");
        if (allShipped) {
            transfer.setOutStatus("outbound");
        }
        Boolean allInStock = MapUtils.getBoolean(status, "allInStock");
        if (allInStock) {
            transfer.setInStatus("received");
        }

        return transferMapper.updateTransfer(transfer) > 0;
    }

    @Override
    public int deleteTransferDetailsByTdIds(String[] tdIds) {
        return transferMapper.deleteTransferDetailsByTdIds(tdIds);
    }


    @Override
    public int reviewInventory(Transfer transfer) {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        transfer.setReviewer(uid);
        transfer.setReviewerTime(nowDate);
        transfer.setUpdateBy(uid);
        transfer.setUpdateTime(nowDate);
        return transferMapper.reviewInventory(transfer);
    }

    /**
     * 查询指定调拨单的出入库状态
     *
     * @param tfId 调拨单 id
     * @return 已全部出库（allShipped）、已全部入库（allInStock）
     */
    @Override
    public Map<String, Boolean> getOutInboundStatus(String tfId) {
        return transferMapper.getOutInboundStatus(tfId);
    }
}
