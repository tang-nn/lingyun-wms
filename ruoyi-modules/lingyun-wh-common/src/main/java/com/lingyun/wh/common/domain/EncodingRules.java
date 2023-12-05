package com.lingyun.wh.common.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * @Author : Tang
 * @Description : 编码规则对象 wh_encoding_rules
 * @CreateDate : 2023/11/29 10:52
 */
public class EncodingRules  extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号规则ID */
    private String erId;

    /** 目标表单，数据字典 */
    @Excel(name = "目标表单")
    private String targetForm;

    /** 编号前缀 */
    @Excel(name = "编号前缀")
    private String numberPrefix;

    /** 时间规则 */
    @Excel(name = "时间规则")
    private String timeRules;

    /** 流水号/位 */
    @Excel(name = "流水号/位")
    private Integer serialNumber;

    /**
     * 当前流水号
     */
    private Integer currentSerialNumber;

    /** 步长 */
    @Excel(name = "步长")
    private Integer stepLength;
    /** 编号生成规则 */
    @Excel(name = "编号生成规则")
    private String numberingRules;

    public Integer getCurrentSerialNumber() {
        return currentSerialNumber;
    }

    public void setCurrentSerialNumber(Integer currentSerialNumber) {
        this.currentSerialNumber = currentSerialNumber;
    }

    public String getErId()
    {
        return erId;
    }

    public void setErId(String erId)
    {
        this.erId = erId;
    }

    public String getTargetForm()
    {
        return targetForm;
    }

    public void setTargetForm(String targetForm)
    {
        this.targetForm = targetForm;
    }

    public String getNumberPrefix()
    {
        return numberPrefix;
    }

    public void setNumberPrefix(String numberPrefix)
    {
        this.numberPrefix = numberPrefix;
    }

    public String getTimeRules()
    {
        return timeRules;
    }

    public void setTimeRules(String timeRules)
    {
        this.timeRules = timeRules;
    }

    public Integer getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public Integer getStepLength()
    {
        return stepLength;
    }

    public void setStepLength(Integer stepLength)
    {
        this.stepLength = stepLength;
    }

    public String getNumberingRules()
    {
        return numberingRules;
    }

    public void setNumberingRules(String numberingRules)
    {
        this.numberingRules = numberingRules;
    }

    @Override
    public String toString() {
        return "EncodingRules{" +
                "erId='" + erId + '\'' +
                ", targetForm='" + targetForm + '\'' +
                ", numberPrefix='" + numberPrefix + '\'' +
                ", timeRules='" + timeRules + '\'' +
                ", serialNumber=" + serialNumber +
                ", currentSerialNumber=" + currentSerialNumber +
                ", stepLength=" + stepLength +
                ", numberingRules='" + numberingRules + '\'' +
                '}';
    }
}
