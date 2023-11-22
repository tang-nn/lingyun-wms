package com.lingyun.wh.warehouse.mapper;

import java.util.List;
import com.lingyun.wh.warehouse.domain.InventoryCount;
import com.lingyun.wh.warehouse.domain.InventoryDetails;

/**
 * 盘点单Mapper接口
 * 
 * @author Tang
 * @date 2023-11-22
 */
public interface InventoryCountMapper 
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
     * 删除盘点单
     * 
     * @param isId 盘点单主键
     * @return 结果
     */
    public int deleteInventoryCountByIsId(String isId);

    /**
     * 批量删除盘点单
     * 
     * @param isIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryCountByIsIds(String[] isIds);

    /**
     * 批量删除盘点明细
     * 
     * @param isIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryDetailsByIsIds(String[] isIds);
    
    /**
     * 批量新增盘点明细
     * 
     * @param inventoryDetailsList 盘点明细列表
     * @return 结果
     */
    public int batchInventoryDetails(List<InventoryDetails> inventoryDetailsList);
    

    /**
     * 通过盘点单主键删除盘点明细信息
     * 
     * @param isId 盘点单ID
     * @return 结果
     */
    public int deleteInventoryDetailsByIsId(String isId);
}
