package com.lingyun.wh.warehouse.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lingyun.wh.warehouse.mapper.OutboundDetailsMapper;
import com.lingyun.wh.warehouse.domain.OutboundDetails;
import com.lingyun.wh.warehouse.service.IOutboundDetailsService;

/**
 * 出库管理明细Service业务层处理
 * 
 * @author Tang
 * @date 2023-12-26
 */
@Service
public class OutboundDetailsServiceImpl implements IOutboundDetailsService 
{
    @Autowired
    private OutboundDetailsMapper outboundDetailsMapper;

    /**
     * 查询出库管理明细
     * 
     * @param obdId 出库管理明细主键
     * @return 出库管理明细
     */
    @Override
    public OutboundDetails selectOutboundDetailsByObdId(String obdId)
    {
        return outboundDetailsMapper.selectOutboundDetailsByObdId(obdId);
    }

    /**
     * 查询出库管理明细列表
     * 
     * @param outboundDetails 出库管理明细
     * @return 出库管理明细
     */
    @Override
    public List<OutboundDetails> selectOutboundDetailsList(OutboundDetails outboundDetails)
    {
        return outboundDetailsMapper.selectOutboundDetailsList(outboundDetails);
    }

    /**
     * 新增出库管理明细
     * 
     * @param outboundDetails 出库管理明细
     * @return 结果
     */
    @Override
    public int insertOutboundDetails(OutboundDetails outboundDetails)
    {
        outboundDetails.setCreateTime(DateUtils.getNowDate());
        return outboundDetailsMapper.insertOutboundDetails(outboundDetails);
    }

    /**
     * 修改出库管理明细
     * 
     * @param outboundDetails 出库管理明细
     * @return 结果
     */
    @Override
    public int updateOutboundDetails(OutboundDetails outboundDetails)
    {
        outboundDetails.setUpdateTime(DateUtils.getNowDate());
        return outboundDetailsMapper.updateOutboundDetails(outboundDetails);
    }

    /**
     * 批量删除出库管理明细
     * 
     * @param obdIds 需要删除的出库管理明细主键
     * @return 结果
     */
    @Override
    public int deleteOutboundDetailsByObdIds(String[] obdIds)
    {
        return outboundDetailsMapper.deleteOutboundDetailsByObdIds(obdIds);
    }

    /**
     * 删除出库管理明细信息
     * 
     * @param obdId 出库管理明细主键
     * @return 结果
     */
    @Override
    public int deleteOutboundDetailsByObdId(String obdId)
    {
        return outboundDetailsMapper.deleteOutboundDetailsByObdId(obdId);
    }
}
