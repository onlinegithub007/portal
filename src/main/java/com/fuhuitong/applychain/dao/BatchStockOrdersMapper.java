package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.BatchStockOrders;

import java.util.ArrayList;

public interface BatchStockOrdersMapper {
    int deleteByPrimaryKey(String batchStockOrderId);

    int insert(BatchStockOrders record);

    int insertSelective(BatchStockOrders record);

    BatchStockOrders selectByPrimaryKey(String batchStockOrderId);
    
    ArrayList<BatchStockOrders> selectAllByMerId(BatchStockOrders param);
    
    Integer selectCountByMerId(String merId);

    int updateByPrimaryKeySelective(BatchStockOrders record);

    int updateByPrimaryKey(BatchStockOrders record);
    
    int updateGoodsCount(String batchStockOrderId);
    
    int updateStoresCount(String batchStockOrderId);
    
    int changeBatchOrderStatus(BatchStockOrders record);
}