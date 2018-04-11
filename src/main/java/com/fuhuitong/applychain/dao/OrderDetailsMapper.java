package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.OrderDetails;

import java.util.ArrayList;

public interface OrderDetailsMapper {
    int deleteByPrimaryKey(Long orderDetailId);

    int insert(OrderDetails record);

    int insertSelective(OrderDetails record);

    int insertBatch(ArrayList<OrderDetails> orderDetailsList);
    
    OrderDetails selectByPrimaryKey(Long orderDetailId);
    
    OrderDetails selectStoreGoodsSales(OrderDetails record);
    
    ArrayList<OrderDetails> selectByOrderId(String orderId);
    
    ArrayList<OrderDetails> statGoodsSalesByDate(OrderDetails record);
    
    ArrayList<OrderDetails> statSalesByDate(OrderDetails record);
    
    ArrayList<OrderDetails> statGoodsSalesByProvider(OrderDetails record);
    
    ArrayList<OrderDetails> statTimeSalesBy(OrderDetails record);

    int updateByPrimaryKeySelective(OrderDetails record);

    int updateByPrimaryKey(OrderDetails record);
    
    int updateStockDetailId(OrderDetails record);
}