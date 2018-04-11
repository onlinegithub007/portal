package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MemberCardCoupons;

import java.util.ArrayList;

public interface MemberCardCouponsMapper {
    int deleteByPrimaryKey(Long memCardCouponId);

    int deleteByCardCouponId(String cardCouponId);
    
    int insert(MemberCardCoupons record);

    int insertSelective(MemberCardCoupons record);
    
    int insertBatch(ArrayList<MemberCardCoupons> records);

    MemberCardCoupons selectByPrimaryKey(Long memCardCouponId);

    int updateByPrimaryKeySelective(MemberCardCoupons record);

    int updateByPrimaryKey(MemberCardCoupons record);
    
    int updateFinalStatus(MemberCardCoupons record);
    
    Integer selectCountByCardCoupon(MemberCardCoupons record);
    
    ArrayList<MemberCardCoupons> selectByCardCoupon(MemberCardCoupons record);
}