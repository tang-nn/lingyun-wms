package com.lingyun.wh.goods.mapper;

import com.lingyun.wh.goods.doman.GoodsType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 货品类型Mapper接口
 *
 * @author Lijin
 * @date 2023-11-21
 */
@Repository
public interface GoodsTypeMapper
{
    /**
     * 查询货品类型
     *
     * @param gtId 货品类型主键
     * @return 货品类型
     */
    public GoodsType selectGoodsTypeByGtId(String gtId);

    /**
     * 查询货品类型列表
     *
     * @param goodsType 货品类型
     * @return 货品类型集合
     */
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType);

    /**
     * 新增货品类型
     *
     * @param goodsType 货品类型
     * @return 结果
     */
    public int insertGoodsType(GoodsType goodsType);

    /**
     * 修改货品类型
     *
     * @param goodsType 货品类型
     * @return 结果
     */
    public int updateGoodsType(GoodsType goodsType);

    /**
     * 删除货品类型
     *
     * @param gtId 货品类型主键
     * @return 结果
     */
    public int deleteGoodsTypeByGtId(String gtId);

    /**
     * 批量删除货品类型
     *
     * @param gtIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsTypeByGtIds(String[] gtIds);
}

