package com.lingyun.wh.warehouse.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 盘点单对象 wh_inventory_sheet
 * 
 * @author LiJin
 * @date 2023-12-13
 */
public class InventorySheet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点ID */
    private String isId;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String isCode;

    /** 盘点结果 */
    @Excel(name = "盘点结果")
    private String isResult;

    /*审核状态*/
    @Excel(name = "审核状态")
    private String reviewStatus;

    /** 仓库 ID */
    private String wId;


    /** 仓库 ID */
    @Excel(name = "仓库名称")
    private String whName;

    /** 来自数据字典,盘点类型 */
    @Excel(name = "来自数据字典,盘点类型")
    private String isType;

    /** 盘点开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date isStartTime;

    /** 盘点结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date isEndTime;

    /** 出库状态 */
    @Excel(name = "出库状态")
    private String outStatus;

    /** 入库状态 */
    @Excel(name = "入库状态")
    private String inStatus;

    /** 关联用户表,盘点经办人 ID*/
    private String isManager;

    /** 关联用户表,盘点经办人 */
    @Excel(name = "经办人")
    private String managerName;
    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Boolean isDelete;

    //审核人
    private String reviewer;

    //审核时间
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerTime;

    //审核意见
    private String comments;
    private String createBy;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date  createTime;
    private String updateBy;

    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;



    /** 盘点明细信息 */
    private List<InventoryDetails> inventoryDetailsList;

    public String getReviewer() {
        return reviewer;
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public void setIsId(String isId)
    {
        this.isId = isId;
    }

    public String getIsId() 
    {
        return isId;
    }
    public void setIsCode(String isCode) 
    {
        this.isCode = isCode;
    }

    public String getIsCode() 
    {
        return isCode;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public void setIsResult(String isResult)
    {
        this.isResult = isResult;
    }

    public String getIsResult() 
    {
        return isResult;
    }
    public void setwId(String wId) 
    {
        this.wId = wId;
    }

    public String getwId() 
    {
        return wId;
    }
    public void setIsType(String isType) 
    {
        this.isType = isType;
    }

    public String getIsType() 
    {
        return isType;
    }
    public void setIsStartTime(Date isStartTime) 
    {
        this.isStartTime = isStartTime;
    }

    public Date getIsStartTime() 
    {
        return isStartTime;
    }
    public void setIsEndTime(Date isEndTime) 
    {
        this.isEndTime = isEndTime;
    }

    public Date getIsEndTime() 
    {
        return isEndTime;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    public void setIsManager(String isManager)
    {
        this.isManager = isManager;
    }

    public String getIsManager() 
    {
        return isManager;
    }
    public void setIsDelete(Boolean isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Boolean getIsDelete() 
    {
        return isDelete;
    }
    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public List<InventoryDetails> getInventoryDetailsList()
    {
        return inventoryDetailsList;
    }

    public void setInventoryDetailsList(List<InventoryDetails> inventoryDetailsList)
    {
        this.inventoryDetailsList = inventoryDetailsList;
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

    @Override
    public String toString() {
        return "InventorySheet{" +
                "isId='" + isId + '\'' +
                ", isCode='" + isCode + '\'' +
                ", isResult='" + isResult + '\'' +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", wId='" + wId + '\'' +
                ", whName='" + whName + '\'' +
                ", isType='" + isType + '\'' +
                ", isStartTime=" + isStartTime +
                ", isEndTime=" + isEndTime +
                ", outStatus=" + outStatus +
                ", inStatus=" + inStatus +
                ", isManager='" + isManager + '\'' +
                ", managerName='" + managerName + '\'' +
                ", isDelete=" + isDelete +
                ", reviewer='" + reviewer + '\'' +
                ", reviewerTime=" + reviewerTime +
                ", comments='" + comments + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", inventoryDetailsList=" + inventoryDetailsList +
                '}';
    }
}
