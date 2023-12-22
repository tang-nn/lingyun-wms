package com.lingyun.wh.warehouse.mapper;

import java.util.List;

import com.lingyun.wh.warehouse.domain.Stock;

/**
 * 库存Mapper接口
 *
 * @author Tang
 * @date 2023-12-17
 */
public interface StockMapper {
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
     * 新增库存
     *
     * @param stock 库存
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 批量新增库存
     *
     * @param stocks 库存
     * @return 结果
     */
    public int batchInsertStock(List<Stock> stocks);

    /**
     * 修改库存
     *
     * @param stock 库存
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 删除库存
     *
     * @param sid 库存主键
     * @return 结果
     */
    public int deleteStockBySid(String sid);

    /**
     * 批量删除库存
     *
     * @param sids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockBySids(String[] sids);
}
