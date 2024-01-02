package com.lingyun.wh.goods.service;


import com.lingyun.wh.goods.api.domain.Goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货品信息Service接口
 *
 * @author lijin
 * @date 2023-11-21
 */
public interface IGoodsService {
    /**
     * 查询货品信息
     *
     * @param gId 货品信息主键
     * @return 货品信息
     */
    public Goods selectGoodsByGId(String gId);

    /**
     * 根据多个 ID 获取多个货品信息详细信息
     * @param ids 货品 ID 数组
     * @author Tang
     * @return
     */
    ArrayList<Goods> queryGoodsByIds(String[] ids);

    /**
     * 根据商品名称查询是否存在
     * @param gname
     * @return
     */
    public int selectGoodsByGname(String gname);
    /**
     * 查询货品信息列表
     *
     * @return 货品信息集合
     */
    public List<Map<String,Object>> selectGoodsList(Map<String,Object>map);

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

    public int updateGoodsStatus(Goods goods);

    /**
     * 批量删除货品信息
     *
     * @param gIds 需要删除的货品信息主键集合
     * @return 结果
     */
    public int deleteGoodsByGIds(String[] gIds);


    /**
     * 导入商品数据
     * @param goodsList 商品数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importGoods(List<Goods> goodsList, Boolean isUpdateSupport, Long operName);
}
