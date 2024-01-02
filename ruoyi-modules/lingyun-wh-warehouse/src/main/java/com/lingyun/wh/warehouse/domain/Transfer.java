package com.lingyun.wh.warehouse.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 调拨单对象 wh_transfer
 * 
 * @author LIJin
 * @date 2023-12-18
 */
public class Transfer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨ID */
    private String tid;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String tdCode;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private String docStatus;

    /** 调拨申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调拨申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

//    查询参数 - 调出申请时间时
    @JsonFormat(pattern = "yyyy-MM-dd")
    private List<Date> dates;


    /** 调出库，仓库 ID */
    private String outWId;

    /** 调出库，仓库 */
    @Excel(name = "调出库")
    private String outName;

    /** 调入库，仓库 ID */
    @Excel(name = "调入库，仓库 ID")
    private String inWId;

    /** 调入库，仓库 */
    @Excel(name = "调入库")
    private String inName;

    /**
     *  本次调拨数量总和
     */
    private BigDecimal transferQuantity;

    /**
     * 本次调拨金额总和 sum（调拨数量 * 单价）
     */
    private BigDecimal transferAmount;
    /** 出库状态 */
    @Excel(name = "出库状态")
    private String outStatus;

    /** 数据字典,调拨类型 */
    @Excel(name = "数据字典,调拨类型")
    private String type;

    /** 入库状态 */
    @Excel(name = "入库状态")
    private String inStatus;

    /** 调拨经办人id */
    @Excel(name = "外键，关联用户表,调拨经办人")
    private String manager;

    /** 调拨经办人 */
    @Excel(name = "调拨经办人")
    private String managerName;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean deleted;


    private String createBy;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date  createTime;
    private String updateBy;

    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;


    /** 外键，关联用户表,审核人 */
    private String reviewer;

    /**审核人 */
    private String revieerName;

    /*审核时间*/
    private Date reviewerTime;


    /*审核意见*/
    private String comments;

    /** 调拨明细信息 */
    private List<TransferDetails> transferDetailsList;

    public BigDecimal getTransferQuantity() {
        return transferQuantity;
    }

    public void setTransferQuantity(BigDecimal transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTid()
    {
        return tid;
    }

    public void setTid(String tid)
    {
        this.tid = tid;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getRevieerName() {
        return revieerName;
    }

    public void setRevieerName(String revieerName) {
        this.revieerName = revieerName;
    }

    public String getTdCode()
    {
        return tdCode;
    }

    public void setTdCode(String tdCode)
    {
        this.tdCode = tdCode;
    }

    public String getDocStatus()
    {
        return docStatus;
    }

    public void setDocStatus(String docStatus)
    {
        this.docStatus = docStatus;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getOutWId()
    {
        return outWId;
    }

    public void setOutWId(String outWId)
    {
        this.outWId = outWId;
    }

    public String getInWId()
    {
        return inWId;
    }

    public void setInWId(String inWId)
    {
        this.inWId = inWId;
    }

    public String getOutStatus()
    {
        return outStatus;
    }

    public void setOutStatus(String outStatus)
    {
        this.outStatus = outStatus;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getInStatus()
    {
        return inStatus;
    }

    public void setInStatus(String inStatus)
    {
        this.inStatus = inStatus;
    }

    public String getManager()
    {
        return manager;
    }

    public void setManager(String manager)
    {
        this.manager = manager;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public List<TransferDetails> getTransferDetailsList()
    {
        return transferDetailsList;
    }

    public void setTransferDetailsList(List<TransferDetails> transferDetailsList)
    {
        this.transferDetailsList = transferDetailsList;
    }
    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public String getReviewer() {
        return reviewer;
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

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getReviewerTime() {
        return reviewerTime;
    }

    public void setReviewerTime(Date reviewerTime) {
        this.reviewerTime = reviewerTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "tid='" + tid + '\'' +
                ", tdCode='" + tdCode + '\'' +
                ", docStatus='" + docStatus + '\'' +
                ", date=" + date +
                ", dates=" + dates +
                ", outWId='" + outWId + '\'' +
                ", outName='" + outName + '\'' +
                ", inWId='" + inWId + '\'' +
                ", inName='" + inName + '\'' +
                ", transferQuantity=" + transferQuantity +
                ", transferAmount=" + transferAmount +
                ", outStatus='" + outStatus + '\'' +
                ", type='" + type + '\'' +
                ", inStatus='" + inStatus + '\'' +
                ", manager='" + manager + '\'' +
                ", managerName='" + managerName + '\'' +
                ", deleted=" + deleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", reviewer='" + reviewer + '\'' +
                ", revieerName='" + revieerName + '\'' +
                ", reviewerTime=" + reviewerTime +
                ", comments='" + comments + '\'' +
                ", transferDetailsList=" + transferDetailsList +
                '}';
    }
}
