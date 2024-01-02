package com.lingyun.wh.warehouse.vo;

import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;

/**
 * 审核数据对象，用于接收前端请求审核的数据
 *
 * @Author : Tang
 * @CreateDate : 2023/12/31 12:54
 */
public class ReviewDto {

    /**
     * 审核订单 id
     */
    @NotEmpty(message = "审核订单 id 不能为空")
    private String orderId;
    /**
     * 审核状态
     */
    @NotEmpty(message = "审核结果不能为空")
    private String reviewStatus;
    /**
     * 审核意见
     */
    private String comments;

    public ReviewDto() {
    }

    public ReviewDto(String orderId, String reviewStatus, String comments) {
        this.orderId = orderId;
        this.reviewStatus = reviewStatus;
        this.comments = comments;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ReviewDto{" + "orderId='" + orderId + '\'' + ", reviewStatus='" + reviewStatus + '\'' + ", comments='" + comments + '\'' + '}';
    }
}
