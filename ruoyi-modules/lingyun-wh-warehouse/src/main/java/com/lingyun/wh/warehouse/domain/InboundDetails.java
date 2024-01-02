package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.lingyun.wh.goods.api.domain.Goods;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 入库管理明细对象 wh_inbound_details
 *
 * @author Tang
 * @date 2023-12-18
 */
public class InboundDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 入库明细单ID
     */
    private String indId;

    /**
     * 入库ID
     */
    @Excel(name = "入库ID")
    private String inid;

    /**
     * 进货明细 ID 或销售退货明细 ID
     */
    @Excel(name = "进货明细 ID 或销售退货明细 ID")
    private String odid;

    /**
     * 货品 ID
     */
    @Excel(name = "货品 ID")
    private String goodsId;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 入库仓位
     */
    @Excel(name = "入库仓位")
    private String slid;

    /**
     * 库存数量，入库货品在库存的数量
     */
    private BigDecimal stockQuantity;

    /**
     * 已入库入库数量，在相关联的表
     */
    @Deprecated
    private BigDecimal receivedQuantity;

    /**
     * 未入库数量，在相关联的表
     */
    @Deprecated
    private BigDecimal unreceivedQuantity;

    /**
     * 本次入库数量
     */
    @Excel(name = "本次入库数量")
    private BigDecimal quantityInbound;

    /**
     * 进货单价
     */
    @Excel(name = "进货单价")
    private BigDecimal puPrice;


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
     * 入库货品对象
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getIndId() {
        return indId;
    }

    public void setIndId(String indId) {
        this.indId = indId;
    }

    public String getInid() {
        return inid;
    }

    public void setInid(String inid) {
        this.inid = inid;
    }

    public String getOdid() {
        return odid;
    }

    public void setOdid(String odid) {
        this.odid = odid;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSlid() {
        return slid;
    }

    public void setSlid(String slid) {
        this.slid = slid;
    }

    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(BigDecimal stockQuantity) {
        if (stockQuantity == null) {
            stockQuantity = BigDecimal.ZERO;
        }
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getUnreceivedQuantity() {
        return unreceivedQuantity;
    }

    public void setUnreceivedQuantity(BigDecimal unreceivedQuantity) {
        this.unreceivedQuantity = unreceivedQuantity;
    }

    public BigDecimal getQuantityInbound() {
        return quantityInbound;
    }

    public void setQuantityInbound(BigDecimal quantityInbound) {
        this.quantityInbound = quantityInbound;
    }

    public BigDecimal getPuPrice() {
        return puPrice;
    }

    public void setPuPrice(BigDecimal puPrice) {
        this.puPrice = puPrice;
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

    @Override
    public String toString() {
        return "InboundDetails{" +
                "indId='" + indId + '\'' +
                ", inid='" + inid + '\'' +
                ", odid='" + odid + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", unit='" + unit + '\'' +
                ", slid='" + slid + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", receivedQuantity=" + receivedQuantity +
                ", unreceivedQuantity=" + unreceivedQuantity +
                ", quantityInbound=" + quantityInbound +
                ", puPrice=" + puPrice +
                ", productionDate=" + productionDate +
                ", batchNumber='" + batchNumber + '\'' +
                ", goods=" + goods +
                '}';
    }
}
