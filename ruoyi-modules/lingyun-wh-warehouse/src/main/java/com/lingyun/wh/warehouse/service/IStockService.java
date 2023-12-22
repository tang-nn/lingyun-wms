package com.lingyun.wh.warehouse.service;

import java.sql.SQLException;
import java.util.List;

import com.lingyun.wh.warehouse.domain.Stock;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存Service接口
 *
 * @author Tang
 * @date 2023-12-17
 */
public interface IStockService {
    /**
     * 查询库存
     *
     * @param sid 库存主键
     * @return 库存
     */
    public Stock selectStockBySid(String sid);

    /**
     * 查询库存列表
     *
     * @param stock 库存
     * @return 库存集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 查询单个库存
     *
     * @param slid 库存库位 ID
     * @param wid  仓库 ID
     * @param gid  商品 ID
     * @return 库存
     */
    Stock selectStock(String slid, String wid, String gid);

    /**
     * 新增库存
     *
     * @param stock 库存
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 批量添加库存, -- 业务: 新增入库, 修改计划数量
     *
     * @param stocks 库存
     * @return 结果
     */
    @Transactional(rollbackFor = SQLException.class)
    boolean batchAddStock(List<Stock> stocks);

    /**
     * 修改库存
     *
     * @param stocks 库存
     * @return 结果
     */
    boolean batchUpdateStock(List<Stock> stocks);

    /**
     * 批量删除库存
     *
     * @param sids 需要删除的库存主键集合
     * @return 结果
     */
    public int deleteStockBySids(String[] sids);

    /**
     * 删除库存信息
     *
     * @param sid 库存主键
     * @return 结果
     */
    public int deleteStockBySid(String sid);
}
