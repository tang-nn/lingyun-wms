package com.lingyun.wh.contactunits.mapper;

import com.lingyun.wh.contactunits.domain.Consumer;
import com.lingyun.wh.contactunits.pojo.vo.ConsumerVo;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 客户Mapper接口
 * @CreateDate : 2023/11/21 15:11
 */
public interface ConsumerMapper {

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
     * 删除客户
     *
     * @param cId 客户主键
     * @return 结果
     */
    public int deleteConsumerByCId(String cId);

    /**
     * 批量删除客户
     *
     * @param cIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConsumerByCIds(String[] cIds);

    /**
     * 客户表单查询
     */
    List<ConsumerVo> consumerQurey();

    int conUpdate(String cid);
}
