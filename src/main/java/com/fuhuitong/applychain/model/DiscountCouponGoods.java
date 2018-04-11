package com.fuhuitong.applychain.model;

import java.util.Date;

public class DiscountCouponGoods {
    private Long discountCouponGoodsId;

    private Long couponId;

    private String goodsId;

    private Date createDate;

    public Long getDiscountCouponGoodsId() {
        return discountCouponGoodsId;
    }

    public void setDiscountCouponGoodsId(Long discountCouponGoodsId) {
        this.discountCouponGoodsId = discountCouponGoodsId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}