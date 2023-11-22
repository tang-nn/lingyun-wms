package com.lingyun.wh.goods.controller;

import com.lingyun.wh.goods.doman.Goods;
import com.lingyun.wh.goods.service.IGoodsService;
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
import java.io.Console;
import java.util.List;

/**
 * 货品信息Controller
 *
 * @author lijin
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController
{
    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询货品信息列表
     */
    @RequiresPermissions("gd:good:gdinfo")
    @GetMapping("/gdinfo")
    public TableDataInfo list(Goods goods)
    {
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);
        System.out.println("list==="+list);
        return getDataTable(list);
    }

    /**
     * 导出货品信息列表
     */
    @RequiresPermissions("lingyun-wh-goods:goods:export")
    @Log(title = "货品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Goods goods)
    {
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.exportExcel(response, list, "货品信息数据");
    }

    /**
     * 获取货品信息详细信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:query")
    @GetMapping(value = "/{gId}")
    public AjaxResult getInfo(@PathVariable("gId") String gId)
    {
        return success(goodsService.selectGoodsByGId(gId));
    }

    /**
     * 新增货品信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:add")
    @Log(title = "货品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Goods goods)
    {
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改货品信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:edit")
    @Log(title = "货品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Goods goods)
    {
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除货品信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:remove")
    @Log(title = "货品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{gIds}")
    public AjaxResult remove(@PathVariable String[] gIds)
    {
        return toAjax(goodsService.deleteGoodsByGIds(gIds));
    }
}
