package com.lingyun.wh.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货退货对象 wh_pr_form
 * @CreateDate : 2023/11/21 20:13
 */
public class PurchaseReturnNote extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 退货ID
     */
    private String prId;

    /**
     * 进货单号
     */
    @Excel(name = "进货单号")
    private String prCode;

    /**
     * 单据状态,审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；
     */
    @Excel(name = "单据状态,审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；")
    private Integer status;

    /**
     * 退货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /**
     * 进货单 ID
     */
    @Excel(name = "进货单 ID")
    private String poId;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String reviewer;

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
     * 进货退货明细信息
     */
    private List<PurchaseReturnDetails> purchaseReturnDetailsList;

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
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

    public List<PurchaseReturnDetails> getPurchaseReturnDetailsList() {
        return purchaseReturnDetailsList;
    }

    public void setPurchaseReturnDetailsList(List<PurchaseReturnDetails> purchaseReturnDetailsList) {
        this.purchaseReturnDetailsList = purchaseReturnDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("prId", getPrId())
                .append("prCode", getPrCode())
                .append("status", getStatus())
                .append("returnDate", getReturnDate())
                .append("poId", getPoId())
                .append("reviewer", getReviewer())
                .append("reviewerDate", getReviewerDate())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .append("purchaseReturnDetailsList", getPurchaseReturnDetailsList())
                .toString();
    }
}
