package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantUserRole;

import java.util.ArrayList;

public interface MerchantUserRoleMapper {
    int deleteByPrimaryKey(Integer merUserRoleId);

    int insert(MerchantUserRole record);

    int insertSelective(MerchantUserRole record);

    MerchantUserRole selectByPrimaryKey(Integer merUserRoleId);
    
    ArrayList<MerchantUserRole> selectByMerId(String merId);
    
    MerchantUserRole selectDefRoleByMerId(MerchantUserRole query);

    int updateByPrimaryKeySelective(MerchantUserRole record);

    int updateByPrimaryKey(MerchantUserRole record);
}