package com.lingyun.wh.order.controller;

import com.lingyun.wh.order.api.domain.SalesOrder;
import com.lingyun.wh.order.service.ISalesOrderService;
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
 * 销售订单Controller
 *
 * @author Tang
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/sales")
public class SalesOrderController extends BaseController {
    @Autowired
    private ISalesOrderService salesOrderService;

    /**
     * 查询销售订单列表
     */
    @RequiresPermissions("SalesOrder:sales:list")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrder salesOrder) {
        startPage();
        List<SalesOrder> list = salesOrderService.selectSalesOrderList(salesOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @RequiresPermissions("SalesOrder:sales:export")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrder salesOrder) {
        List<SalesOrder> list = salesOrderService.selectSalesOrderList(salesOrder);
        ExcelUtil<SalesOrder> util = new ExcelUtil<SalesOrder>(SalesOrder.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @RequiresPermissions("SalesOrder:sales:query")
    @GetMapping(value = "/{sId}")
    public AjaxResult getInfo(@PathVariable("sId") String sId) {
        return success(salesOrderService.selectSalesOrderBySId(sId));
    }

    /**
     * 新增销售订单
     */
    @RequiresPermissions("SalesOrder:sales:add")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrder salesOrder) {
        return toAjax(salesOrderService.insertSalesOrder(salesOrder));
    }

    /**
     * 修改销售订单
     */
    @RequiresPermissions("SalesOrder:sales:edit")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrder salesOrder) {
        return toAjax(salesOrderService.updateSalesOrder(salesOrder));
    }

    /**
     * 删除销售订单
     */
    @RequiresPermissions("SalesOrder:sales:remove")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sIds}")
    public AjaxResult remove(@PathVariable String[] sIds) {
        return toAjax(salesOrderService.deleteSalesOrderBySIds(sIds));
    }
}
