package com.lingyun.wh.common.controller;

import com.lingyun.wh.common.domain.EncodingRules;
import com.lingyun.wh.common.service.IEncodingRulesService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 编码规则Controller
 *
 * @author Tang
 * @date 2023-11-29
 */

@RestController
@RequestMapping("/encoding")
public class EncodingRulesController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(EncodingRulesController.class);

    @Autowired
    private IEncodingRulesService encodingRulesService;


    /**
     * 查询编码规则列表
     */
    @RequiresPermissions("system:encoding_rules:list")
    @GetMapping("/list")
    public AjaxResult list(EncodingRules encodingRules)
    {
        System.out.println("encodingRules: " + encodingRules);
        List<EncodingRules> list = encodingRulesService.selectEncodingRulesList(encodingRules);
        return success(list);
    }

    /**
     * 导出编码规则列表
     */
    @RequiresPermissions("system:encoding_rules:export")
    @Log(title = "编码规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EncodingRules encodingRules)
    {
        List<EncodingRules> list = encodingRulesService.selectEncodingRulesList(encodingRules);
        ExcelUtil<EncodingRules> util = new ExcelUtil<EncodingRules>(EncodingRules.class);
        util.exportExcel(response, list, "编码规则数据");
    }

    /**
     * 获取编码规则详细信息
     */
    @RequiresPermissions("system:encoding_rules:query")
    @GetMapping(value = "/{erId}")
    public AjaxResult getInfo(@PathVariable("erId") Integer erId)
    {
        return success(encodingRulesService.selectEncodingRulesByErId(erId));
    }

    /**
     * 新增编码规则
     */
    @RequiresPermissions("system:encoding_rules:add")
    @Log(title = "编码规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EncodingRules encodingRules)
    {
        return toAjax(encodingRulesService.insertEncodingRules(encodingRules));
    }

    /**
     * 修改编码规则
     */
    @RequiresPermissions("system:encoding_rules:edit")
    @Log(title = "编码规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EncodingRules encodingRules)
    {
        System.out.println("encodingRules: " + encodingRules);
        return toAjax(encodingRulesService.updateEncodingRules(encodingRules));
    }

    /**
     * 生成订单编码
     */
    @InnerAuth
    @RequiresPermissions("system:encoding_rules:gen_encoding")
    @Log(title = "生成编码", businessType = BusinessType.UPDATE)
    @GetMapping("/genEncode/{rulesId}")
    public R<String> genSpecifyEncoding(@PathVariable int rulesId)
    {
        System.out.println("rulesId: " + rulesId);
        return R.ok(encodingRulesService.genSpecifyEncoding(rulesId));
    }

    /**
     * 生成订单编码后，某个服务使用编码，
     * 调用此服务进行将该编码规则的当前流水号增加
     */
    @InnerAuth
    @RequiresPermissions("system:encoding_rules:incr_encoding")
    @Log(title = "增加当前流水号", businessType = BusinessType.UPDATE)
    @GetMapping("/incrEncode/{rulesId}")
    public R<String> increaseCurrentSerialNumber(@PathVariable int rulesId)
    {
        System.out.println("增加当前流水号: " + rulesId);
        return encodingRulesService.increaseCurrentSerialNumber(rulesId)>0?R.ok():R.fail("订单编号流水号迭代成功");
    }
}
