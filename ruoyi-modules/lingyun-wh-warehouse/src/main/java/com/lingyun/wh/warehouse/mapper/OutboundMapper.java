package com.lingyun.wh.warehouse.mapper;

import java.util.List;
import java.util.Map;

import com.lingyun.wh.warehouse.domain.Outbound;
import com.lingyun.wh.warehouse.domain.OutboundDetails;
import org.apache.ibatis.annotations.Param;

/**
 * 出库管理Mapper接口
 * 
 * @author Tang
 * @date 2023-12-26
 */
public interface OutboundMapper 
{
    /**
     * 查询出库管理
     * 
     * @param outId 出库管理主键
     * @return 出库管理
     */
    public Outbound selectOutboundByOutId(String outId);

    /**
     * 查询出库管理列表
     * 
     * @param outbound 出库管理
     * @return 出库管理集合
     */
    public List<Outbound> selectOutboundList(Outbound outbound);

    /**
     * 入库明细查询
     *
     * @param params
     * @return
     */
    List<OutboundDetails> selectOutboundDetailsList(@Param("params") Map<String, Object> params);

    /**
     * 新增出库管理
     * 
     * @param outbound 出库管理
     * @return 结果
     */
    public int insertOutbound(Outbound outbound);

    /**
     * 修改出库管理
     * 
     * @param outbound 出库管理
     * @return 结果
     */
    public int updateOutbound(Outbound outbound);

    /**
     * 删除出库管理
     * 
     * @param outId 出库管理主键
     * @return 结果
     */
    public int deleteOutboundByOutId(String outId);

    /**
     * 批量删除出库管理
     * 
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutboundByOutIds(String[] outIds);

    /**
     * 批量删除出库管理明细
     * 
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutboundDetailsByOutIds(String[] outIds);
    
    /**
     * 批量新增出库管理明细
     * 
     * @param outboundDetailsList 出库管理明细列表
     * @return 结果
     */
    public int batchOutboundDetails(List<OutboundDetails> outboundDetailsList);
    

    /**
     * 通过出库管理主键删除出库管理明细信息
     * 
     * @param outId 出库管理ID
     * @return 结果
     */
    public int deleteOutboundDetailsByOutId(String outId);
}
