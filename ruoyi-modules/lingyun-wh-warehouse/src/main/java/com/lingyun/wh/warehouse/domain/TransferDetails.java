package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 调拨明细对象 wh_transfer_details
 * 
 * @author Tang
 * @date 2023-11-22
 */
public class TransferDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨明细ID */
    private String tdId;

    /** 调拨ID */
    @Excel(name = "调拨ID")
    private String tId;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String tdCode;

    /** 货品 ID */
    @Excel(name = "货品 ID")
    private String goodsId;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 调拨数量 */
    @Excel(name = "调拨数量")
    private BigDecimal tQuantity;

    /** 入库单价 */
    @Excel(name = "入库单价")
    private BigDecimal iuPrice;

    /** 调拨金额 */
    @Excel(name = "调拨金额")
    private BigDecimal tAmount;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;

    public void setTdId(String tdId) 
    {
        this.tdId = tdId;
    }

    public String getTdId() 
    {
        return tdId;
    }
    public void settId(String tId) 
    {
        this.tId = tId;
    }

    public String gettId() 
    {
        return tId;
    }
    public void setTdCode(String tdCode) 
    {
        this.tdCode = tdCode;
    }

    public String getTdCode() 
    {
        return tdCode;
    }
    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }
    public void setSpecCode(String specCode) 
    {
        this.specCode = specCode;
    }

    public String getSpecCode() 
    {
        return specCode;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void settQuantity(BigDecimal tQuantity) 
    {
        this.tQuantity = tQuantity;
    }

    public BigDecimal gettQuantity() 
    {
        return tQuantity;
    }
    public void setIuPrice(BigDecimal iuPrice) 
    {
        this.iuPrice = iuPrice;
    }

    public BigDecimal getIuPrice() 
    {
        return iuPrice;
    }
    public void settAmount(BigDecimal tAmount) 
    {
        this.tAmount = tAmount;
    }

    public BigDecimal gettAmount() 
    {
        return tAmount;
    }
    public void setIsDelete(Boolean isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Boolean getIsDelete() 
    {
        return isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tdId", getTdId())
            .append("tId", gettId())
            .append("tdCode", getTdCode())
            .append("goodsId", getGoodsId())
            .append("specCode", getSpecCode())
            .append("unit", getUnit())
            .append("tQuantity", gettQuantity())
            .append("iuPrice", getIuPrice())
            .append("tAmount", gettAmount())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelete", getIsDelete())
            .toString();
    }
}
