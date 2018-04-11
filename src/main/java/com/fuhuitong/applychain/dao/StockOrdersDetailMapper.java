package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StockOrdersDetail;

import java.util.ArrayList;

public interface StockOrdersDetailMapper {
    int deleteByPrimaryKey(Integer stockOrderDetailId);

    int insert(StockOrdersDetail record);

    int insertSelective(StockOrdersDetail record);

    StockOrdersDetail selectByPrimaryKey(Integer stockOrderDetailId);
    
    ArrayList<StockOrdersDetail> selectStoreStockOrderDetail(StockOrdersDetail record);
    
    StockOrdersDetail selectTotalAckPriceBy(String stockOrderId);
    
    StockOrdersDetail findByGoodsId(StockOrdersDetail record);
    
    StockOrdersDetail statDetailOrder(String stockOrderId);

    int updateByPrimaryKeySelective(StockOrdersDetail record);

    int updateByPrimaryKey(StockOrdersDetail record);
    
    int deleteByOrderId(String stockOrderId);
    
    int selectTotalPriceBy(String stockOrderId);
    
    int updateOnAck(StockOrdersDetail record);
    
    int updateOnAuditAfterProviderAck(StockOrdersDetail record);
    
}