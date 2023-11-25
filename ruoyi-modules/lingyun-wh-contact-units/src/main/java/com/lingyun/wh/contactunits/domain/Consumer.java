package com.lingyun.wh.contactunits.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author : Tang
 * @Description : 客户对象 wh_consumer
 * @CreateDate : 2023/11/20 19:13
 */
public class Consumer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private String cId;

    /**
     * 财务ID
     */
    @Excel(name = "财务ID")
    private String fId;

    /**
     * 客户编码
     */
    @Excel(name = "客户编码")
    private String cCode;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String cName;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactNumber;

    /**
     * 电子邮箱
     */
    @Excel(name = "电子邮箱")
    private String email;

    /**
     * 客户地址
     */
    @Excel(name = "客户地址")
    private String address;

    /**
     * 使用数据字典，客户来源
     */
    @Excel(name = "使用数据字典，客户来源")
    private String source;

    /**
     * 单据状态；0：default，启用；1：禁用
     */
    @Excel(name = "单据状态；0：default，启用；1：禁用")
    private Integer status;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
                .append("cId", getcId())
                .append("fId", getfId())
                .append("cCode", getcCode())
                .append("cName", getcName())
                .append("contactPerson", getContactPerson())
                .append("contactNumber", getContactNumber())
                .append("email", getEmail())
                .append("Address", getAddress())
                .append("Source", getSource())
                .append("remark", getRemark())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }

}
