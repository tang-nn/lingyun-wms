package com.lingyun.wh.goods.controller;

import com.lingyun.wh.goods.api.domain.GoodsType;
import com.lingyun.wh.goods.service.IGoodsTypeService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.lingyun.wh.goods.doman.vo.TreeSelect;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        System.out.println("货品类型列表====="+list);
        return getDataTable(list);
    }


    /***
     * 查询货品类型下拉框
     */
    @RequiresPermissions("lingyun-wh-goods:type:select")
    @GetMapping("/select")
    public AjaxResult selectGoodsTypes()
    {
        List<TreeSelect> gtTree = goodsTypeService.selectGoodsTypeTreeList();
        System.out.println("商品type========"+gtTree);
        return success(gtTree);
    }


    /**
     * 导入货品类型列表
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "货品管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("gd:goodtype:import")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        List<GoodsType> goodsList = util.importExcel(file.getInputStream());
        System.out.println("goodsList==="+goodsList);
        String message = goodsTypeService.importGoodsType(goodsList, updateSupport);
        return success(message);
    }



    /**
     * 导出货品类型列表
     */
    @RequiresPermissions("lingyun-wh-goods:type:export")
    @Log(title = "货品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,GoodsType goodsType)
    {
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        util.exportExcel(response, list, "货品类型数据");
    }


    /**
     * 模板
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        util.importTemplateExcel(response, "商品类型数据");
    }


    /**
     * 获取货品类型详细信息
     */
    @RequiresPermissions("lingyun-wh-goods:type:query")
    @PostMapping ( value="{gtId}")
    public AjaxResult getInfo(@PathVariable("gtId") String gtId)
    {
        System.out.println("gtId***************"+gtId);
        return success(goodsTypeService.selectGoodsTypeByGtId(gtId));
    }

    /**
     * 新增货品类型
     */
    @RequiresPermissions("lingyun-wh-goods:type:add")
    @Log(title = "货品类型", businessType = BusinessType.INSERT)
    @PostMapping("/addGoodsType")
    public AjaxResult add(@RequestBody GoodsType goodsType)
    {
        goodsType.setCreateBy(SecurityUtils.getUserId().toString());
        System.out.println("goodsType```````````````"+goodsType);
        return toAjax(goodsTypeService.insertGoodsType(goodsType));
    }

    /**
     * 修改货品类型
     */
    @RequiresPermissions("lingyun-wh-goods:type:edit")
    @Log(title = "货品类型", businessType = BusinessType.UPDATE)
    @PutMapping("/editGoodsType")
    public AjaxResult edit(@RequestBody Map<String,Object> map)
    {
        GoodsType goodsType=new GoodsType();
        goodsType.setUpdateBy(SecurityUtils.getUserId().toString());
        if (map.get("gtId")!=null){
            goodsType.setGtId(map.get("gtId").toString());
        }
       if (map.get("parentId")!=null){
           goodsType.setParentId(map.get("parentId").toString());
       }
       if (map.get("gtName")!=null){
           goodsType.setGtName(map.get("gtName").toString());
       }
        if (map.get("sort")!=null){
            goodsType.setStatus(Integer.valueOf(map.get("status").toString()));
        }
        if (map.get("remark")!=null){
            goodsType.setRemark(map.get("remark").toString());
        }
        System.out.println("goodsType```````````````"+goodsType);
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

    //查询货品分类下是否有子类
    @PostMapping("/isExits/{gtIds}")
    public AjaxResult isExits(@PathVariable String[] gtIds)
    {

        return success(goodsTypeService.countByIsDelete(gtIds));
    }
}
