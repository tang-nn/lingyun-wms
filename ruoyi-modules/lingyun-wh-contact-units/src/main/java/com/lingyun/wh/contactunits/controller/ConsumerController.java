package com.lingyun.wh.contactunits.controller;

import com.lingyun.wh.contactunits.domain.Consumer;
import com.lingyun.wh.contactunits.pojo.vo.ConsumerVo;
import com.lingyun.wh.contactunits.service.IConsumerService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
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
 * @Description :
 * @CreateDate : 2023/11/21 15:42
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController extends BaseController {

    @Autowired
    private IConsumerService consumerService;

    /**
     * 导出客户列表
     */
    @RequiresPermissions("consumer:consumer:export")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Consumer consumer) {
        List<Consumer> list = consumerService.selectConsumerList(consumer);
        ExcelUtil<Consumer> util = new ExcelUtil<Consumer>(Consumer.class);
        util.exportExcel(response, list, "客户数据");
    }

    /**
     * 获取客户详细信息
     */
    @RequiresPermissions("consumer:consumer:query")
    @GetMapping(value = "/{cId}")
    public AjaxResult getInfo(@PathVariable("cId") String cId) {
        return success(consumerService.selectConsumerByCId(cId));
    }

    /**
     * 新增客户
     */
    @RequiresPermissions("consumer:consumer:add")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Consumer consumer)
    {
        return toAjax(consumerService.insertConsumer(consumer));
    }

    /**
     * 修改客户
     */
    @RequiresPermissions("consumer:consumer:edit")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Consumer consumer) {
        return toAjax(consumerService.updateConsumer(consumer));
    }

    /**
     * 删除客户
     */
    @RequiresPermissions("consumer:consumer:remove")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cIds}")
    public AjaxResult remove(@PathVariable String[] cIds) {
        return toAjax(consumerService.deleteConsumerByCIds(cIds));
    }

    /**
     * 查询客户列表
     */
    @RequiresPermissions("consumer:consumer:list")
    @RequestMapping("/list")
    public TableDataInfo consumerQuery() {
        startPage();
        List<ConsumerVo> csm = consumerService.consumerQurey();
        return getDataTable(csm);
    }


    /**
     * 修改is_delete字段，达到删除效果
     * @return
     */
    @PutMapping("/del/{cid}")
    public AjaxResult conUpdate(@PathVariable String cid){
        if(StringUtils.isEmpty(cid) || !StringUtils.isNumericSpace(cid)){
            return error("参数错误");
        }
        return toAjax(consumerService.conUpdate(cid));
    }

}
