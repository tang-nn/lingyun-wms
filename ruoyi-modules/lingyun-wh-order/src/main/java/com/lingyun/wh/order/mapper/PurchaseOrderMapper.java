package com.lingyun.wh.order.mapper;

import com.lingyun.wh.order.api.domain.PurchaseDetails;
import com.lingyun.wh.order.api.domain.PurchaseOrder;
import com.lingyun.wh.order.pojo.vo.PurchaseOrderVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author : Tang
 * @Description : 进货订单Mapper接口
 * @CreateDate : 2023/11/21 17:24
 */
public interface PurchaseOrderMapper {
    /**
     * 查询进货订单
     *
     * @param poId 进货订单主键
     * @return 进货订单
     */
    public PurchaseOrder selectPurchaseOrderByPoId(String poId);

    /**
     * 根据进货单 ID 查询进货明细
     *
     * @param poid 进货单 ID
     * @return 进货订单
     */
    List<PurchaseDetails> selectOrderDetailsListById(@Param("poid") String poid, @Param("params") Map<String, Object> params);

    /**
     * 查询进货订单列表
     *
     * @param params 进货订单
     * @return 进货订单集合
     */
    public List<PurchaseOrderVo> selectPurchaseDetailsList(@Param("params") Map<String, Object> params);

    /**
     * 新增进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 修改进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder);

    boolean queryInboundCompleted(@Param("pid") String pid);

    /**
     * 删除进货订单
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPoId(String poId);

    /**
     * 批量删除进货订单
     *
     * @param poIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPoIds(String[] poIds);

    /**
     * 批量删除进货明细 - [逻辑删除]
     *
     * @param poIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseDetailsByPoIds(String[] poIds);

    /**
     * 批量新增进货明细 - [逻辑删除]
     *
     * @param purchaseDetailsList 进货明细列表
     * @return 结果
     */
    public int batchPurchaseDetails(List<PurchaseDetails> purchaseDetailsList);


    /**
     * 通过进货订单主键删除进货明细信息
     *
     * @param poId 进货订单ID
     * @return 结果
     */
    public int deletePurchaseDetailsByPoId(String poId);

    /**
     * 用于入库审核查询
     *
     * @param poId
     * @param dids
     * @return
     */
    PurchaseOrder selectPurchaseInfoByIds(@Param("poId") String poId, @Param("dids") String[] dids);

    /**
     * 更新入库数量
     * @return
     */
    int updateInboundInStock(@Param("pd") PurchaseDetails pd);
}
