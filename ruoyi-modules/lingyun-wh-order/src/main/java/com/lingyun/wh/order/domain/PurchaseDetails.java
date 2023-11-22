package com.lingyun.wh.order.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @Author : Tang
 * @Description : 进货明细
 * @CreateDate : 2023/11/21 19:47
 */
public class PurchaseDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 进货明细单ID
     */
    private String pdId;

    /**
     * 进货ID
     */
    @Excel(name = "进货ID")
    private String poId;

    /**
     * 货品 ID
     */
    @Excel(name = "货品 ID")
    private String goodsId;

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
     * 进货数量
     */
    @Excel(name = "进货数量")
    private BigDecimal purchaseQuantity;

    /**
     * 进货单价
     */
    @Excel(name = "进货单价")
    private BigDecimal puPrice;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private String isDelete;

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public BigDecimal getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public BigDecimal getPuPrice() {
        return puPrice;
    }

    public void setPuPrice(BigDecimal puPrice) {
        this.puPrice = puPrice;
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
                .append("pdId", getPdId())
                .append("poId", getPoId())
                .append("goodsId", getGoodsId())
                .append("specCode", getSpecCode())
                .append("unit", getUnit())
                .append("purchaseQuantity", getPurchaseQuantity())
                .append("puPrice", getPuPrice())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }
}