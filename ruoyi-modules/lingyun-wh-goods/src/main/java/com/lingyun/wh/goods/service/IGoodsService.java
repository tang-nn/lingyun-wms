package com.lingyun.wh.goods.service;

import com.lingyun.wh.goods.doman.Goods;
import com.ruoyi.system.api.domain.SysUser;

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

    /**
     * 批量删除货品信息
     *
     * @param gIds 需要删除的货品信息主键集合
     * @return 结果
     */
    public int deleteGoodsByGIds(String[] gIds);

    /**
     * 删除货品信息信息
     *
     * @param gId 货品信息主键
     * @return 结果
     */
    public int deleteGoodsByGId(String gId);

    /**
     * 导入商品数据
     * @param goodsList 商品数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importGoods(List<Goods> goodsList, Boolean isUpdateSupport, Long operName);
}
