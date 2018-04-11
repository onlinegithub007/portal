package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.DiscountStores;

import java.util.ArrayList;

public interface DiscountStoresMapper {
    int deleteByPrimaryKey(Integer discountStoreId);

    int insert(DiscountStores record);

    int insertSelective(DiscountStores record);

    DiscountStores selectByPrimaryKey(Integer discountStoreId);
    
    ArrayList<DiscountStores> selectByDiscountId(String discountId);

    int updateByPrimaryKeySelective(DiscountStores record);

    int updateByPrimaryKey(DiscountStores record);
    
    int deleteByDiscountId(String discountId);
}