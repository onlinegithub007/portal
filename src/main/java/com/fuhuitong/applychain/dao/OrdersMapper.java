package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.Orders;

import java.util.ArrayList;

public interface OrdersMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(String orderId);
    
    Orders selectByOrderCode(Orders query);
    
    Orders selectPrintByOrderCode(Orders query);
    
    Orders statOnClearingByDate(Orders query);
    
    ArrayList<Orders> selectByDate(Orders record);
    
    ArrayList<Orders> selectByDatetime(Orders record);
    
    ArrayList<Orders> selectByDate2(Orders record);
    
    ArrayList<Orders> selectByDate2EXCEL(Orders record);
    
    ArrayList<Orders> selectAll();
    
    ArrayList<Orders> selectStoreSaleDataByDates(Orders record);
    
    int selectCountByDate2(Orders record);

    int updateByPrimaryKeySelective(Orders record);

//    int updateByPrimaryKeyWithBLOBs(Orders record);

    int updateByPrimaryKey(Orders record);
    
    int updateBillPrintFlag(String orderId);
    
    int updateOrderProfit(String orderId);
    
    int updateOrderConfirm(Orders record);
}