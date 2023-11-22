package com.lingyun.wh.order.mapper;

import com.lingyun.wh.order.domain.PurchaseDetails;
import com.lingyun.wh.order.domain.PurchaseOrder;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货订单Mapper接口
 * @CreateDate : 2023/11/21 17:24
 */
public interface PurchaseOrderMapper {
    /**
     * 查询进货订单
     *
     * @param poId 进货订单主键
     * @return 进货订单
     */
    public PurchaseOrder selectPurchaseOrderByPoId(String poId);

    /**
     * 查询进货订单列表
     *
     * @param purchaseOrder 进货订单
     * @return 进货订单集合
     */
    public List<PurchaseOrder> selectPurchaseOrderList(PurchaseOrder purchaseOrder);

    /**
     * 新增进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 修改进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 删除进货订单
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPoId(String poId);

    /**
     * 批量删除进货订单
     *
     * @param poIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPoIds(String[] poIds);

    /**
     * 批量删除进货明细
     *
     * @param poIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseDetailsByPoIds(String[] poIds);

    /**
     * 批量新增进货明细
     *
     * @param purchaseDetailsList 进货明细列表
     * @return 结果
     */
    public int batchPurchaseDetails(List<PurchaseDetails> purchaseDetailsList);


    /**
     * 通过进货订单主键删除进货明细信息
     *
     * @param poId 进货订单ID
     * @return 结果
     */
    public int deletePurchaseDetailsByPoId(String poId);
}
