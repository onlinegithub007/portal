package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.BatchStockGoodsDetails;

import java.util.ArrayList;

public interface BatchStockGoodsDetailsMapper {
    int deleteByPrimaryKey(Integer batchStockGoodsId);
    
    int deleteByStockId(String batchStockOrderId);
    
    int deleteBy(BatchStockGoodsDetails record);

    int insert(BatchStockGoodsDetails record);
    
    int insertGoods(BatchStockGoodsDetails record);

    int insertSelective(BatchStockGoodsDetails record);

    BatchStockGoodsDetails selectByPrimaryKey(Integer batchStockGoodsId);
    
    ArrayList<BatchStockGoodsDetails> selectByStockId(String batchStockOrderId);

    int updateByPrimaryKeySelective(BatchStockGoodsDetails record);

    int updateByPrimaryKey(BatchStockGoodsDetails record);
    
    int updateCount(BatchStockGoodsDetails record);
    
    int updateProvider(BatchStockGoodsDetails record);
}