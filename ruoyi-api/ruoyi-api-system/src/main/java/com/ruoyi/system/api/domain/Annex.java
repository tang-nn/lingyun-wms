package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Annex extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 附件ID */
    private String aid;

    /** 附件类型，来之数据字典 */
    @Excel(name = "附件类型，来之数据字典")
    private String type;

    /** 其它表的 ID，如货品表，进货表 */
    @Excel(name = "其它表的 ID，如货品表，进货表")
    private String formId;

    /** 附件内容，文件路径 */
    @Excel(name = "附件内容，文件路径")
    private String content;

    public Annex() {
    }

    public Annex(String type, String formId, String content) {
        this.type = type;
        this.formId = formId;
        this.content = content;
    }

    public String getAid()
    {
        return aid;
    }

    public void setAid(String aid)
    {
        this.aid = aid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getFormId()
    {
        return formId;
    }

    public void setFormId(String formId)
    {
        this.formId = formId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("aid", getAid())
                .append("type", getType())
                .append("formId", getFormId())
                .append("content", getContent())
                .toString();
    }
}
