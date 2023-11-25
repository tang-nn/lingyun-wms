package com.lingyun.wh.order.controller;

import com.lingyun.wh.order.domain.PurchaseOrder;
import com.lingyun.wh.order.service.IPurchaseOrderService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 进货订单Controller
 *
 * @author Tang
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/purchase")
@RefreshScope
public class PurchaseOrderController extends BaseController {
    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    @Value("${tang.content}")
    private String content;

    /**
     * 用于测试服务
     */
    @GetMapping("/test")
    public AjaxResult test() {
        System.out.println("content: " + content);
        return success(content);
    }

    /**
     * 查询进货订单列表
     */
    @RequiresPermissions("Purchase:Purchase:list")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseOrder purchaseOrder) {
        startPage();
        List<PurchaseOrder> list = purchaseOrderService.selectPurchaseOrderList(purchaseOrder);
        return getDataTable(list);
    }

    /**
     * 导出进货订单列表
     */
    @RequiresPermissions("PurchaseOrder:PurchaseOrder:export")
    @Log(title = "进货订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrder purchaseOrder) {
        List<PurchaseOrder> list = purchaseOrderService.selectPurchaseOrderList(purchaseOrder);
        ExcelUtil<PurchaseOrder> util = new ExcelUtil<>(PurchaseOrder.class);
        util.exportExcel(response, list, "进货订单数据");
    }

    /**
     * 获取进货订单详细信息
     */
    @RequiresPermissions("PurchaseOrder:PurchaseOrder:query")
    @GetMapping(value = "/{poId}")
    public AjaxResult getInfo(@PathVariable("poId") String poId) {
        return success(purchaseOrderService.selectPurchaseOrderByPoId(poId));
    }

    /**
     * 新增进货订单
     */
    @RequiresPermissions("PurchaseOrder:PurchaseOrder:add")
    @Log(title = "进货订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseOrder purchaseOrder) {
        return toAjax(purchaseOrderService.insertPurchaseOrder(purchaseOrder));
    }

    /**
     * 修改进货订单
     */
    @RequiresPermissions("PurchaseOrder:PurchaseOrder:edit")
    @Log(title = "进货订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrder purchaseOrder) {
        return toAjax(purchaseOrderService.updatePurchaseOrder(purchaseOrder));
    }

    /**
     * 删除进货订单
     */
    @RequiresPermissions("PurchaseOrder:PurchaseOrder:remove")
    @Log(title = "进货订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{poIds}")
    public AjaxResult remove(@PathVariable String[] poIds) {
        return toAjax(purchaseOrderService.deletePurchaseOrderByPoIds(poIds));
    }
}
