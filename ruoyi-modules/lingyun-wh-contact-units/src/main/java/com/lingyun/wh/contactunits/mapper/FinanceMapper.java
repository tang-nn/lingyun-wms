package com.lingyun.wh.contactunits.mapper;

import com.lingyun.wh.contactunits.domain.Finance;

/**
 * @Author : Tang
 * @Description : 客户和供应商的财务信息对象 wh_finance
 * @CreateDate : 2023/11/21 10:59
 */
public interface FinanceMapper {
    /**
     * 查询客户和供应商的财务信息
     *
     * @param fId 客户和供应商的财务信息主键
     * @return 客户和供应商的财务信息
     */
    Finance selectFinanceByFId(String fId);

    /**
     * 新增客户和供应商的财务信息
     *
     * @param finance 客户和供应商的财务信息
     * @return 结果
     */
    public int insertFinance(Finance finance);

    /**
     * 修改客户和供应商的财务信息
     *
     * @param finance 客户和供应商的财务信息
     * @return 结果
     */
    public int updateFinance(Finance finance);

    /**
     * 删除客户和供应商的财务信息
     *
     * @param fId 客户和供应商的财务信息主键
     * @return 结果
     */
    public int deleteFinanceByFId(String fId);

    /**
     * 批量删除客户和供应商的财务信息
     *
     * @param fIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceByFIds(String[] fIds);
}