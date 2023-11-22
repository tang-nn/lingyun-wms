package com.lingyun.wh.goods.mapper;

import com.lingyun.wh.goods.doman.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 货品信息Mapper接口
 *
 * @author lijin
 * @date 2023-11-21
 */
@Repository
public interface GoodsMapper {
    /**
     * 查询货品信息
     *
     * @param gId 货品信息主键
     * @return 货品信息
     */
    public Goods selectGoodsByGId(String gId);

    /**
     * 查询货品信息列表
     *
     * @param goods 货品信息
     * @return 货品信息集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增货品信息
     *
     * @param goods 货品信息
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改货品信息
     *
     * @param goods 货品信息
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 删除货品信息
     *
     * @param gId 货品信息主键
     * @return 结果
     */
    public int deleteGoodsByGId(String gId);

    /**
     * 批量删除货品信息
     *
     * @param gIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByGIds(String[] gIds);

}
