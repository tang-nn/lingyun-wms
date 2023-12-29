package com.lingyun.wh.warehouse.service.impl;


import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.mapper.StorageLocationMapper;
import com.lingyun.wh.warehouse.service.IStorageLocationService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 库位信息Service业务层处理
 * 
 * @author lijin
 * @date 2023-12-07
 */
@Service
public class StorageLocationServiceImpl implements IStorageLocationService
{
    @Autowired
    private StorageLocationMapper storageLocationMapper;

    /**
     * 查询库位信息
     * 
     * @param slId 库位信息主键
     * @return 库位信息
     */
    @Override
    public StorageLocation selectStorageLocationBySlId(String slId)
    {
        return storageLocationMapper.selectStorageLocationBySlId(slId);
    }

    /**
     * 查询库位信息列表
     * 
     * @param storageLocation 库位信息
     * @return 库位信息
     */
    @Override
    public List<StorageLocation> selectStorageLocationList(StorageLocation storageLocation)
    {
        return storageLocationMapper.selectStorageLocationList(storageLocation);
    }

    /**
     * 新增库位信息
     * 
     * @param storageLocation 库位信息
     * @return 结果
     */
    @Override
    public int insertStorageLocation(StorageLocation storageLocation)
    {
        storageLocation.setCreateTime(DateUtils.getNowDate());
        return storageLocationMapper.insertStorageLocation(storageLocation);
    }

    /**
     * 修改库位信息
     * 
     * @param storageLocations 库位信息
     * @return 结果
     */
    @Override
    public int updateStorageLocation(String wId, List<StorageLocation>storageLocations)
    {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        for (StorageLocation sl : storageLocations) {
            sl.setUpdateBy(uid);
            sl.setUpdateTime(nowDate);
        }
        return storageLocationMapper.updateStorageLocation(wId,storageLocations);
    }

    @Override
    public int changeLocationStatus(Map<String, Object> map) {
        return storageLocationMapper.changeLocationStatus(map);
    }

    /**
     * 批量删除库位信息
     * 
     * @param slIds 需要删除的库位信息主键
     * @return 结果
     */
    @Override
    public int deleteStorageLocationBySlIds(String[] slIds)
    {
        return storageLocationMapper.deleteStorageLocationBySlIds(slIds);
    }

    /**
     * 删除库位信息信息
     * 
     * @param slId 库位信息主键
     * @return 结果
     */
    @Override
    public int deleteStorageLocationBySlId(String slId)
    {
        return storageLocationMapper.deleteStorageLocationBySlId(slId);
    }
}
