package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.service.IStorageLocationService;
import com.lingyun.wh.warehouse.service.IWareHouseService;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private IWareHouseService iWareHouseService;
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
     * 查询某个仓库下的库位列表 - 用于下拉框
     */
    @RequiresPermissions("system:location:list")
    @GetMapping("/list/wid/{wid}")
    public AjaxResult listByWid(@PathVariable String wid)
    {
        if(!StringUtils.isNumericSpace(wid)){
            return AjaxResult.error("仓库编号错误");
        }
        StorageLocation sl = new StorageLocation();
        sl.setwId(wid);
        List<StorageLocation> list = storageLocationService.selectStorageLocationList(sl);
        return success(list);
    }
    /**
     * 导出库位信息列表
     */
    @RequiresPermissions("system:location:export")
    @Log(title = "库位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestParam Map<String,Object>map)
    {
        System.out.println("exportMap===="+map);
        List<StorageLocation> list = iWareHouseService.selectStorageListfindByWid(map);
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
     * 修改库位状态
     */

    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping("/changeLocationStatus")
    public AjaxResult editStatus(@RequestBody Map<String,Object> map)
    {
        System.out.println("changeLocationStatus==map==="+map);
        return toAjax(storageLocationService.changeLocationStatus(map));
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
