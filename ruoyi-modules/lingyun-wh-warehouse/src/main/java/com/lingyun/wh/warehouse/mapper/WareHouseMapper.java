package com.lingyun.wh.warehouse.mapper;

import java.util.List;
import java.util.Map;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.WareHouse;

/**
 * 仓库Mapper接口
 * 
 * @author lijin
 * @date 2023-12-07
 */
public interface WareHouseMapper 
{
    /**
     * 根据wId查询仓库详情details(联查没有封装到对象，导致修改回显值不好操作)
     * 
     * @param wId 仓库主键
     * @return 仓库
     */

    public List<Map<String,Object>> selectWareHouseByWId(String wId);

    /**
     * 根据wId查询仓库详情(修改回显数据,联查,升级版)
     *
     * @param wId 仓库主键
     * @return 仓库
     */
    public WareHouse selectWareHouseByWIdupdate(String wId);


    /**
     * 查询仓库列表
     *
     * @return 仓库集合
     */
    public List<Map<String,Object>> selectWareHouseList(Map<String,Object>map);

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
     * 修改仓库状态
     * @param map
     * @return
     */
    public int changeStatus(Map<String,Object>map);
    /**
     * 删除仓库
     *
     * @param wId 仓库主键
     * @return 结果
     */
    public int deleteWareHouseByWId(String wId);

    /**
     * 批量删除仓库
     * 
     * @param wIds 需要删除的数据主键集合
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
     * 批量新增库位信息
     * 
     * @param storageLocationList 库位信息列表
     * @return 结果
     */
    public int batchStorageLocation(List<StorageLocation> storageLocationList);
    

    /**
     * 通过仓库主键删除库位信息信息
     *
     * @param wId 仓库ID
     * @return 结果
     */
    public int deleteStorageLocationByWId(String wId);


    /**
     * 查询仓库下的库位
     * @param map
     * @return
     */
    public List<StorageLocation>selectStorageListfindByWid(Map<String,Object> map);

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
