package com.lingyun.wh.goods.doman;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 货品类型对象 wh_goods_type
 *
 * @author Lijin
 * @date 2023-11-21
 */
public class GoodsType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 货品类型 ID */
    private String gtId;

    /** 货品类型编号 */
    @Excel(name = "货品类型编号")
    private String gtCode;

    /** 货品类型名称 */
    @Excel(name = "货品类型名称")
    private String gtName;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 货品状态 */
    @Excel(name = "货品状态")
    private Integer status;

    /** 父级类型 */
    @Excel(name = "父级类型")
    private String parentId;

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
    @Excel(name = "逻辑删除，0：存在；1：已删除，不存在")
    private Boolean isDelete;


    public String getGtId() {
        return gtId;
    }

    public void setGtId(String gtId) {
        this.gtId = gtId;
    }

    public String getGtCode() {
        return gtCode;
    }

    public void setGtCode(String gtCode) {
        this.gtCode = gtCode;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public void setIsDelete(Boolean isDelete)
    {
        this.isDelete = isDelete;
    }

    public Boolean getIsDelete()
    {
        return isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("gtId", getGtId())
                .append("gtCode", getGtCode())
                .append("gtName", getGtName())
                .append("sort", getSort())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("parentId", getParentId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }
}

