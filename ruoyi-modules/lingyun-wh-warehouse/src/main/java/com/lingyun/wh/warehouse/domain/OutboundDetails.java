package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 出库管理明细对象 wh_outbound_details
 *
 * @author Tang
 * @date 2023-12-26
 */
public class OutboundDetails<T> extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 出库相关联的数据，如：调拨明细表、销售订单明细表、进货退货单明细表等
     */
    T orderDetails;
    /**
     * 出库明细单ID
     */
    private String obdId;
    /**
     * 出库 ID
     */
    @Excel(name = "出库 ID")
    private String outId;
    /**
     * 进货退货明细 ID 或销售订单 ID
     */
    @Excel(name = "进货退货明细 ID 或销售订单 ID")
    private String odid;
    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;
    /**
     * 货品 ID
     */
    @Excel(name = "货品 ID")
    private String goodsId;

    /**
     * 货品单价
     */
    @Excel(name = "货品单价")
    private BigDecimal puPrice;
    /**
     * 本次出库数量
     */
    @Excel(name = "本次出库数量")
    private BigDecimal outboundQuantity;
    /**
     * 出库仓位
     */
    @Excel(name = "出库仓位")
    private String slid;
    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNumber;

    public T getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(T orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getObdId() {
        return obdId;
    }

    public void setObdId(String obdId) {
        this.obdId = obdId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOdid() {
        return odid;
    }

    public void setOdid(String odid) {
        this.odid = odid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getPuPrice() {
        return puPrice;
    }

    public void setPuPrice(BigDecimal puPrice) {
        this.puPrice = puPrice;
    }

    public BigDecimal getOutboundQuantity() {
        if (outboundQuantity == null) {
            outboundQuantity = BigDecimal.ZERO;
        }
        return outboundQuantity;
    }

    public void setOutboundQuantity(BigDecimal outboundQuantity) {
        this.outboundQuantity = outboundQuantity;
    }

    public String getSlid() {
        return slid;
    }

    public void setSlid(String slid) {
        this.slid = slid;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("obdId", getObdId())
                .append("outId", getOutId())
                .append("odid", getOdid())
                .append("unit", getUnit())
                .append("goodsId", getGoodsId())
                .append("puPrice", getPuPrice())
                .append("outboundQuantity", getOutboundQuantity())
                .append("slid", getSlid())
                .append("batchNumber", getBatchNumber())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
