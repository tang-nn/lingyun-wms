package com.lingyun.wh.order.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/21 23:43
 */
public class SalesReturnDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售退货明细 ID
     */
    private String srdId;

    /**
     * 销售退货 ID
     */
    @Excel(name = "销售退货 ID")
    private String srId;

    /**
     * 退货数量
     */
    @Excel(name = "退货数量")
    private BigDecimal returnQuantity;

    /**
     * 销售明细 ID
     */
    @Excel(name = "销售明细 ID")
    private String sdId;

    /**
     * 逻辑删除,0：存在；1：已删除，不存在
     */
    @Excel(name = "逻辑删除,0：存在；1：已删除，不存在")
    private Boolean isDelete;

    public String getSrdId() {
        return srdId;
    }

    public void setSrdId(String srdId) {
        this.srdId = srdId;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public BigDecimal getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(BigDecimal returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getSdId() {
        return sdId;
    }

    public void setSdId(String sdId) {
        this.sdId = sdId;
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
                .append("srdId", getSrdId())
                .append("srId", getSrId())
                .append("returnQuantity", getReturnQuantity())
                .append("sdId", getSdId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelete", getIsDelete())
                .toString();
    }
}
