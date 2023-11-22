package com.lingyun.wh.order.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @Author : Tang
 * @Description : 进货退货明细对象 wh_pr_details
 * @CreateDate : 2023/11/21 20:14
 */
public class PurchaseReturnDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 进货退货明细 ID
     */
    private String prdId;

    /**
     * 退货ID
     */
    @Excel(name = "退货ID")
    private String prId;

    /**
     * 进货明细单 ID
     */
    @Excel(name = "进货明细单 ID")
    private String pdId;

    /**
     * 规格型号,数据字典
     */
    @Excel(name = "规格型号,数据字典")
    private String specCode;

    /**
     * 单位,数据字典
     */
    @Excel(name = "单位,数据字典")
    private String unit;

    /**
     * 本次退货数量
     */
    @Excel(name = "本次退货数量")
    private BigDecimal returnQuantity;

    /**
     * 逻辑删除,0：存在；1：已删除，不存在
     */
    @Excel(name = "逻辑删除,0：存在；1：已删除，不存在")
    private String isDelete;

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(BigDecimal returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("prdId", getPrdId())
                .append("prId", getPrId())
                .append("pdId", getPdId())
                .append("specCode", getSpecCode())
                .append("unit", getUnit())
                .append("returnQuantity", getReturnQuantity())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }
}
