package com.lingyun.wh.goods.service.impl;

import com.lingyun.wh.goods.doman.GoodsType;
import com.lingyun.wh.goods.mapper.GoodsTypeMapper;
import com.lingyun.wh.goods.service.IGoodsTypeService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 货品类型Service业务层处理
 *
 * @author Lijin
 * @date 2023-11-21
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService
{
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    /**
     * 查询货品类型
     *
     * @param gtId 货品类型主键
     * @return 货品类型
     */
    @Override
    public GoodsType selectGoodsTypeByGtId(String gtId)
    {
        return goodsTypeMapper.selectGoodsTypeByGtId(gtId);
    }

    /**
     * 查询货品类型列表
     *
     * @param goodsType 货品类型
     * @return 货品类型
     */
    @Override
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType)
    {
        return goodsTypeMapper.selectGoodsTypeList(goodsType);
    }


    @Override
    public List<GoodsType> selectGoodsTypes() {
        return goodsTypeMapper.selectGoodsTypes();
    }


    /**
     * 新增货品类型
     *
     * @param goodsType 货品类型
     * @return 结果
     */
    @Override
    public int insertGoodsType(GoodsType goodsType)
    {
        goodsType.setCreateTime(DateUtils.getNowDate());
        return goodsTypeMapper.insertGoodsType(goodsType);
    }

    /**
     * 修改货品类型
     *
     * @param goodsType 货品类型
     * @return 结果
     */
    @Override
    public int updateGoodsType(GoodsType goodsType)
    {
        goodsType.setUpdateTime(DateUtils.getNowDate());
        return goodsTypeMapper.updateGoodsType(goodsType);
    }

    /**
     * 批量删除货品类型
     *
     * @param gtIds 需要删除的货品类型主键
     * @return 结果
     */
    @Override
    public int deleteGoodsTypeByGtIds(String[] gtIds)
    {
        return goodsTypeMapper.deleteGoodsTypeByGtIds(gtIds);
    }

    /**
     * 删除货品类型信息
     *
     * @param gtId 货品类型主键
     * @return 结果
     */
    @Override
    public int deleteGoodsTypeByGtId(String gtId)
    {
        return goodsTypeMapper.deleteGoodsTypeByGtId(gtId);
    }
}