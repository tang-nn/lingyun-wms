package com.lingyun.wh.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lingyun.wh.goods.api.domain.Goods;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : Tang
 * @Description : 入库管理明细对象 wh_im_details
 * @CreateDate : 2023/12/12 13:01
 */
public class InventoryDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 入库明细单ID
     */
    private String imdId;

    /**
     * 入库ID
     */
    @Excel(name = "入库ID")
    private String inId;

    /**
     * 进货明细 ID 或销售退货明细 ID
     */
    @Excel(name = "进货明细 ID 或销售退货明细 ID")
    private String odId;

    /**
     * 货品 ID
     */
    private String goodsId;
    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 已入库数量，已废弃
     */
    @Deprecated
    private BigDecimal quantityInStock;

    /**
     * 入库仓位
     */
    @Excel(name = "入库仓位")
    private String slId;

    /**
     * 本次入库数量
     */
    @Excel(name = "本次入库数量")
    private BigDecimal thisQuantity;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNumber;

    /**
     * 0：存在；1：已删除，不存在
     */
    private Boolean isDelete;
    /**
     * 入库货品信息
     */
    private Goods goods;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getImdId() {
        return imdId;
    }

    public void setImdId(String imdId) {
        this.imdId = imdId;
    }

    public String getInId() {
        return inId;
    }

    public void setInId(String inId) {
        this.inId = inId;
    }

    public String getOdId() {
        return odId;
    }

    public void setOdId(String odId) {
        this.odId = odId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(BigDecimal quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getSlId() {
        return slId;
    }

    public void setSlId(String slId) {
        this.slId = slId;
    }

    public BigDecimal getThisQuantity() {
        return thisQuantity;
    }

    public void setThisQuantity(BigDecimal thisQuantity) {
        this.thisQuantity = thisQuantity;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "InventoryDetails{" +
                "imdId='" + imdId + '\'' +
                ", inId='" + inId + '\'' +
                ", odId='" + odId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", unit='" + unit + '\'' +
                ", slId='" + slId + '\'' +
                ", thisQuantity=" + thisQuantity +
                ", productionDate=" + productionDate +
                ", batchNumber='" + batchNumber + '\'' +
                ", isDelete=" + isDelete +
                ", goods=" + goods +
                '}';
    }
}
