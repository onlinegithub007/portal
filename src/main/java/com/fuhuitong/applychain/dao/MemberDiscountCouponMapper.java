package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MemberDiscountCoupon;

public interface MemberDiscountCouponMapper {
    int deleteByPrimaryKey(Long memCouponId);

    int insert(MemberDiscountCoupon record);

    int insertSelective(MemberDiscountCoupon record);

    MemberDiscountCoupon selectByPrimaryKey(Long memCouponId);

    int updateByPrimaryKeySelective(MemberDiscountCoupon record);

    int updateByPrimaryKey(MemberDiscountCoupon record);
    
    int updateCouponStatus(MemberDiscountCoupon record);
}