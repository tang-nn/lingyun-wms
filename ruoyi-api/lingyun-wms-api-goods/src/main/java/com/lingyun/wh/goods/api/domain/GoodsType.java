package com.lingyun.wh.goods.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 货品类型对象 wh_goods_type
 *
 * @author Lijin
 * @date 2023-11-21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsType extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Excel(name = "备注")
    public String remark;
    /** 货品类型 ID */
    @Excel(name = "货品类型ID")
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
    @Excel(name = "操作人")
    private String createBy;
    private String creater;
    /** 操作时间 **/
    @Excel(name = "操作时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<Date> createTimes;
    /** 修改人 **/
    private  String updateBy;
    /** 修改时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /** 逻辑删除，0：存在；1：已删除，不存在 */
    private Boolean isDelete;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<GoodsType> children;

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

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


    public String getCreateBy() {
        return createBy;
    }


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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public List<GoodsType> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsType> children) {
        this.children = children;
    }

    public List<Date> getCreateTimes() {
        return createTimes;
    }

    public void setCreateTimes(List<Date> createTimes) {
        this.createTimes = createTimes;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "gtId='" + gtId + '\'' +
                ", gtCode='" + gtCode + '\'' +
                ", gtName='" + gtName + '\'' +
                ", sort=" + sort +
                ", status=" + status +
                ", parentId='" + parentId + '\'' +
                ", createBy='" + createBy + '\'' +
                ", creater='" + creater + '\'' +
                ", createTime=" + createTime +
                ", createTimes=" + createTimes +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", children=" + children +
                '}';
    }
}

