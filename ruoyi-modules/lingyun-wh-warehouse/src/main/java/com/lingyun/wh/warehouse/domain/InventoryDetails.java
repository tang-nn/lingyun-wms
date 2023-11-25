package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 盘点明细对象 wh_is_details
 * 
 * @author Tang
 * @date 2023-11-22
 */
public class InventoryDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点明细ID */
    private String isdId;

    /** 盘点 ID */
    @Excel(name = "盘点 ID")
    private String isId;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String isCode;

    /** 库存 ID */
    @Excel(name = "库存 ID")
    private String sId;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 盘点数量 */
    @Excel(name = "盘点数量")
    private BigDecimal countQuantity;

    /** 盈亏数量 */
    @Excel(name = "盈亏数量")
    private Integer profitLossQuantity;

    /** 盘点状态 */
    @Excel(name = "盘点状态")
    private Integer isStatus;

    /** 盘点金额 */
    @Excel(name = "盘点金额")
    private BigDecimal countAmount;

    /** 入库单价 */
    @Excel(name = "入库单价")
    private BigDecimal iuPrice;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;

    public void setIsdId(String isdId) 
    {
        this.isdId = isdId;
    }

    public String getIsdId() 
    {
        return isdId;
    }
    public void setIsId(String isId) 
    {
        this.isId = isId;
    }

    public String getIsId() 
    {
        return isId;
    }
    public void setIsCode(String isCode) 
    {
        this.isCode = isCode;
    }

    public String getIsCode() 
    {
        return isCode;
    }
    public void setsId(String sId) 
    {
        this.sId = sId;
    }

    public String getsId() 
    {
        return sId;
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
    public void setCountQuantity(BigDecimal countQuantity) 
    {
        this.countQuantity = countQuantity;
    }

    public BigDecimal getCountQuantity() 
    {
        return countQuantity;
    }
    public void setProfitLossQuantity(Integer profitLossQuantity) 
    {
        this.profitLossQuantity = profitLossQuantity;
    }

    public Integer getProfitLossQuantity() 
    {
        return profitLossQuantity;
    }
    public void setIsStatus(Integer isStatus) 
    {
        this.isStatus = isStatus;
    }

    public Integer getIsStatus() 
    {
        return isStatus;
    }
    public void setCountAmount(BigDecimal countAmount) 
    {
        this.countAmount = countAmount;
    }

    public BigDecimal getCountAmount() 
    {
        return countAmount;
    }
    public void setIuPrice(BigDecimal iuPrice) 
    {
        this.iuPrice = iuPrice;
    }

    public BigDecimal getIuPrice() 
    {
        return iuPrice;
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
            .append("isdId", getIsdId())
            .append("isId", getIsId())
            .append("isCode", getIsCode())
            .append("sId", getsId())
            .append("specCode", getSpecCode())
            .append("unit", getUnit())
            .append("countQuantity", getCountQuantity())
            .append("profitLossQuantity", getProfitLossQuantity())
            .append("isStatus", getIsStatus())
            .append("countAmount", getCountAmount())
            .append("iuPrice", getIuPrice())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelete", getIsDelete())
            .toString();
    }
}
