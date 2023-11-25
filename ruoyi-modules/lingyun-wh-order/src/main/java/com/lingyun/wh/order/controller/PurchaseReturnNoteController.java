package com.lingyun.wh.order.controller;

import com.lingyun.wh.order.domain.PurchaseReturnNote;
import com.lingyun.wh.order.service.IPurchaseReturnNoteService;
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
 * @Author : Tang
 * @Description : 进货退货Controller
 * @CreateDate : 2023/11/21 23:25
 */
@RestController
@RequestMapping("/purchase/return")
public class PurchaseReturnNoteController extends BaseController {
    @Autowired
    private IPurchaseReturnNoteService purchaseReturnNoteService;

    /**
     * 查询进货退货列表
     */
    @RequiresPermissions("PurchaseReturn:PurchaseReturnNote:list")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseReturnNote purchaseReturnNote) {
        startPage();
        List<PurchaseReturnNote> list = purchaseReturnNoteService.selectPurchaseReturnNoteList(purchaseReturnNote);
        return getDataTable(list);
    }

    /**
     * 导出进货退货列表
     */
    @RequiresPermissions("PurchaseReturn:PurchaseReturnNote:export")
    @Log(title = "进货退货", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseReturnNote purchaseReturnNote) {
        List<PurchaseReturnNote> list = purchaseReturnNoteService.selectPurchaseReturnNoteList(purchaseReturnNote);
        ExcelUtil<PurchaseReturnNote> util = new ExcelUtil<PurchaseReturnNote>(PurchaseReturnNote.class);
        util.exportExcel(response, list, "进货退货数据");
    }

    /**
     * 获取进货退货详细信息
     */
    @RequiresPermissions("PurchaseReturn:PurchaseReturnNote:query")
    @GetMapping(value = "/{prId}")
    public AjaxResult getInfo(@PathVariable("prId") String prId) {
        return success(purchaseReturnNoteService.selectPurchaseReturnNoteByPrId(prId));
    }

    /**
     * 新增进货退货
     */
    @RequiresPermissions("PurchaseReturn:PurchaseReturnNote:add")
    @Log(title = "进货退货", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseReturnNote purchaseReturnNote) {
        return toAjax(purchaseReturnNoteService.insertPurchaseReturnNote(purchaseReturnNote));
    }

    /**
     * 修改进货退货
     */
    @RequiresPermissions("PurchaseReturn:PurchaseReturnNote:edit")
    @Log(title = "进货退货", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseReturnNote purchaseReturnNote) {
        return toAjax(purchaseReturnNoteService.updatePurchaseReturnNote(purchaseReturnNote));
    }

    /**
     * 删除进货退货
     */
    @RequiresPermissions("PurchaseReturn:PurchaseReturnNote:remove")
    @Log(title = "进货退货", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prIds}")
    public AjaxResult remove(@PathVariable String[] prIds) {
        return toAjax(purchaseReturnNoteService.deletePurchaseReturnNoteByPrIds(prIds));
    }
}
