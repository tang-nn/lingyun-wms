package com.lingyun.wh.goods.service.impl;


import com.lingyun.wh.goods.api.domain.GoodsType;
import com.lingyun.wh.goods.mapper.GoodsTypeMapper;
import com.lingyun.wh.goods.service.IGoodsTypeService;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.lingyun.wh.goods.doman.vo.TreeSelect;
import com.ruoyi.common.core.utils.bean.BeanValidators;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 货品类型Service业务层处理
 *
 * @author Lijin
 * @date 2023-11-21
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {
    private static final Logger log = LoggerFactory.getLogger(IGoodsTypeService.class);

    @Autowired
    protected Validator validator;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Autowired
    private RemoteEncodeRuleService remoteEncodeRuleService;

    /**
     * 查询货品类型
     *
     * @param gtId 货品类型主键
     * @return 货品类型
     */
    @Override
    public GoodsType selectGoodsTypeByGtId(String gtId) {
        return goodsTypeMapper.selectGoodsTypeByGtId(gtId);
    }

    @Override
    public int selectGoodsTypeByGname(String gtname) {
        return goodsTypeMapper.selectGoodsTypeByGname(gtname);
    }

    /**
     * 查询货品类型列表
     *
     * @return 货品类型
     */
    @Override
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType) {
        System.out.println(goodsType + "*******************************");
        List<GoodsType> goodsTypes = goodsTypeMapper.selectGoodsTypeList(goodsType);
        List<GoodsType> list = new ArrayList<GoodsType>();
        if (goodsTypes.size() > 0) {
            list = this.diGui(goodsTypes.get(0).getParentId(), goodsTypes, new ArrayList<GoodsType>());
        }
        return list;
    }

    private List<GoodsType> diGui(String id, List<GoodsType> goods, List<GoodsType> list) {
        for (GoodsType good : goods) {
            if (good.getParentId().equals(id)) {
                list.add(good);
                this.diGui(good.getGtId(), goods, list);
            }
        }
        return list;
    }

    /**
     * 查询部门树结构信息
     *
     * @return 货品树信息集合
     */
    @Override
    public List<TreeSelect> selectGoodsTypeTreeList() {
        List<GoodsType> gt = selectGoodsTypes();
        return buildTreeSelect(gt);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param gt
     * @return 下拉树结构列表
     */
    private List<TreeSelect> buildTreeSelect(List<GoodsType> gt) {
        List<GoodsType> gtTree = buildGoodsType(gt);
        return gtTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    /**
     * 构建前端所需要树结构
     *
     * @param gts 货品类型列表
     * @return 树结构列表
     */
    private List<GoodsType> buildGoodsType(List<GoodsType> gts) {
        List<GoodsType> returnList = new ArrayList<>();
        List<String> tempList = gts.stream().map(GoodsType::getGtId).collect(Collectors.toList());
        for (GoodsType goodsType : gts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(goodsType.getParentId())) {
                recursionFn(gts, goodsType);
                returnList.add(goodsType);
            }
        }
        if (returnList.isEmpty()) {
            returnList = gts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<GoodsType> list, GoodsType gt) {
        // 得到子节点列表
        List<GoodsType> childList = getChildList(list, gt);
        gt.setChildren(childList);
        for (GoodsType tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<GoodsType> getChildList(List<GoodsType> list, GoodsType gt) {
        List<GoodsType> tlist = new ArrayList<GoodsType>();
        Iterator<GoodsType> it = list.iterator();
        while (it.hasNext()) {
            GoodsType n = (GoodsType) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(gt.getGtId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<GoodsType> list, GoodsType gt) {
        return getChildList(list, gt).size() > 0 ? true : false;
    }

    @Override
    public List<GoodsType> selectGoodsTypes() {
        return goodsTypeMapper.selectGoodsTypes();
    }

    @Override
    public int countByIsDelete(String[] gtId) {
        return goodsTypeMapper.countByIsDelete(gtId);
    }


    /**
     * 新增货品类型
     *
     * @param goodsType 货品类型
     * @return 结果
     */
    @Transactional(rollbackFor = SQLException.class)
    @Override
    public int insertGoodsType(GoodsType goodsType) {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        goodsType.setCreateBy(uid);
        goodsType.setCreateTime(nowDate);
        goodsType.setUpdateBy(uid);
        goodsType.setUpdateTime(nowDate);
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.GOODS_TYPE, 1, SecurityConstants.INNER);
        System.out.println("货品类型编码获取 res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertGoodsType 货品类型编码获取失败");
            throw new RuntimeException("货品类型编码获取失败");
        } else {
            goodsType.setGtCode(res.getData()[0]);
        }
        int i = goodsTypeMapper.insertGoodsType(goodsType);
        if (i > 0) {
            R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.GOODS_TYPE, 1, SecurityConstants.INNER);
            if (r == null || r.getCode() != 200) {
                log.error("insertGoodsType 流水号迭代失败");
                throw new RuntimeException("进货数据插入失败");
            }
        }
        return i;
    }

    /**
     * 修改货品类型
     *
     * @param goodsType 货品类型
     * @return 结果
     */
    @Override
    public int updateGoodsType(GoodsType goodsType) {
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
    public int deleteGoodsTypeByGtIds(String[] gtIds) {
        System.out.println("gtIds===" + gtIds.length);
        int i = goodsTypeMapper.countByIsDelete(gtIds);//查询旗下是否有子类
        System.out.println("i======" + i);
        if (i > 0) {
            //有子类
            int father = goodsTypeMapper.deleteGoodsTypeByGtIds(gtIds);//删除所有父类
            int child = goodsTypeMapper.deleteGoodsTypeByParentGtIds(gtIds);//删除所有子类
            if (father > 0 && child > 0) {
                return 1;
            }

        }
        return goodsTypeMapper.deleteGoodsTypeByGtIds(gtIds);//删除所有父类


    }

    @Override
    public String importGoodsType(List<GoodsType> goodsTypeListList, Boolean isUpdateSupport) {
        System.out.println("=====isUpdateSupport:" + isUpdateSupport);//1  false
        if (StringUtils.isNull(goodsTypeListList) || goodsTypeListList.size() == 0) {
            throw new ServiceException("导入商品类型数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (GoodsType goodsType : goodsTypeListList) {
            try {
                // 验证是否存在这个商品
                int u = goodsTypeMapper.selectGoodsTypeByGname(goodsType.getGtName());
                System.out.println("是否存在此商品：" + u);
                if (u == 0) {
                    //不存在
                    BeanValidators.validateWithException(validator, goodsType);
                    goodsTypeMapper.insertGoodsType(goodsType);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、货品 " + goodsType.getGtName() + " 导入成功");
                } else if (isUpdateSupport) {
                    //更新已经存在的商品数据
                    BeanValidators.validateWithException(validator, goodsType);
                    int i = goodsTypeMapper.updateGoodsType(goodsType);
                    if (i > 0) {
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、货品 " + goodsType.getGtName() + " 更新成功");
                    } else {
                        failureNum++;
                        successMsg.append("<br/>" + successNum + "、货品 " + goodsType.getGtName() + " 更新失败");
                    }

                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、货品 " + goodsType.getGtName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、货品 " + goodsType.getGtName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {

            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


}