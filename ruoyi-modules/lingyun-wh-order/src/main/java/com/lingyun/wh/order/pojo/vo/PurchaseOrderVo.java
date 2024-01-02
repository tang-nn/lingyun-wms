package com.lingyun.wh.order.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * @Author : Tang
 * @Description : 进货订单视图对象
 * @CreateDate : 2023/11/23 22:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseOrderVo {
    private String poId;

    /**
     * 进货单号
     */
    private String poCode;

    /**
     * 单据状态
     */
    private String status;

    /**
     * 进货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    /**
     * 供应商ID
     */
    private String sId;

    /**
     * 供应商名称
     */
    private String supplName;
    /**
     * 供应商联系人
     */
    private String contactPerson;
    /**
     * 供应商联系电话
     */
    private String contactNumber;
    /**
     * 进货人 ID
     */
    private String purchaserId;
    /**
     * 进货人 姓名
     */
    private String purNickName;
    /**
     * 进货部门 ID
     */
    private String purDeptId;
    /**
     * 进货部门名称
     */
    private String purDeptName;
    /**
     * 审核人 ID
     */
    private String reviewer;
    /**
     * 审核人名字
     */
    private String reviewerUname;
    /**
     * 审核意见
     */
    private String reviewComments;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewerDate;
    /**
     * 制单人 ID
     */
    private String cuid;
    /**
     * 制单人 姓名
     */
    private String cuname;
    /**
     * 制单人所在部门 ID
     */
    private String cudeptId;
    /**
     * 制单人所在部门名称
     */
    private String cudeptName;
    /**
     * 制单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date cudate;
    /**
     * 进货明细
     */
    private List<PurchaseDetailVo> purchaseDetails;

    public String getSupplName() {
        return supplName;
    }

    public void setSupplName(String supplName) {
        this.supplName = supplName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<PurchaseDetailVo> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetailVo> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
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

    public String getPurNickName() {
        return purNickName;
    }

    public void setPurNickName(String purNickName) {
        this.purNickName = purNickName;
    }

    public String getPurDeptId() {
        return purDeptId;
    }

    public void setPurDeptId(String purDeptId) {
        this.purDeptId = purDeptId;
    }

    public String getPurDeptName() {
        return purDeptName;
    }

    public void setPurDeptName(String purDeptName) {
        this.purDeptName = purDeptName;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewComments() {
        return reviewComments;
    }

    public void setReviewComments(String reviewComments) {
        this.reviewComments = reviewComments;
    }

    public Date getReviewerDate() {
        return reviewerDate;
    }

    public void setReviewerDate(Date reviewerDate) {
        this.reviewerDate = reviewerDate;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getCuname() {
        return cuname;
    }

    public void setCuname(String cuname) {
        this.cuname = cuname;
    }

    public String getCudeptId() {
        return cudeptId;
    }

    public void setCudeptId(String cudeptId) {
        this.cudeptId = cudeptId;
    }

    public String getCudeptName() {
        return cudeptName;
    }

    public void setCudeptName(String cudeptName) {
        this.cudeptName = cudeptName;
    }

    public Date getCudate() {
        return cudate;
    }

    public void setCudate(Date cudate) {
        this.cudate = cudate;
    }

    public String getReviewerUname() {
        return reviewerUname;
    }

    public void setReviewerUname(String reviewerUname) {
        this.reviewerUname = reviewerUname;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("poId", getPoId())
                .append("poCode", getPoCode())
                .append("status", getStatus())
                .append("purchaseDate", getPurchaseDate())
                .append("sId", getsId())
                .append("supplName", getSupplName())
                .append("contactPerson", getContactPerson())
                .append("contactNumber", getContactNumber())
                .append("purchaserId", getPurchaserId())
                .append("purNickName", getContactNumber())
                .append("purDeptId", getPurDeptId())
                .append("purDeptName", getPurDeptName())
                .append("reviewer", getReviewer())
                .append("reviewerUname", getReviewerUname())
                .append("reviewComments", getReviewComments())
                .append("reviewerDate", getReviewerDate())
                .append("cuid", getCuid())
                .append("cuname", getCuname())
                .append("cudeptId", getCudeptId())
                .append("cudeptName", getPurDeptName())
                .append("cudate", getCudate())
                .append("purchaseDetails", getPurchaseDetails())
                .toString();
    }
}
