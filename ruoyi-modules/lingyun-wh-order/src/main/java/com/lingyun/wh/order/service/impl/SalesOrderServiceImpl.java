package com.lingyun.wh.order.service.impl;

import com.lingyun.wh.order.api.domain.SalesDetails;
import com.lingyun.wh.order.api.domain.SalesOrder;
import com.lingyun.wh.order.mapper.SalesOrderMapper;
import com.lingyun.wh.order.service.ISalesOrderService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售订单Service业务层处理
 *
 * @author Tang
 * @date 2023-11-21
 */
@Service
public class SalesOrderServiceImpl implements ISalesOrderService {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    /**
     * 查询销售订单
     *
     * @param sId 销售订单主键
     * @return 销售订单
     */
    @Override
    public SalesOrder selectSalesOrderBySId(String sId) {
        return salesOrderMapper.selectSalesOrderBySId(sId);
    }

    /**
     * 查询销售订单列表
     *
     * @param salesOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<SalesOrder> selectSalesOrderList(SalesOrder salesOrder) {
        return salesOrderMapper.selectSalesOrderList(salesOrder);
    }

    /**
     * 新增销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSalesOrder(SalesOrder salesOrder) {
        salesOrder.setCreateTime(DateUtils.getNowDate());
        int rows = salesOrderMapper.insertSalesOrder(salesOrder);
        insertSalesDetails(salesOrder);
        return rows;
    }

    /**
     * 修改销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSalesOrder(SalesOrder salesOrder) {
        salesOrder.setUpdateTime(DateUtils.getNowDate());
        salesOrderMapper.deleteSalesDetailsBySId(salesOrder.getsId());
        insertSalesDetails(salesOrder);
        return salesOrderMapper.updateSalesOrder(salesOrder);
    }

    /**
     * 批量删除销售订单
     *
     * @param sIds 需要删除的销售订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSalesOrderBySIds(String[] sIds) {
        salesOrderMapper.deleteSalesDetailsBySIds(sIds);
        return salesOrderMapper.deleteSalesOrderBySIds(sIds);
    }

    /**
     * 删除销售订单信息
     *
     * @param sId 销售订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSalesOrderBySId(String sId) {
        salesOrderMapper.deleteSalesDetailsBySId(sId);
        return salesOrderMapper.deleteSalesOrderBySId(sId);
    }

    /**
     * 新增销售明细信息
     *
     * @param salesOrder 销售订单对象
     */
    public void insertSalesDetails(SalesOrder salesOrder) {
        List<SalesDetails> salesDetailsList = salesOrder.getSalesDetailsList();
        String sId = salesOrder.getsId();
        if (StringUtils.isNotNull(salesDetailsList)) {
            List<SalesDetails> list = new ArrayList<SalesDetails>();
            for (SalesDetails salesDetails : salesDetailsList) {
                salesDetails.setgId(sId);
                list.add(salesDetails);
            }
            if (!list.isEmpty()) {
                salesOrderMapper.batchSalesDetails(list);
            }
        }
    }
}
