package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsType;

import java.util.ArrayList;

public interface GoodsTypeMapper 
{
    int deleteByPrimaryKey(String goodsTypeId);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(String goodsTypeId);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);
    
    ArrayList<GoodsType> selectByMerId(String merId);
    
    ArrayList<GoodsType> selectByParentId(String merId);
    
    ArrayList<GoodsType> selectTopTypeByMerId(String merId);
    
    ArrayList<GoodsType> selectSubTypeByMerId(GoodsType query);
    
}