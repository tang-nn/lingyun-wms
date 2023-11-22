package com.lingyun.wh.goods.doman;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 货品信息对象 wh_goods
 *
 * @author lijin
 * @date 2023-11-21
 */
public class Goods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 货品ID */
    private String gId;

    /** 货品编号 */
    @Excel(name = "货品编号")
    private String gCode;

    /** 货品名称 */
    @Excel(name = "货品名称")
    private String gName;

    /** 货品类_货品类型 ID */
    @Excel(name = "货品类_货品类型 ID")
    private String gtId;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 状态，0:default，启用；1：禁用 */
    @Excel(name = "状态，0:default，启用；1：禁用")
    private Integer status;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    /** 出库参考价 */
    @Excel(name = "出库参考价")
    private BigDecimal orPrice;

    /** 入库参考价 */
    @Excel(name = "入库参考价")
    private BigDecimal wrPrice;

    /** 有保质期，0:default，没有保质期；1：有保质期 */
    @Excel(name = "有保质期，0:default，没有保质期；1：有保质期")
    private Integer hasShelfLife;

    /** 预警天数 */
    @Excel(name = "预警天数")
    private Integer warningDays;

    /** 货品上限 */
    @Excel(name = "货品上限")
    private BigDecimal itemLimit;

    /** 货品下限 */
    @Excel(name = "货品下限")
    private BigDecimal lowerLimit;

    /** 逻辑删除，0：存在；1：已删除，不存在 */
    @Excel(name = "逻辑删除，0：存在；1：已删除，不存在")
    private String isDelete;

    public void setgId(String gId)
    {
        this.gId = gId;
    }

    public String getgId()
    {
        return gId;
    }
    public void setgCode(String gCode)
    {
        this.gCode = gCode;
    }

    public String getgCode()
    {
        return gCode;
    }
    public void setgName(String gName)
    {
        this.gName = gName;
    }

    public String getgName()
    {
        return gName;
    }
    public void setGtId(String gtId)
    {
        this.gtId = gtId;
    }

    public String getGtId()
    {
        return gtId;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setSpecCode(String specCode)
    {
        this.specCode = specCode;
    }

    public String getSpecCode()
    {
        return specCode;
    }
    public void setOrPrice(BigDecimal orPrice)
    {
        this.orPrice = orPrice;
    }

    public BigDecimal getOrPrice()
    {
        return orPrice;
    }
    public void setWrPrice(BigDecimal wrPrice)
    {
        this.wrPrice = wrPrice;
    }

    public BigDecimal getWrPrice()
    {
        return wrPrice;
    }
    public void setHasShelfLife(Integer hasShelfLife)
    {
        this.hasShelfLife = hasShelfLife;
    }

    public Integer getHasShelfLife()
    {
        return hasShelfLife;
    }
    public void setWarningDays(Integer warningDays)
    {
        this.warningDays = warningDays;
    }

    public Integer getWarningDays()
    {
        return warningDays;
    }
    public void setItemLimit(BigDecimal itemLimit)
    {
        this.itemLimit = itemLimit;
    }

    public BigDecimal getItemLimit()
    {
        return itemLimit;
    }
    public void setLowerLimit(BigDecimal lowerLimit)
    {
        this.lowerLimit = lowerLimit;
    }

    public BigDecimal getLowerLimit()
    {
        return lowerLimit;
    }
    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete()
    {
        return isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("gId", getgId())
                .append("gCode", getgCode())
                .append("gName", getgName())
                .append("gtId", getGtId())
                .append("sort", getSort())
                .append("status", getStatus())
                .append("specCode", getSpecCode())
                .append("remark", getRemark())
                .append("orPrice", getOrPrice())
                .append("wrPrice", getWrPrice())
                .append("hasShelfLife", getHasShelfLife())
                .append("warningDays", getWarningDays())
                .append("itemLimit", getItemLimit())
                .append("lowerLimit", getLowerLimit())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }

}
