package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;
import java.util.List;

import com.lingyun.wh.goods.api.domain.Goods;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 调拨明细对象 wh_transfer_details
 * 
 * @author LIJin
 * @date 2023-12-18
 */
public class TransferDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨明细ID */
    private String tdId;

    /** 调拨ID */
    @Excel(name = "调拨ID")
    private String tid;


    /** 库存ID */
    @Excel(name = "库存ID")
    private String stockId;

    /** 调拨数量 */
    @Excel(name = "调拨数量")
    private BigDecimal quantity;


    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean deleted;

    /**
     * 入库仓库货品库存数
     */
    private Integer totalItemQuantity;

    /*入库仓库货品的库位id*/
    private String inLocationId;

    public String getInLocationId() {
        return inLocationId;
    }

    public void setInLocationId(String inLocationId) {
        this.inLocationId = inLocationId;
    }

    public Integer getTotalItemQuantity() {
        return totalItemQuantity;
    }

    public void setTotalItemQuantity(Integer totalItemQuantity) {
        this.totalItemQuantity = totalItemQuantity;
    }

    /*库存*/
    private Stock stock;
    private String sl_id;

    /*货品*/
    private Goods goods;

    public String getSl_id() {
        return sl_id;
    }

    public void setSl_id(String sl_id) {
        this.sl_id = sl_id;
    }

    private String g_id;

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    //库位
    private StorageLocation location;

    public StorageLocation getLocation() {
        return location;
    }

    public void setLocation(StorageLocation location) {
        this.location = location;
    }

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "TransferDetails{" +
                "tdId='" + tdId + '\'' +
                ", tid='" + tid + '\'' +
                ", stockId='" + stockId + '\'' +
                ", quantity=" + quantity +
                ", deleted=" + deleted +
                ", totalItemQuantity=" + totalItemQuantity +
                ", inLocationId='" + inLocationId + '\'' +
                ", stock=" + stock +
                ", sl_id='" + sl_id + '\'' +
                ", goods=" + goods +
                ", g_id='" + g_id + '\'' +
                ", location=" + location +
                '}';
    }
}