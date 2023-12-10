package com.lingyun.wh.order.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingyun.wh.goods.api.RemoteGoodsService;
import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.order.domain.PurchaseDetails;
import com.lingyun.wh.order.domain.PurchaseOrder;
import com.lingyun.wh.order.mapper.PurchaseOrderMapper;
import com.lingyun.wh.order.pojo.vo.PurchaseOrderVo;
import com.lingyun.wh.order.service.IPurchaseOrderService;
import com.ruoyi.common.core.constant.AttachmentType;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAnnexService;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import com.ruoyi.system.api.domain.Annex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/21 17:46
 */
@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    @Autowired
    RemoteEncodeRuleService remoteEncodeRuleService;
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private RemoteAnnexService remoteAnnexService;

    @Autowired
    private RemoteGoodsService remoteGoodsService;

    /**
     * 查询进货订单
     *
     * @param poId 进货订单主键
     * @return 进货订单
     */
    @Override
    public JSONObject selectPurchaseOrderByPoId(String poId) {
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectPurchaseOrderByPoId(poId);
        JSONObject po = JSONObject.from(purchaseOrder);
        List<PurchaseDetails> detailsList = purchaseOrder.getPurchaseDetailsList();
        String[] ids = detailsList.stream().map(PurchaseDetails::getGoodsId).toArray(String[]::new);
        if (ids.length > 0) {
            AjaxResult infoByIds = remoteGoodsService.getInfoByIds(ids);
            System.out.println("获取货物数据：" + infoByIds);
            if ("200".equals(String.valueOf(infoByIds.get(AjaxResult.CODE_TAG)))) {
                // List<Goods> goodsList = JSONArray.from(infoByIds.get(AjaxResult.DATA_TAG)).toList(Goods.class);
                // detailsList.forEach(e -> {
                //     System.out.println("e.getGoodsId(): " + e.getGoodsId());
                //     goodsList.forEach(g -> {
                //         System.out.println("g.getGId(): " + g.getGId());
                //         if (e.getGoodsId().equals(g.getGId())) {
                //             System.out.println("进货订单 ID：" + e.getPoId() + "商品id：" + g.getGId() + "商品名称：" + g.getGName());
                //             e.setGoods(g);
                //         }
                //     });
                // });
                JSONArray goodsList = JSONArray.from(infoByIds.get(AjaxResult.DATA_TAG));
                JSONArray dl = JSONArray.from(detailsList);
                dl.forEach(e -> {
                    JSONObject d = (JSONObject) e;
                    goodsList.forEach(i -> {
                        JSONObject g = (JSONObject) i;
                        // System.out.println("d.get('goodsId'): " + d.get("goodsId"));
                        // System.out.println("g.get('g_id'): " + g.get("g_id"));
                        if (d.get("goodsId").equals(String.valueOf(g.get("g_id")))) {
                            d.put("goods", g);
                            // System.out.println("g: " + g);
                        }
                    });
                });
                po.put("purchaseDetailsList", dl);
                System.out.println("goodsList: " + goodsList);
                System.out.println("dl: "+ dl);
            }
        }
        TableDataInfo list = remoteAnnexService.list(new Annex(AttachmentType.PURCHASE_ANNEX, poId, null));
        System.out.println("remoteAnnexService list: " + list);
        if (list != null && list.getCode() == 200) {
            // purchaseOrder.setAnnexes((List<Annex>) list.getRows());
            po.put("annexes", list.getRows());
        } else {
            log.error("进货 ID：{}，查询附件失败", poId);
        }
        return po;
    }

    /**
     * 查询进货订单列表
     *
     * @param params 进货订单
     * @return 进货订单
     */
    @Override
    public List<PurchaseOrderVo> selectPurchaseOrderList(Map<String, Object> params) {
        List<PurchaseOrderVo> purchaseOrders = purchaseOrderMapper.selectPurchaseDetailsList(params);
        // purchaseOrders.forEach(e -> {
        //     remoteGoodsService.getInfo();
        // });
        return purchaseOrders;
    }

    /**
     * 新增进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        //
        R<String> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.PURCHASE_ORDER, SecurityConstants.INNER);
        // System.out.println("res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertPurchaseOrder 获取编码失败");
            throw new RuntimeException("获取编码失败");
        } else {
            purchaseOrder.setPoCode(res.getData());
        }
        purchaseOrder.setCreateTime(nowDate);
        purchaseOrder.setUpdateTime(nowDate);
        purchaseOrder.setCreateBy(userId);
        purchaseOrder.setUpdateBy(userId);
        purchaseOrder.setStatus("pending_review");
        System.out.println("purchaseOrder: " + purchaseOrder);
        int rows = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        insertPurchaseDetails(purchaseOrder);
        purchaseOrder.getAnnexes().forEach(e -> {
            e.setFormId(purchaseOrder.getPoId());
            e.setType(AttachmentType.PURCHASE_ANNEX);
        });
        List<Annex> annexeList = purchaseOrder.getAnnexes();
        if (annexeList != null && !annexeList.isEmpty()) {
            R<?> add = remoteAnnexService.add(annexeList);
            if (add == null || add.getCode() != 200) {
                log.error("insertPurchaseOrder 附件信息插入失败");
                throw new RuntimeException("附件信息插入失败");
            }
            System.out.println("remoteAnnexService add: " + add);
        }
        res = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.PURCHASE_ORDER, SecurityConstants.INNER);
        if (rows <= 0 || res == null || res.getCode() != 200) {
            log.error("insertPurchaseOrder 流水号迭代失败");
            throw new RuntimeException("进货数据插入失败");
        }
        return rows;
    }

    /**
     * 修改进货订单
     *
     * @param purchaseOrder 进货订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setUpdateTime(DateUtils.getNowDate());
        purchaseOrderMapper.deletePurchaseDetailsByPoId(purchaseOrder.getPoId());
        insertPurchaseDetails(purchaseOrder);
        return purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * 批量删除进货订单
     *
     * @param poIds 需要删除的进货订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPoIds(String[] poIds) {
        purchaseOrderMapper.deletePurchaseDetailsByPoIds(poIds);
        return purchaseOrderMapper.deletePurchaseOrderByPoIds(poIds);
    }

    /**
     * 删除进货订单信息
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPoId(String poId) {
        purchaseOrderMapper.deletePurchaseDetailsByPoId(poId);
        return purchaseOrderMapper.deletePurchaseOrderByPoId(poId);
    }

    /**
     * 新增进货明细信息
     *
     * @param purchaseOrder 进货订单对象
     */
    public void insertPurchaseDetails(PurchaseOrder purchaseOrder) {
        List<PurchaseDetails> purchaseDetailsList = purchaseOrder.getPurchaseDetailsList();
        String poId = purchaseOrder.getPoId();
        if (StringUtils.isNotNull(purchaseDetailsList)) {
            List<PurchaseDetails> list = new ArrayList<PurchaseDetails>();
            for (PurchaseDetails purchaseDetails : purchaseDetailsList) {
                purchaseDetails.setPoId(poId);
                String createBy = purchaseOrder.getCreateBy();
                Date createTime = purchaseOrder.getCreateTime();
                purchaseDetails.setCreateBy(createBy);
                purchaseDetails.setCreateTime(createTime);
                purchaseDetails.setUpdateBy(createBy);
                purchaseDetails.setUpdateTime(createTime);
                if (StringUtils.isEmpty(purchaseDetails.getRemark())) {
                    purchaseDetails.setRemark("");
                }
                System.out.println("purchaseDetails = " + purchaseDetails);
                list.add(purchaseDetails);
            }
            if (!list.isEmpty()) {
                purchaseOrderMapper.batchPurchaseDetails(list);
            }
        }
    }
}
