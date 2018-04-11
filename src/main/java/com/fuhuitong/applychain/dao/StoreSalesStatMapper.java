package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StoreSalesStat;

import java.util.ArrayList;

public interface StoreSalesStatMapper {
    int deleteByPrimaryKey(Integer saleStatId);

    int insert(StoreSalesStat record);

    int insertSelective(StoreSalesStat record);

    StoreSalesStat selectByPrimaryKey(Integer saleStatId);

    int updateByPrimaryKeySelective(StoreSalesStat record);

    int updateByPrimaryKey(StoreSalesStat record);
    
    int deleteByDate(StoreSalesStat record);
    
    ArrayList<StoreSalesStat> selectByDate(StoreSalesStat record);
    
    Integer selectCountByDate(StoreSalesStat record);
}