package com.lingyun.wh.order.service.impl;

import com.lingyun.wh.order.domain.PurchaseReturnDetails;
import com.lingyun.wh.order.domain.PurchaseReturnNote;
import com.lingyun.wh.order.mapper.PurchaseReturnNoteMapper;
import com.lingyun.wh.order.service.IPurchaseReturnNoteService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货退货Service业务层处理
 * @CreateDate : 2023/11/21 23:24
 */
@Service
public class PurchaseReturnNoteServiceImpl implements IPurchaseReturnNoteService {
    @Autowired
    private PurchaseReturnNoteMapper purchaseReturnNoteMapper;

    /**
     * 查询进货退货
     *
     * @param prId 进货退货主键
     * @return 进货退货
     */
    @Override
    public PurchaseReturnNote selectPurchaseReturnNoteByPrId(String prId) {
        return purchaseReturnNoteMapper.selectPurchaseReturnNoteByPrId(prId);
    }

    /**
     * 查询进货退货列表
     *
     * @param purchaseReturnNote 进货退货
     * @return 进货退货
     */
    @Override
    public List<PurchaseReturnNote> selectPurchaseReturnNoteList(PurchaseReturnNote purchaseReturnNote) {
        return purchaseReturnNoteMapper.selectPurchaseReturnNoteList(purchaseReturnNote);
    }

    /**
     * 新增进货退货
     *
     * @param purchaseReturnNote 进货退货
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseReturnNote(PurchaseReturnNote purchaseReturnNote) {
        purchaseReturnNote.setCreateTime(DateUtils.getNowDate());
        int rows = purchaseReturnNoteMapper.insertPurchaseReturnNote(purchaseReturnNote);
        insertPurchaseReturnDetails(purchaseReturnNote);
        return rows;
    }

    /**
     * 修改进货退货
     *
     * @param purchaseReturnNote 进货退货
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseReturnNote(PurchaseReturnNote purchaseReturnNote) {
        purchaseReturnNote.setUpdateTime(DateUtils.getNowDate());
        purchaseReturnNoteMapper.deletePurchaseReturnDetailsByPrId(purchaseReturnNote.getPrId());
        insertPurchaseReturnDetails(purchaseReturnNote);
        return purchaseReturnNoteMapper.updatePurchaseReturnNote(purchaseReturnNote);
    }

    /**
     * 批量删除进货退货
     *
     * @param prIds 需要删除的进货退货主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseReturnNoteByPrIds(String[] prIds) {
        purchaseReturnNoteMapper.deletePurchaseReturnDetailsByPrIds(prIds);
        return purchaseReturnNoteMapper.deletePurchaseReturnNoteByPrIds(prIds);
    }

    /**
     * 删除进货退货信息
     *
     * @param prId 进货退货主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseReturnNoteByPrId(String prId) {
        purchaseReturnNoteMapper.deletePurchaseReturnDetailsByPrId(prId);
        return purchaseReturnNoteMapper.deletePurchaseReturnNoteByPrId(prId);
    }

    /**
     * 新增进货退货明细信息
     *
     * @param purchaseReturnNote 进货退货对象
     */
    public void insertPurchaseReturnDetails(PurchaseReturnNote purchaseReturnNote) {
        List<PurchaseReturnDetails> purchaseReturnDetailsList = purchaseReturnNote.getPurchaseReturnDetailsList();
        String prId = purchaseReturnNote.getPrId();
        if (StringUtils.isNotNull(purchaseReturnDetailsList)) {
            List<PurchaseReturnDetails> list = new ArrayList<PurchaseReturnDetails>();
            for (PurchaseReturnDetails purchaseReturnDetails : purchaseReturnDetailsList) {
                purchaseReturnDetails.setPrId(prId);
                list.add(purchaseReturnDetails);
            }
            if (list.size() > 0) {
                purchaseReturnNoteMapper.batchPurchaseReturnDetails(list);
            }
        }
    }
}
