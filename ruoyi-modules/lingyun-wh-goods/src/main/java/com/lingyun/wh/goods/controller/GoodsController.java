package com.lingyun.wh.goods.controller;

import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.goods.service.IGoodsService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 货品信息Controller
 *
 * @author lijin
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询货品信息列表
     */
    @RequiresPermissions("gd:good:gdinfo")
    @GetMapping("/gdinfo")
    public TableDataInfo list(@RequestParam Map<String, Object> map) {
        System.out.println("map===" + map);
        startPage();
        List<Map<String, Object>> list = goodsService.selectGoodsList(map);
        System.out.println("list===" + list);
        return getDataTable(list);
    }

    /**
     * 导出货品信息列表
     */
    @RequiresPermissions("lingyun-wh-goods:goods:export")
    @Log(title = "货品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Map<String, Object> goods) {
        List<Map<String, Object>> list = goodsService.selectGoodsList(goods);
        System.out.println("list===" + list);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        List<Goods> goodsList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            System.out.println("map===" + map);
            Goods g = new Goods();
            g.setSpecCode(map.get("spec_code").toString());
            g.setGId(map.get("g_id").toString());
            g.setGCode(map.get("g_code").toString());
            g.setOrPrice((BigDecimal) map.get("or_price"));
            g.setSort((Long) map.get("sort"));
            g.setWrPrice((BigDecimal) map.get("wr_price"));
            g.setGtId(map.get("gt_id").toString());
            g.setGName(map.get("g_name").toString());
            if (map.get("s_id") != null) {
                g.setSId(map.get("s_id").toString());
            }
            g.setStatus((Integer) map.get("status"));
            if (map.get("warning_id") != null) {
                g.setWarningId(map.get("warning_id").toString());
            }

            g.setHasShelfLife((Integer) map.get("has_shelf_life"));
            g.setShelfLife(map.get("shelf_life").toString());
            g.setGtName(map.get("gt_name").toString());
            goodsList.add(g);
        }

        util.exportExcel(response, goodsList, "货品信息数据");
    }


    /**
     * 新增货品信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:add")
    @Log(title = "货品信息", businessType = BusinessType.INSERT)
    @PostMapping("/agds")
    public AjaxResult add(@RequestBody Goods goods)
    {
        return toAjax(goodsService.insertGoods(goods));
    }



    /**
     * 导入货品信息列表
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "货品管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("gd:good:import")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        List<Goods> goodsList = util.importExcel(file.getInputStream());
        System.out.println("goodsList===" + goodsList);
        Long operName = SecurityUtils.getUserId();
        String message = goodsService.importGoods(goodsList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.importTemplateExcel(response, "商品数据");
    }

    /**
     * 获取货品信息详细信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:query")
    @GetMapping(value = "/findByGid/{gId}")
    public AjaxResult getInfo(@PathVariable("gId") String gId) {
        return success(goodsService.selectGoodsByGId(gId));
    }

    /**
     * 根据多个 ID 获取多个货品 *库存* 信息详细信息
     * 主要用于入库相关，不能用于出库相关查询
     *
     * @author Tang
     */
    @RequiresPermissions("lingyun-wh-goods:goods:query")
    @GetMapping(value = "/inbound")
    public R<ArrayList<Goods>> getInfoByIds(@RequestParam String[] ids, @RequestParam(required = false) Map<String, Object> params) {
        System.out.println("ids: " + Arrays.toString(ids));
        System.out.println("query params: "+ params);
        boolean flag = ids == null || ids.length == 0;
        if (flag) {
            R<ArrayList<Goods>> fail = R.fail(new ArrayList<>());
            fail.setMsg("goods id 不能为空");
            return fail;
        }
        ArrayList<Goods> goodsList = goodsService.queryGoodsByIds(ids, params);
        goodsList.forEach(System.out::println);
        return R.ok(goodsList);
    }

    /**
     * 根据多个 ID 获取多个货品 *库存* 信息详细信息
     * 主要用于入库相关，不能用于出库相关查询
     *
     * @author Tang
     */
    @RequiresPermissions("lingyun-wh-goods:goods:query")
    @GetMapping(value = "/inbound/all")
    public TableDataInfo inboundQuery(@RequestParam Map<String, Object> params) {
        startPage();
        List<Goods> goodsList = goodsService.queryGoodsByIds(null, params);
        return getDataTable(goodsList);
    }

    /**
     * 修改货品信息(状态)
     */

//    @RequiresPermissions("lingyun-wh-goods:goods:edit")
    @Log(title = "货品信息", businessType = BusinessType.UPDATE)
    @PostMapping("/editGoods")
    public AjaxResult edit(@RequestBody Map<String, Object> map) {
        Goods goods = new Goods();
        goods.setGId(map.get("gId").toString());
        goods.setStatus(Integer.valueOf(map.get("status").toString()));
        goods.setUpdateBy(SecurityUtils.getUserId().toString());
        goods.setUpdateTime(DateUtils.getNowDate());
        return toAjax(goodsService.updateGoodsStatus(goods));
    }

    /**
     * 修改货品信息
     */

    @RequiresPermissions("lingyun-wh-goods:goods:edit")
    @Log(title = "货品信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult editGoods(@RequestBody Goods goods) {
        System.out.println(goods+"************");
        return toAjax(goodsService.updateGoods(goods));
    }


    /**
     * 删除货品信息
     */
    @RequiresPermissions("lingyun-wh-goods:goods:remove")
    @Log(title = "货品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{gIds}")
    public AjaxResult remove(@PathVariable String[] gIds) {
        return toAjax(goodsService.deleteGoodsByGIds(gIds));
    }
}
