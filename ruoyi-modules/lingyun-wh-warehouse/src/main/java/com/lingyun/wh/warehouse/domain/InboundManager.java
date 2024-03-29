package com.lingyun.wh.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 入库管理对象 wh_inbound_mag
 *
 * @author Tang
 * @date 2023-12-12
 */
public class InboundManager extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 入库ID
     */
    private String inId;

    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String inCode;

    /**
     * 入库类型, 关联数据字典
     */
    @Excel(name = "入库类型, 关联数据字典")
    private String inType;

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
     * 入库状态
     */
    @Excel(name = "入库状态")
    private String status;

    /**
     * 供应商 ID
     */
    @Excel(name = "供应商 ID")
    private String sid;

    /**
     * 关联进货订单或者是其他订单
     */
    @Excel(name = "关联进货订单或者是其他订单")
    private String relatedOrder;

    /**
     * 入库经办人
     */
    @Excel(name = "入库经办人 ID")
    private String manager;

    /**
     * 入库经办人姓名
     */
    @Excel(name = "入库经办人姓名")
    private String managerName;

    /**
     * 审核人
     */
    @Excel(name = "审核人 ID")
    private String reviewer;
    /**
     * 审核人
     */
    @Excel(name = "审核人姓名")
    private String reviewerName;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerTime;

    /**
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date storageDate;

    /**
     * 销售退货 ID 或进货订单 ID
     */
    @Excel(name = "销售退货 ID 或进货订单 ID")
    private String orderId;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private String deleted;
    /**
     * 入库管理明细信息
     */
    private List<InboundDetails> inboundDetails;

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
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

    public String getInId() {
        return inId;
    }

    public void setInId(String inId) {
        this.inId = inId;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRelatedOrder() {
        return relatedOrder;
    }

    public void setRelatedOrder(String relatedOrder) {
        this.relatedOrder = relatedOrder;
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

    public Date getReviewerTime() {
        return reviewerTime;
    }

    public void setReviewerTime(Date reviewerTime) {
        this.reviewerTime = reviewerTime;
    }

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public List<InboundDetails> getInboundDetails() {
        return inboundDetails;
    }

    public void setInboundDetails(List<InboundDetails> inboundDetails) {
        this.inboundDetails = inboundDetails;
    }

    @Override
    public String toString() {
        return "InboundManager{" +
                "inId='" + inId + '\'' +
                ", inCode='" + inCode + '\'' +
                ", inType='" + inType + '\'' +
                ", wid='" + wid + '\'' +
                ", whName='" + whName + '\'' +
                ", status='" + status + '\'' +
                ", sid='" + sid + '\'' +
                ", relatedOrder='" + relatedOrder + '\'' +
                ", manager='" + manager + '\'' +
                ", managerName='" + managerName + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", reviewerTime=" + reviewerTime +
                ", storageDate=" + storageDate +
                ", orderId='" + orderId + '\'' +
                ", deleted='" + deleted + '\'' +
                ", inboundDetails=" + inboundDetails +
                '}';
    }
}
