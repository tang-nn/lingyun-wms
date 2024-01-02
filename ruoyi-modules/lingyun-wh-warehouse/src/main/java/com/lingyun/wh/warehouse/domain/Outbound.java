package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 出库管理对象 wh_outbound
 *
 * @author Tang
 * @date 2023-12-26
 */
public class Outbound<T> extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 出库相关联的主表数据，如：调拨主表、销售订单主表、进货退货单主表等
     */
    T orderData;
    /**
     * 出库 ID
     */
    private String outId;
    /**
     * 出库单号
     */
    @Excel(name = "出库单号")
    private String outCode;
    /**
     * 出库类型
     */
    @Excel(name = "出库类型")
    private String outType;
    /**
     * 仓库 ID
     */
    @Excel(name = "仓库 ID")
    private String wid;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String whName;

    /**
     * 出库状态；数据字典
     */
    @Excel(name = "出库状态；数据字典")
    private String status;
    /**
     * 出库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outboundDate;
    /**
     * 客户 ID
     */
    @Excel(name = "客户 ID")
    private String consumerId;
    /**
     * 关联订单号
     */
    @Excel(name = "关联订单号")
    private String relatedOrder;
    /**
     * 关联订单 ID,,,,,进货退货订单 ID 或销售订单 ID
     */
    @Excel(name = "关联订单 ID,,,,,进货退货订单 ID 或销售订单 ID")
    private String orderId;
    /**
     * 是否关联销售订单号(0：不关联，1：关联)
     */
    @Excel(name = "是否关联销售订单号(0：不关联，1：关联)")
    private String associated;
    /**
     * 本单总出库数量
     */
    private BigDecimal outboundQuantity;
    /**
     * 本单总出库金额
     */
    private BigDecimal outboundAmount;
    /**
     * 本单说有出库货品名称
     */
    private String goodsNames;

    /**
     * 出库负责人 id
     */
    private String manager;
    /**
     * 出库负责人
     */
    @Excel(name = "出库负责人")
    private String managerName;
    /**
     * 审核人 id
     */
    private String reviewer;
    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String reviewerName;
    /**
     * 审核意见
     */
    @Excel(name = "审核意见")
    private String comments;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerTime;
    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean deleted;
    /**
     * 出库管理明细信息
     */
    private List<OutboundDetails> outboundDetailsList;

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public BigDecimal getOutboundQuantity() {
        return outboundQuantity;
    }

    public void setOutboundQuantity(BigDecimal outboundQuantity) {
        this.outboundQuantity = outboundQuantity;
    }

    public BigDecimal getOutboundAmount() {
        return outboundAmount;
    }

    public void setOutboundAmount(BigDecimal outboundAmount) {
        this.outboundAmount = outboundAmount;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public T getOrderData() {
        return orderData;
    }

    public void setOrderData(T orderData) {
        this.orderData = orderData;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getRelatedOrder() {
        return relatedOrder;
    }

    public void setRelatedOrder(String relatedOrder) {
        this.relatedOrder = relatedOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAssociated() {
        return associated;
    }

    public void setAssociated(String associated) {
        this.associated = associated;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getReviewerTime() {
        return reviewerTime;
    }

    public void setReviewerTime(Date reviewerTime) {
        this.reviewerTime = reviewerTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<OutboundDetails> getOutboundDetailsList() {
        return outboundDetailsList;
    }

    public void setOutboundDetailsList(List<OutboundDetails> outboundDetailsList) {
        this.outboundDetailsList = outboundDetailsList;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("outId", getOutId())
                .append("outCode", getOutCode())
                .append("outType", getOutType())
                .append("wid", getWid())
                .append("status", getStatus())
                .append("outboundDate", getOutboundDate())
                .append("consumerId", getConsumerId())
                .append("relatedOrder", getRelatedOrder())
                .append("orderId", getOrderId())
                .append("associated", getAssociated())
                .append("remark", getRemark())
                .append("manager", getManager())
                .append("reviewer", getReviewer())
                .append("comments", getComments())
                .append("reviewerTime", getReviewerTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("deleted", getDeleted())
                .append("outboundDetailsList", getOutboundDetailsList())
                .toString();
    }
}
