package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.AppDownload;
import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.MerchantUsers;
import com.fuhuitong.applychain.model.request.*;
import com.fuhuitong.applychain.model.response.*;
import org.springframework.http.ResponseEntity;

public interface IPOSAppService {

	public CheckNewVersionResp checkNewVersion(AppDownload appDownloadParam);
	
	public ResponseEntity<byte[]> downloadApp(AppDownload appDownloadParam, String ipAddress);
	
	public PosUserLoginResp posUserLogin(MerchantUsers user, String ipAddress);
	
	public PosQueryGoodsResp posQueryGoodsInfo(GoodsInfo goodsInfo, String ipAddress);
	
	public CreateOrderResp createOrder(CreateOrderReq reqParam, String ipAddress);
	
	public CreateOrderResp posCreateOrderWithoutGoods(CreateOrderReq reqParam, String ipAddress);
	
	public QueryVIPInfosResp posQueryVIPInfos(QueryVIPInfosReq reqParam, String ipAddress);
	
	public POSPaySuccessResp posPaySuccess(POSPaySuccessReq reqParam, String ipAddress);
	
	public QueryOrdersResp posQueryOrders(QueryOrdersReq reqParam, String ipAddress);
	
	public QueryOrderDetailResp posGetOrderDetail(QueryOrderDetailReq reqParam, String ipAddress);
	
	public OrderPrintResp posGetOrderPrintInfo(QueryOrderDetailReq reqParam, String ipAddress);
	
	public QueryMemberResp posQueryMember(QueryMemberReq reqParam, String ipAddress);
	
	public GoodsStockParam posGetGoodsStock(GoodsStockParam goodsStockParam, String ipAddress);
	
	public GoodsStockParam posSetGoodsStock(GoodsStockParam goodsStockParam, String ipAddress);
	
}
