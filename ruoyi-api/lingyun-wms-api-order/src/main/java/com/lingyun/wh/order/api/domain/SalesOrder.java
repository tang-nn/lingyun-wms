package com.lingyun.wh.order.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 销售订单对象 wh_sales_order
 *
 * @author Tang
 * @date 2023-11-21
 */
public class SalesOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售订单ID
     */
    private String sId;

    /**
     * 客户ID
     */
    @Excel(name = "客户ID")
    private String cId;

    /**
     * 销售单号
     */
    @Excel(name = "销售单号")
    private String sCode;

    /**
     * 单据状态,进货审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；
     */
    @Excel(name = "单据状态,进货审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；")
    private Integer status;

    /**
     * 销售日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "销售日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date saleDate;

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
    private Date reviewDate;

    /**
     * 逻辑删除,0：存在；1：已删除，不存在
     */
    @Excel(name = "逻辑删除,0：存在；1：已删除，不存在")
    private Boolean isDelete;

    /**
     * 销售明细信息
     */
    private List<SalesDetails> salesDetailsList;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public List<SalesDetails> getSalesDetailsList() {
        return salesDetailsList;
    }

    public void setSalesDetailsList(List<SalesDetails> salesDetailsList) {
        this.salesDetailsList = salesDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("sId", getsId())
                .append("cId", getcId())
                .append("sCode", getsCode())
                .append("status", getStatus())
                .append("saleDate", getSaleDate())
                .append("reviewer", getReviewer())
                .append("reviewDate", getReviewDate())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .append("salesDetailsList", getSalesDetailsList())
                .toString();
    }
}
