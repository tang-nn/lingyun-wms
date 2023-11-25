package com.lingyun.wh.contactunits.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author : Tang
 * @Description : 供应商对象 wh_supplier
 * @CreateDate : 2023/11/21 16:40
 */
public class Supplier extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    private String sId;

    /**
     * 财务ID
     */
    @Excel(name = "财务ID")
    private String fId;

    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码")
    private String sCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String sName;

    /**
     * 所属行业
     */
    @Excel(name = "所属行业")
    private String industry;

    /**
     * 供应商分类
     */
    @Excel(name = "供应商分类")
    private String supplierType;

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
     * 进货负责人(Person in charge of purchasing goods)
     */
    @Excel(name = "进货负责人(Person in charge of purchasing goods)")
    private String principalId;

    /**
     * 状态,(MySQL：bit)0：default，启用；1：禁用
     */
    @Excel(name = "状态,(MySQL：bit)0：default，启用；1：禁用")
    private String status;

    /**
     * 供应商地址
     */
    @Excel(name = "供应商地址")
    private String sAddress;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
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

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
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
                .append("sId", getsId())
                .append("fId", getfId())
                .append("sCode", getsCode())
                .append("sName", getsName())
                .append("industry", getIndustry())
                .append("supplierType", getSupplierType())
                .append("contactPerson", getContactPerson())
                .append("contactNumber", getContactNumber())
                .append("email", getEmail())
                .append("principalId", getPrincipalId())
                .append("status", getStatus())
                .append("sAddress", getsAddress())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }

}
