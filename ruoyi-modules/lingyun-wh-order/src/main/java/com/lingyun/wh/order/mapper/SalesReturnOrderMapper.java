package com.lingyun.wh.order.mapper;

import com.lingyun.wh.order.domain.SalesReturnDetails;
import com.lingyun.wh.order.domain.SalesReturnOrder;

import java.util.List;

/**
 * 销售退货Mapper接口
 *
 * @author Tang
 * @date 2023-11-21
 */
public interface SalesReturnOrderMapper {
    /**
     * 查询销售退货
     *
     * @param srId 销售退货主键
     * @return 销售退货
     */
    public SalesReturnOrder selectSalesReturnBySrId(String srId);

    /**
     * 查询销售退货列表
     *
     * @param salesReturn 销售退货
     * @return 销售退货集合
     */
    public List<SalesReturnOrder> selectSalesReturnList(SalesReturnOrder salesReturn);

    /**
     * 新增销售退货
     *
     * @param salesReturn 销售退货
     * @return 结果
     */
    public int insertSalesReturn(SalesReturnOrder salesReturn);

    /**
     * 修改销售退货
     *
     * @param salesReturn 销售退货
     * @return 结果
     */
    public int updateSalesReturn(SalesReturnOrder salesReturn);

    /**
     * 删除销售退货
     *
     * @param srId 销售退货主键
     * @return 结果
     */
    public int deleteSalesReturnBySrId(String srId);

    /**
     * 批量删除销售退货
     *
     * @param srIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalesReturnBySrIds(String[] srIds);

    /**
     * 批量删除销售退货明细
     *
     * @param srIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalesReturnDetailsBySrIds(String[] srIds);

    /**
     * 批量新增销售退货明细
     *
     * @param salesReturnDetailsList 销售退货明细列表
     * @return 结果
     */
    public int batchSalesReturnDetails(List<SalesReturnDetails> salesReturnDetailsList);


    /**
     * 通过销售退货主键删除销售退货明细信息
     *
     * @param srId 销售退货ID
     * @return 结果
     */
    public int deleteSalesReturnDetailsBySrId(String srId);
}
