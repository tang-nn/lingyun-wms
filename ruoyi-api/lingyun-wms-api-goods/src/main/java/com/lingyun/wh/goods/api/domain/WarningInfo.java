package com.lingyun.wh.goods.api.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 预警对象 wh_warning_info
 */
public class WarningInfo  extends BaseEntity {

    //预警id
    private String wareId;


    //预警天数
    private Integer wDays;
    //预警通知人
    private String warningNotifier;

    //预警信息
    private String  warningInfo;



    public Integer getwDays() {
        return wDays;
    }

    public void setwDays(Integer wDays) {
        this.wDays = wDays;
    }

    public String getWarningNotifier() {
        return warningNotifier;
    }

    public void setWarningNotifier(String warningNotifier) {
        this.warningNotifier = warningNotifier;
    }

    public String getWarningInfo() {
        return warningInfo;
    }

    public void setWarningInfo(String warningInfo) {
        this.warningInfo = warningInfo;
    }

    @Override
    public String toString() {
        return "WarningInfo{" +
                "wareId='" + wareId + '\'' +
                ", wDays=" + wDays +
                ", warningNotifier='" + warningNotifier + '\'' +
                ", warningInfo='" + warningInfo + '\'' +
                '}';
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }
}
