package com.lingyun.wh.warehouse.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.mapper.InventoryCountMapper;
import com.lingyun.wh.warehouse.domain.InventoryCount;
import com.lingyun.wh.warehouse.service.IInventoryCountService;

/**
 * 盘点单Service业务层处理
 * 
 * @author Tang
 * @date 2023-11-22
 */
@Service
public class InventoryCountServiceImpl implements IInventoryCountService 
{
    @Autowired
    private InventoryCountMapper inventoryCountMapper;

    /**
     * 查询盘点单
     * 
     * @param isId 盘点单主键
     * @return 盘点单
     */
    @Override
    public InventoryCount selectInventoryCountByIsId(String isId)
    {
        return inventoryCountMapper.selectInventoryCountByIsId(isId);
    }

    /**
     * 查询盘点单列表
     * 
     * @param inventoryCount 盘点单
     * @return 盘点单
     */
    @Override
    public List<InventoryCount> selectInventoryCountList(InventoryCount inventoryCount)
    {
        return inventoryCountMapper.selectInventoryCountList(inventoryCount);
    }

    /**
     * 新增盘点单
     * 
     * @param inventoryCount 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertInventoryCount(InventoryCount inventoryCount)
    {
        inventoryCount.setCreateTime(DateUtils.getNowDate());
        int rows = inventoryCountMapper.insertInventoryCount(inventoryCount);
        insertInventoryDetails(inventoryCount);
        return rows;
    }

    /**
     * 修改盘点单
     * 
     * @param inventoryCount 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateInventoryCount(InventoryCount inventoryCount)
    {
        inventoryCount.setUpdateTime(DateUtils.getNowDate());
        inventoryCountMapper.deleteInventoryDetailsByIsId(inventoryCount.getIsId());
        insertInventoryDetails(inventoryCount);
        return inventoryCountMapper.updateInventoryCount(inventoryCount);
    }

    /**
     * 批量删除盘点单
     * 
     * @param isIds 需要删除的盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInventoryCountByIsIds(String[] isIds)
    {
        inventoryCountMapper.deleteInventoryDetailsByIsIds(isIds);
        return inventoryCountMapper.deleteInventoryCountByIsIds(isIds);
    }

    /**
     * 删除盘点单信息
     * 
     * @param isId 盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInventoryCountByIsId(String isId)
    {
        inventoryCountMapper.deleteInventoryDetailsByIsId(isId);
        return inventoryCountMapper.deleteInventoryCountByIsId(isId);
    }

    /**
     * 新增盘点明细信息
     * 
     * @param inventoryCount 盘点单对象
     */
    public void insertInventoryDetails(InventoryCount inventoryCount)
    {
        List<InventoryDetails> inventoryDetailsList = inventoryCount.getInventoryDetailsList();
        String isId = inventoryCount.getIsId();
        if (StringUtils.isNotNull(inventoryDetailsList))
        {
            List<InventoryDetails> list = new ArrayList<InventoryDetails>();
            for (InventoryDetails inventoryDetails : inventoryDetailsList)
            {
                inventoryDetails.setIsId(isId);
                list.add(inventoryDetails);
            }
            if (list.size() > 0)
            {
                inventoryCountMapper.batchInventoryDetails(list);
            }
        }
    }
}
