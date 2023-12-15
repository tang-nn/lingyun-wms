package com.lingyun.wh.warehouse.domain;


import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * 库存对象
 *
 * @author LiJin
 * @date 2023-12-13
 */
public class Stock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String sid;
    private String wid;
    private String slid;
    private String unit;
    private Integer itemQuantity;
    private Integer numberPlans;
    private String gid;
    private String remark;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Boolean deleted;

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

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getNumberPlans() {
        return numberPlans;
    }

    public void setNumberPlans(Integer numberPlans) {
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
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
