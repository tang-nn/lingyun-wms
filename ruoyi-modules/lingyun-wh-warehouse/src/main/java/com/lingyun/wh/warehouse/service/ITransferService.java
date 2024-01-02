package com.lingyun.wh.warehouse.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.domain.TransferDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调拨单Service接口
 *
 * @author LIJin
 * @date 2023-12-18
 */
public interface ITransferService {
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
     * 调拨出入库，订单查询
     *
     * @param map
     * @return
     */
    List<Transfer> getTransferList(Map<String, Object> map);

    /**
     * 调拨出入库查询，订单查询 by id
     *
     * @param tfId   调拨 id
     * @param params 调拨明细过滤参数
     * @return 调拨单和明细
     */
    Transfer getTransferById(String tfId, Map<String, Object> params);

    /**
     * 调拨出入库，订单明细查询
     *
     * @param map
     * @return
     */
    List<TransferDetails> getTransferDetails(Map<String, Object> map);

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
    public int InItemQuantity(String indWid, String g_id);


//    // 查询调入仓库某库位下的某货品货品计划数量
//    public int numberPlansIn( String w_id, String sl_id, String g_id);

//    /*change调拨goods的计划数量*/直接吊dao层
//    public int changePlanNums(String quantity, String sl_id, String g_id);


    // 查询调入仓库货品的库位(调入仓库id 货品id)
    public List<StorageLocation> getLocation(String w_id, String g_id);


    /**
     * 修改调拨单
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateTransfer(Transfer transfer);

    /**
     * 批量删除调拨单
     *
     * @param tids 需要删除的调拨单主键集合
     * @return 结果
     */
    public int deleteTransferByTids(String[] tids);

    /**
     * 修改调拨单出入库状态、及明细中的出入库数量
     *
     * @param transfer 调拨单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTransferInOutbound(Transfer transfer);

    /**
     * 查询指定调拨单出入库状态
     *
     * @param tfId 调拨单 id
     * @return
     */
    Map<String, Boolean> getOutInboundStatus(String tfId);
}
