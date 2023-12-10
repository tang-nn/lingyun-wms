package com.lingyun.wh.common.service.impl;

import com.lingyun.wh.common.domain.EncodingRules;
import com.lingyun.wh.common.mapper.EncodingRulesMapper;
import com.lingyun.wh.common.service.IEncodingRulesService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.PSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/29 11:09
 */
@Service
public class EncodingRulesServiceImpl implements IEncodingRulesService {
    private static final Logger log = LoggerFactory.getLogger(EncodingRulesServiceImpl.class);
    @Autowired
    private EncodingRulesMapper encodingRulesMapper;

    /**
     * 查询编码规则
     *
     * @param erId 编码规则主键
     * @return 编码规则
     */
    @Override
    public EncodingRules selectEncodingRulesByErId(Integer erId) {
        return encodingRulesMapper.selectEncodingRules(erId, null);
    }

    /**
     * 查询编码规则列表
     *
     * @param encodingRules 编码规则
     * @return 编码规则
     */
    @Override
    public List<EncodingRules> selectEncodingRulesList(EncodingRules encodingRules) {
        return encodingRulesMapper.selectEncodingRulesList(encodingRules);
    }

    /**
     * 新增编码规则
     *
     * @param encodingRules 编码规则
     * @return 结果
     */
    @Override
    public int insertEncodingRules(EncodingRules encodingRules) {
        encodingRules.setCreateTime(DateUtils.getNowDate());
        return encodingRulesMapper.insertEncodingRules(encodingRules);
    }

    /**
     * 修改编码规则
     *
     * @param encodingRules 编码规则
     * @return 结果
     */
    @Override
    public int updateEncodingRules(EncodingRules encodingRules) {
        encodingRules.setUpdateTime(DateUtils.getNowDate());
        encodingRules.setUpdateBy(String.valueOf(SecurityUtils.getLoginUser().getUserid()));
        System.out.println("updateEncodingRules： " + encodingRules);
        return encodingRulesMapper.updateEncodingRules(encodingRules);
    }

    /**
     * 批量删除编码规则
     *
     * @param erIds 需要删除的编码规则主键
     * @return 结果
     */
    @Override
    public int deleteEncodingRulesByErIds(Integer[] erIds) {
        return encodingRulesMapper.deleteEncodingRulesByErIds(erIds);
    }

    /**
     * 删除编码规则信息
     *
     * @param erId 编码规则主键
     * @return 结果
     */
    @Override
    public int deleteEncodingRulesByErId(Integer erId) {
        return encodingRulesMapper.deleteEncodingRulesByErId(erId);
    }

    /**
     * 根据指定编码规则生成编码
     *
     * @param rulesId 目标订单，数据字典
     * @return 结果
     */
    @Override
    public String genSpecifyEncoding(int rulesId) {
        EncodingRules rules = encodingRulesMapper.selectEncodingRules(rulesId, null);
        String orderNumber = null;
        // 数据库查看为空，抛出异常
        if (rules == null) {
            throw new RuntimeException("The encoding rule for the target [" + rulesId + "] form does not exist");
        }
        System.out.println("rules = " + rules);
        int csn = rules.getCurrentSerialNumber() + rules.getStepLength();
        // 当前流水号超过规定位数，抛出异常
        if (rules.getSerialNumber() < String.valueOf(csn).length()) {
            throw new RuntimeException("前流水号超过规定位数，请重新指定编码的流水好长度");
        }

        String timeRule = rules.getTimeRules();
        System.out.println("timeRule: " + timeRule);
        orderNumber = rules.getNumberPrefix() + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(timeRule)) + String.format("%0" + rules.getSerialNumber() + "d", csn);
        log.info("目标表单: {}，生成订单号：{}", rules.getTargetForm(), orderNumber);
        return orderNumber;
    }

    /**
     * 生成订单编码后，某个服务使用编码，
     * 调用此服务进行将该编码规则的当前流水号增加
     *
     * @param rulesId 编号规则 Id，数据字典
     * @return
     */
    @Override
    public int increaseCurrentSerialNumber(int rulesId) {
        EncodingRules rules = encodingRulesMapper.selectEncodingRules(rulesId, null);
        String orderNumber = null;
        // 数据库查看为空，抛出异常
        if (rules == null) {
            throw new RuntimeException("The encoding rule for the target [" + rulesId + "] form does not exist");
        }
        EncodingRules newRules = new EncodingRules();
        newRules.setErId(rules.getErId());
        newRules.setCurrentSerialNumber(rules.getCurrentSerialNumber() + rules.getStepLength());
        newRules.setUpdateTime(DateUtils.getNowDate());
        newRules.setUpdateBy(String.valueOf(SecurityUtils.getLoginUser().getUserid()));
        System.out.println("newRules = " + newRules);
        return encodingRulesMapper.updateEncodingRules(newRules);
    }
}
