package com.lingyun.wh.order.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.annotation.Excel;

import java.math.BigDecimal;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/27 15:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseDetailVo {
    /**
     * 进货明细单ID
     */
    private String pdId;

    /**
     * 货品 ID
     */
    private String goodsId;


    /**
     * 货品名称
     */
    private String goodsName;

    /**
     * 入库数量
     */
    private BigDecimal quantityInStock;

    /**
     * 进货数量
     */
    private BigDecimal purchaseQuantity;

    /**
     * 进货单价
     */
    private BigDecimal puPrice;


    /**
     * 规格型号
     */
    private String specCode;

    /**
     * 单位
     */
    private String unit;

    public BigDecimal getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(BigDecimal quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    @Override
    public String toString() {
        return "PurchaseDetailVo{" +
                "pdId='" + pdId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", purchaseQuantity=" + purchaseQuantity +
                ", puPrice=" + puPrice +
                ", specCode='" + specCode + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
