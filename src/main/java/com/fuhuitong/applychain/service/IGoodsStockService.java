package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IGoodsStockService extends IBaseService{
	public String merchantGoodsStock(HttpServletRequest request, HttpSession session);
	
	public String merchantGoodsStockData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public ResponseEntity<byte[]> downloadMerchantStockTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String importMerchantStock(MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);

	public String storeStockOrders(HttpServletRequest request, HttpSession session, Integer stockOrderStatus, String storeId);

	public String storeStockOrdersData(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String storeStockOrderInfo(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String saveStoreStockOrders(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String deleteStoreStockOrders(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String storeStockOrderGoods(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public ResponseEntity<byte[]> downloadStoreGoods(HttpServletRequest request, HttpSession session);
	
	public String storeStockOrderGoodsData(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String importStoreStockOrderGoods(MultipartFile uploadFile, HttpServletRequest request, HttpSession session);
	
	public String storeStockOrdersAudit(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String storeStockOrdersAuditData(HttpServletRequest request, HttpSession session);
	
	public String storeStockOrdersSubmit(HttpServletRequest request, HttpSession session, StockOrders order, String stockOrderDetailIdStr, String goodsCountStr);
	
	public String storeStockOrderGoodsDataAudit(HttpServletRequest request, HttpSession session, StockOrders order);
	
	public String selectGoodsProvider(HttpServletRequest request, HttpSession session, String goodsId);
	
	public String selectAllStoreGoodsStock(HttpServletRequest request, HttpSession session, String goodsId);
	
	public String setGoodsProviderInfo(HttpServletRequest request, HttpSession session, StockOrdersDetail detail);
	
	public String setGoodsCountInfoInstockOrder(HttpServletRequest request, HttpSession session, StockOrdersDetail detail);
	
	public String setStockOrderDealType(HttpServletRequest request, HttpSession session, StockOrdersDetail detail);
	
	public String setStockOrderWhichStore(HttpServletRequest request, HttpSession session, StockOrdersDetail detail);
	
	public String saveStockOrderAudit1(HttpServletRequest request, HttpSession session, boolean pass, String desc);
	
	public String saveStockOrderAudit2(HttpServletRequest request, HttpSession session, boolean pass, String desc);
	
	public String listStockOrderProviders(HttpServletRequest request, HttpSession session, PurchaseOrders queryParam);
	
	public String storePurchaseOrderView(HttpServletRequest request, HttpSession session, String purchaseOrderId);
	
	public String storePurchaseOrderViewGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId);
	
	public String saveStockOrderAudit3(HttpServletRequest request, HttpSession session);
	
	public String storeStockOrdersView(HttpServletRequest request, HttpSession session, String stockOrderId, String adminMode);
	
	public String storeStockOrdersAck(HttpServletRequest request, HttpSession session, String stockOrderId);
	
	public String storeStockOrdersAckGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId);
	
	public String storeStockOrdersAckGoods2(HttpServletRequest request, HttpSession session, PurchaseOrdersDetail orderDetail);
	
	public String storeStockOrdersAckAllGoods2(HttpServletRequest request, HttpSession session, String purchaseDetailIdStr, String goodsAckAmountStr);
	
	public String storeStockOrdersAckSuccess(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsStock(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String storeGoodsStockChange(HttpServletRequest request, HttpSession session, GoodsStock goodsStock);
	
	public String storeGoodsExpiredStock(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String storeGoodsExpiredStockData(HttpServletRequest request, HttpSession session, Integer expiredType);
	
	public String storeGoodsStockData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String storeGoodsStockCheckData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String storeGoodsStockDetail(HttpServletRequest request, HttpSession session, String goodsId);
	
	public String storeGoodsStockDataDetail(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsStockOut(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsStockOutData(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsStockOutGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId);
	
	public String storeGoodsStockOutGoodsData(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsStockOutAck(HttpServletRequest request, HttpSession session);
	
	public String masterStoreGoodsStockSelect(HttpServletRequest request, HttpSession session);
	
	public String storeStockOrdersReports(HttpServletRequest request, HttpSession session);
	
	public String storeStockOrdersReportsData(HttpServletRequest request, HttpSession session, StockOrders stockOrder);
	
	public String storeGoodsStockCheck(HttpServletRequest request, HttpSession session, GoodsStockCheck stockCheck);
	
	public String storeStockInitOrdersDetail(HttpServletRequest request, HttpSession session, String goodsList);
	
	public String storeStockDeleteOrdersDetail(HttpServletRequest request, HttpSession session, Integer stockOrderDetailId);
	
	public String storeStockUpdateOrdersDetail(HttpServletRequest request, HttpSession session, StockOrdersDetail detail);
	
	public String storeStockBatchUpdateCount(HttpServletRequest request, HttpSession session, String stockOrderDetailIdStr, String goodsCountStr);
	
	public String storeStockUpdate2OrdersDetail(HttpServletRequest request, HttpSession session, StockOrdersDetail detail);
	
	public String providerMyOrders(HttpServletRequest request, HttpSession session);
	
	public String providerMyOrdersData(HttpServletRequest request, HttpSession session, PurchaseOrders param);
	
	public String providerMyOrdersGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId, String action);
	
	public String providerMyOrdersGoodsData(HttpServletRequest request, HttpSession session);
	
	public String providerMyOrdersAckGoods(HttpServletRequest request, HttpSession session, PurchaseOrdersDetail purchaseDetail);
	
	public String providerMyOrdersAck(HttpServletRequest request, HttpSession session);
	
	public String providerOrdersAudit(HttpServletRequest request, HttpSession session);
	
	public String providerOrdersAuditData(HttpServletRequest request, HttpSession session, PurchaseOrders purchaseOrder);
	
	public String providerOrdersGoodsAudit(HttpServletRequest request, HttpSession session, String purchaseOrderId, String action);
	
	public String providerOrdersAuditAck(HttpServletRequest request, HttpSession session);
	
	public String masterStockOrderSelect(HttpServletRequest request, HttpSession session);
	
	public String masterBatchStockOrderSelect(HttpServletRequest request, HttpSession session);
	
	public String masterBatchStockOrders(HttpServletRequest request, HttpSession session);
	
	public String masterBatchStockOrdersData(HttpServletRequest request, HttpSession session, BatchStockOrders param);
	
	public String masterBatchStockOrdersCreate(HttpServletRequest request, HttpSession session);
	
	public String masterBatchStockOrdersDelete(HttpServletRequest request, HttpSession session, String batchStockOrderId);
	
	public String masterBatchStockOrderGoods(HttpServletRequest request, HttpSession session, String batchStockOrderId);
	
	public String masterBatchStockOrderStores(HttpServletRequest request, HttpSession session, String batchStockOrderId);
	
	public String masterBatchStockOrderStoresData(HttpServletRequest request, HttpSession session, String batchStockOrderId);
	
	public String masterBatchStockOrderStoresSave(HttpServletRequest request, HttpSession session, String storeIds);
	
	public String masterBatchStockOrderStoresRemove(HttpServletRequest request, HttpSession session, BatchStockStoreDetails storeDetails);
	
	public String masterBatchStockOrderSubmit(HttpServletRequest request, HttpSession session, String batchStockOrderId);
	
	public String masterBatchStockOrderGoodsDel(HttpServletRequest request, HttpSession session, BatchStockGoodsDetails BatchStockGoodsDetails);
	
	public String masterBatchStockOrderGoodsCount(HttpServletRequest request, HttpSession session, String batchStockGoodsIdStr, String goodsCountStr);
	
	public String masterBatchStockOrderGoodsData(HttpServletRequest request, HttpSession session);
	
	public String masterBatchStockInitGoods(HttpServletRequest request, HttpSession session, String goodsList);
	
	public String masterBatchStockSetGoodsProvider(HttpServletRequest request, HttpSession session, BatchStockGoodsDetails param);
	
	public String storeStockDispatch0(HttpServletRequest request, HttpSession session, StockDispatch dispatch);
	
	public String masterStockDispatch0(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch1(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch3(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch4(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch2(HttpServletRequest request, HttpSession session, String purchaseOrderId);
	
	public String masterStockDispatchDetails(HttpServletRequest request, HttpSession session, String purchaseOrderId, String url);
	
	public String masterStockDispatch2Data(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch2Action(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch3Action(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch4Action(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch0Data(HttpServletRequest request, HttpSession session);
	
	public String masterStockDispatch1Data(HttpServletRequest request, HttpSession session, PurchaseOrders purchaseOrders);
	
	public String masterStockDispatch0Action(HttpServletRequest request, HttpSession session, String dispatchIds);
	
	public String masterStockDispatch0Cancel(HttpServletRequest request, HttpSession session, Integer stockDispatchId);
	
	public String purchaseOrdersReports(HttpServletRequest request, HttpSession session);
	
	public String purchaseOrdersReportsData(HttpServletRequest request, HttpSession session, PurchaseOrders param);
	
	public String purchaseOrdersReportsDataUpdate(HttpServletRequest request, HttpSession session);
	
}
