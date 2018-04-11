package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.ProductMenus;

import java.util.ArrayList;

public interface ProductMenusMapper {
    int deleteByPrimaryKey(Integer productMenuId);

    int insert(ProductMenus record);

    int insertSelective(ProductMenus record);

    ProductMenus selectByPrimaryKey(Integer productMenuId);
    
    ArrayList<ProductMenus> selectAllMenus();

    int updateByPrimaryKeySelective(ProductMenus record);

    int updateByPrimaryKey(ProductMenus record);
}