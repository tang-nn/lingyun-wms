package com.lingyun.wh.order.mapper;


import com.lingyun.wh.order.api.domain.PurchaseReturnDetails;
import com.lingyun.wh.order.api.domain.PurchaseReturnNote;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货退货Mapper接口
 * @CreateDate : 2023/11/21 20:15
 */
public interface PurchaseReturnNoteMapper {

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
     * 删除进货退货
     *
     * @param prId 进货退货主键
     * @return 结果
     */
    public int deletePurchaseReturnNoteByPrId(String prId);

    /**
     * 批量删除进货退货
     *
     * @param prIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseReturnNoteByPrIds(String[] prIds);

    /**
     * 批量删除进货退货明细
     *
     * @param prIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseReturnDetailsByPrIds(String[] prIds);

    /**
     * 批量新增进货退货明细
     *
     * @param purchaseReturnDetailsList 进货退货明细列表
     * @return 结果
     */
    public int batchPurchaseReturnDetails(List<PurchaseReturnDetails> purchaseReturnDetailsList);


    /**
     * 通过进货退货主键删除进货退货明细信息
     *
     * @param prId 进货退货ID
     * @return 结果
     */
    public int deletePurchaseReturnDetailsByPrId(String prId);

}
