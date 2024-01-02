package com.lingyun.wh.warehouse.service;

import java.util.List;

import com.lingyun.wh.warehouse.domain.Inbound;

/**
 * 入库管理Service接口
 *
 * @author Tang
 * @date 2023-12-18
 */
public interface IInboundService {
    /**
     * 查询入库管理
     *
     * @param inid 入库 ID
     * @return 入库管理
     */
    public Inbound selectInboundByInid(String inid);

    /**
     * 查询入库管理列表
     *
     * @param inbound 入库管理
     * @return 入库管理集合
     */
    public List<Inbound> selectInboundList(Inbound inbound);

    /**
     * 新增入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    public int insertInbound(Inbound inbound);

    /**
     * 修改入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    public int updateInbound(Inbound inbound);

    /**
     * 入库审核
     *
     * @param inbound 入库对象
     * @return 结果
     */
    public boolean reviewInbound(Inbound inbound);

    /**
     * 批量删除入库管理
     *
     * @param inids 需要删除的入库管理主键集合
     * @return 结果
     */
    public int deleteInboundByInids(String[] inids);

    /**
     * 删除入库管理信息
     *
     * @param inid 入库管理主键
     * @return 结果
     */
    public int deleteInboundByInid(String inid);
}
