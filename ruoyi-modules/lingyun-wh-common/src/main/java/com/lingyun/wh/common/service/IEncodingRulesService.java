package com.lingyun.wh.common.service;

import com.lingyun.wh.common.domain.EncodingRules;
import io.swagger.models.auth.In;

import java.util.List;

/**
* 
* @Author : Tang
* @Description : 编码规则Service接口
* @CreateDate : 2023/11/29 11:08
*
*/
public interface IEncodingRulesService {

    /**
     * 查询编码规则
     *
     * @param erId 编码规则主键
     * @return 编码规则
     */
    public EncodingRules selectEncodingRulesByErId(Integer erId);

    /**
     * 查询编码规则列表
     *
     * @param encodingRules 编码规则
     * @return 编码规则集合
     */
    public List<EncodingRules> selectEncodingRulesList(EncodingRules encodingRules);

    /**
     * 新增编码规则
     *
     * @param encodingRules 编码规则
     * @return 结果
     */
    public int insertEncodingRules(EncodingRules encodingRules);

    /**
     * 修改编码规则
     *
     * @param encodingRules 编码规则
     * @return 结果
     */
    public int updateEncodingRules(EncodingRules encodingRules);

    /**
     * 批量删除编码规则
     *
     * @param erIds 需要删除的编码规则主键集合
     * @return 结果
     */
    public int deleteEncodingRulesByErIds(Integer[] erIds);

    /**
     * 删除编码规则信息
     *
     * @param erId 编码规则主键
     * @return 结果
     */
    public int deleteEncodingRulesByErId(Integer erId);


    /**
     * 根据指定编码规则生成编码
     *
     * @param rulesId 目标订单，数据字典
     * @return 结果
     */
    String[] genSpecifyEncoding(int rulesId, int num);

    /**
     * 生成订单编码后，某个服务使用编码，
     * 调用此服务进行将该编码规则的当前流水号增加
     *
     * @param rulesId 目标订单，数据字典
     * @return 结果
     */
    int increaseCurrentSerialNumber(int rulesId, int num);


}
