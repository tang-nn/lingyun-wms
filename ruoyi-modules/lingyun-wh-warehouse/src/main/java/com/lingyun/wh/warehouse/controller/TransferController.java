package com.lingyun.wh.warehouse.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.warehouse.domain.Transfer;
import com.lingyun.wh.warehouse.service.ITransferService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 调拨单Controller
 * 
 * @author Tang
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/transfer")
public class TransferController extends BaseController
{
    @Autowired
    private ITransferService transferService;

    /**
     * 查询调拨单列表
     */
    @RequiresPermissions("transfer:transfer:list")
    @GetMapping("/list")
    public TableDataInfo list(Transfer transfer)
    {
        startPage();
        List<Transfer> list = transferService.selectTransferList(transfer);
        return getDataTable(list);
    }

    /**
     * 导出调拨单列表
     */
    @RequiresPermissions("transfer:transfer:export")
    @Log(title = "调拨单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Transfer transfer)
    {
        List<Transfer> list = transferService.selectTransferList(transfer);
        ExcelUtil<Transfer> util = new ExcelUtil<Transfer>(Transfer.class);
        util.exportExcel(response, list, "调拨单数据");
    }

    /**
     * 获取调拨单详细信息
     */
    @RequiresPermissions("transfer:transfer:query")
    @GetMapping(value = "/{tId}")
    public AjaxResult getInfo(@PathVariable("tId") String tId)
    {
        return success(transferService.selectTransferByTId(tId));
    }

    /**
     * 新增调拨单
     */
    @RequiresPermissions("transfer:transfer:add")
    @Log(title = "调拨单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Transfer transfer)
    {
        return toAjax(transferService.insertTransfer(transfer));
    }

    /**
     * 修改调拨单
     */
    @RequiresPermissions("transfer:transfer:edit")
    @Log(title = "调拨单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Transfer transfer)
    {
        return toAjax(transferService.updateTransfer(transfer));
    }

    /**
     * 删除调拨单
     */
    @RequiresPermissions("transfer:transfer:remove")
    @Log(title = "调拨单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tIds}")
    public AjaxResult remove(@PathVariable String[] tIds)
    {
        return toAjax(transferService.deleteTransferByTIds(tIds));
    }
}
