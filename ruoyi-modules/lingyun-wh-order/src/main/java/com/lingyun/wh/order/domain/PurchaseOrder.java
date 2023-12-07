package com.lingyun.wh.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.system.api.model.Annex;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货订单对象 wh_purchase_order
 * @CreateDate : 2023/11/21 17:03
 */
public class PurchaseOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 进货ID
     */
    private String poId;

    /**
     * 进货单号
     */
    @Excel(name = "进货单号")
    private String poCode;

    /**
     * 单据状态
     */
    @Excel(name = "单据状态")
    private String status;

    /**
     * 进货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /**
     * 供应商ID
     */
    @Excel(name = "供应商ID")
    private String sId;

    /**
     * 进货人
     */
    @Excel(name = "进货人")
    private String purchaserId;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String reviewer;

    @Excel(name = "审核意见")
    private String reviewComments;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerDate;
    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;
    /**
     * 进货明细信息
     */
    private List<PurchaseDetails> purchaseDetailsList;

    /**
     * 附件列表
     */
    private List<Annex> annexes;
    public PurchaseOrder() {
    }


    public List<Annex> getAnnexes() {
        return annexes;
    }

    public void setAnnexes(List<Annex> annexes) {
        this.annexes = annexes;
    }

    public String getReviewComments() {
        return reviewComments;
    }

    public void setReviewComments(String reviewComments) {
        this.reviewComments = reviewComments;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getReviewerDate() {
        return reviewerDate;
    }

    public void setReviewerDate(Date reviewerDate) {
        this.reviewerDate = reviewerDate;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public List<PurchaseDetails> getPurchaseDetailsList() {
        return purchaseDetailsList;
    }

    public void setPurchaseDetailsList(List<PurchaseDetails> purchaseDetailsList) {
        this.purchaseDetailsList = purchaseDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("poId", getPoId())
                .append("poCode", getPoCode())
                .append("status", getStatus())
                .append("purchaseDate", getPurchaseDate())
                .append("sId", getsId())
                .append("purchaserId", getPurchaserId())
                .append("remark", getRemark())
                .append("reviewer", getReviewer())
                .append("reviewComments", getReviewComments())
                .append("reviewerDate", getReviewerDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getDelete())
                .append("purchaseDetailsList", getPurchaseDetailsList())
                .append("Annexes", getAnnexes())
                .append("BaseEntity", super.toString())
                .toString();
    }

}
