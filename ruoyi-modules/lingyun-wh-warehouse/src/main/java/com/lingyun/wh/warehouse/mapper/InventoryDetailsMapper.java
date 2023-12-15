package com.lingyun.wh.warehouse.mapper;

import java.util.List;
import com.lingyun.wh.warehouse.domain.InventoryDetails;

/**
 * 盘点明细Mapper接口
 * 
 * @author LiJin
 * @date 2023-12-13
 */
public interface InventoryDetailsMapper 
{
    /**
     * 查询盘点明细
     * 
     * @param isdId 盘点明细主键
     * @return 盘点明细
     */
    public InventoryDetails selectInventoryDetailsByIsdId(String isdId);

    /**
     * 查询盘点明细列表
     * 
     * @param inventoryDetails 盘点明细
     * @return 盘点明细集合
     */
    public List<InventoryDetails> selectInventoryDetailsList(InventoryDetails inventoryDetails);

    /**
     * 新增盘点明细
     * 
     * @param inventoryDetails 盘点明细
     * @return 结果
     */
    public int insertInventoryDetails(InventoryDetails inventoryDetails);

    /**
     * 修改盘点明细
     * 
     * @param inventoryDetails 盘点明细
     * @return 结果
     */
    public int updateInventoryDetails(InventoryDetails inventoryDetails);

    /**
     * 删除盘点明细
     * 
     * @param isdId 盘点明细主键
     * @return 结果
     */
    public int deleteInventoryDetailsByIsdId(String isdId);

    /**
     * 批量删除盘点明细
     * 
     * @param isdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryDetailsByIsdIds(String[] isdIds);
}
