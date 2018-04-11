package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.PurchaseOrders;

import java.util.ArrayList;

public interface PurchaseOrdersMapper {
    int deleteByPrimaryKey(String purchaseOrderId);

    int deleteByStockOrderId(String stockOrderId);
    
    int insert(PurchaseOrders record);

    int insertSelective(PurchaseOrders record);

    PurchaseOrders selectByPrimaryKey(String purchaseOrderId);
    
    ArrayList<PurchaseOrders> selectByStockOrder(PurchaseOrders purchaseOrder);
    
    ArrayList<PurchaseOrders> selectByStockOrderOnAck(PurchaseOrders purchaseOrder);
    
    ArrayList<PurchaseOrders> selectStoreOutStockOrders(PurchaseOrders purchaseOrder);
    
    ArrayList<PurchaseOrders> selectByProvider(PurchaseOrders purchaseOrder);
    
    Integer selectByProviderCount(PurchaseOrders record);
    
    ArrayList<PurchaseOrders> selectStoreDispatch(PurchaseOrders purchaseOrder);
    
    Integer selectStoreDispatchCount(PurchaseOrders record);

    int updateByPrimaryKeySelective(PurchaseOrders record);

    int updateByPrimaryKey(PurchaseOrders record);
    
    Integer selectByUnAcked(PurchaseOrders record);
    
    int changeOrderStatus(PurchaseOrders record);
    
    int updateStoreOutAck(PurchaseOrders record);
    
    int updateAckAmount(PurchaseOrders record);
    
    int updateProviderAck(PurchaseOrders record);
    
    int updateTotalProviderValue(PurchaseOrders record);
    
    int updateAuditAfterProviderAck(PurchaseOrders record);
    
    Integer selectByProviderToAuditCount(PurchaseOrders record);
    
    ArrayList<PurchaseOrders> selectByProviderToAudit(PurchaseOrders purchaseOrder);
    
    Integer selectAckedPurchaseOrdersCount(PurchaseOrders purchaseOrder);
    
    ArrayList<PurchaseOrders> selectAckedPurchaseOrders(PurchaseOrders purchaseOrder);
}