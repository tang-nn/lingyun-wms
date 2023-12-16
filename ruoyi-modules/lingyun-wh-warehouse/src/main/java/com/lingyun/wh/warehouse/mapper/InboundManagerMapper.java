package com.lingyun.wh.warehouse.mapper;

import com.lingyun.wh.warehouse.domain.InboundManager;
import com.lingyun.wh.warehouse.domain.InventoryDetails;

import java.util.List;

/**
 * 入库管理Mapper接口
 *
 * @Author : Tang
 * @CreateDate : 2023/12/12 13:19
 */
public interface InboundManagerMapper {
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
     * 删除入库管理
     *
     * @param inId 入库管理主键
     * @return 结果
     */
    public int deleteInboundManagerByInId(String inId);

    /**
     * 批量删除入库管理
     *
     * @param inIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundManagerByInIds(String[] inIds);

    /**
     * 批量删除入库管理明细
     *
     * @param inIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryDetailsByInIds(String[] inIds);

    /**
     * 批量新增入库管理明细
     *
     * @param inventoryDetailsList 入库管理明细列表
     * @return 结果
     */
    public int batchInventoryDetails(List<InventoryDetails> inventoryDetailsList);


    /**
     * 通过入库管理主键删除入库管理明细信息
     *
     * @param inId 入库管理ID
     * @return 结果
     */
    public int deleteInventoryDetailsByInId(String inId);
}
