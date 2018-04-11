package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StockOrdersSN;

public interface StockOrdersSNMapper {
    int deleteByPrimaryKey(Integer stockOrderSn);

    int insert(StockOrdersSN record);

    int insertSelective(StockOrdersSN record);

    StockOrdersSN selectByPrimaryKey(Integer stockOrderSn);

    int updateByPrimaryKeySelective(StockOrdersSN record);

    int updateByPrimaryKey(StockOrdersSN record);
    
    StockOrdersSN selectByOrderId(String stockOrderId);
}