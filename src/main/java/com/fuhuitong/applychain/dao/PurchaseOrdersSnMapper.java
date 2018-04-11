package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.PurchaseOrdersSn;

public interface PurchaseOrdersSnMapper {
    int deleteByPrimaryKey(Integer purchaseOrderSn);

    int insert(PurchaseOrdersSn record);

    int insertSelective(PurchaseOrdersSn record);

    PurchaseOrdersSn selectByPrimaryKey(Integer purchaseOrderSn);

    int updateByPrimaryKeySelective(PurchaseOrdersSn record);

    int updateByPrimaryKey(PurchaseOrdersSn record);
    
    PurchaseOrdersSn selectByPurchaseOrderId(String purchaseOrderId);
}