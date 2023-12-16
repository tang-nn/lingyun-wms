package com.lingyun.wh.warehouse.service.impl;

import java.util.List;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.WareHouse;
import com.lingyun.wh.warehouse.mapper.WareHouseMapper;
import com.lingyun.wh.warehouse.service.IWareHouseService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仓库Service业务层处理
 * 
 * @author lijin
 * @date 2023-12-07
 */
@Service
public class WareHouseServiceImpl implements IWareHouseService
{
    @Autowired
    private WareHouseMapper wareHouseMapper;

    /**
     * 查询仓库
     * 
     * @param wId 仓库主键
     * @return 仓库
     */
    @Override
    public WareHouse selectWareHouseByWId(String wId)
    {
        return wareHouseMapper.selectWareHouseByWId(wId);
    }

    @Override
    public List<WareHouse> lists() {
        return wareHouseMapper.lists();
    }

    /**
     * 查询仓库列表
     *
     * @return 仓库
     */
    @Override
    public  List<Map<String,Object>> selectWareHouseList(Map<String,Object>map)
    {
        return wareHouseMapper.selectWareHouseList(map);
    }

    @Override
    public int changeStatus(Map<String, Object> map) {
        return wareHouseMapper.changeStatus(map);
    }

    /**
     * 新增仓库
     * 
     * @param wareHouse 仓库
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWareHouse(WareHouse wareHouse)
    {
        wareHouse.setCreateTime(DateUtils.getNowDate());
        int rows = wareHouseMapper.insertWareHouse(wareHouse);
        insertStorageLocation(wareHouse);
        return rows;
    }

    /**
     * 修改仓库
     * 
     * @param wareHouse 仓库
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWareHouse(WareHouse wareHouse)
    {
        wareHouse.setUpdateTime(DateUtils.getNowDate());
        wareHouseMapper.deleteStorageLocationByWId(wareHouse.getwId());
        insertStorageLocation(wareHouse);
        return wareHouseMapper.updateWareHouse(wareHouse);
    }

    /**
     * 批量删除仓库
     * 
     * @param wIds 需要删除的仓库主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWareHouseByWIds(String[] wIds)
    {
        wareHouseMapper.deleteStorageLocationByWIds(wIds);
        return wareHouseMapper.deleteWareHouseByWIds(wIds);
    }

    /**
     * 删除仓库信息
     * 
     * @param wId 仓库主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWareHouseByWId(String wId)
    {
        wareHouseMapper.deleteStorageLocationByWId(wId);
        return wareHouseMapper.deleteWareHouseByWId(wId);
    }

    /**
     * 新增库位信息信息
     * 
     * @param wareHouse 仓库对象
     */
    public void insertStorageLocation(WareHouse wareHouse)
    {
        List<StorageLocation> storageLocationList = wareHouse.getStorageLocationList();
        String wId = wareHouse.getwId();
        if (StringUtils.isNotNull(storageLocationList))
        {
            List<StorageLocation> list = new ArrayList<StorageLocation>();
            for (StorageLocation storageLocation : storageLocationList)
            {
                storageLocation.setwId(wId);
                list.add(storageLocation);
            }
            if (list.size() > 0)
            {
                wareHouseMapper.batchStorageLocation(list);
            }
        }
    }
}
