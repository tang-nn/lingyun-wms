package com.lingyun.wh.order.service.impl;

import com.lingyun.wh.order.domain.PurchaseDetails;
import com.lingyun.wh.order.domain.PurchaseOrder;
import com.lingyun.wh.order.mapper.PurchaseOrderMapper;
import com.lingyun.wh.order.service.IPurchaseOrderService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/21 17:46
 */
@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    /**
     * 查询进货订单
     *
     * @param poId 进货订单主键
     * @return 进货订单
     */
    @Override
    public PurchaseOrder selectPurchaseOrderByPoId(String poId) {
        return purchaseOrderMapper.selectPurchaseOrderByPoId(poId);
    }

    /**
     * 查询进货订单列表
     *
     * @param purchaseOrder 进货订单
     * @return 进货订单
     */
    @Override
    public List<PurchaseOrder> selectPurchaseOrderList(PurchaseOrder purchaseOrder) {
        return purchaseOrderMapper.selectPurchaseOrderList(purchaseOrder);
    }

    /**
     * 新增进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setCreateTime(DateUtils.getNowDate());
        int rows = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        insertPurchaseDetails(purchaseOrder);
        return rows;
    }

    /**
     * 修改进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setUpdateTime(DateUtils.getNowDate());
        purchaseOrderMapper.deletePurchaseDetailsByPoId(purchaseOrder.getPoId());
        insertPurchaseDetails(purchaseOrder);
        return purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * 批量删除进货订单
     *
     * @param poIds 需要删除的进货订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPoIds(String[] poIds) {
        purchaseOrderMapper.deletePurchaseDetailsByPoIds(poIds);
        return purchaseOrderMapper.deletePurchaseOrderByPoIds(poIds);
    }

    /**
     * 删除进货订单信息
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPoId(String poId) {
        purchaseOrderMapper.deletePurchaseDetailsByPoId(poId);
        return purchaseOrderMapper.deletePurchaseOrderByPoId(poId);
    }

    /**
     * 新增进货明细信息
     *
     * @param purchaseOrder 进货订单对象
     */
    public void insertPurchaseDetails(PurchaseOrder purchaseOrder) {
        List<PurchaseDetails> purchaseDetailsList = purchaseOrder.getPurchaseDetailsList();
        String poId = purchaseOrder.getPoId();
        if (StringUtils.isNotNull(purchaseDetailsList)) {
            List<PurchaseDetails> list = new ArrayList<PurchaseDetails>();
            for (PurchaseDetails purchaseDetails : purchaseDetailsList) {
                purchaseDetails.setPoId(poId);
                list.add(purchaseDetails);
            }
            if (list.size() > 0) {
                purchaseOrderMapper.batchPurchaseDetails(list);
            }
        }
    }
}
