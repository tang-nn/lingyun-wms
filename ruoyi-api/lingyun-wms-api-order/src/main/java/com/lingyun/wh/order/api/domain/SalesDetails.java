package com.lingyun.wh.order.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author : Tang
 * @Description : 销售明细对象 wh_sales_details
 * @CreateDate : 2023/11/21 23:32
 */
public class SalesDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售明细ID
     */
    private String sdId;

    /**
     * 销售订单ID
     */
    @Excel(name = "销售订单ID")
    private String sId;

    /**
     * 销售单号
     */
    @Excel(name = "销售单号")
    private String sCode;

    /**
     * 货品 ID
     */
    @Excel(name = "货品 ID")
    private String gId;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specCode;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 销售单价
     */
    @Excel(name = "销售单价")
    private String suPrice;

    /**
     * 销售数量
     */
    @Excel(name = "销售数量")
    private String salesVolume;

    /**
     * 逻辑删除,0：存在；1：已删除，不存在
     */
    @Excel(name = "逻辑删除,0：存在；1：已删除，不存在")
    private Boolean isDelete;

    public String getSdId() {
        return sdId;
    }

    public void setSdId(String sdId) {
        this.sdId = sdId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
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

    public String getSuPrice() {
        return suPrice;
    }

    public void setSuPrice(String suPrice) {
        this.suPrice = suPrice;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("sdId", getSdId())
                .append("sId", getsId())
                .append("sCode", getsCode())
                .append("gId", getgId())
                .append("specCode", getSpecCode())
                .append("unit", getUnit())
                .append("suPrice", getSuPrice())
                .append("salesVolume", getSalesVolume())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }
}
