package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.ProductRole;

import java.util.ArrayList;

public interface ProductRoleMapper {
    int deleteByPrimaryKey(Integer productRoleId);

    int insert(ProductRole record);

    int insertSelective(ProductRole record);

    ProductRole selectByPrimaryKey(Integer productRoleId);
    
    ArrayList<ProductRole> selectAll();

    int updateByPrimaryKeySelective(ProductRole record);

    int updateByPrimaryKey(ProductRole record);
}