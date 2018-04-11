package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StoreDailyClearing;

import java.util.ArrayList;

public interface StoreDailyClearingMapper 
{
    int deleteByPrimaryKey(Integer dailyClearId);
    
    int deleteByDate(StoreDailyClearing record);

    int insert(StoreDailyClearing record);

    int insertSelective(StoreDailyClearing record);

    StoreDailyClearing selectByPrimaryKey(Integer dailyClearId);

    int updateByPrimaryKeySelective(StoreDailyClearing record);

    int updateByPrimaryKey(StoreDailyClearing record);
    
    ArrayList<StoreDailyClearing> selectStoreDaily(StoreDailyClearing record);
    
    Integer selectStoreDailyCount(StoreDailyClearing record);
}