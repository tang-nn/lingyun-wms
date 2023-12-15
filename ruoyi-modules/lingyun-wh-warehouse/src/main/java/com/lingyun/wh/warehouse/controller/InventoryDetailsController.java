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
import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.service.IInventoryDetailsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 盘点明细Controller
 * 
 * @author LiJin
 * @date 2023-12-13
 */
@RestController
@RequestMapping("/inventoryDetails")
public class InventoryDetailsController extends BaseController
{
    @Autowired
    private IInventoryDetailsService inventoryDetailsService;

    /**
     * 查询盘点明细列表
     */
    @RequiresPermissions("inventory:inventory:list")
    @GetMapping("/list")
    public TableDataInfo list(InventoryDetails inventoryDetails)
    {
        startPage();
        List<InventoryDetails> list = inventoryDetailsService.selectInventoryDetailsList(inventoryDetails);
        return getDataTable(list);
    }

    /**
     * 导出盘点明细列表
     */
    @RequiresPermissions("inventory:inventory:export")
    @Log(title = "盘点明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventoryDetails inventoryDetails)
    {
        List<InventoryDetails> list = inventoryDetailsService.selectInventoryDetailsList(inventoryDetails);
        ExcelUtil<InventoryDetails> util = new ExcelUtil<InventoryDetails>(InventoryDetails.class);
        util.exportExcel(response, list, "盘点明细数据");
    }

    /**
     * 获取盘点明细详细信息
     */
    @RequiresPermissions("inventory:inventory:query")
    @GetMapping(value = "/{isdId}")
    public AjaxResult getInfo(@PathVariable("isdId") String isdId)
    {
        return success(inventoryDetailsService.selectInventoryDetailsByIsdId(isdId));
    }

    /**
     * 新增盘点明细
     */
    @RequiresPermissions("inventory:inventory:add")
    @Log(title = "盘点明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InventoryDetails inventoryDetails)
    {
        return toAjax(inventoryDetailsService.insertInventoryDetails(inventoryDetails));
    }

    /**
     * 修改盘点明细
     */
    @RequiresPermissions("inventory:inventory:edit")
    @Log(title = "盘点明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventoryDetails inventoryDetails)
    {
        return toAjax(inventoryDetailsService.updateInventoryDetails(inventoryDetails));
    }

    /**
     * 删除盘点明细
     */
    @RequiresPermissions("inventory:inventory:remove")
    @Log(title = "盘点明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{isdIds}")
    public AjaxResult remove(@PathVariable String[] isdIds)
    {
        return toAjax(inventoryDetailsService.deleteInventoryDetailsByIsdIds(isdIds));
    }
}
