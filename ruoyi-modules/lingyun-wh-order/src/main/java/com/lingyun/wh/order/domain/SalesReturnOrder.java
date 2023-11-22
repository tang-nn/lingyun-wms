package com.lingyun.wh.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 销售退货对象 wh_sh_return
 *
 * @author Tang
 * @date 2023-11-21
 */
public class SalesReturnOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售退货ID
     */
    private String srId;

    /**
     * 退货单号
     */
    @Excel(name = "退货单号")
    private String srCode;

    /**
     * 销售订单ID
     */
    @Excel(name = "销售订单ID")
    private String sId;

    /**
     * 单据状态,进货审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；
     */
    @Excel(name = "单据状态,进货审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；")
    private Integer status;

    /**
     * 退货原因
     */
    @Excel(name = "退货原因")
    private String reasons;

    /**
     * 退货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /**
     * 销售负责人
     */
    @Excel(name = "销售负责人")
    private String sPrincipal;

    /**
     * 逻辑删除,0：存在；1：已删除，不存在
     */
    @Excel(name = "逻辑删除,0：存在；1：已删除，不存在")
    private Boolean isDelete;

    /**
     * 销售退货明细信息
     */
    private List<SalesReturnDetails> salesReturnDetailsList;

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getSrCode() {
        return srCode;
    }

    public void setSrCode(String srCode) {
        this.srCode = srCode;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getsPrincipal() {
        return sPrincipal;
    }

    public void setsPrincipal(String sPrincipal) {
        this.sPrincipal = sPrincipal;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public List<SalesReturnDetails> getSalesReturnDetailsList() {
        return salesReturnDetailsList;
    }

    public void setSalesReturnDetailsList(List<SalesReturnDetails> salesReturnDetailsList) {
        this.salesReturnDetailsList = salesReturnDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("srId", getSrId())
                .append("srCode", getSrCode())
                .append("sId", getsId())
                .append("status", getStatus())
                .append("reasons", getReasons())
                .append("remark", getRemark())
                .append("returnDate", getReturnDate())
                .append("sPrincipal", getsPrincipal())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .append("salesReturnDetailsList", getSalesReturnDetailsList())
                .toString();
    }
}
