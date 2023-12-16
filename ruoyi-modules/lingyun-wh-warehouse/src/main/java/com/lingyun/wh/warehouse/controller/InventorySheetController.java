package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.warehouse.domain.InventorySheet;
import com.lingyun.wh.warehouse.service.IInventorySheetService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 盘点单Controller
 * 
 * @author LiJin
 * @date 2023-12-13
 */
@RestController
@RequestMapping("/inventory")
public class InventorySheetController extends BaseController
{
    @Autowired
    private IInventorySheetService inventorySheetService;

    /**
     * 查询盘点单列表
     */
    @RequiresPermissions("InventorySheet:inventory:list")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String,Object>map)
    {
        System.out.println("inventoryListMap==========="+map);
        startPage();
        List<InventorySheet> list = inventorySheetService.selectInventorySheetList(map);
        System.out.println("inventoryList*******************"+list.size());
        return getDataTable(list);
    }

    /**
     * 导出盘点单列表
     */
    @RequiresPermissions("InventorySheet:inventory:export")
    @Log(title = "盘点单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventorySheet inventorySheet)
    {
        //List<InventorySheet> list = inventorySheetService.selectInventorySheetList(inventorySheet);
        ExcelUtil<InventorySheet> util = new ExcelUtil<InventorySheet>(InventorySheet.class);
        util.exportExcel(response, null, "盘点单数据");
    }

    /**
     * 获取盘点单详细信息
     */
    @RequiresPermissions("InventorySheet:inventory:query")
    @GetMapping(value = "/{isId}")
    public AjaxResult getInfo(@PathVariable("isId") String isId)
    {
        return success(inventorySheetService.selectInventorySheetByIsId(isId));
    }

    /**
     * 新增盘点单-盘点明细
     */
    @RequiresPermissions("InventorySheet:inventory:add")
    @Log(title = "盘点单", businessType = BusinessType.INSERT)
    @PostMapping("/inventoryAndDetials")
    public AjaxResult add(@RequestBody InventorySheet inventorySheet)
    {
        System.out.println("inventorySheet================="+inventorySheet);
        return toAjax(inventorySheetService.insertInventorySheet(inventorySheet));
    }

    /**
     * 修改盘点单
     */
    @RequiresPermissions("InventorySheet:inventory:edit")
    @Log(title = "盘点单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventorySheet inventorySheet)
    {
        return toAjax(inventorySheetService.updateInventorySheet(inventorySheet));
    }

    /**
     * 删除盘点单
     */
    @RequiresPermissions("InventorySheet:inventory:remove")
    @Log(title = "盘点单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{isIds}")
    public AjaxResult remove(@PathVariable String[] isIds)
    {
        return toAjax(inventorySheetService.deleteInventorySheetByIsIds(isIds));
    }

    //根据仓库查询下面的盘点货品
    @Log(title = "盘点单", businessType = BusinessType.DELETE)
    @GetMapping("/getByWid")
    public TableDataInfo goodsByWid(@RequestParam Map<String,Object> map)
    {
        System.out.println("goodsByWid map"+map);
        System.out.println("selectInventorySheetByWid "+inventorySheetService.selectInventorySheetByWid(map));
        return getDataTable(inventorySheetService.selectInventorySheetByWid(map));
    }


}
