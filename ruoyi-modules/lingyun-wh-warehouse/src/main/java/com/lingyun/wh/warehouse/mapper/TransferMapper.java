package com.lingyun.wh.warehouse.mapper;

import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.domain.TransferDetails;

import java.util.List;

/**
 * 调拨单Mapper接口
 *
 * @author Tang
 * @date 2023-11-22
 */
public interface TransferMapper {
    /**
     * 查询调拨单
     *
     * @param tId 调拨单主键
     * @return 调拨单
     */
    public Transfer selectTransferByTId(String tId);

    /**
     * 查询调拨单列表
     *
     * @param transfer 调拨单
     * @return 调拨单集合
     */
    public List<Transfer> selectTransferList(Transfer transfer);

    /**
     * 新增调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    public int insertTransfer(Transfer transfer);

    /**
     * 修改调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    public int updateTransfer(Transfer transfer);

    /**
     * 删除调拨单
     *
     * @param tId 调拨单主键
     * @return 结果
     */
    public int deleteTransferByTId(String tId);

    /**
     * 批量删除调拨单
     *
     * @param tIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTransferByTIds(String[] tIds);

    /**
     * 批量删除调拨明细
     *
     * @param tIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTransferDetailsByTIds(String[] tIds);

    /**
     * 批量新增调拨明细
     *
     * @param transferDetailsList 调拨明细列表
     * @return 结果
     */
    public int batchTransferDetails(List<TransferDetails> transferDetailsList);


    /**
     * 通过调拨单主键删除调拨明细信息
     *
     * @param tId 调拨单ID
     * @return 结果
     */
    public int deleteTransferDetailsByTId(String tId);
}
