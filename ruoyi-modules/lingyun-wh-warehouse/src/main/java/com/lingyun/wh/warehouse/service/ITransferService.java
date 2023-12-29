package com.lingyun.wh.warehouse.service;

import java.util.List;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.Transfer;
import org.apache.ibatis.annotations.Param;

/**
 * 调拨单Service接口
 * 
 * @author LIJin
 * @date 2023-12-18
 */
public interface ITransferService 
{
    /**
     * 查询调拨单
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
     * 新增调拨单
     * 
     * @param transfer 调拨单
     * @return 结果
     */
    public int insertTransfer(Transfer transfer);

    /*根据调拨仓库id查询所有库位*/

    public List<StorageLocation> location(String w_id);

    /*查询调入仓库货品库存

     */
    public int InItemQuantity(String indWid,String g_id);


//    // 查询调入仓库某库位下的某货品货品计划数量
//    public int numberPlansIn( String w_id, String sl_id, String g_id);

//    /*change调拨goods的计划数量*/直接吊dao层
//    public int changePlanNums(String quantity, String sl_id, String g_id);



//    //查询调入仓库货品的库位(调入仓库id 货品id)
//    public List<StorageLocation> getLocation( String w_id, String g_id);


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
     * @param tids 需要删除的调拨单主键集合
     * @return 结果
     */
    public int deleteTransferByTids(String[] tids);

    public int deleteTransferDetailsByTdIds(String[]tdIds);



    //审核调拨单
    public int reviewInventory(Transfer transfer);

}
