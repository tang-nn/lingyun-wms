package com.lingyun.wh.warehouse.service;

import java.util.List;
import com.lingyun.wh.warehouse.domain.InventoryCount;

/**
 * 盘点单Service接口
 * 
 * @author Tang
 * @date 2023-11-22
 */
public interface IInventoryCountService 
{
    /**
     * 查询盘点单
     * 
     * @param isId 盘点单主键
     * @return 盘点单
     */
    public InventoryCount selectInventoryCountByIsId(String isId);

    /**
     * 查询盘点单列表
     * 
     * @param inventoryCount 盘点单
     * @return 盘点单集合
     */
    public List<InventoryCount> selectInventoryCountList(InventoryCount inventoryCount);

    /**
     * 新增盘点单
     * 
     * @param inventoryCount 盘点单
     * @return 结果
     */
    public int insertInventoryCount(InventoryCount inventoryCount);

    /**
     * 修改盘点单
     * 
     * @param inventoryCount 盘点单
     * @return 结果
     */
    public int updateInventoryCount(InventoryCount inventoryCount);

    /**
     * 批量删除盘点单
     * 
     * @param isIds 需要删除的盘点单主键集合
     * @return 结果
     */
    public int deleteInventoryCountByIsIds(String[] isIds);

    /**
     * 删除盘点单信息
     * 
     * @param isId 盘点单主键
     * @return 结果
     */
    public int deleteInventoryCountByIsId(String isId);
}
