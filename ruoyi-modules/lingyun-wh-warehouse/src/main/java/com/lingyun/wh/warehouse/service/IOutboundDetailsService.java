package com.lingyun.wh.warehouse.service;

import java.util.List;
import com.lingyun.wh.warehouse.domain.OutboundDetails;

/**
 * 出库管理明细Service接口
 * 
 * @author Tang
 * @date 2023-12-26
 */
public interface IOutboundDetailsService 
{
    /**
     * 查询出库管理明细
     * 
     * @param obdId 出库管理明细主键
     * @return 出库管理明细
     */
    public OutboundDetails selectOutboundDetailsByObdId(String obdId);

    /**
     * 查询出库管理明细列表
     * 
     * @param outboundDetails 出库管理明细
     * @return 出库管理明细集合
     */
    public List<OutboundDetails> selectOutboundDetailsList(OutboundDetails outboundDetails);

    /**
     * 新增出库管理明细
     * 
     * @param outboundDetails 出库管理明细
     * @return 结果
     */
    public int insertOutboundDetails(OutboundDetails outboundDetails);

    /**
     * 修改出库管理明细
     * 
     * @param outboundDetails 出库管理明细
     * @return 结果
     */
    public int updateOutboundDetails(OutboundDetails outboundDetails);

    /**
     * 批量删除出库管理明细
     * 
     * @param obdIds 需要删除的出库管理明细主键集合
     * @return 结果
     */
    public int deleteOutboundDetailsByObdIds(String[] obdIds);

    /**
     * 删除出库管理明细信息
     * 
     * @param obdId 出库管理明细主键
     * @return 结果
     */
    public int deleteOutboundDetailsByObdId(String obdId);
}
