package com.lingyun.wh.goods.api.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存对象
 *
 * @author LiJin
 * @date 2023-12-13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String sid;
    /**
     * 仓库 ID
     */
    private String wid;
    /**
     * 库位 ID
     */
    private String slid;
    private String unit;
    private BigDecimal itemQuantity;
    private BigDecimal numberPlans;
    /**
     * 货品 ID
     */
    private String gid;
    private String remark;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getSlid() {
        return slid;
    }

    public void setSlid(String slid) {
        this.slid = slid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(BigDecimal itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public BigDecimal getNumberPlans() {
        if (numberPlans == null) {
            numberPlans = BigDecimal.ZERO;
        }
        return numberPlans;
    }

    public void setNumberPlans(BigDecimal numberPlans) {
        if (numberPlans == null) {
            numberPlans = BigDecimal.ZERO;
        }
        this.numberPlans = numberPlans;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "sid='" + sid + '\'' +
                ", wid='" + wid + '\'' +
                ", slid='" + slid + '\'' +
                ", unit='" + unit + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", numberPlans=" + numberPlans +
                ", gid='" + gid + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
