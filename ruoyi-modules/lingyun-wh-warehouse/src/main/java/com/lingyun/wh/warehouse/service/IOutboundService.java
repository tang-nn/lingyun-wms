package com.lingyun.wh.warehouse.service;

import java.util.List;
import java.util.Map;

import com.lingyun.wh.warehouse.domain.Outbound;
import com.lingyun.wh.warehouse.domain.OutboundDetails;

/**
 * 出库管理Service接口
 *
 * @author Tang
 * @date 2023-12-26
 */
public interface IOutboundService {
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
     * 查询出库明细列表
     *
     * @param params 查询参数
     * @return 出库管理集合
     */
    public List<OutboundDetails<?>> selectOutboundDetails(Map<String, Object> params);

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
     * 审核出库管理
     *
     * @param outbound 出库管理
     * @return 结果
     */
    public int reviewOutbound(Outbound outbound);

    /**
     * 批量删除出库管理
     *
     * @param outIds 需要删除的出库管理主键集合
     * @return 结果
     */
    public int deleteOutboundByOutIds(String[] outIds);

    /**
     * 删除出库管理信息
     *
     * @param outId 出库管理主键
     * @return 结果
     */
    public int deleteOutboundByOutId(String outId);
}
