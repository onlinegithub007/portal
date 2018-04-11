package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsPrice;

import java.util.ArrayList;

public interface GoodsPriceMapper {
    int deleteByPrimaryKey(Long goodsPriceId);

    int insert(GoodsPrice record);

    int insertSelective(GoodsPrice record);

    GoodsPrice selectByPrimaryKey(Long goodsPriceId);
    
    GoodsPrice selectByStoreGoods(GoodsPrice record);
    
    ArrayList<GoodsPrice> selectByStore(String storeId);

    int updateByPrimaryKeySelective(GoodsPrice record);

    int updateByPrimaryKey(GoodsPrice record);
    
    int savePrice(GoodsPrice record);
    
    int grantPrice(GoodsPrice record);
}