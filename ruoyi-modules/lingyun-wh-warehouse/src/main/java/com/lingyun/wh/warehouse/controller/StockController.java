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
import com.lingyun.wh.goods.api.domain.Stock;
import com.lingyun.wh.warehouse.service.IStockService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 库存Controller
 *
 * @author Tang
 * @date 2023-12-17
 */
@RestController
@RequestMapping("/stock")
public class StockController extends BaseController {
    @Autowired
    private IStockService stockService;

    /**
     * 查询库存列表
     */
    @RequiresPermissions("warehouse:stock:list")
    @GetMapping("/list")
    public TableDataInfo list(Stock stock) {
        startPage();
        List<Stock> list = stockService.selectStockList(stock);
        return getDataTable(list);
    }

    /**
     * 导出库存列表
     */
    @RequiresPermissions("warehouse:stock:export")
    @Log(title = "库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Stock stock) {
        List<Stock> list = stockService.selectStockList(stock);
        ExcelUtil<Stock> util = new ExcelUtil<Stock>(Stock.class);
        util.exportExcel(response, list, "库存数据");
    }

    /**
     * 获取库存详细信息
     */
    @RequiresPermissions("warehouse:stock:query")
    @GetMapping(value = "/{sid}")
    public AjaxResult getInfo(@PathVariable("sid") String sid) {
        return success(stockService.selectStockBySid(sid));
    }

    /**
     * 新增库存
     */
    @RequiresPermissions("warehouse:stock:add")
    @Log(title = "库存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Stock stock) {
        return toAjax(stockService.insertStock(stock));
    }


    // /**
    //  * 修改库存
    //  */
    // @RequiresPermissions("warehouse:stock:edit")
    // @Log(title = "库存", businessType = BusinessType.UPDATE)
    // @PutMapping
    // public AjaxResult edit(@RequestBody Stock stock)
    // {
    //     return toAjax(stockService.updateStock(stock));
    // }

    /**
     * 删除库存
     */
    @RequiresPermissions("warehouse:stock:remove")
    @Log(title = "库存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sids}")
    public AjaxResult remove(@PathVariable String[] sids) {
        return toAjax(stockService.deleteStockBySids(sids));
    }
}
