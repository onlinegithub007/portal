package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.Discounts;

import java.util.ArrayList;

public interface DiscountsMapper {
	
    int deleteByPrimaryKey(String discountId);

    int insert(Discounts record);

    int insertSelective(Discounts record);

    Discounts selectByPrimaryKey(String discountId);

    int updateByPrimaryKeySelective(Discounts record);

    int updateByPrimaryKey(Discounts record);
    
    ArrayList<Discounts> selectAllByDiscountType(Discounts record);
    
    Integer selectAllCountByDiscountType(Discounts record);
    
    int changeDiscountActive(Discounts record);
}