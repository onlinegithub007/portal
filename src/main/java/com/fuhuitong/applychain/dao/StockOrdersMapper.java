package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StockOrders;

import java.util.ArrayList;

public interface StockOrdersMapper {
    int deleteByPrimaryKey(String stockOrderId);

    int insert(StockOrders record);

    int insertSelective(StockOrders record);

    StockOrders selectByPrimaryKey(String stockOrderId);

    int updateByPrimaryKeySelective(StockOrders record);

    int updateByPrimaryKey(StockOrders record);
    
    int updateGoodsCount(StockOrders record);
    
    int updateAckGoodsCount(StockOrders record);
    
    int updateBriefInfo(StockOrders record);
    
    ArrayList<StockOrders> selectStoreStockOrders(StockOrders query);
    
    ArrayList<StockOrders> selectStoreStockOrdersReports(StockOrders query);
    
    int selectStoreStockOrdersReportsCount(StockOrders query);
    
    int selectStoreStockOrdersCount(StockOrders query);
    
    int appendOrderProcess(StockOrders query);
    
    int changeOrderStatus(StockOrders query);
    
    int updateProviderAck(StockOrders query);
}