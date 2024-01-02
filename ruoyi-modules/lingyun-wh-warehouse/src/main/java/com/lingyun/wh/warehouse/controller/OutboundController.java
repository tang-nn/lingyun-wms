package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import com.lingyun.wh.warehouse.domain.OutboundDetails;
import com.lingyun.wh.warehouse.vo.ReviewDto;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.warehouse.domain.Outbound;
import com.lingyun.wh.warehouse.service.IOutboundService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 出库管理Controller
 *
 * @author Tang
 * @date 2023-12-26
 */
@RestController
@RequestMapping("/outbound")
public class OutboundController extends BaseController {
    @Autowired
    private IOutboundService outboundService;

    /**
     * 查询出库管理列表
     */
    @RequiresPermissions("warehouse:outbound:list")
    @GetMapping("/list")
    public TableDataInfo list(Outbound outbound) {
        startPage();
        List<Outbound> list = outboundService.selectOutboundList(outbound);
        return getDataTable(list);
    }

    /**
     * 查询出库管理列表
     */
    @RequiresPermissions("warehouse:outbound:list")
    @GetMapping("/list/details")
    public TableDataInfo details(@RequestParam Map<String, Object> params) {
        startPage();
        List<OutboundDetails<?>> list = outboundService.selectOutboundDetails(params);
        return getDataTable(list);
    }

    /**
     * 导出出库管理列表
     */
    @RequiresPermissions("warehouse:outbound:export")
    @Log(title = "出库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Outbound outbound) {
        List<Outbound> list = outboundService.selectOutboundList(outbound);
        ExcelUtil<Outbound> util = new ExcelUtil<Outbound>(Outbound.class);
        util.exportExcel(response, list, "出库管理数据");
    }

    /**
     * 获取出库管理详细信息
     */
    @RequiresPermissions("warehouse:outbound:query")
    @GetMapping(value = "/{outId}")
    public AjaxResult getInfo(@PathVariable("outId") String outId) {
        return success(outboundService.selectOutboundByOutId(outId));
    }

    /**
     * 新增出库管理
     */
    @RequiresPermissions("warehouse:outbound:add")
    @Log(title = "出库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Outbound outbound) {
        return toAjax(outboundService.insertOutbound(outbound));
    }

    /**
     * 修改出库管理
     */
    @RequiresPermissions("warehouse:outbound:edit")
    @Log(title = "出库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Outbound outbound) {
        Assert.isTrue(outbound != null && StringUtils.isNotEmpty(outbound.getOutId()), "请求参数异常");
        return toAjax(outboundService.updateOutbound(outbound));
    }

    /**
     * 审核出库管理
     */
    @RequiresPermissions("warehouse:outbound:edit")
    @Log(title = "出库管理", businessType = BusinessType.UPDATE)
    @PutMapping("/review")
    public AjaxResult review(@RequestBody @Validated ReviewDto outboundReviewDto) {
        Outbound outbound = new Outbound();
        outbound.setOutId(outboundReviewDto.getOrderId());
        outbound.setStatus(outboundReviewDto.getReviewStatus());
        outbound.setComments(outboundReviewDto.getComments());
        return toAjax(outboundService.reviewOutbound(outbound));
    }

    /**
     * 删除出库管理
     */
    @RequiresPermissions("warehouse:outbound:remove")
    @Log(title = "出库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{outIds}")
    public AjaxResult remove(@PathVariable String[] outIds) {
        return toAjax(outboundService.deleteOutboundByOutIds(outIds));
    }
}
