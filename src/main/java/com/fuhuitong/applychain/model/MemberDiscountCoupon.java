package com.fuhuitong.applychain.model;

import java.util.Date;

public class MemberDiscountCoupon 
{
    private Long memCouponId;

    private Long memberId;

    private Long couponId;

    private Date createDate;

    private Integer finalStatus;

    public Long getMemCouponId() {
        return memCouponId;
    }

    public void setMemCouponId(Long memCouponId) {
        this.memCouponId = memCouponId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Integer finalStatus) {
        this.finalStatus = finalStatus;
    }
}