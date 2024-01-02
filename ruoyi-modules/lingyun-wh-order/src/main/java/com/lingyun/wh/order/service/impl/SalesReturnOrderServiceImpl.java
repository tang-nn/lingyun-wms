package com.lingyun.wh.order.service.impl;

import com.lingyun.wh.order.api.domain.SalesReturnDetails;
import com.lingyun.wh.order.api.domain.SalesReturnOrder;
import com.lingyun.wh.order.mapper.SalesReturnOrderMapper;
import com.lingyun.wh.order.service.ISalesReturnOrderService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售退货Service业务层处理
 *
 * @author Tang
 * @date 2023-11-21
 */
@Service
public class SalesReturnOrderServiceImpl implements ISalesReturnOrderService {
    @Autowired
    private SalesReturnOrderMapper salesReturnOrderMapper;

    /**
     * 查询销售退货
     *
     * @param srId 销售退货主键
     * @return 销售退货
     */
    @Override
    public SalesReturnOrder selectSalesReturnOrderBySrId(String srId) {
        return salesReturnOrderMapper.selectSalesReturnBySrId(srId);
    }

    /**
     * 查询销售退货列表
     *
     * @param salesReturnOrder 销售退货
     * @return 销售退货
     */
    @Override
    public List<SalesReturnOrder> selectSalesReturnOrderList(SalesReturnOrder salesReturnOrder) {
        return salesReturnOrderMapper.selectSalesReturnList(salesReturnOrder);
    }

    /**
     * 新增销售退货
     *
     * @param salesReturnOrder 销售退货
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSalesReturnOrder(SalesReturnOrder salesReturnOrder) {
        salesReturnOrder.setCreateTime(DateUtils.getNowDate());
        int rows = salesReturnOrderMapper.insertSalesReturn(salesReturnOrder);
        insertSalesReturnDetails(salesReturnOrder);
        return rows;
    }

    /**
     * 修改销售退货
     *
     * @param salesReturnOrder 销售退货
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSalesReturnOrder(SalesReturnOrder salesReturnOrder) {
        salesReturnOrder.setUpdateTime(DateUtils.getNowDate());
        salesReturnOrderMapper.deleteSalesReturnDetailsBySrId(salesReturnOrder.getSrId());
        insertSalesReturnDetails(salesReturnOrder);
        return salesReturnOrderMapper.updateSalesReturn(salesReturnOrder);
    }

    /**
     * 批量删除销售退货
     *
     * @param srIds 需要删除的销售退货主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSalesReturnOrderBySrIds(String[] srIds) {
        salesReturnOrderMapper.deleteSalesReturnDetailsBySrIds(srIds);
        return salesReturnOrderMapper.deleteSalesReturnBySrIds(srIds);
    }

    /**
     * 删除销售退货信息
     *
     * @param srId 销售退货主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSalesReturnOrderBySrId(String srId) {
        salesReturnOrderMapper.deleteSalesReturnDetailsBySrId(srId);
        return salesReturnOrderMapper.deleteSalesReturnBySrId(srId);
    }

    /**
     * 新增销售退货明细信息
     *
     * @param salesReturnOrder 销售退货对象
     */
    public void insertSalesReturnDetails(SalesReturnOrder salesReturnOrder) {
        List<SalesReturnDetails> salesReturnDetailsList = salesReturnOrder.getSalesReturnDetailsList();
        String srId = salesReturnOrder.getSrId();
        if (StringUtils.isNotNull(salesReturnDetailsList)) {
            List<SalesReturnDetails> list = new ArrayList<SalesReturnDetails>();
            for (SalesReturnDetails salesReturnDetails : salesReturnDetailsList) {
                salesReturnDetails.setSrId(srId);
                list.add(salesReturnDetails);
            }
            if (!list.isEmpty()) {
                salesReturnOrderMapper.batchSalesReturnDetails(list);
            }
        }
    }
}
