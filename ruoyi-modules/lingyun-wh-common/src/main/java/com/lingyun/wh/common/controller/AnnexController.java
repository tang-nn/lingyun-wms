package com.lingyun.wh.common.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.Annex;
import com.lingyun.wh.common.service.IAnnexService;
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
 * @Description : 附件Controller
 * @CreateDate : 2023/12/7 19:35
 */
@RestController
@RequestMapping("/annex")
public class AnnexController extends BaseController {
    @Autowired
    private IAnnexService annexService;

    /**
     * 查询附件列表
     */
    @RequiresPermissions("annex:annex:list")
    @GetMapping("/list")
    public TableDataInfo list(Annex annex) {
        startPage();
        List<Annex> list = annexService.selectAnnexList(annex);
        return getDataTable(list);
    }

    /**
     * 导出附件列表
     */
    @RequiresPermissions("annex:annex:export")
    @Log(title = "附件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Annex annex) {
        List<Annex> list = annexService.selectAnnexList(annex);
        ExcelUtil<Annex> util = new ExcelUtil<Annex>(Annex.class);
        util.exportExcel(response, list, "附件数据");
    }

    /**
     * 获取附件详细信息
     */
    @RequiresPermissions("annex:annex:query")
    @GetMapping(value = "/{aId}")
    public AjaxResult getInfo(@PathVariable("aId") Long aId) {
        return success(annexService.selectAnnexByAId(aId));
    }

    /**
     * 新增附件
     */
    @RequiresPermissions("annex:annex:add")
    @Log(title = "附件", businessType = BusinessType.INSERT)
    @PostMapping
    public R<?> add(@RequestBody List<Annex> annexes) {
        if(annexes == null || annexes.isEmpty()){
            return R.ok("附件为空");
        }
        return annexService.insertAnnex(annexes) == annexes.size() ? R.ok() : R.fail();
    }

    /**
     * 修改附件
     */
    @RequiresPermissions("annex:annex:edit")
    @Log(title = "附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Annex annex) {
        return toAjax(annexService.updateAnnex(annex));
    }

    /**
     * 删除附件
     */
    @RequiresPermissions("annex:annex:remove")
    @Log(title = "附件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{aIds}")
    public AjaxResult remove(@PathVariable Long[] aIds) {
        return toAjax(annexService.deleteAnnexByAIds(aIds));
    }
}
