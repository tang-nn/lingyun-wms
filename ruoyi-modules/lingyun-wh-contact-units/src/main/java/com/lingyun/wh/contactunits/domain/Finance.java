package com.lingyun.wh.contactunits.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/21 10:57
 */
public class Finance extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 财务ID
     */
    private String fId;

    /**
     * 账户名称
     */
    @Excel(name = "账户名称")
    private String accountName;

    /**
     * 纳税人识别号
     */
    @Excel(name = "纳税人识别号")
    private String tiNumber;

    /**
     * 银行账户
     */
    @Excel(name = "银行账户")
    private String bankAccount;

    /**
     * 开户银行
     */
    @Excel(name = "开户银行")
    private String bankDeposit;

    /**
     * 开户行地址
     */
    @Excel(name = "开户行地址")
    private String aobAddress;

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTiNumber() {
        return tiNumber;
    }

    public void setTiNumber(String tiNumber) {
        this.tiNumber = tiNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getAobAddress() {
        return aobAddress;
    }

    public void setAobAddress(String aobAddress) {
        this.aobAddress = aobAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fId", getfId())
                .append("accountName", getAccountName())
                .append("tiNumber", getTiNumber())
                .append("bankAccount", getBankAccount())
                .append("bankDeposit", getBankDeposit())
                .append("aobAddress", getAobAddress())
                .toString();
    }
}
