package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.warehouse.domain.InventoryCount;
import com.lingyun.wh.warehouse.service.IInventoryCountService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 盘点单Controller
 * 
 * @author Tang
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/inventory")
public class InventoryCountController extends BaseController
{
    @Autowired
    private IInventoryCountService inventoryCountService;

    /**
     * 查询盘点单列表
     */
    @RequiresPermissions("InventoryCount:inventory:list")
    @GetMapping("/list")
    public TableDataInfo list(InventoryCount inventoryCount)
    {
        startPage();
        List<InventoryCount> list = inventoryCountService.selectInventoryCountList(inventoryCount);
        return getDataTable(list);
    }

    /**
     * 导出盘点单列表
     */
    @RequiresPermissions("InventoryCount:inventory:export")
    @Log(title = "盘点单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventoryCount inventoryCount)
    {
        List<InventoryCount> list = inventoryCountService.selectInventoryCountList(inventoryCount);
        ExcelUtil<InventoryCount> util = new ExcelUtil<InventoryCount>(InventoryCount.class);
        util.exportExcel(response, list, "盘点单数据");
    }

    /**
     * 获取盘点单详细信息
     */
    @RequiresPermissions("InventoryCount:inventory:query")
    @GetMapping(value = "/{isId}")
    public AjaxResult getInfo(@PathVariable("isId") String isId)
    {
        return success(inventoryCountService.selectInventoryCountByIsId(isId));
    }

    /**
     * 新增盘点单
     */
    @RequiresPermissions("InventoryCount:inventory:add")
    @Log(title = "盘点单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InventoryCount inventoryCount)
    {
        return toAjax(inventoryCountService.insertInventoryCount(inventoryCount));
    }

    /**
     * 修改盘点单
     */
    @RequiresPermissions("InventoryCount:inventory:edit")
    @Log(title = "盘点单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventoryCount inventoryCount)
    {
        return toAjax(inventoryCountService.updateInventoryCount(inventoryCount));
    }

    /**
     * 删除盘点单
     */
    @RequiresPermissions("InventoryCount:inventory:remove")
    @Log(title = "盘点单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{isIds}")
    public AjaxResult remove(@PathVariable String[] isIds)
    {
        return toAjax(inventoryCountService.deleteInventoryCountByIsIds(isIds));
    }
}
