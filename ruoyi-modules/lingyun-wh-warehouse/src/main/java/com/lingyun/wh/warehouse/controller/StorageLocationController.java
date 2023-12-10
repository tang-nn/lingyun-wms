package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.service.IStorageLocationService;
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
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 库位信息Controller
 * 
 * @author lijin
 * @date 2023-12-07
 */
@RestController
@RequestMapping("/location")
public class StorageLocationController extends BaseController
{
    @Autowired
    private IStorageLocationService storageLocationService;

    /**
     * 查询库位信息列表
     */
    @RequiresPermissions("system:location:list")
    @GetMapping("/list")
    public TableDataInfo list(StorageLocation storageLocation)
    {
        startPage();
        List<StorageLocation> list = storageLocationService.selectStorageLocationList(storageLocation);
        return getDataTable(list);
    }

    /**
     * 导出库位信息列表
     */
    @RequiresPermissions("system:location:export")
    @Log(title = "库位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StorageLocation storageLocation)
    {
        List<StorageLocation> list = storageLocationService.selectStorageLocationList(storageLocation);
        ExcelUtil<StorageLocation> util = new ExcelUtil<StorageLocation>(StorageLocation.class);
        util.exportExcel(response, list, "库位信息数据");
    }

    /**
     * 获取库位信息详细信息
     */
    @RequiresPermissions("system:location:query")
    @GetMapping(value = "/{slId}")
    public AjaxResult getInfo(@PathVariable("slId") String slId)
    {
        return success(storageLocationService.selectStorageLocationBySlId(slId));
    }

    /**
     * 新增库位信息
     */
    @RequiresPermissions("system:location:add")
    @Log(title = "库位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StorageLocation storageLocation)
    {
        return toAjax(storageLocationService.insertStorageLocation(storageLocation));
    }

    /**
     * 修改库位信息
     */
    @RequiresPermissions("system:location:edit")
    @Log(title = "库位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StorageLocation storageLocation)
    {
        return toAjax(storageLocationService.updateStorageLocation(storageLocation));
    }

    /**
     * 删除库位信息
     */
    @RequiresPermissions("system:location:remove")
    @Log(title = "库位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{slIds}")
    public AjaxResult remove(@PathVariable String[] slIds)
    {
        return toAjax(storageLocationService.deleteStorageLocationBySlIds(slIds));
    }
}
