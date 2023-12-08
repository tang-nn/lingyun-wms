package com.lingyun.wh.warehouse.mapper;

import com.lingyun.wh.warehouse.domain.StorageLocation;

import java.util.List;

/**
 * 库位信息Mapper接口
 * 
 * @author lijin
 * @date 2023-12-07
 */
public interface StorageLocationMapper 
{
    /**
     * 查询库位信息
     * 
     * @param slId 库位信息主键
     * @return 库位信息
     */
    public StorageLocation selectStorageLocationBySlId(String slId);

    /**
     * 查询库位信息列表
     * 
     * @param storageLocation 库位信息
     * @return 库位信息集合
     */
    public List<StorageLocation> selectStorageLocationList(StorageLocation storageLocation);

    /**
     * 新增库位信息
     * 
     * @param storageLocation 库位信息
     * @return 结果
     */
    public int insertStorageLocation(StorageLocation storageLocation);

    /**
     * 修改库位信息
     * 
     * @param storageLocation 库位信息
     * @return 结果
     */
    public int updateStorageLocation(StorageLocation storageLocation);

    /**
     * 删除库位信息
     * 
     * @param slId 库位信息主键
     * @return 结果
     */
    public int deleteStorageLocationBySlId(String slId);

    /**
     * 批量删除库位信息
     * 
     * @param slIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStorageLocationBySlIds(String[] slIds);
}
