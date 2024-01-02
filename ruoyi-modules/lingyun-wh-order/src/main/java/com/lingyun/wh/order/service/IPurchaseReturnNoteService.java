package com.lingyun.wh.order.service;


import com.lingyun.wh.order.api.domain.PurchaseReturnNote;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货退货Service接口
 * @CreateDate : 2023/11/21 23:23
 */
public interface IPurchaseReturnNoteService {

    /**
     * 查询进货退货
     *
     * @param prId 进货退货主键
     * @return 进货退货
     */
    public PurchaseReturnNote selectPurchaseReturnNoteByPrId(String prId);

    /**
     * 查询进货退货列表
     *
     * @param purchaseReturnNote 进货退货
     * @return 进货退货集合
     */
    public List<PurchaseReturnNote> selectPurchaseReturnNoteList(PurchaseReturnNote purchaseReturnNote);

    /**
     * 新增进货退货
     *
     * @param purchaseReturnNote 进货退货
     * @return 结果
     */
    public int insertPurchaseReturnNote(PurchaseReturnNote purchaseReturnNote);

    /**
     * 修改进货退货
     *
     * @param purchaseReturnNote 进货退货
     * @return 结果
     */
    public int updatePurchaseReturnNote(PurchaseReturnNote purchaseReturnNote);

    /**
     * 批量删除进货退货
     *
     * @param prIds 需要删除的进货退货主键集合
     * @return 结果
     */
    public int deletePurchaseReturnNoteByPrIds(String[] prIds);

    /**
     * 删除进货退货信息
     *
     * @param prId 进货退货主键
     * @return 结果
     */
    public int deletePurchaseReturnNoteByPrId(String prId);

}
