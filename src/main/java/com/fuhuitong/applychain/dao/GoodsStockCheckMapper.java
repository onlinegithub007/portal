package com.fuhuitong.applychain.dao;


import com.fuhuitong.applychain.model.GoodsStockCheck;

public interface GoodsStockCheckMapper {
    int deleteByPrimaryKey(Integer stockCheckId);

    int insert(GoodsStockCheck record);

    int insertSelective(GoodsStockCheck record);

    GoodsStockCheck selectByPrimaryKey(Integer stockCheckId);

    int updateByPrimaryKeySelective(GoodsStockCheck record);

    int updateByPrimaryKey(GoodsStockCheck record);
    
    int deleteByDay(GoodsStockCheck record);
}