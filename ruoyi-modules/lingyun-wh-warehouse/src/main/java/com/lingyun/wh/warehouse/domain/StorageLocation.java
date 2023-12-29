package com.lingyun.wh.warehouse.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.Objects;

/**
 * 库位信息对象 wh_storage_location
 * 
 * @author lijin
 * @date 2023-12-07
 */
public class StorageLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库位ID */
    @Excel(name = "库位ID")
    private String slId;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private String wId;

    /** 库位编号 */
    @Excel(name = "库位编号")
    private String slCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String slName;

    /** 库位容量/立方 */
    @Excel(name = "库位容量")
    private Integer locationCapacity;

    /** 关联至用户表,仓位主管 */
    @Excel(name = "仓位主管")
    private String positionManager;

    private String locationUser;

    private String tela;

    //库位下库存数量（有库位不允许删除）
    private String stockNum;

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 库位状态,0:default,启用;1:警用 */
    @Excel(name = "库位状态")
    private Integer status;


    @Excel(name = "备注")
    private String remark;

    /** 0：存在；1：已删除，不存在 */
    private String isDelete;



    public String getSlId() {
        return slId;
    }

    public void setSlId(String slId) {
        this.slId = slId;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public String getSlCode() {
        return slCode;
    }

    public void setSlCode(String slCode) {
        this.slCode = slCode;
    }

    public String getSlName() {
        return slName;
    }

    public void setSlName(String slName) {
        this.slName = slName;
    }

    public String getLocationUser() {
        return locationUser;
    }

    public void setLocationUser(String locationUser) {
        this.locationUser = locationUser;
    }

    public Integer getLocationCapacity() {
        return locationCapacity;
    }

    public void setLocationCapacity(Integer locationCapacity) {
        this.locationCapacity = locationCapacity;
    }

    public String getPositionManager() {
        return positionManager;
    }

    public void setPositionManager(String positionManager) {
        this.positionManager = positionManager;
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

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "StorageLocation{" +
                "slId='" + slId + '\'' +
                ", wId='" + wId + '\'' +
                ", slCode='" + slCode + '\'' +
                ", slName='" + slName + '\'' +
                ", locationCapacity=" + locationCapacity +
                ", positionManager='" + positionManager + '\'' +
                ", locationUser='" + locationUser + '\'' +
                ", tela='" + tela + '\'' +
                ", stockNum='" + stockNum + '\'' +
                ", sort=" + sort +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", isDelete='" + isDelete + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageLocation that = (StorageLocation) o;
        return Objects.equals(slId, that.slId) && Objects.equals(wId, that.wId) && Objects.equals(slCode, that.slCode) && Objects.equals(slName, that.slName) && Objects.equals(locationCapacity, that.locationCapacity) && Objects.equals(positionManager, that.positionManager) && Objects.equals(sort, that.sort) && Objects.equals(status, that.status) && Objects.equals(remark, that.remark) && Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slId, wId, slCode, slName, locationCapacity, positionManager, sort, status, remark, isDelete);
    }
}
