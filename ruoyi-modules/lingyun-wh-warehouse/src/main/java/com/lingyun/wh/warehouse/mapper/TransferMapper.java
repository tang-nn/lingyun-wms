package com.lingyun.wh.warehouse.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lingyun.wh.warehouse.domain.InventorySheet;
import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.domain.TransferDetails;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * 调拨单Mapper接口
 * 
 * @author LIJin
 * @date 2023-12-18
 */
public interface TransferMapper 
{
    /**
     * 根据tid查询调拨单
     * 
     * @param tid 调拨单主键
     * @return 调拨单
     */
    public Transfer transferByTid(String tid);

    /**
     * 查询调拨单列表
     * 
     * @param transfer 调拨单
     * @return 调拨单集合
     */
    public List<Transfer> selectTransferList(Transfer transfer);

    /**
     * 调拨出入库，订单查询
     *
     * @param map
     * @return
     */
    List<Transfer> getTransferList(@Param("map") Map<String, Object> map);

    /**
     * 调拨出入库，订单明细查询
     *
     * @param params
     * @return
     */
    List<TransferDetails> getTransferDetails(@Param("params") Map<String, Object> params);

    /**
     * 新增调拨单
     * 
     * @param transfer 调拨单
     * @return 结果
     */
    public int insertTransfer(Transfer transfer);

    /**
     * 批量新增调拨明细
     *
     * @param transferDetailsList 调拨明细列表
     * @return 结果
     */
    public int batchTransferDetails(List<TransferDetails> transferDetailsList);


    /*根据调拨仓库id查询所有库位*/

    public List<StorageLocation> location(String w_id);

    /*查询调入仓库货品库存

     */
    public int InItemQuantity(@Param("w_id") String w_id, @Param("g_id") String g_id);


//    // 查询调入仓库某库位下的某货品货品计划数量
//    public int numberPlansIn(@Param("w_id") String w_id, @Param("sl_id")String sl_id,@Param("g_id") String g_id);

    /*change调拨goods的计划数量*/
    public int changePlanNums(@Param("quantity") BigDecimal quantity, @Param("w_id") String w_id, @Param("sl_id") String sl_id, @Param("g_id") String g_id);


    //修改调拨明细单的调拨数量时需要查出修改前调出仓库对应的库位的货品的计划数量和调拨数量
    public List<Map<String,Object>> getPlansAndTquantity(Map<String,Object>map);



//    //查询调入仓库货品的库位(调入仓库id 货品id)注释的原因是因为数据库加了调拨入库库位字段(in_sl_id)
//    public  List<StorageLocation> getLocation(@Param("w_id") String w_id,@Param("g_id") String g_id);



    /**
     * 批量删除调拨单
     *
     * @param tids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTransferByTids(String[] tids);

    /**
     * 通过调拨单主键批量删除调拨明细
     *
     * @param tids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTransferDetailsByTIds(String[] tids);
    /**
     * 通过调拨明细单主键批量删除调拨明细*/
    public int deleteTransferDetailsByTdIds(String[]tdIds);

    /**
     * 修改调拨单
     * 
     * @param transfer 调拨单
     * @return 结果
     */
    public int updateTransfer(Transfer transfer);

    /**
     * 批量修改调拨明细单
     * @param transferDetails
     * @return
     */
    public int updateTransferDetailsList(@Param("transferDetails") List<TransferDetails>transferDetails);
    /**
     * 更新调拨明细，的出入库数量
     *
     * @param transferDetails 调拨明细
     * @return 结果
     */
    public int updateTransferDetails(TransferDetails transferDetails);


    //审核调拨单
    public int reviewInventory(Transfer transfer);

    /**
     * 查询指定调拨单出入库状态
     *
     * @param tfId 调拨单 id
     * @return
     */
    Map<String, Boolean> getOutInboundStatus(String tfId);
}
