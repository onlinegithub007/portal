package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MerchantsSettings;

public interface MerchantsSettingsMapper {
    int deleteByPrimaryKey(Integer merSettingId);

    int insert(MerchantsSettings record);

    int insertSelective(MerchantsSettings record);

    MerchantsSettings selectByPrimaryKey(Integer merSettingId);
    
    MerchantsSettings selectByMerId(String merId);

    int updateByPrimaryKeySelective(MerchantsSettings record);

    int updateByPrimaryKey(MerchantsSettings record);
}