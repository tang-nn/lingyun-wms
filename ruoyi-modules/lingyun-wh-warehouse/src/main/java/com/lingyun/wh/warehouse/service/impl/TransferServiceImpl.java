package com.lingyun.wh.warehouse.service.impl;

import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.domain.TransferDetails;
import com.lingyun.wh.warehouse.mapper.TransferMapper;
import com.lingyun.wh.warehouse.service.ITransferService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 调拨单Service业务层处理
 *
 * @author Tang
 * @date 2023-11-22
 */
@Service
public class TransferServiceImpl implements ITransferService {
    @Autowired
    private TransferMapper transferMapper;

    /**
     * 查询调拨单
     *
     * @param tId 调拨单主键
     * @return 调拨单
     */
    @Override
    public Transfer selectTransferByTId(String tId) {
        return transferMapper.selectTransferByTId(tId);
    }

    /**
     * 查询调拨单列表
     *
     * @param transfer 调拨单
     * @return 调拨单
     */
    @Override
    public List<Transfer> selectTransferList(Transfer transfer) {
        return transferMapper.selectTransferList(transfer);
    }

    /**
     * 新增调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTransfer(Transfer transfer) {
        transfer.setCreateTime(DateUtils.getNowDate());
        int rows = transferMapper.insertTransfer(transfer);
        insertTransferDetails(transfer);
        return rows;
    }

    /**
     * 修改调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTransfer(Transfer transfer) {
        transfer.setUpdateTime(DateUtils.getNowDate());
        transferMapper.deleteTransferDetailsByTId(transfer.gettId());
        insertTransferDetails(transfer);
        return transferMapper.updateTransfer(transfer);
    }

    /**
     * 批量删除调拨单
     *
     * @param tIds 需要删除的调拨单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTransferByTIds(String[] tIds) {
        transferMapper.deleteTransferDetailsByTIds(tIds);
        return transferMapper.deleteTransferByTIds(tIds);
    }

    /**
     * 删除调拨单信息
     *
     * @param tId 调拨单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTransferByTId(String tId) {
        transferMapper.deleteTransferDetailsByTId(tId);
        return transferMapper.deleteTransferByTId(tId);
    }

    /**
     * 新增调拨明细信息
     *
     * @param transfer 调拨单对象
     */
    public void insertTransferDetails(Transfer transfer) {
        List<TransferDetails> transferDetailsList = transfer.getTransferDetailsList();
        String tId = transfer.gettId();
        if (StringUtils.isNotNull(transferDetailsList)) {
            List<TransferDetails> list = new ArrayList<TransferDetails>();
            for (TransferDetails transferDetails : transferDetailsList) {
                transferDetails.settId(tId);
                list.add(transferDetails);
            }
            if (list.size() > 0) {
                transferMapper.batchTransferDetails(list);
            }
        }
    }
}
