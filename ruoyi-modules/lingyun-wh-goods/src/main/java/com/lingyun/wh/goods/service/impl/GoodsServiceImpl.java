package com.lingyun.wh.goods.service.impl;

import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.goods.api.domain.WarningInfo;
import com.lingyun.wh.goods.mapper.GoodsMapper;
import com.lingyun.wh.goods.service.IGoodsService;
import com.ruoyi.common.core.constant.AttachmentType;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanValidators;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAnnexService;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import com.ruoyi.system.api.domain.Annex;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Validator;
import java.sql.SQLException;
import java.util.*;


/**
 * 货品信息Service业务层处理
 *
 * @author lijin
 * @date 2023-11-21
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    private static final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Autowired
    protected Validator validator;
    @Autowired
    private RemoteEncodeRuleService remoteEncodeRuleService;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RemoteAnnexService remoteAnnexService;

    /**
     * 查询货品信息
     *
     * @param gId 货品信息主键
     * @return 货品信息
     */

    @Override
    public Goods selectGoodsByGId(String gId)
    {
        Goods goods = goodsMapper.selectGoodsByGId(gId);
        R<List<Annex>> img = remoteAnnexService.list(AttachmentType.PRODUCT_IMAGE, goods.getgId(), null);
        R<List<Annex>> annx = remoteAnnexService.list(AttachmentType.PRODUCT_ANNEX, goods.getgId(), null);
        goods.setGoodsImages(img.getData());
        goods.setGoodsAnnex(annx.getData());
        System.out.println("goodsbyGid==========="+goods);
        return goods;
    }

    /**
     * 根据多个 ID 获取多个货品信息详细信息，主要用于入库相关，不能用于出库相关
     * @param ids 货品 ID 数组，如果 ids 为空则查询所有货品库存信息
     * @return
     */
    @Override
    public ArrayList<Goods> queryGoodsByIds(String[] ids, Map<String, Object> params) {
        return goodsMapper.selectGoodsByIds(ids, params);
    }

    /**
     * 根据商品名称查询是否存在
     * @param gname
     * @return
     */
    @Override
    public int selectGoodsByGname(String gname) {
        return goodsMapper.selectGoodsByGname(gname);
    }


    /**
     * 查询货品信息列表
     * @return 货品信息
     */

    @Override
    public List<Map<String, Object>> selectGoodsList(Map<String, Object> map) {
        return goodsMapper.selectGoodsList(map);
    }


    /**
     * 新增货品信息
     *
     * @param goods 货品信息
     * @return 结果
     */
    @Override

    @Transactional(rollbackFor = SQLException.class)
    public int insertGoods(Goods goods)
    {
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();

        goods.setCreateTime(nowDate);
        goods.setCreateBy(uid);
        goods.setUpdateTime(nowDate);
        goods.setUpdateBy(uid);
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.PRODUCT_INFO, 1, SecurityConstants.INNER);
        System.out.println("货品编码获取 res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertGoodsType 货品编码获取失败");
            throw new RuntimeException("货品编码获取失败");
        } else {
            goods.setGCode(res.getData()[0]);
        }

        System.out.println("oooooooooo"+goods);
        WarningInfo w=new WarningInfo();
        w.setwDays(goods.getwDays());
        goodsMapper.insertWarning(w);
        goods.setWarningId(String.valueOf(w.getWareId()));
        int rows = goodsMapper.insertGoods(goods);
        System.out.println("goods.getGoodsImages()"+goods.getGoodsImages());
        extracted(goods);
        if (rows > 0) {
            R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.PRODUCT_INFO, 1, SecurityConstants.INNER);
            System.out.println("流水号迭代 Res: "+ r);
            if (r == null || r.getCode() != 200) {
                log.error("insertWareHouse 流水号迭代失败");
                throw new RuntimeException("货品数据插入失败");
            }
        }
        return rows;
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
        Date nowDate = DateUtils.getNowDate();
        String uid = SecurityUtils.getUserId().toString();
        goods.setUpdateBy(uid);
        goods.setUpdateTime(nowDate);
        //删除原来的附件、图片，重新添加
        if (goods.getGoodsImages() != null&&goods.getGoodsImages().size()>0){
            remoteAnnexService.remove(new String[]{goods.getgId()},AttachmentType.PRODUCT_IMAGE);
        }
        if (goods.getGoodsAnnex() != null&&goods.getGoodsAnnex().size()>0){
            remoteAnnexService.remove(new String[]{goods.getgId()},AttachmentType.PRODUCT_ANNEX);
        }

        System.out.println("goods.getWarningId()============"+goods.getWarningId());
        extracted(goods);
        if (goods.getwDays()!=null){
            WarningInfo w=new WarningInfo();
            w.setwDays(goods.getwDays());
            goodsMapper.insertWarning(w);
            goods.setWarningId(String.valueOf(w.getWareId()));
        }else {
            goodsMapper.deleteWarnId(goods.getWarningId());
            goods.setWarningId(null);
        }
        return goodsMapper.updateGoods(goods);
    }

    @Override
    public int updateGoodsStatus(Goods goods) {
        return goodsMapper.updateGoodsStatus(goods);
    }

    //添加货品附件、图片
    private void extracted(Goods goods) {
                goods.getGoodsImages().forEach(e -> {
                    e.setFormId(goods.getgId());
                    e.setType(AttachmentType.PRODUCT_IMAGE);
                });

                goods.getGoodsAnnex().forEach(k -> {
                    k.setFormId(goods.getgId());
                    k.setType(AttachmentType.PRODUCT_ANNEX);
                });

            List<Annex> annexeImage = goods.getGoodsImages();
            List<Annex> goodsAnnex = goods.getGoodsAnnex();
            List<Annex> all=new ArrayList<>();
            all.addAll(goodsAnnex);
            all.addAll(annexeImage);
            if (all != null && !all.isEmpty()) {
                R<?> add = remoteAnnexService.add(all);
                System.out.println("all-------------"+all);
                if (add == null || add.getCode() != 200) {
                    log.error("insertPurchaseOrder 附件信息插入失败");
                    throw new RuntimeException("附件信息插入失败");
                }
                System.out.println("remoteAnnexService add: " + add);
            }

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
     * 导入商品数据
     * @param goodsList 商品数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importGoods(List<Goods> goodsList, Boolean isUpdateSupport, Long operName) {
        System.out.println("operName"+operName+"=====isUpdateSupport:"+isUpdateSupport);//1  false
        if (StringUtils.isNull(goodsList) || goodsList.size() == 0)
        {
            throw new ServiceException("导入商品数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Goods goods : goodsList)
        {
            try
            {
                // 验证是否存在这个商品
                int u = goodsMapper.selectGoodsByGname(goods.getGName());
                System.out.println("是否存在此商品："+u);
                if (u==0)
                {
                    //不存在
                    BeanValidators.validateWithException(validator, goods);
                    goods.setCreateBy(operName.toString());
                    goods.setCreateTime(new Date());
                    goods.setUpdateBy(operName.toString());
                    goods.setUpdateTime(new Date());
                    goodsMapper.insertGoods(goods);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、货品 " + goods.getGName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    //更新已经存在的商品数据
                    BeanValidators.validateWithException(validator, goods);
                    goods.setUpdateBy(operName.toString());
                    goods.setUpdateTime(new Date());
                    int i=goodsMapper.updateGoods(goods);
                  if (i>0){
                      successNum++;
                      successMsg.append("<br/>" + successNum + "、货品 " + goods.getGName() + " 更新成功");
                  }else {
                      failureNum++;
                      successMsg.append("<br/>" + successNum + "、货品 " + goods.getGName() + " 更新失败");
                  }

                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、货品 " + goods.getGName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、货品 " + goods.getGName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {

            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


}
