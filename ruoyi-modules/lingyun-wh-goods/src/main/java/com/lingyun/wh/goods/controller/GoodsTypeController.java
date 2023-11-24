package com.lingyun.wh.goods.controller;

import com.lingyun.wh.goods.doman.GoodsType;
import com.lingyun.wh.goods.service.IGoodsTypeService;
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
 * 货品类型Controller
 *
 * @author Lijin
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/goods/type")
public class GoodsTypeController extends BaseController
{
    @Autowired
    private IGoodsTypeService goodsTypeService;

    /**
     * 查询货品类型列表
     */
    @RequiresPermissions("lingyun-wh-goods:type:list")
    @GetMapping("/list")
    public TableDataInfo list(GoodsType goodsType)
    {
        startPage();
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        return getDataTable(list);
    }

    /***
     * 查询货品类型下拉框
     */
    @RequiresPermissions("lingyun-wh-goods:type:select")
    @GetMapping("/select")
    public AjaxResult selectGoodsTypes()
    {
        startPage();
        List<GoodsType> list = goodsTypeService.selectGoodsTypes();
        System.out.println("商品type========"+list);
        return success(list);
    }

    /**
     * 导出货品类型列表
     */
    @RequiresPermissions("lingyun-wh-goods:type:export")
    @Log(title = "货品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsType goodsType)
    {
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        util.exportExcel(response, list, "货品类型数据");
    }

    /**
     * 获取货品类型详细信息
     */
    @RequiresPermissions("lingyun-wh-goods:type:query")
    @GetMapping(value = "/{gtId}")
    public AjaxResult getInfo(@PathVariable("gtId") String gtId)
    {
        return success(goodsTypeService.selectGoodsTypeByGtId(gtId));
    }

    /**
     * 新增货品类型
     */
    @RequiresPermissions("lingyun-wh-goods:type:add")
    @Log(title = "货品类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsType goodsType)
    {
        return toAjax(goodsTypeService.insertGoodsType(goodsType));
    }

    /**
     * 修改货品类型
     */
    @RequiresPermissions("lingyun-wh-goods:type:edit")
    @Log(title = "货品类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsType goodsType)
    {
        return toAjax(goodsTypeService.updateGoodsType(goodsType));
    }

    /**
     * 删除货品类型
     */
    @RequiresPermissions("lingyun-wh-goods:type:remove")
    @Log(title = "货品类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{gtIds}")
    public AjaxResult remove(@PathVariable String[] gtIds)
    {
        return toAjax(goodsTypeService.deleteGoodsTypeByGtIds(gtIds));
    }
}
