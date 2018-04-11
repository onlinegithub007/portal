package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantSeeds;

public interface MerchantSeedsMapper {
    int deleteByPrimaryKey(Integer merSeedId);
    
    int deleteByMerId(String merId);

    int insert(MerchantSeeds record);

    int insertSelective(MerchantSeeds record);

    MerchantSeeds selectByPrimaryKey(Integer merSeedId);
    
    MerchantSeeds selectByMerId(String merId);

    int updateByPrimaryKeySelective(MerchantSeeds record);

    int updateByPrimaryKey(MerchantSeeds record);
    
    
}