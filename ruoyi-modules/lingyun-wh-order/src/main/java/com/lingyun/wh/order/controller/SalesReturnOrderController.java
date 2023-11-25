package com.lingyun.wh.order.controller;

import com.lingyun.wh.order.domain.SalesReturnOrder;
import com.lingyun.wh.order.service.ISalesReturnOrderService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 销售退货Controller
 *
 * @author Tang
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/sales/return")
public class SalesReturnOrderController extends BaseController {
    @Autowired
    private ISalesReturnOrderService salesReturnOrderService;

    /**
     * 查询销售退货列表
     */
    @RequiresPermissions("SalesReturn:sales/return:list")
    @GetMapping("/list")
    public TableDataInfo list(SalesReturnOrder salesReturnOrder) {
        startPage();
        List<SalesReturnOrder> list = salesReturnOrderService.selectSalesReturnOrderList(salesReturnOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售退货列表
     */
    @RequiresPermissions("SalesReturn:sales/return:export")
    @Log(title = "销售退货", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesReturnOrder salesReturnOrder) {
        List<SalesReturnOrder> list = salesReturnOrderService.selectSalesReturnOrderList(salesReturnOrder);
        ExcelUtil<SalesReturnOrder> util = new ExcelUtil<SalesReturnOrder>(SalesReturnOrder.class);
        util.exportExcel(response, list, "销售退货数据");
    }

    /**
     * 获取销售退货详细信息
     */
    @RequiresPermissions("SalesReturn:sales/return:query")
    @GetMapping(value = "/{srId}")
    public AjaxResult getInfo(@PathVariable("srId") String srId) {
        return success(salesReturnOrderService.selectSalesReturnOrderBySrId(srId));
    }

    /**
     * 新增销售退货
     */
    @RequiresPermissions("SalesReturn:sales/return:add")
    @Log(title = "销售退货", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesReturnOrder salesReturnOrder) {
        return toAjax(salesReturnOrderService.insertSalesReturnOrder(salesReturnOrder));
    }

    /**
     * 修改销售退货
     */
    @RequiresPermissions("SalesReturn:sales/return:edit")
    @Log(title = "销售退货", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesReturnOrder salesReturnOrder) {
        return toAjax(salesReturnOrderService.updateSalesReturnOrder(salesReturnOrder));
    }

    /**
     * 删除销售退货
     */
    @RequiresPermissions("SalesReturn:sales/return:remove")
    @Log(title = "销售退货", businessType = BusinessType.DELETE)
    @DeleteMapping("/{srIds}")
    public AjaxResult remove(@PathVariable String[] srIds) {
        return toAjax(salesReturnOrderService.deleteSalesReturnOrderBySrIds(srIds));
    }
}
