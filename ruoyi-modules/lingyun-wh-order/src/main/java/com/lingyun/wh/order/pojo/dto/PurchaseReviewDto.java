package com.lingyun.wh.order.pojo.dto;

/**
 * @Author : Tang
 * @Description : 用于进货订单审核
 * @CreateDate : 2023/12/11 21:02
 */
public class PurchaseReviewDto {
    /**
     * 订单 ID
     */
    private String poId;

    /**
     * 单据状态
     */
    private String status;

    /**
     * 审核意见
     */
    private String reviewComments;

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewComments() {
        return reviewComments;
    }

    public void setReviewComments(String reviewComments) {
        this.reviewComments = reviewComments;
    }
}
