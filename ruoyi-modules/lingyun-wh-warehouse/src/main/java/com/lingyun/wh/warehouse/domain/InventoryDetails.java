package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lingyun.wh.goods.api.domain.Goods;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.lingyun.wh.goods.api.domain.Stock;

/**
 * 盘点明细对象 wh_is_details
 *
 * @author LiJin
 * @date 2023-12-13
 */
public class InventoryDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 盘点明细ID
     */
    private String isdId;

    /**
     * 盘点 ID
     */
    @Excel(name = "盘点 ID")
    private String isId;

    /**
     * 盘点单号
     */
    @Excel(name = "盘点单号")
    private String isCode;

    /**
     * 库存 ID
     */
    @Excel(name = "库存 ID")
    private String sId;


    /**
     * 盘点数量
     */
    @Excel(name = "盘点数量")
    private BigDecimal countQuantity;

    /**
     * 盈亏数量
     */
    @Excel(name = "盈亏数量")
    private BigDecimal profitLossQuantity;

    /**
     * 已入库数量
     */
    @JsonProperty(defaultValue = "0.00")
    private BigDecimal receivedQuantity;

    /**
     * 已出库数量
     */
    @JsonProperty(defaultValue = "0.00")
    private BigDecimal quantityShipped;
    /**
     * 盘点状态
     */
    @Excel(name = "盘点状态")
    private Integer isStatus;

    /**
     * 盘点金额
     */
    @Excel(name = "盘点金额")
    private BigDecimal countAmount;

    /*库存*/
    private Stock stock;

    /*货品*/
    private Goods goods;
    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;


    /*库位*/
    private StorageLocation storageLocation;


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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getIsdId() {
        return isdId;
    }

    public void setIsdId(String isdId) {
        this.isdId = isdId;
    }

    public String getIsId() {
        return isId;
    }

    public void setIsId(String isId) {
        this.isId = isId;
    }

    public String getIsCode() {
        return isCode;
    }

    public void setIsCode(String isCode) {
        this.isCode = isCode;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public BigDecimal getCountQuantity() {
        return countQuantity;
    }

    public void setCountQuantity(BigDecimal countQuantity) {
        this.countQuantity = countQuantity;
    }

    public BigDecimal getProfitLossQuantity() {
        if (profitLossQuantity == null) {
            profitLossQuantity = BigDecimal.ZERO;
        }
        return profitLossQuantity;
    }

    public void setProfitLossQuantity(BigDecimal profitLossQuantity) {
        this.profitLossQuantity = profitLossQuantity;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }

    public BigDecimal getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(BigDecimal countAmount) {
        this.countAmount = countAmount;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getQuantityShipped() {
        if (quantityShipped == null) {
            quantityShipped = BigDecimal.ZERO;
        }
        return quantityShipped;
    }

    public void setQuantityShipped(BigDecimal quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("isdId", getIsdId())
                .append("isId", getIsId())
                .append("isCode", getIsCode())
                .append("sId", getsId())
                .append("countQuantity", getCountQuantity())
                .append("profitLossQuantity", getProfitLossQuantity())
                .append("isStatus", getIsStatus())
                .append("countAmount", getCountAmount())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }
}
