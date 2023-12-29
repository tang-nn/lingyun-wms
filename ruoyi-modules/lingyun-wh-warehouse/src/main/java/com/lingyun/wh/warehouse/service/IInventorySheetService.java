package com.lingyun.wh.warehouse.service;

import java.util.List;
import java.util.Map;

import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.domain.InventorySheet;
import org.apache.ibatis.annotations.Param;

/**
 * 盘点单Service接口
 * 
 * @author LiJin
 * @date 2023-12-13
 */
public interface IInventorySheetService 
{
    /**
     * 查询盘点单
     * 
     * @param isId 盘点单主键
     * @return 盘点单
     */
    public InventorySheet selectInventorySheetByIsId(String isId);

    /**
     * 查询盘点单列表
     *
     * @return 盘点单集合
     */
    public List<InventorySheet> selectInventorySheetList(Map<String,Object>map);


    /**
     * 根据仓库查询下面的盘点货品
     * @return
     */
    public  List<Map<String,Object>> selectInventorySheetByWid(Map<String,Object> map);

    /**
     * 新增盘点单
     *
     * @param inventorySheet 盘点单
     * @return 结果
     */
    public int insertInventorySheet(InventorySheet inventorySheet);



    /**
     * 修改盘点单
     * 
     * @param inventorySheet 盘点单
     * @return 结果
     */
    public int updateInventorySheet(InventorySheet inventorySheet);

    /**
     * 批量删除盘点单
     * 
     * @param isIds 需要删除的盘点单主键集合
     * @return 结果
     */
    public int deleteInventorySheetByIsIds(String[] isIds);


    /**
     * 批量删除盘点明细
     *
     * @param isIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryDetailsByIsIds(String[] isIds);

    /**
     * 删除盘点单信息
     *
     * @param isId 盘点单主键
     * @return 结果
     */
    public int deleteInventoryDetailsByIsId(String isId);

    //审核盘点单
    public int reviewInventory(InventorySheet inventorySheet);

}
