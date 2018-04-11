package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantClients;

public interface MerchantClientsMapper {
    int deleteByPrimaryKey(Long merClientId);

    int insert(MerchantClients record);

    int insertSelective(MerchantClients record);

    MerchantClients selectByPrimaryKey(Long merClientId);

    int updateByPrimaryKeySelective(MerchantClients record);

    int updateByPrimaryKey(MerchantClients record);
}