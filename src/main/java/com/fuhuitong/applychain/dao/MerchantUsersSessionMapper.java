package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantUsersSession;

public interface MerchantUsersSessionMapper {
    int deleteByPrimaryKey(String merUserToken);

    int insert(MerchantUsersSession record);

    int insertSelective(MerchantUsersSession record);

    MerchantUsersSession selectByPrimaryKey(String merUserToken);

    int updateByPrimaryKeySelective(MerchantUsersSession record);

    int updateByPrimaryKey(MerchantUsersSession record);
    
    int updateSession(String merUserId);
}