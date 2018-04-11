package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsStockBills;

public interface GoodsStockBillsMapper {
    int deleteByPrimaryKey(String stockBillId);

    int insert(GoodsStockBills record);

    int insertSelective(GoodsStockBills record);

    GoodsStockBills selectByPrimaryKey(String stockBillId);
    
    GoodsStockBills selectTempByPurchaseOrder(String purchaseOrderId);

    int updateByPrimaryKeySelective(GoodsStockBills record);

    int updateByPrimaryKey(GoodsStockBills record);
    
    int updateBillStatus(GoodsStockBills record);
}