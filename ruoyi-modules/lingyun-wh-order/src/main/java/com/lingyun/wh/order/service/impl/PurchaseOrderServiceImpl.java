package com.lingyun.wh.order.service.impl;

import com.lingyun.wh.goods.api.RemoteGoodsService;
import com.lingyun.wh.goods.api.domain.Goods;
import com.lingyun.wh.order.api.domain.PurchaseDetails;
import com.lingyun.wh.order.api.domain.PurchaseOrder;
import com.lingyun.wh.order.mapper.PurchaseOrderMapper;
import com.lingyun.wh.order.pojo.dto.PurchaseReviewDto;
import com.lingyun.wh.order.pojo.vo.PurchaseOrderVo;
import com.lingyun.wh.order.service.IPurchaseOrderService;
import com.ruoyi.common.core.constant.AttachmentType;
import com.ruoyi.common.core.constant.OrderType;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAnnexService;
import com.ruoyi.system.api.RemoteEncodeRuleService;
import com.ruoyi.system.api.domain.Annex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
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
    public PurchaseOrder selectPurchaseOrderByPoId(String poId) {
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectPurchaseOrderByPoId(poId);
        // JSONObject po = JSONObject.from(purchaseOrder);
        List<PurchaseDetails> detailsList = purchaseOrder.getPurchaseDetailsList();
        purchaseOrder.setPurchaseDetailsList(packagedGoodsStocks(detailsList, null));
        R<List<Annex>> list = remoteAnnexService.list(AttachmentType.PURCHASE_ANNEX, poId, null);
        // System.out.println("remoteAnnexService list: " + list);
        if (list != null && list.getCode() == 200) {
            purchaseOrder.setAnnexes(list.getData());
        } else {
            log.error("进货 ID：{}，查询附件失败", poId);
        }
        return purchaseOrder;
    }

    /**
     * 根据进货单 ID 查询进货明细
     *
     * @param poid   进货单 ID
     * @param params 其它查询差数
     * @return 进货订单
     */
    @Override
    public List<PurchaseDetails> selectPurchaseDetailsById(String poid, Map<String, Object> params) {
        List<PurchaseDetails> purchaseOrders = purchaseOrderMapper.selectOrderDetailsListById(poid, params);
        return packagedGoodsStocks(purchaseOrders, params);
    }

    /**
     * 获取进货订单详细信息
     * 在进货审核后，入库检查数据
     *
     * @param poId 进货 ID
     * @param dids 进货明细 ID
     * @return
     */
    @Override
    public PurchaseOrder queryPurchaseInfoByIds(String poId, String[] dids) {
        return purchaseOrderMapper.selectPurchaseInfoByIds(poId, dids);
    }

    /**
     * 将货品库存数据封装进进货明细
     *
     * @param purchaseDetails
     * @param params          货品库存查询差数
     * @return
     */
    public List<PurchaseDetails> packagedGoodsStocks(List<PurchaseDetails> purchaseDetails, Map<String, Object> params) {
        String[] ids = purchaseDetails.stream().map(PurchaseDetails::getGoodsId).toArray(String[]::new);
        if (ids.length > 0) {
            R<ArrayList<Goods>> res = remoteGoodsService.getInfoByIds(ids, params);
            System.out.println("ids：" + Arrays.toString(ids));
            System.out.println("获取货物库存数据：" + res);
            if ("200".equals(String.valueOf(res.getCode()))) {
                ArrayList<Goods> goodsList = res.getData();
                if (goodsList != null && goodsList.size() > 0) {
                    return purchaseDetails.stream().map(dl -> goodsList.stream().filter(g -> Objects.equals(dl.getGoodsId(), g.getGId())).map(g -> {
                        dl.setGoods(g);
                        return dl;
                    }).collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());
                }
            }
        }
        return purchaseDetails;
    }

    /**
     * 查询进货订单列表
     *
     * @param params 进货订单
     * @return 进货订单
     */
    @Override
    public List<PurchaseOrderVo> selectPurchaseOrderList(Map<String, Object> params) {
        return purchaseOrderMapper.selectPurchaseDetailsList(params);
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
        R<String[]> res = remoteEncodeRuleService.genSpecifyEncoding(OrderType.PURCHASE_ORDER, 1, SecurityConstants.INNER);
        // System.out.println("res: " + res);
        if (res == null || res.getCode() != 200) {
            log.error("insertPurchaseOrder 获取编码失败");
            throw new RuntimeException("获取编码失败");
        } else {
            purchaseOrder.setPoCode(res.getData()[0]);
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
        R<String> r = remoteEncodeRuleService.increaseCurrentSerialNumber(OrderType.PURCHASE_ORDER, 1, SecurityConstants.INNER);
        if (rows <= 0 || r == null || r.getCode() != 200) {
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
        Date nowDate = DateUtils.getNowDate();
        String userId = String.valueOf(SecurityUtils.getUserId());
        purchaseOrder.setUpdateTime(nowDate);
        purchaseOrder.setUpdateBy(userId);
        purchaseOrderMapper.deletePurchaseDetailsByPoId(purchaseOrder.getPoId());
        insertPurchaseDetails(purchaseOrder);
        return purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * 更新进货订单状态
     *
     * @param purchaseOrder
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderStatus(PurchaseOrder purchaseOrder) {
        String userId = String.valueOf(SecurityUtils.getUserId());
        Date nowDate = DateUtils.getNowDate();
        PurchaseOrder p = new PurchaseOrder();
        p.setPoId(purchaseOrder.getPoId());
        p.setStatus(purchaseOrder.getStatus());
        p.setUpdateBy(userId);
        p.setUpdateTime(nowDate);
        List<PurchaseDetails> pd = purchaseOrder.getPurchaseDetailsList();
        if (pd == null || pd.size() == 0) {
            throw new RuntimeException("进货明细异常！");
        }
        for (PurchaseDetails purchaseDetails : pd) {
            purchaseDetails.setPoId(purchaseOrder.getPoId());
            purchaseDetails.setUpdateBy(userId);
            purchaseDetails.setUpdateTime(nowDate);
            updatePurchaseDetails(purchaseDetails);
        }
        if (selectInboundCompleted(p.getPoId())) {
            purchaseOrder.setStatus("done");
        } else {
            purchaseOrder.setStatus("partial_storage");
        }
        purchaseOrderMapper.updatePurchaseOrder(p);
        return true;
    }

    /**
     * 查询进货订单的入库情况
     *
     * @param pid 进货订单 id
     * @return
     */
    public boolean selectInboundCompleted(String pid) {
        return purchaseOrderMapper.queryInboundCompleted(pid);
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
        AjaxResult result = remoteAnnexService.remove(poIds, AttachmentType.PURCHASE_ANNEX);
        if (!"200".equals(String.valueOf(result.get(AjaxResult.CODE_TAG)))) {
            throw new RuntimeException("附件删除失败");
        }
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

    /**
     * 审核进货
     *
     * @param dto
     * @return
     */
    @Override
    public boolean reviewPurchaseOrder(PurchaseReviewDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        String userId = String.valueOf(SecurityUtils.getUserId());
        Date nowDate = DateUtils.getNowDate();
        purchaseOrder.setStatus(dto.getStatus());
        purchaseOrder.setReviewComments(dto.getReviewComments());
        purchaseOrder.setReviewerDate(nowDate);
        purchaseOrder.setUpdateBy(userId);
        purchaseOrder.setUpdateTime(nowDate);
        purchaseOrder.setPoId(dto.getPoId());
        System.out.println("purchaseOrder = " + purchaseOrder);
        return purchaseOrderMapper.updatePurchaseOrder(purchaseOrder) > 0;
    }

    /**
     * 修改单个明细
     *
     * @param purchaseDetails
     * @return
     */
    boolean updatePurchaseDetails(PurchaseDetails purchaseDetails) {
        return purchaseOrderMapper.updateInboundInStock(purchaseDetails) > 0;
    }
}
