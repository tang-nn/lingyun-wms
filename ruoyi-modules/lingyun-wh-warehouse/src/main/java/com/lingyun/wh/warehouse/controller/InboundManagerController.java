package com.lingyun.wh.warehouse.controller;

import com.lingyun.wh.warehouse.domain.InboundManager;
import com.lingyun.wh.warehouse.service.IInboundManagerService;
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
 * 入库管理Controller
 *
 * @Author : Tang
 * @CreateDate : 2023/12/12 13:23
 */
@RestController
@RequestMapping("/inventory")
public class InboundManagerController extends BaseController {
    @Autowired
    private IInboundManagerService inboundManagerService;

    /**
     * 查询入库管理列表
     */
    @RequiresPermissions("warehouse:inventory:list")
    @GetMapping("/list")
    public TableDataInfo list(InboundManager inboundManager)
    {
        startPage();
        List<InboundManager> list = inboundManagerService.selectInboundManagerList(inboundManager);
        return getDataTable(list);
    }

    /**
     * 导出入库管理列表
     */
    @RequiresPermissions("warehouse:inventory:export")
    @Log(title = "入库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InboundManager inboundManager)
    {
        List<InboundManager> list = inboundManagerService.selectInboundManagerList(inboundManager);
        ExcelUtil<InboundManager> util = new ExcelUtil<InboundManager>(InboundManager.class);
        util.exportExcel(response, list, "入库管理数据");
    }

    /**
     * 获取入库管理详细信息
     */
    @RequiresPermissions("warehouse:inventory:query")
    @GetMapping(value = "/{inId}")
    public AjaxResult getInfo(@PathVariable("inId") String inId)
    {
        return success(inboundManagerService.selectInboundManagerByInId(inId));
    }

    /**
     * 新增入库管理
     */
    @RequiresPermissions("warehouse:inventory:add")
    @Log(title = "入库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InboundManager inboundManager)
    {
        return toAjax(inboundManagerService.insertInboundManager(inboundManager));
    }

    /**
     * 修改入库管理
     */
    @RequiresPermissions("warehouse:inventory:edit")
    @Log(title = "入库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InboundManager inboundManager)
    {
        return toAjax(inboundManagerService.updateInboundManager(inboundManager));
    }

    /**
     * 删除入库管理
     */
    @RequiresPermissions("warehouse:inventory:remove")
    @Log(title = "入库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{inIds}")
    public AjaxResult remove(@PathVariable String[] inIds)
    {
        return toAjax(inboundManagerService.deleteInboundManagerByInIds(inIds));
    }
}
