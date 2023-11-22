package com.lingyun.wh.contactunits.service;

import com.lingyun.wh.contactunits.domain.Consumer;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 客户Service接口
 * @CreateDate : 2023/11/21 15:16
 */
public interface IConsumerService {

    /**
     * 查询客户
     *
     * @param cId 客户主键
     * @return 客户
     */
    public Consumer selectConsumerByCId(String cId);

    /**
     * 查询客户列表
     *
     * @param consumer 客户
     * @return 客户集合
     */
    public List<Consumer> selectConsumerList(Consumer consumer);

    /**
     * 新增客户
     *
     * @param consumer 客户
     * @return 结果
     */
    public int insertConsumer(Consumer consumer);

    /**
     * 修改客户
     *
     * @param consumer 客户
     * @return 结果
     */
    public int updateConsumer(Consumer consumer);

    /**
     * 批量删除客户
     *
     * @param cIds 需要删除的客户主键集合
     * @return 结果
     */
    public int deleteConsumerByCIds(String[] cIds);

    /**
     * 删除客户信息
     *
     * @param cId 客户主键
     * @return 结果
     */
    public int deleteConsumerByCId(String cId);

}
