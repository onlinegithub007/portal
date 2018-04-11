package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.BatchStockStoreDetails;

import java.util.ArrayList;

public interface BatchStockStoreDetailsMapper {
    int deleteByPrimaryKey(Integer batchStockStoreId);
    
    int deleteByStockId(String batchStockOrderId);
    
    int deleteByStore(BatchStockStoreDetails record);

    int insert(BatchStockStoreDetails record);

    int insertSelective(BatchStockStoreDetails record);

    BatchStockStoreDetails selectByPrimaryKey(Integer batchStockStoreId);
    
    ArrayList<BatchStockStoreDetails> selectByStockOrderId(String batchStockOrderId);

    int updateByPrimaryKeySelective(BatchStockStoreDetails record);

    int updateByPrimaryKey(BatchStockStoreDetails record);
}