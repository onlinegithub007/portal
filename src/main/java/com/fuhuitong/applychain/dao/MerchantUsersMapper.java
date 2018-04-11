package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantUsers;

import java.util.ArrayList;

public interface MerchantUsersMapper {
    int deleteByPrimaryKey(String merUserId);

    int insert(MerchantUsers record);

    int insertSelective(MerchantUsers record);

    MerchantUsers selectByPrimaryKey(String merUserId);
    
    MerchantUsers login(String userAccount);
    
    MerchantUsers selectByMerCode(String merCode);

    int updateByPrimaryKeySelective(MerchantUsers record);

    int updateByPrimaryKey(MerchantUsers record);
    
    int updatePassword(MerchantUsers record);
    
    int changeUserStatus(MerchantUsers record);
    
    int updateGoodsProvider(MerchantUsers record);
    
    ArrayList<MerchantUsers> selectByMerGroupId(MerchantUsers query);
    
    ArrayList<MerchantUsers> selectByMerStoreId(MerchantUsers query);
    
    ArrayList<MerchantUsers> selectSuperAdmin(String merId);
    
    ArrayList<MerchantUsers> selectGoodsProviders(MerchantUsers query);
    
    ArrayList<MerchantUsers> selectStoreMaster(MerchantUsers query);
}