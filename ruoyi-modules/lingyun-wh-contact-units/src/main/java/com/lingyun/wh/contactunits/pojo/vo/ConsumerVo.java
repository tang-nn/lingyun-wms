package com.lingyun.wh.contactunits.pojo.vo;


import com.lingyun.wh.contactunits.domain.Finance;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.system.api.domain.SysDept;

import java.sql.Date;

public class ConsumerVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private String cId;

    /**
     * 财务ID
     */
    private String fId;

    /**
     * 客户编码
     */
    private String cCode;

    /**
     * 客户名称
     */
    private String cName;

    /**
     * 所属行业
     */
    private String industry;


    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 使用数据字典，客户来源
     */
    private String source;

    /**
     * 单据状态；0：default，启用；1：禁用
     */
    private Integer status;

    /**
     * 0：存在；1：已删除，不存在
     */
    private Boolean isDelete;

    /**
     * 修改时间
     */
    private Date updateTime;


    /**
     * 部门表
     */
    private String deptName;


    /**
     * 销售负责人
     */
    private String saleManager;
    /**
     * 财务表
     */
    private Finance finance;


    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

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
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
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
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
