package com.lingyun.wh.warehouse.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lingyun.wh.warehouse.mapper.InventoryDetailsMapper;
import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.service.IInventoryDetailsService;

/**
 * 盘点明细Service业务层处理
 * 
 * @author LiJin
 * @date 2023-12-13
 */
@Service
public class InventoryDetailsServiceImpl implements IInventoryDetailsService 
{
    @Autowired
    private InventoryDetailsMapper inventoryDetailsMapper;

    /**
     * 查询盘点明细
     * 
     * @param isdId 盘点明细主键
     * @return 盘点明细
     */
    @Override
    public InventoryDetails selectInventoryDetailsByIsdId(String isdId)
    {
        return inventoryDetailsMapper.selectInventoryDetailsByIsdId(isdId);
    }

    /**
     * 查询盘点明细列表
     * 
     * @param inventoryDetails 盘点明细
     * @return 盘点明细
     */
    @Override
    public List<InventoryDetails> selectInventoryDetailsList(InventoryDetails inventoryDetails)
    {
        return inventoryDetailsMapper.selectInventoryDetailsList(inventoryDetails);
    }

    /**
     * 新增盘点明细
     * 
     * @param inventoryDetails 盘点明细
     * @return 结果
     */
    @Override
    public int insertInventoryDetails(InventoryDetails inventoryDetails)
    {
        inventoryDetails.setCreateTime(DateUtils.getNowDate());
        return inventoryDetailsMapper.insertInventoryDetails(inventoryDetails);
    }

    /**
     * 修改盘点明细
     * 
     * @param inventoryDetails 盘点明细
     * @return 结果
     */
    @Override
    public int updateInventoryDetails(InventoryDetails inventoryDetails)
    {
        inventoryDetails.setUpdateTime(DateUtils.getNowDate());
        return inventoryDetailsMapper.updateInventoryDetails(inventoryDetails);
    }

    /**
     * 批量删除盘点明细
     * 
     * @param isdIds 需要删除的盘点明细主键
     * @return 结果
     */
    @Override
    public int deleteInventoryDetailsByIsdIds(String[] isdIds)
    {
        return inventoryDetailsMapper.deleteInventoryDetailsByIsdIds(isdIds);
    }

    /**
     * 删除盘点明细信息
     * 
     * @param isdId 盘点明细主键
     * @return 结果
     */
    @Override
    public int deleteInventoryDetailsByIsdId(String isdId)
    {
        return inventoryDetailsMapper.deleteInventoryDetailsByIsdId(isdId);
    }
}
