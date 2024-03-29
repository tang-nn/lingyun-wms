package com.lingyun.wh.goods.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.system.api.domain.Annex;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 货品信息对象 wh_goods
 *
 * @author lijin
 * @date 2023-11-21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods extends BaseEntity {

    /**
     * 序列化和反序列化的兼容性
     */
    private static final long serialVersionUID = 1L;
    private List<GoodsType> children;

    /** 货品ID */
    @Excel(name = "货品ID")
    private String gId;

    private Integer wDays;//预警天数
    /** 货品编号 */
    @Excel(name = "货品编号")
    private String gCode;
    /** 货品名称 */
    @Excel(name = "货品名称")
    private String gName;
    @Excel(name = "货品类型ID")
    private String gtId;

//    private Integer warningDays;
    /** 货品类_货品类型 ID */
    @Excel(name = "货品类型")
    private String gtName;
    /**
     * 货品类型对象
     */
    private GoodsType goodsType;
    /** 供应商 ID */
    @Excel(name = "供应商ID")
    private String sId;
    /** 排序 */
    @Excel(name = "排序")
    private Long sort;
    /** 状态，0:default，启用；1：禁用 */
    @Excel(name = "状态")
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
    /** 预警 ID */
    @Excel(name = "预警ID")
    private String warningId;
    /** 有保质期，0:default，没有保质期；1：有保质期 */
    @Excel(name = "是否有保质期")
    private Integer hasShelfLife;
    /** 保质期 */
    @Excel(name = "保质期")
    private String shelfLife;
    /** 货品上限 */
//    @Excel(name = "货品上限")
    private String itemLimit;
    /** 货品下限 */
//    @Excel(name = "货品下限")
    private String lowerLimit;
    /**
     * 备注
     */
//    @Excel(name = "备注")
    private String remark;
    /** 创建人 **/
    private String createBy;
    /** 操作时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 修改人 **/
    private  String updateBy;
    /** 修改时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /** 逻辑删除，0：存在；1：已删除，不存在 */
    private Boolean isDelete;
    /**
     * 货品列表，一个货品对应多个库存
     */
    private List<Stock> stockList;

    /**
     * 货品图片
     */
    private List<Annex> goodsImages;

    /**
     * 货品附件
     */
    private List<Annex> goodsAnnex;
    public Integer getwDays() {
        return wDays;
    }

    public void setwDays(Integer wDays) {
        this.wDays = wDays;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
    }

    public List<GoodsType> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsType> children) {
        this.children = children;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

//    public String getgCode() {
//        return gCode;
//    }
//
//    public void setgCode(String gCode) {
//        this.gCode = gCode;
//    }
    public List<Annex> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<Annex> goodsImages) {
        this.goodsImages = goodsImages;
    }

    public List<Annex> getGoodsAnnex() {
        return goodsAnnex;
    }

    public void setGoodsAnnex(List<Annex> goodsAnnex) {
        this.goodsAnnex = goodsAnnex;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGId() {
        return gId;
    }

    public void setGId(String gId) {
        this.gId = gId;
    }

    public String getGCode() {
        return gCode;
    }

    public void setGCode(String gCode) {
        this.gCode = gCode;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public String getGtId() {
        return gtId;
    }

    public void setGtId(String gtId) {
        this.gtId = gtId;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public Long getSort() {
        return sort;
    }


    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public BigDecimal getOrPrice() {
        return orPrice;
    }

    public void setOrPrice(BigDecimal orPrice) {
        this.orPrice = orPrice;
    }

    public BigDecimal getWrPrice() {
        return wrPrice;
    }

    public void setWrPrice(BigDecimal wrPrice) {
        this.wrPrice = wrPrice;
    }

    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    public Integer getHasShelfLife() {
        return hasShelfLife;
    }

    public void setHasShelfLife(Integer hasShelfLife) {
        this.hasShelfLife = hasShelfLife;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getItemLimit() {
        return itemLimit;
    }

    public void setItemLimit(String itemLimit) {
        this.itemLimit = itemLimit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }


    public String getReMark() {
        return remark;
    }


    public void setReMark(String remark) {
        this.remark = remark;
    }


    public String getCreateBy() {
        return createBy;
    }


    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getUpdateBy() {
        return updateBy;
    }


    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }


    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "children=" + children +
                ", gId='" + gId + '\'' +
                ", wDays=" + wDays +
                ", gCode='" + gCode + '\'' +
                ", gName='" + gName + '\'' +
                ", gtId='" + gtId + '\'' +
                ", gtName='" + gtName + '\'' +
                ", goodsType=" + goodsType +
                ", sId='" + sId + '\'' +
                ", sort=" + sort +
                ", status=" + status +
                ", specCode='" + specCode + '\'' +
                ", orPrice=" + orPrice +
                ", wrPrice=" + wrPrice +
                ", warningId='" + warningId + '\'' +
                ", hasShelfLife=" + hasShelfLife +
                ", shelfLife='" + shelfLife + '\'' +
                ", itemLimit='" + itemLimit + '\'' +
                ", lowerLimit='" + lowerLimit + '\'' +
                ", remark='" + remark + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", stockList=" + stockList +
                ", goodsImages=" + goodsImages +
                ", goodsAnnex=" + goodsAnnex +
                '}';
    }
}
