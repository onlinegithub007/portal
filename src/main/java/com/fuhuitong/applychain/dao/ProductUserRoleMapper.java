package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.ProductUserRole;

import java.util.ArrayList;

public interface ProductUserRoleMapper 
{
    int deleteByPrimaryKey(Integer productUserRoleId);

    int insert(ProductUserRole record);

    int insertSelective(ProductUserRole record);

    ProductUserRole selectByPrimaryKey(Integer productUserRoleId);

    int updateByPrimaryKeySelective(ProductUserRole record);

    int updateByPrimaryKey(ProductUserRole record);
    
    ArrayList<ProductUserRole> selectByProduct(Integer productRoleId);
    
    ArrayList<ProductUserRole> selectByMerId(String merId);
}