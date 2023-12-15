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
}
