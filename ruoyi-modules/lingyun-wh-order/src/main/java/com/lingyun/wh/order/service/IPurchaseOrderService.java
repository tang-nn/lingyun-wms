package com.lingyun.wh.order.service;

import com.lingyun.wh.order.domain.PurchaseOrder;
import com.lingyun.wh.order.pojo.vo.PurchaseOrderVo;

import java.util.List;
import java.util.Map;

/**
 * @Author : Tang
 * @Description : 进货订单Service接口
 * @CreateDate : 2023/11/21 17:45
 */
public interface IPurchaseOrderService {
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
     * @param params 进货订单
     * @return 进货订单集合
     */
    public List<PurchaseOrderVo> selectPurchaseOrderList(Map<String, Object> params);

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
     * 批量删除进货订单
     *
     * @param poIds 需要删除的进货订单主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPoIds(String[] poIds);

    /**
     * 删除进货订单信息
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPoId(String poId);
}
