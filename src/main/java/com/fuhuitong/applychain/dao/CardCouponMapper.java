package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.CardCoupon;

import java.util.ArrayList;

public interface CardCouponMapper {
    int deleteByPrimaryKey(String cardCouponId);

    int insert(CardCoupon record);

    int insertSelective(CardCoupon record);

    CardCoupon selectByPrimaryKey(String cardCouponId);
    
    ArrayList<CardCoupon> selectAll(CardCoupon record);
    
    int selectAllCount(CardCoupon record);

    int updateByPrimaryKeySelective(CardCoupon record);

    int updateByPrimaryKey(CardCoupon record);
    
    int updateValidStatus(CardCoupon record);
}