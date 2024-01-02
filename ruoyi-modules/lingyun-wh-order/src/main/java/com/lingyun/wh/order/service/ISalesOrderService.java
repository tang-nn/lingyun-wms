package com.lingyun.wh.order.service;


import com.lingyun.wh.order.api.domain.SalesOrder;

import java.util.List;

/**
 * 销售订单Service接口
 *
 * @author Tang
 * @date 2023-11-21
 */
public interface ISalesOrderService {
    /**
     * 查询销售订单
     *
     * @param sId 销售订单主键
     * @return 销售订单
     */
    public SalesOrder selectSalesOrderBySId(String sId);

    /**
     * 查询销售订单列表
     *
     * @param salesOrder 销售订单
     * @return 销售订单集合
     */
    public List<SalesOrder> selectSalesOrderList(SalesOrder salesOrder);

    /**
     * 新增销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    public int insertSalesOrder(SalesOrder salesOrder);

    /**
     * 修改销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    public int updateSalesOrder(SalesOrder salesOrder);

    /**
     * 批量删除销售订单
     *
     * @param sIds 需要删除的销售订单主键集合
     * @return 结果
     */
    public int deleteSalesOrderBySIds(String[] sIds);

    /**
     * 删除销售订单信息
     *
     * @param sId 销售订单主键
     * @return 结果
     */
    public int deleteSalesOrderBySId(String sId);
}
