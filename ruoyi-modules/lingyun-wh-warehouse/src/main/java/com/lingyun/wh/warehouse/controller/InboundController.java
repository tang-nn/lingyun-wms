package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.lingyun.wh.warehouse.domain.Outbound;
import com.lingyun.wh.warehouse.vo.ReviewDto;
import com.ruoyi.common.core.utils.StringUtils;
import io.jsonwebtoken.lang.Assert;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.warehouse.domain.Inbound;
import com.lingyun.wh.warehouse.service.IInboundService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 入库管理Controller
 *
 * @author Tang
 * @date 2023-12-18
 */
@RestController
@RequestMapping("/inbound")
public class InboundController extends BaseController {
    @Autowired
    private IInboundService inboundService;

    /**
     * 查询入库管理列表
     */
    @RequiresPermissions("inbound:inbound:list")
    @GetMapping("/list")
    public TableDataInfo list(Inbound inbound) {
        startPage();
        List<Inbound> list = inboundService.selectInboundList(inbound);
        return getDataTable(list);
    }

    /**
     * 导出入库管理列表
     */
    @RequiresPermissions("inbound:inbound:export")
    @Log(title = "入库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Inbound inbound) {
        List<Inbound> list = inboundService.selectInboundList(inbound);
        ExcelUtil<Inbound> util = new ExcelUtil<Inbound>(Inbound.class);
        util.exportExcel(response, list, "入库管理数据");
    }

    /**
     * 获取入库管理详细信息
     *
     * @param inid 入库 ID
     * @return
     */
    @RequiresPermissions("inbound:inbound:query")
    @GetMapping(value = "/{inid}")
    public AjaxResult getInfo(@PathVariable String inid) {
        return success(inboundService.selectInboundByInid(inid));
    }

    /**
     * 获取入库管理详细信息
     *
     * @param inid 入库 ID
     * @return
     */
    @RequiresPermissions("inbound:inbound:query")
    @GetMapping(value = "/details")
    public AjaxResult getDetails(@RequestParam(required = false) String inid, @RequestParam(required = false) String odid) {
        // TODO 查询入库明细
        return success(inboundService.selectInboundByInid(inid));
    }

    /**
     * 新增入库管理
     */
    @RequiresPermissions("inbound:inbound:add")
    @Log(title = "入库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Inbound inbound) {
        System.out.println("新增入库 + inbound:" + inbound);
        return toAjax(inboundService.insertInbound(inbound));
    }

    /**
     * 修改入库管理
     */
    @RequiresPermissions("inbound:inbound:edit")
    @Log(title = "入库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Inbound inbound) {
        Assert.isTrue(inbound.getInid() != null && StringUtils.isNotEmpty(inbound.getInid()), "入库单号不能为空");
        return toAjax(inboundService.updateInbound(inbound));
    }

    /**
     * 入库审核
     */
    @RequiresPermissions("inbound:inbound:review")
    @Log(title = "入库审核", businessType = BusinessType.UPDATE)
    @PutMapping("/review")
    public AjaxResult review(@Validated @RequestBody ReviewDto reviewDto) {
        Inbound inbound = new Inbound();
        inbound.setInid(reviewDto.getOrderId());
        inbound.setStatus(reviewDto.getReviewStatus());
        inbound.setComments(reviewDto.getComments());
        return toAjax(inboundService.reviewInbound(inbound));
    }

    /**
     * 删除入库管理
     */
    @RequiresPermissions("inbound:inbound:remove")
    @Log(title = "入库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{inids}")
    public AjaxResult remove(@PathVariable String[] inids) {
        return toAjax(inboundService.deleteInboundByInids(inids));
    }
}
