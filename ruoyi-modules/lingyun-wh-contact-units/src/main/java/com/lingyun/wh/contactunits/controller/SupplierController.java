package com.lingyun.wh.contactunits.controller;

import com.lingyun.wh.contactunits.domain.Supplier;
import com.lingyun.wh.contactunits.pojo.vo.ConsumerVo;
import com.lingyun.wh.contactunits.service.ISupplierService;
import com.ruoyi.common.core.utils.StringUtils;
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
 * 供应商Controller
 *
 * @author Tang
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController {
    @Autowired
    private ISupplierService supplierService;

    /**
     * 查询供应商列表
     */
    @RequiresPermissions("supplier:supplier:list")
    @GetMapping("/list")
    public TableDataInfo list(Supplier supplier) {
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    /**
     * 查询供应商下拉框(货品新增)
     */
    @RequiresPermissions("supplier:supplier:list")
    @GetMapping("/supplierList")
    public AjaxResult consumerList() {
        return success(supplierService.selectSupplierList(new Supplier()));
    }

    /**
     * 导出供应商列表
     */
    @RequiresPermissions("supplier:supplier:export")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Supplier supplier) {
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        util.exportExcel(response, list, "供应商数据");
    }

    /**
     * 获取供应商详细信息
     */
    @RequiresPermissions("supplier:supplier:query")
    @GetMapping(value = "/{sId}")
    public AjaxResult getInfo(@PathVariable("sId") String sId) {
        return success(supplierService.selectSupplierBySId(sId));
    }

    /**
     * 新增供应商
     */
    @RequiresPermissions("supplier:supplier:add")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Supplier supplier) {
        return toAjax(supplierService.insertSupplier(supplier));
    }

    /**
     * 修改供应商
     */
    @RequiresPermissions("supplier:supplier:edit")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Supplier supplier) {
        return toAjax(supplierService.updateSupplier(supplier));
    }

    /**
     * 删除供应商
     */
    @RequiresPermissions("supplier:supplier:remove")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sIds}")
    public AjaxResult remove(@PathVariable String[] sIds) {
        return toAjax(supplierService.deleteSupplierBySIds(sIds));
    }

    // /**
    //  * 查询供应商信息
    //  * @return
    //  */
    // @RequiresPermissions("consumer:consumer:list")
    // @RequestMapping("/list")
    // public TableDataInfo supplierQuery() {
    //     startPage();
    //     List<Supplier> csm = supplierService.supplierQurey();
    //     return getDataTable(csm);
    // }


    /**
     * 修改is_delete字段，达到删除效果
     * @return
     */
    @PutMapping("/del/{cid}")
    public AjaxResult conUpdate(@PathVariable String cid){
        if(StringUtils.isEmpty(cid) || !StringUtils.isNumericSpace(cid)){
            return error("参数错误");
        }
        return toAjax(supplierService.supUpdate(cid));
    }

}
