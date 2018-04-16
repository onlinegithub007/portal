package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.OrderDetails;
import com.fuhuitong.applychain.model.Orders;
import com.fuhuitong.applychain.model.StoreDailyClearing;
import com.fuhuitong.applychain.model.StoreSalesReports;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IOrdersService extends IBaseService{

	public String storeOrderRecords(HttpServletRequest request, HttpSession session);
	
	public String merchantStoresOrderRecords(HttpServletRequest request, HttpSession session);
	
	public String storeOrderRecordsData(HttpServletRequest request, HttpSession session, Orders query);
	
	public String masterStoresOrderRecordsData(HttpServletRequest request, HttpSession session, Orders query);
	
	public String masterStoresOrderRecordsConfirm(HttpServletRequest request, HttpSession session, Orders query);
	
	public String storeOrderGoodsData(HttpServletRequest request, HttpSession session, Orders query);
	
	public String storeDailyClearing(HttpServletRequest request, HttpSession session);
	
	public String storeDailyClearingDetails(HttpServletRequest request, HttpSession session, String createDateText);
	
	public String storeDailyClearingDetailsData(HttpServletRequest request, HttpSession session);
	
	public String storeDailyClearingData(HttpServletRequest request, HttpSession session, StoreDailyClearing param);
	
	public String storeDailyClearingDo(HttpServletRequest request, HttpSession session, String createDateText);
	
	public String storeDailyClearingRereshProfit(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsSaleSort(HttpServletRequest request, HttpSession session);
	
	public String storeTimeSaleSort(HttpServletRequest request, HttpSession session);
	
	public String storeTimeSaleData(HttpServletRequest request, HttpSession session, OrderDetails param);
	
	public String storeSaleSort(HttpServletRequest request, HttpSession session);
	
	public String storeGoodsSaleSortData(HttpServletRequest request, HttpSession session, OrderDetails param);
	
	public String storeSaleSortData(HttpServletRequest request, HttpSession session, Orders param);
	
	public String storeSaleSortChartData(HttpServletRequest request, HttpSession session);
	
	public String providersSaleStatSelect(HttpServletRequest request, HttpSession session);
	
	public String providersSaleStatReport(HttpServletRequest request, HttpSession session, String merUserId);
	
	public String providersSaleStatReportData(HttpServletRequest request, HttpSession session, OrderDetails orderDetail);
	
	public String storesSaleReports(HttpServletRequest request, HttpSession session);
	
	public String storesSaleReportsData(HttpServletRequest request, HttpSession session, StoreSalesReports param);

	String addRateByMerId(HttpServletRequest request, HttpSession session);
	
	public ResponseEntity<byte[]> exportAllOrders(HttpServletRequest request, HttpSession session, Orders query);
	public ResponseEntity<byte[]> exportAgentsAllOrders(HttpServletRequest request, HttpSession session, Orders query);
	
	/**
	 * 生成门店销售报表任务
	 * @return
	 */
	public String makeStoreSalesDailyReport(String[] merIds, String saleDateText);
	
	public ResponseEntity<byte[]> downloadStoreSalesReport(HttpServletRequest request, HttpSession session, StoreSalesReports param);
}
