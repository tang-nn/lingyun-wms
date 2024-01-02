package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.nacos.common.utils.MapUtil;
import com.lingyun.wh.warehouse.domain.InventoryDetails;
import com.lingyun.wh.warehouse.mapper.TransferMapper;
import com.lingyun.wh.warehouse.service.ITransferService;
import org.apache.commons.collections4.MapUtils;
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
public class InventorySheetController extends BaseController {
    @Autowired
    private IInventorySheetService inventorySheetService;
    @Autowired
    private ITransferService transferMapper;

    /**
     * 查询盘点单列表
     */
    @RequiresPermissions("InventorySheet:inventory:list")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String, Object> map) {
        startPage();
        List<InventorySheet> list = inventorySheetService.selectInventorySheetList(map);
        return getDataTable(list);
    }


//    @RequiresPermissions("InventorySheet:inventory:list")
//    @GetMapping("/listByIsId")
//    public AjaxResult listbyIsId(@RequestParam Map<String,Object>map)
//    {
//        System.out.println("inventoryListMap==========="+map);
//        startPage();
//        List<InventorySheet> list = inventorySheetService.selectInventorySheetList(map);
//        return success(list);
//    }

    /**
     * 导出盘点单列表
     */
    @RequiresPermissions("InventorySheet:inventory:export")
    @Log(title = "盘点单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventorySheet inventorySheet) {
        // List<InventorySheet> list = inventorySheetService.selectInventorySheetList(inventorySheet);
        ExcelUtil<InventorySheet> util = new ExcelUtil<InventorySheet>(InventorySheet.class);
        util.exportExcel(response, null, "盘点单数据");
    }

    /**
     * 获取盘点单详细信息 - 出入库查询
     */
    @RequiresPermissions("InventorySheet:inventory:query")
    @GetMapping(value = "/details")
    public AjaxResult getInfo(@RequestParam(required = false) String[] isIds, @RequestParam(required = false) Map<String, Object> params) {
        List<InventoryDetails> inventoryDetails = inventorySheetService.selectInventoryDetailsByIsId(isIds, params);
        return success(inventoryDetails);
    }

    /**
     * 新增盘点单 - 盘点明细
     */
    @RequiresPermissions("InventorySheet:inventory:add")
    @Log(title = "盘点单", businessType = BusinessType.INSERT)
    @PostMapping("/inventoryAndDetials")
    public AjaxResult add(@RequestBody InventorySheet inventorySheet) {
        System.out.println("inventorySheet=================" + inventorySheet);
        return toAjax(inventorySheetService.insertInventorySheet(inventorySheet));
    }

    /**
     * 修改盘点单
     */
    @RequiresPermissions("InventorySheet:inventory:edit")
    @Log(title = "盘点单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventorySheet inventorySheet) {
        return toAjax(inventorySheetService.updateInventorySheet(inventorySheet));
    }

    /**
     * 删除盘点单
     */
    @RequiresPermissions("InventorySheet:inventory:remove")
    @Log(title = "盘点单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{isIds}")
    public AjaxResult remove(@PathVariable String[] isIds) {
        return toAjax(inventorySheetService.deleteInventorySheetByIsIds(isIds));
    }

    // 根据仓库查询下面的盘点货品
    @Log(title = "盘点单", businessType = BusinessType.DELETE)
    @GetMapping("/getByWid")
    public TableDataInfo goodsByWid(@RequestParam Map<String, Object> map) {
        startPage();
        List<Map<String, Object>> list = inventorySheetService.selectInventorySheetByWid(map);
        System.out.println("ppppppppppp[][][]" + list);
        // 入库仓库的 ID
        String w_id = MapUtils.getString(map, "inWId");
        list.forEach(e -> {
            // 货品 ID
            String goodsId = MapUtils.getString(e, "g_id");
            // 去数据库查询
            int totalItemQuantity = transferMapper.InItemQuantity(w_id, goodsId);
            e.put("stockQuantity", totalItemQuantity);
        });
        return getDataTable(list);
    }


}
