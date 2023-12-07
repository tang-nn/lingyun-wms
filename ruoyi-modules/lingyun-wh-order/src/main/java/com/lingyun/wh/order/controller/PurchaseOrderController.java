package com.lingyun.wh.order.controller;

import com.lingyun.wh.order.domain.PurchaseOrder;
import com.lingyun.wh.order.pojo.vo.PurchaseOrderVo;
import com.lingyun.wh.order.service.IPurchaseOrderService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileTypeUtils;
import com.ruoyi.common.core.utils.file.MimeTypeUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RemoteFileService remoteFileService;
    private String content;


    /**
     * 查询进货订单列表
     */
    @RequiresPermissions("order:purchase:list")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String, Object> params) {
        System.out.println("params: " + params);
        startPage();
        List<PurchaseOrderVo> list = purchaseOrderService.selectPurchaseOrderList(params);
        return getDataTable(list);
    }

    /**
     * 导出进货订单列表
     */
    @RequiresPermissions("order:purchase:export")
    @Log(title = "进货订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrderVo params) {
        // List<PurchaseOrder> list = purchaseOrderService.selectPurchaseOrderList(params);
        // ExcelUtil<PurchaseOrder> util = new ExcelUtil<>(PurchaseOrder.class);
        // util.exportExcel(response, list, "进货订单数据");
    }

    /**
     * 获取进货订单详细信息
     */
    @RequiresPermissions("order:purchase:query")
    @GetMapping(value = "/{poId}")
    public AjaxResult getInfo(@PathVariable("poId") String poId) {
        return success(purchaseOrderService.selectPurchaseOrderByPoId(poId));
    }

    /**
     * 新增进货订单
     */
    @RequiresPermissions("order:purchase:add")
    @Log(title = "进货订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PurchaseOrder purchaseOrder) {
        return toAjax(purchaseOrderService.insertPurchaseOrder(purchaseOrder));
    }

    /**
     * 修改进货订单
     */
    @RequiresPermissions("order:purchase:edit")
    @Log(title = "进货订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrder purchaseOrder) {
        return toAjax(purchaseOrderService.updatePurchaseOrder(purchaseOrder));
    }

    /**
     * 删除进货订单
     */
    @RequiresPermissions("order:purchase:remove")
    @Log(title = "进货订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{poIds}")
    public AjaxResult remove(@PathVariable String[] poIds) {
        return toAjax(purchaseOrderService.deletePurchaseOrderByPoIds(poIds));
    }
}
