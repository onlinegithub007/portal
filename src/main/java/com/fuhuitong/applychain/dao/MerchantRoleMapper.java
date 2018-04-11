package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantRole;

import java.util.ArrayList;

public interface MerchantRoleMapper 
{
    int deleteByPrimaryKey(Integer merRoleId);

    int insert(MerchantRole record);

    int insertSelective(MerchantRole record);

    MerchantRole selectByPrimaryKey(Integer merRoleId);
    
    ArrayList<MerchantRole> selectMerchantRoles(String merId);

    int updateByPrimaryKeySelective(MerchantRole record);

    int updateByPrimaryKey(MerchantRole record);
    
    int deleteByMerId(String merId);
}