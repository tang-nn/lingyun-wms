package com.lingyun.wh.goods.mapper;


import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.goods.api.domain.WarningInfo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货品信息Mapper接口
 *
 * @author lijin
 * @date 2023-11-21
 */
public interface GoodsMapper {

    /**
     * 查询货品信息列表
     *
     * @param
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

    //新增预警
    public int insertWarning(WarningInfo warningInfo);
    //删除预警
    public int deleteWarnId(String warnId);
    /**
     * 查询货品信息
     *
     * @param gId 货品信息主键
     * @return 货品信息
     */
    public Goods selectGoodsByGId(String gId);
    /**
     * 根据多个 ID 获取多个货品库存信息详细信息，主要用于入库相关，不能用于出库相关
     * @param ids 货品 ID 数组
     * @author tang-nn
     * @return
     */
    ArrayList<Goods> selectGoodsByIds(@Param("ids") String[] ids, @Param("params") Map<String, Object> params);
    /**
     * 根据商品名称查询是否存在
     * @param gname
     * @return
     */
    public int selectGoodsByGname(String gname);

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
     * @param gIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByGIds(String[] gIds);


    /**
     * 根据商品名称查询是否存在
     * @param gname
     * @return
     */
    public int selectGoodsByGname(String gname);

}
