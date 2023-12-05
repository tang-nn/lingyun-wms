package com.lingyun.wh.common.mapper;

import com.lingyun.wh.common.domain.EncodingRules;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 编码规则Mapper接口
 * @CreateDate : 2023/11/29 11:06
 */
public interface EncodingRulesMapper {
    /**
     * 查询编码规则
     *
     * @param erId 编码规则主键
     * @return 编码规则
     */
    public EncodingRules selectEncodingRules(@Param("erId") Integer erId, @Param("targetForm") String targetForm);

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
     * 删除编码规则
     *
     * @param erId 编码规则主键
     * @return 结果
     */
    public int deleteEncodingRulesByErId(Integer erId);

    /**
     * 批量删除编码规则
     *
     * @param erIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEncodingRulesByErIds(Integer[] erIds);
}
