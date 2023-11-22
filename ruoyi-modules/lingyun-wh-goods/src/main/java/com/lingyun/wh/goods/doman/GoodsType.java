package com.lingyun.wh.goods.doman;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 货品类型对象 wh_goods_type
 *
 * @author Lijin
 * @date 2023-11-21
 */
public class GoodsType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 货品类型 ID
     */
    private String gtId;

    /**
     * 货品类型编号
     */
    @Excel(name = "货品类型编号")
    private String gtCode;

    /**
     * 货品类型名称
     */
    @Excel(name = "货品类型名称")
    private String gtName;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 货品状态
     */
    @Excel(name = "货品状态")
    private Integer status;

    /**
     * 父级类型
     */
    @Excel(name = "父级类型")
    private String parentId;

    /**
     * 逻辑删除，0：存在；1：已删除，不存在
     */
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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

