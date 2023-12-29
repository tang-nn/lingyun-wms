package com.lingyun.wh.warehouse.service;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.WareHouse;

import java.util.List;
import java.util.Map;

/**
 * 仓库Service接口
 * 
 * @author lijin
 * @date 2023-12-07
 */
public interface IWareHouseService 
{
    /**
     * 查询仓库
     * 
     * @param wId 仓库主键
     * @return 仓库
     */
    public List<Map<String,Object>> selectWareHouseByWId(String wId);


    /**
     * 查询仓库列表
     * @return 仓库集合
     */
    public List<Map<String,Object>> selectWareHouseList(Map<String,Object>map);


    /**
     * 根据wId查询仓库详情(联查,升级版)
     *
     * @param wId 仓库主键
     * @return 仓库
     */
    public WareHouse selectWareHouseByWIdupdate(String wId);

    /**
     * 修改仓库状态
     * @param map
     * @return
     */
    public int changeStatus(Map<String,Object>map);

    /**
     * 新增仓库
     * 
     * @param wareHouse 仓库
     * @return 结果
     */
    public int insertWareHouse(WareHouse wareHouse);

    /**
     * 修改仓库
     * 
     * @param wareHouse 仓库
     * @return 结果
     */
    public int updateWareHouse(WareHouse wareHouse);

    /**
     * 批量删除仓库
     * 
     * @param wIds 需要删除的仓库主键集合
     * @return 结果
     */
    public int deleteWareHouseByWIds(String[] wIds);

    /**
     * 批量删除库位信息
     *
     * @param wIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStorageLocationByWIds(String[] wIds);

    /**
     * 删除仓库信息
     * 
     * @param wId 仓库主键
     * @return 结果
     */
    public int deleteWareHouseByWId(String wId);

    /**
     * 查询仓库下的库位
     * @param map
     * @return
     */
    public List<StorageLocation>selectStorageListfindByWid(Map<String,Object>map);

    /**
     * 查询仓库
     */
    public List<WareHouse> lists();


    //首页显示数据

//    1.库存数量占比

    public List<Map<String,Object>> StockNumber();
    //    2.库存金额占比
    public List<Map<String,Object>> StockPrice();

    //    3.入库统计
    public List<Map<String,Object>> inboundStatistics();

    //    4.出库统计
    public List<Map<String,Object>> outBoundStatistics();

    //5.入库排名
    public List<Map<String,Object>> inboundRank();

    //6.出库排名
    public List<Map<String,Object>> outboundRank();

    //今日概况（入库）
    public Map<String,Object> inTodaydetails();
    //    (出库)
    public Map<String,Object> outTodaydetails();



    //昨日概况（入库）
    public Map<String,Object> inYesterdaydetails();
    //    (出库)
    public Map<String,Object> outYesterdaydetails();
}
