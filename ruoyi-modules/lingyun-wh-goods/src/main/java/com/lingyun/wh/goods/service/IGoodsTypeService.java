package com.lingyun.wh.goods.service;

import com.lingyun.wh.goods.doman.Goods;
import com.lingyun.wh.goods.doman.GoodsType;
import com.lingyun.wh.goods.doman.vo.TreeSelect;

import java.util.List;
import java.util.Map;

/**
 * 货品类型Service接口
 *
 * @author Lijin
 * @date 2023-11-21
 */
public interface IGoodsTypeService
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
    public List<GoodsType>  selectGoodsTypeList(GoodsType goodsType);

    /**
     * 查询货品树结构信息
     *
     * @return 货品树信息集合
     */
    List<TreeSelect> selectGoodsTypeTreeList();

    /***
     * 查询货品类型下拉框
     */

    public List<GoodsType>selectGoodsTypes();



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
     * 批量删除货品类型
     *
     * @param gtIds 需要删除的货品类型主键集合
     * @return 结果
     */
    public int deleteGoodsTypeByGtIds(String[] gtIds);


    /**
     * 导入商品类型数据
     */
    public String importGoodsType(List<GoodsType> goodsTypeListList, Boolean isUpdateSupport);

}

