package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.PurchaseOrdersDetail;

import java.util.ArrayList;

public interface PurchaseOrdersDetailMapper {
    int deleteByPrimaryKey(Integer purchaseDetailId);

    int deleteByStockOrderId(String stockOrderId);
    
    int insert(PurchaseOrdersDetail record);

    int insertSelective(PurchaseOrdersDetail record);

    PurchaseOrdersDetail selectByPrimaryKey(Integer purchaseDetailId);
    
    PurchaseOrdersDetail statProviderAckTotalValue(String purchaseOrderId);
    
    /**
     * 统计确认后的商品总数量和价格
     * @param purchaseOrderId
     * @return
     */
    PurchaseOrdersDetail statAckedTotalValue(String purchaseOrderId);

    int updateByPrimaryKeySelective(PurchaseOrdersDetail record);

    int updateByPrimaryKey(PurchaseOrdersDetail record);
    
    int selectByUnAckedCount(String purchaseOrderId);
    
    int updateProviderAck(PurchaseOrdersDetail record);
    
    int updateAckStatus1(String purchaseOrderId);
    
    int updateAckStatus2(String purchaseOrderId);
    
    ArrayList<PurchaseOrdersDetail> selectByPurchaseOrderId(String purchaseOrderId);
    
    ArrayList<PurchaseOrdersDetail> selectByPurchOrderIdWithStock(PurchaseOrdersDetail query);
}