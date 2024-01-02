package com.lingyun.wh.warehouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.lingyun.wh.warehouse.domain.StorageLocation;
import com.lingyun.wh.warehouse.domain.WareHouse;
import com.lingyun.wh.warehouse.service.IStorageLocationService;
import com.lingyun.wh.warehouse.service.IWareHouseService;
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
 * 仓库Controller
 *
 * @author lijin
 * @date 2023-12-07
 */
@RestController
@RequestMapping("/warehouse")
public class WareHouseController extends BaseController {
    @Autowired
    private IWareHouseService wareHouseService;
    @Autowired
    private IStorageLocationService iStorageLocationService;


    /**
     * 查询仓库列表
     */
    @RequiresPermissions("wh:house:manager")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String, Object> map) {
        startPage();
        List<Map<String, Object>> list = wareHouseService.selectWareHouseList(map);
        return getDataTable(list);
    }

    /**
     * 查询仓库，主要用于下拉框
     */
    @RequiresPermissions("system:warehouse:lists")
    @GetMapping("/lists")
    public List<WareHouse> lists() {
        return wareHouseService.lists();
    }

    /**
     * 查询仓库下的仓位列表
     */
    @GetMapping("/storagelist")
    public TableDataInfo storageList(@RequestParam Map<String, Object> map) {
        System.out.println("map======" + map);
        List<StorageLocation> storageLocations = wareHouseService.selectStorageListfindByWid(map);
        System.out.println("storageLocations=====" + storageLocations);
        startPage();
        return getDataTable(storageLocations);
    }

    /**
     * 导出仓库列表
     */
    @RequiresPermissions("system:warehouse:export")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WareHouse wareHouse) {
        List<WareHouse> list = new ArrayList<>();
        ExcelUtil<WareHouse> util = new ExcelUtil<WareHouse>(WareHouse.class);
        util.exportExcel(response, list, "仓库数据");
    }

    /**
     * 获取仓库详细信息
     */
    @RequiresPermissions("wh:house:details")
    @GetMapping(value = "/{wId}")
    public AjaxResult getInfo(@PathVariable("wId") String wId) {
        return success(wareHouseService.selectWareHouseByWId(wId));
    }

    /**
     * 获取仓库详细信息(修改回显数据)
     */
    @RequiresPermissions("wh:house:details")
    @GetMapping(value = "/getby/{wId}")
    public AjaxResult getInfoUpdate(@PathVariable("wId") String wId)
    {

        System.out.println("qqqqqqqq"+wareHouseService.selectWareHouseByWIdupdate(wId));
        return success(wareHouseService.selectWareHouseByWIdupdate(wId));
    }

    /**
     * 新增仓库
     */
    @RequiresPermissions("system:warehouse:add")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WareHouse wareHouse) {
        return toAjax(wareHouseService.insertWareHouse(wareHouse));
    }

    /**
     * 修改仓库
     */
    @RequiresPermissions("system:warehouse:edit")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WareHouse wareHouse) {
        return toAjax(wareHouseService.updateWareHouse(wareHouse));
    }

    /**
     * 删除库位
     * @param slIds
     * @return
     */
    @DeleteMapping("/location/{slIds}")
    public AjaxResult removeLocation(@PathVariable String[] slIds)
    {
        return toAjax(iStorageLocationService.deleteStorageLocationBySlIds(slIds));
    }

    /**
     * 删除仓库和旗下的库位
     */
    @RequiresPermissions("wh:house:delete")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{wIds}")
    public AjaxResult remove(@PathVariable String[] wIds) {
        toAjax(wareHouseService.deleteStorageLocationByWIds(wIds));
        return toAjax(wareHouseService.deleteWareHouseByWIds(wIds));
    }


    /**
     * 修改仓库状态
     */

    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult editStatus(@RequestBody Map<String, Object> map) {
        System.out.println("map===" + map);
        return toAjax(wareHouseService.changeStatus(map));
    }


//    首页数据的查询
    @Log(title = "首页-库存数量占比", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/stockNumber")
    public AjaxResult stockNumber()
    {
        List<Map<String, Object>> list = wareHouseService.StockNumber();
        return success(list);
    }

    @Log(title = "首页-库存金额占比", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/stockPrice")
    public AjaxResult stockPrice()
    {
        List<Map<String, Object>> list = wareHouseService.StockPrice();
        return success(list);
    }
    @Log(title = "首页-入库统计", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/ibStatistics")
    public AjaxResult ibStatistics()
    {
        List<Map<String, Object>> list = wareHouseService.inboundStatistics();
        return success(list);
    }

    @Log(title = "首页-出库统计", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/obStatistics")
    public AjaxResult obStatistics()
    {
        List<Map<String, Object>> list = wareHouseService.outBoundStatistics();
        return success(list);
    }

    @Log(title = "首页-入库排名", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/ibRank")
    public AjaxResult ibRank()
    {
        List<Map<String, Object>> list = wareHouseService.inboundRank();
        return success(list);
    }

    @Log(title = "首页-出库排名", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/obRank")
    public AjaxResult obRank()
    {
        List<Map<String, Object>> list = wareHouseService.outboundRank();
        return success(list);
    }
    @Log(title = "首页-今日概况（入库）", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/itdetails")
    public AjaxResult itdetails()
    {
        Map<String, Object> list = wareHouseService.inTodaydetails();
        return success(list);
    }

    @Log(title = "首页-今日概况（入库）", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/otdetails")
    public AjaxResult otdetails()
    {
        Map<String, Object> list = wareHouseService.outTodaydetails();
        return success(list);
    }

    @Log(title = "首页-昨日概况（入库）", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/iydetails")
    public AjaxResult iydetails()
    {
        Map<String, Object> list = wareHouseService.inYesterdaydetails();
        return success(list);
    }

    @Log(title = "首页-昨日概况（入库）", businessType = BusinessType.OTHER)
    @RequiresPermissions("wh:house:home")
    @GetMapping("/oydetails")
    public AjaxResult oydetails()
    {
        Map<String, Object> list = wareHouseService.outYesterdaydetails();
        return success(list);
    }
}
