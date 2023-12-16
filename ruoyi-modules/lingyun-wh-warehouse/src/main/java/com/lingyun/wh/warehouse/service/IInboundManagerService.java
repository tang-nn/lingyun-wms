package com.lingyun.wh.warehouse.service;

import com.lingyun.wh.warehouse.domain.InboundManager;

import java.util.List;

/**
 * @Author : Tang
 * @CreateDate : 2023/12/12 13:21
 */
public interface IInboundManagerService {

    /**
     * 查询入库管理
     *
     * @param inId 入库管理主键
     * @return 入库管理
     */
    public InboundManager selectInboundManagerByInId(String inId);

    /**
     * 查询入库管理列表
     *
     * @param inboundManager 入库管理
     * @return 入库管理集合
     */
    public List<InboundManager> selectInboundManagerList(InboundManager inboundManager);

    /**
     * 新增入库管理
     *
     * @param inboundManager 入库管理
     * @return 结果
     */
    public int insertInboundManager(InboundManager inboundManager);

    /**
     * 修改入库管理
     *
     * @param inboundManager 入库管理
     * @return 结果
     */
    public int updateInboundManager(InboundManager inboundManager);

    /**
     * 批量删除入库管理
     *
     * @param inIds 需要删除的入库管理主键集合
     * @return 结果
     */
    public int deleteInboundManagerByInIds(String[] inIds);

    /**
     * 删除入库管理信息
     *
     * @param inId 入库管理主键
     * @return 结果
     */
    public int deleteInboundManagerByInId(String inId);

}
