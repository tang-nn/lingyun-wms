package com.lingyun.wh.warehouse.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 仓库对象 wh_warehouse
 * 
 * @author lijin
 * @date 2023-12-07
 */
public class WareHouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库ID */
    private String wId;

    /** 仓库编号 */
    @Excel(name = "仓库编号")
    private String wCode;

    private String deptName;


    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String wName;

    /** 仓库容量 */
    @Excel(name = "仓库容量")
    private String wCapacity;

    /** 仓库状态,,,0：default，启用；1：禁用 */
    @Excel(name = "仓库状态,,,0：default，启用；1：禁用")
    private Integer status;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String wAddress;

    /** 是否默认,,,,0：不是默认的；1：是默认的 */
    @Excel(name = "是否默认,,,,0：不是默认的；1：是默认的")
    private Boolean isDefault;

    /** 仓库锁定,,,,0：没有锁库；1：锁库，冻结仓库 */
    @Excel(name = "仓库锁定,,,,0：没有锁库；1：锁库，冻结仓库")
    private Boolean isLock;

    /** 关联至用户表，仓库主管 */
    @Excel(name = "关联至用户表id")
    private String wSupervisor;
    @Excel(name = "关联至用户表，仓库主管")
    private String supervisorUser;

    private String tel;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;

    /** 库位信息信息 */
    private List<StorageLocation> storageLocationList;

    public void setwId(String wId) 
    {
        this.wId = wId;
    }

    public String getwId() 
    {
        return wId;
    }
    public void setwCode(String wCode) 
    {
        this.wCode = wCode;
    }

    public String getwCode() 
    {
        return wCode;
    }
    public void setwName(String wName) 
    {
        this.wName = wName;
    }

    public String getwName() 
    {
        return wName;
    }
    public void setwCapacity(String wCapacity) 
    {
        this.wCapacity = wCapacity;
    }

    public String getwCapacity() 
    {
        return wCapacity;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSupervisorUser() {
        return supervisorUser;
    }

    public void setSupervisorUser(String supervisorUser) {
        this.supervisorUser = supervisorUser;
    }
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setwAddress(String wAddress) 
    {
        this.wAddress = wAddress;
    }

    public String getwAddress() 
    {
        return wAddress;
    }
    public void setIsDefault(Boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public Boolean getIsDefault()
    {
        return isDefault;
    }
    public void setIsLock(Boolean isLock)
    {
        this.isLock = isLock;
    }

    public Boolean getIsLock()
    {
        return isLock;
    }
    public void setwSupervisor(String wSupervisor) 
    {
        this.wSupervisor = wSupervisor;
    }

    public String getwSupervisor() 
    {
        return wSupervisor;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setIsDelete(Boolean isDelete)
    {
        this.isDelete = isDelete;
    }

    public Boolean getIsDelete()
    {
        return isDelete;
    }

    public List<StorageLocation> getStorageLocationList()
    {
        return storageLocationList;
    }

    public void setStorageLocationList(List<StorageLocation> storageLocationList)
    {
        this.storageLocationList = storageLocationList;
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "wId='" + wId + '\'' +
                ", wCode='" + wCode + '\'' +
                ", deptName='" + deptName + '\'' +
                ", wName='" + wName + '\'' +
                ", wCapacity='" + wCapacity + '\'' +
                ", status=" + status +
                ", wAddress='" + wAddress + '\'' +
                ", isDefault=" + isDefault +
                ", isLock=" + isLock +
                ", wSupervisor='" + wSupervisor + '\'' +
                ", supervisorUser='" + supervisorUser + '\'' +
                ", tel='" + tel + '\'' +
                ", sort=" + sort +
                ", isDelete=" + isDelete +
                ", storageLocationList=" + storageLocationList +
                '}';
    }
}
