package com.lingyun.wh.common.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 附件对象 wh_annex
 *
 * @author Tang
 * @date 2023-11-20
 */
public class Annex extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 附件ID
     */
    private Long aId;

    /**
     * 附件类型，来之数据字典
     */
    @Excel(name = "附件类型，来之数据字典")
    private String aType;

    /**
     * 附件内容，文件路径
     */
    @Excel(name = "附件内容，文件路径")
    private String aContent;

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("aId", getaId())
                .append("aType", getaType())
                .append("aContent", getaContent())
                .toString();
    }
}
