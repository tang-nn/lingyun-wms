package com.lingyun.wh.warehouse.service;

import java.util.List;
import com.lingyun.wh.warehouse.domain.Transfer;

/**
 * 调拨单Service接口
 * 
 * @author Tang
 * @date 2023-11-22
 */
public interface ITransferService 
{
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
     * 批量删除调拨单
     * 
     * @param tIds 需要删除的调拨单主键集合
     * @return 结果
     */
    public int deleteTransferByTIds(String[] tIds);

    /**
     * 删除调拨单信息
     * 
     * @param tId 调拨单主键
     * @return 结果
     */
    public int deleteTransferByTId(String tId);
}
