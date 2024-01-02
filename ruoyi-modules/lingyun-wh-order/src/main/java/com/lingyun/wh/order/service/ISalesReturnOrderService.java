package com.lingyun.wh.order.service;


import com.lingyun.wh.order.api.domain.SalesReturnOrder;

import java.util.List;

/**
 * 销售退货Service接口
 *
 * @author Tang
 * @date 2023-11-21
 */
public interface ISalesReturnOrderService {
    /**
     * 查询销售退货
     *
     * @param srId 销售退货主键
     * @return 销售退货
     */
    public SalesReturnOrder selectSalesReturnOrderBySrId(String srId);

    /**
     * 查询销售退货列表
     *
     * @param salesReturnOrder 销售退货
     * @return 销售退货集合
     */
    public List<SalesReturnOrder> selectSalesReturnOrderList(SalesReturnOrder salesReturnOrder);

    /**
     * 新增销售退货
     *
     * @param salesReturnOrder 销售退货
     * @return 结果
     */
    public int insertSalesReturnOrder(SalesReturnOrder salesReturnOrder);

    /**
     * 修改销售退货
     *
     * @param salesReturnOrder 销售退货
     * @return 结果
     */
    public int updateSalesReturnOrder(SalesReturnOrder salesReturnOrder);

    /**
     * 批量删除销售退货
     *
     * @param srIds 需要删除的销售退货主键集合
     * @return 结果
     */
    public int deleteSalesReturnOrderBySrIds(String[] srIds);

    /**
     * 删除销售退货信息
     *
     * @param srId 销售退货主键
     * @return 结果
     */
    public int deleteSalesReturnOrderBySrId(String srId);
}
