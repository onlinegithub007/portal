package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.BindingGoods;

import java.util.ArrayList;

public interface BindingGoodsMapper {
    int deleteByPrimaryKey(String bindGoodsId);

    int insert(BindingGoods record);

    int insertSelective(BindingGoods record);

    BindingGoods selectByPrimaryKey(String bindGoodsId);
    
    BindingGoods selectByCode(BindingGoods record);
    
    ArrayList<BindingGoods> selectByStore(String storeId);

    int updateByPrimaryKeySelective(BindingGoods record);

    int updateByPrimaryKey(BindingGoods record);
}