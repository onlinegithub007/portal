package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StockDispatch;

import java.util.ArrayList;

public interface StockDispatchMapper {
    int deleteByPrimaryKey(Integer stockDispatchId);

    int insert(StockDispatch record);

    int insertSelective(StockDispatch record);

    StockDispatch selectByPrimaryKey(Integer stockDispatchId);
    
    ArrayList<StockDispatch> selectMyTempDispatch(String merId);
    
    ArrayList<StockDispatch> selectFinishedByDay(StockDispatch record);
    
    ArrayList<StockDispatch> selectByPurchaseOrder(String purchaseOrderId);
    
    ArrayList<StockDispatch> selectByIds(StockDispatch record);

    int updateByPrimaryKeySelective(StockDispatch record);

    int updateByPrimaryKey(StockDispatch record);
    
    int updateStatus(StockDispatch record);
}