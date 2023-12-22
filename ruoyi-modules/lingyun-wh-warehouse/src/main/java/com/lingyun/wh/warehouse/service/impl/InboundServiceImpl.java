package com.lingyun.wh.warehouse.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.lingyun.wh.goods.api.domain.Stock;
import com.lingyun.wh.warehouse.service.IStockService;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lingyun.wh.warehouse.domain.InboundDetails;
import com.lingyun.wh.warehouse.mapper.InboundMapper;
import com.lingyun.wh.warehouse.domain.Inbound;
import com.lingyun.wh.warehouse.service.IInboundService;

/**
 * 入库管理Service业务层处理
 *
 * @author Tang
 * @date 2023-12-18
 */
@Service
public class InboundServiceImpl implements IInboundService {

    private static final Logger log = LoggerFactory.getLogger(InboundServiceImpl.class);
    @Autowired
    RemoteEncodeRuleService remoteEncodeRuleService;
    @Autowired
    private InboundMapper inboundMapper;

    @Autowired
    private IStockService stockService;

    /**
     * 查询入库管理
     *
     * @param inid 入库 ID
     * @return 入库管理
     */
    @Override
    public Inbound selectInboundByInid(String inid) {
        Inbound inbound = new Inbound();
        inbound.setInid(inid);
        List<Inbound> inbounds = inboundMapper.selectInboundList(inbound);
        return inbounds != null && inbounds.size() > 0 ? inbounds.get(0) : null;
    }

    /**
     * 查询入库管理列表
     *
     * @param inbound 入库管理
     * @return 入库管理
     */
    @Override
    public List<Inbound> selectInboundList(Inbound inbound) {
        return inboundMapper.selectInboundList(inbound);
    }

    /**
     * 新增入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    @Transactional(rollbackFor = SQLException.class)
    @Override
    public int insertInbound(Inbound inbound) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.INBOUND_ORDER, 1, SecurityConstants.INNER);
        // System.out.println("res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertInbound 获取编码失败");
            throw new RuntimeException("获取编码失败");
        }
        inbound.setStatus("is_pending_review");
        inbound.setInCode(res.getData()[0]);
        inbound.setCreateTime(nowDate);
        inbound.setCreateBy(userId);
        inbound.setUpdateBy(userId);
        inbound.setUpdateTime(nowDate);
        int rows = inboundMapper.insertInbound(inbound);
        insertInboundDetails(inbound);
        R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.INBOUND_ORDER, 1, SecurityConstants.INNER);
        if (r.getCode() != 200) {
            throw new RuntimeException("编号迭代失败");
        }
        return rows;
    }

    /**
     * 修改入库管理
     *
     * @param inbound 入库管理
     * @return 结果
     */
    @Transactional(rollbackFor = SQLException.class)
    @Override
    public int updateInbound(Inbound inbound) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        inbound.setUpdateBy(userId);
        inbound.setUpdateTime(nowDate);
        int rows = inboundMapper.deleteInboundDetailsByInId(inbound.getInid());
        System.out.println("deleteInboundDetailsByInId rows: " + rows);
        insertInboundDetails(inbound);
        return inboundMapper.updateInbound(inbound);
    }

    /**
     * 批量删除入库管理
     *
     * @param inids 需要删除的入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundByInids(String[] inids) {
        inboundMapper.deleteInboundDetailsByInIds(inids);
        return inboundMapper.deleteInboundByInids(inids);
    }

    /**
     * 删除入库管理信息
     *
     * @param inid 入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundByInid(String inid) {
        inboundMapper.deleteInboundDetailsByInId(inid);
        return inboundMapper.deleteInboundByInid(inid);
    }

    /**
     * 新增 OR 修改 入库管理明细信息
     *
     * @param inbound 入库管理对象
     */
    @Transactional(rollbackFor = SQLException.class)
    public void insertInboundDetails(Inbound inbound) {
        List<InboundDetails> inboundDetailsList = inbound.getInboundDetails();
        // List<Stock> stocks = new ArrayList<>();
        String inId = inbound.getInid();
        String userId = inbound.getUpdateBy();
        Date nowDate = inbound.getUpdateTime();
        if (StringUtils.isNotNull(inboundDetailsList)) {
            List<InboundDetails> list = new ArrayList<>();
            for (InboundDetails id : inboundDetailsList) {
                id.setInid(inId);
                id.setCreateBy(userId);
                id.setCreateTime(nowDate);
                id.setUpdateBy(userId);
                id.setUpdateTime(nowDate);
                list.add(id);
                // Stock stock = new Stock();
                // stock.setGid(id.getGoodsId());
                // stock.setSlid(id.getSlid());
                // stock.setUnit(id.getUnit());
                // stock.setWid(inbound.getWid());
                // stock.setUnit(id.getUnit());
                // stock.setCreateBy(userId);
                // stock.setCreateTime(nowDate);
                // stock.setUpdateBy(userId);
                // stock.setUpdateTime(nowDate);
                // stock.setNumberPlans(id.getQuantityInbound());
                // stocks.add(stock);
            }
            if (!list.isEmpty()) {
                inboundMapper.batchInboundDetails(list);
                // 添加库存
                // stockService.batchAddStock(stocks);
            }
        }
    }
}