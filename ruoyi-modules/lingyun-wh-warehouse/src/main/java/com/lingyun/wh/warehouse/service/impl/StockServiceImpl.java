package com.lingyun.wh.warehouse.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lingyun.wh.warehouse.mapper.StockMapper;
import com.lingyun.wh.goods.api.domain.Stock;
import com.lingyun.wh.warehouse.service.IStockService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 库存Service业务层处理
 *
 * @author Tang
 * @date 2023-12-17
 */
@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询库存
     *
     * @param sid 库存主键
     * @return 库存
     */
    @Override
    public Stock selectStockBySid(String sid) {
        return stockMapper.selectStockBySid(sid);
    }

    /**
     * 查询库存列表
     *
     * @param stock 库存
     * @return 库存
     */
    @Override
    public List<Stock> selectStockList(Stock stock) {
        return stockMapper.selectStockList(stock);
    }

    /**
     * 查询单个库存
     *
     * @param slid 库存库位 ID
     * @param wid  仓库 ID
     * @param gid  商品 ID
     * @return 库存
     */
    @Override
    public Stock selectStock(String slid, String wid, String gid) {
        Stock stock = new Stock();
        stock.setSlid(slid);
        stock.setGid(gid);
        stock.setWid(wid);
        List<Stock> stocks = stockMapper.selectStockList(stock);
        if (stocks == null || stocks.size() == 0) {
            return null;
        } else {
            return stocks.get(0);
        }
    }

    /**
     * 新增库存
     *
     * @param stock 库存
     * @return 结果
     */
    @Override
    public int insertStock(Stock stock) {
        stock.setCreateTime(DateUtils.getNowDate());
        return stockMapper.insertStock(stock);
    }

    /**
     * 批量新增库存
     *
     * @param stocks 库存
     * @return 结果
     */
    @Transactional(rollbackFor = SQLException.class)
    public boolean batchInsertStock(List<Stock> stocks) {
        if (stocks == null || stocks.size() == 0) {
            return true;
        }
        return stockMapper.batchInsertStock(stocks) > 0;
    }

    /**
     * 批量添加库存, -- 业务:
     *
     * @param stocks 库存
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAddStock(List<Stock> stocks) {
        if (stocks == null || stocks.size() == 0) {
            //
            return false;
        }
        List<Stock> stockToBeAdded = new ArrayList<>();
        List<Stock> stockToBeUpdated = new ArrayList<>();
        stocks.forEach(stock -> {
            Stock goodsStock = selectStock(stock.getSlid(), stock.getWid(), stock.getGid());
            goodsStock = goodsStock == null ? new Stock() : goodsStock;
            Date nowDate = DateUtils.getNowDate();
            String userId = String.valueOf(SecurityUtils.getUserId());
            stock.setUpdateTime(nowDate);
            stock.setUpdateBy(userId);
            if (StringUtils.isNotEmpty(goodsStock.getSid())) {
                // 库存 id 不为空，添加到需要更新的集合
                stock.setUpdateTime(nowDate);
                stock.setUpdateBy(userId);
                stock.setSid(goodsStock.getSid());
                stock.setItemQuantity(stock.getItemQuantity().add(goodsStock.getItemQuantity()));
                stock.setNumberPlans(stock.getNumberPlans().add(goodsStock.getNumberPlans()));
                stockToBeUpdated.add(stock);
            } else {
                stock.setCreateBy(userId);
                stock.setCreateTime(nowDate);
                stock.setUpdateTime(nowDate);
                stock.setUpdateBy(userId);
                BigDecimal iq = stock.getItemQuantity();
                stock.setItemQuantity(iq != null ? iq : BigDecimal.ZERO);
                stock.setNumberPlans(iq != null ? iq : BigDecimal.ZERO);
                stockToBeAdded.add(stock);
            }
            Assert.isTrue(stock.getItemQuantity().compareTo(BigDecimal.ZERO) >= 0, "库存数量不足，请检查出库明细");
        });
        System.out.println("stockToBeAdded: ");
        stockToBeAdded.forEach(System.out::println);
        System.out.println("stockToBeUpdated: ");
        stockToBeUpdated.forEach(System.out::println);
        batchInsertStock(stockToBeAdded);
        try {
            boolean f = batchUpdateStock(stockToBeUpdated);
            if (!f) {
                throw new RuntimeException("批量修改库存失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 批量修改库存 - 开启新事务
     *
     * @param stocks 库存
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchUpdateStock(List<Stock> stocks) {
        if (stocks == null || stocks.size() == 0) {
            return true;
        }
        boolean success = true;
        for (Stock stock : stocks) {
            success = success && (stockMapper.updateStock(stock) > 0);
            System.out.println("stock: " + stock + "; 执行结果：" + success);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    /**
     * 批量删除库存
     *
     * @param sids 需要删除的库存主键
     * @return 结果
     */
    @Override
    public int deleteStockBySids(String[] sids) {
        return stockMapper.deleteStockBySids(sids);
    }

    /**
     * 删除库存信息
     *
     * @param sid 库存主键
     * @return 结果
     */
    @Override
    public int deleteStockBySid(String sid) {
        return stockMapper.deleteStockBySid(sid);
    }
}