package com.lingyun.wh.warehouse.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

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

    public void setSlId(String slId) 
    {
        this.slId = slId;
    }

    public String getSlId() 
    {
        return slId;
    }
    public void setwId(String wId) 
    {
        this.wId = wId;
    }

    public String getwId() 
    {
        return wId;
    }
    public void setSlCode(String slCode) 
    {
        this.slCode = slCode;
    }

    public String getSlCode() 
    {
        return slCode;
    }
    public void setSlName(String slName) 
    {
        this.slName = slName;
    }

    public String getSlName() 
    {
        return slName;
    }
    public void setLocationCapacity(Integer locationCapacity) 
    {
        this.locationCapacity = locationCapacity;
    }

    public Integer getLocationCapacity() 
    {
        return locationCapacity;
    }
    public void setPositionManager(String positionManager) 
    {
        this.positionManager = positionManager;
    }

    public String getPositionManager() 
    {
        return positionManager;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setIsDelete(String isDelete) 
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete() 
    {
        return isDelete;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
                ", sort=" + sort +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", isDelete='" + isDelete + '\'' +
                '}';
    }
}
