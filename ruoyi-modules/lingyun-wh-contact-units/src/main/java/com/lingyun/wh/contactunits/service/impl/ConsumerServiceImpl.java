package com.lingyun.wh.contactunits.service.impl;

import com.lingyun.wh.contactunits.domain.Consumer;
import com.lingyun.wh.contactunits.mapper.ConsumerMapper;
import com.lingyun.wh.contactunits.pojo.vo.ConsumerVo;
import com.lingyun.wh.contactunits.service.IConsumerService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Tang
 * @Description : 客户Service业务层处理
 * @CreateDate : 2023/11/21 15:17
 */
@Service
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 查询客户
     *
     * @param cId 客户主键
     * @return 客户
     */
    @Override
    public Consumer selectConsumerByCId(String cId) {
        return consumerMapper.selectConsumerByCId(cId);
    }

    /**
     * 查询客户列表
     *
     * @param consumer 客户
     * @return 客户
     */
    @Override
    public List<Consumer> selectConsumerList(Consumer consumer) {
        return consumerMapper.selectConsumerList(consumer);
    }

    /**
     * 新增客户
     *
     * @param consumer 客户
     * @return 结果
     */
    @Override
    public int insertConsumer(Consumer consumer) {
        consumer.setCreateTime(DateUtils.getNowDate());
        return consumerMapper.insertConsumer(consumer);
    }

    /**
     * 修改客户
     *
     * @param consumer 客户
     * @return 结果
     */
    @Override
    public int updateConsumer(Consumer consumer) {
        consumer.setUpdateTime(DateUtils.getNowDate());
        return consumerMapper.updateConsumer(consumer);
    }

    /**
     * 批量删除客户
     *
     * @param cIds 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteConsumerByCIds(String[] cIds) {
        return consumerMapper.deleteConsumerByCIds(cIds);
    }

    /**
     * 删除客户信息
     *
     * @param cId 客户主键
     * @return 结果
     */
    @Override
    public int deleteConsumerByCId(String cId) {
        return consumerMapper.deleteConsumerByCId(cId);
    }
    @Override
    public List<ConsumerVo> consumerQurey(){
        return consumerMapper.consumerQurey();
    };

    @Override
    public boolean conUpdate(String cid){

        return consumerMapper.conUpdate(cid) > 0;
    }
}
