package com.lingyun.wh.goods.service.impl;

import com.lingyun.wh.goods.doman.Goods;
import com.lingyun.wh.goods.mapper.GoodsMapper;
import com.lingyun.wh.goods.service.IGoodsService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 货品信息Service业务层处理
 *
 * @author lijin
 * @date 2023-11-21
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询货品信息
     *
     * @param gId 货品信息主键
     * @return 货品信息
     */
    @Override
    public Goods selectGoodsByGId(String gId)
    {
        return goodsMapper.selectGoodsByGId(gId);
    }

    /**
     * 查询货品信息列表
     *
     * @param goods 货品信息
     * @return 货品信息
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增货品信息
     *
     * @param goods 货品信息
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        goods.setCreateTime(DateUtils.getNowDate());
        return goodsMapper.insertGoods(goods);
    }

    /**
     * 修改货品信息
     *
     * @param goods 货品信息
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除货品信息
     *
     * @param gIds 需要删除的货品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByGIds(String[] gIds)
    {
        return goodsMapper.deleteGoodsByGIds(gIds);
    }

    /**
     * 删除货品信息信息
     *
     * @param gId 货品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByGId(String gId)
    {
        return goodsMapper.deleteGoodsByGId(gId);
    }
}
