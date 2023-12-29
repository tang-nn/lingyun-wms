package com.lingyun.wh.goods.mapper;

import com.lingyun.wh.goods.api.domain.GoodsType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 货品类型Mapper接口
 *
 * @author Lijin
 * @date 2023-11-21
 */
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
     * 根据商品类型名称查询是否存在
     * @param gtname
     * @return
     */
    public int selectGoodsTypeByGname(String gtname);

    /**
     * 查询货品类型列表
     *
     * @return 货品类型集合
     */
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType);

    /***
     * 查询货品类型下拉框
     */

    public List<GoodsType> selectGoodsTypes();

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
     * 查询货品下面是否有子类商品
     * @param gtId
     * @return
     */
    public int countByIsDelete(@Param("gtId") String [] gtId);

    /**
     * 批量删除货品类型
     *
     * @param gtIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsTypeByGtIds(@Param("gtIds") String[] gtIds);

    //子类
    public int deleteGoodsTypeByParentGtIds(@Param("gtIds") String[] gtIds);
}

