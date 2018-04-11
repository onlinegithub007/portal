package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantsGroups;

import java.util.ArrayList;

public interface MerchantsGroupsMapper {
    int deleteByPrimaryKey(String merGroupId);

    int insert(MerchantsGroups record);

    int insertSelective(MerchantsGroups record);

    MerchantsGroups selectByPrimaryKey(String merGroupId);
    
    MerchantsGroups selectGroupCode(String merGroupId);
    
    ArrayList<MerchantsGroups> selectAllGroups(String merId);
    
    ArrayList<MerchantsGroups> selectAllStores(String merId);
    
    ArrayList<MerchantsGroups> selectAllStores2(MerchantsGroups store);

    int updateByPrimaryKeySelective(MerchantsGroups record);

    int updateByPrimaryKey(MerchantsGroups record);
    
    int selectAllStores2Count(MerchantsGroups record);
}