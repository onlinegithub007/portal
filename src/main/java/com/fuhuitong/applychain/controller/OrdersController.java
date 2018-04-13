package com.fuhuitong.applychain.controller;


import com.fuhuitong.applychain.model.OrderDetails;
import com.fuhuitong.applychain.model.Orders;
import com.fuhuitong.applychain.model.StoreDailyClearing;
import com.fuhuitong.applychain.model.StoreSalesReports;
import com.fuhuitong.applychain.service.IOrdersService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OrdersController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IOrdersService orderService;
	
	/**
	 * 进入门店交易数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_orders_records.html")
	public String storeOrderRecords(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeOrderRecords(request, session);
	}
	
	/**
	 * 进入总部交易数据界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/merchant_stores_orders_records.html")
	public String merchantStoresOrderRecords(HttpServletRequest request, HttpSession session)
	{
		return orderService.merchantStoresOrderRecords(request, session);
	}

	/**
	 * 总交易查询
	 * @param request
	 * @param session
	 * @param query
	 * @return
	 */
	
	@RequestMapping("/backstage/store_orders_records_data.html")
	public @ResponseBody String storeOrderRecordsData(HttpServletRequest request, HttpSession session, Orders query)
	{
		return orderService.storeOrderRecordsData(request, session, query);
	}
	
	@RequestMapping("/backstage/master_stores_orders_records_data.html")
	public @ResponseBody String masterStoresOrderRecordsData(HttpServletRequest request, HttpSession session, Orders query)
	{
		return orderService.masterStoresOrderRecordsData(request, session, query);
	}
	
	
	@RequestMapping("/backstage/exportAllOrders.html")
	public ResponseEntity<byte[]> exportAllOrders(HttpServletRequest request, HttpSession session, Orders query)
	{
		return orderService.exportAllOrders(request, session, query);
	}
	
	@RequestMapping("/backstage/exportAgentsAllOrders.html")
	public ResponseEntity<byte[]> exportAgentsAllOrders(HttpServletRequest request, HttpSession session, Orders query)
	{
		return orderService.exportAgentsAllOrders(request, session, query);
	}
	
	/**
	 * 线下门店核销
	 * @param request
	 * @param session
	 * @param query
	 * @return
	 */
	@RequestMapping("/backstage/master_stores_orders_records_confirm.html")
	public @ResponseBody String masterStoresOrderRecordsConfirm(HttpServletRequest request, HttpSession session, Orders query)
	{
		return orderService.masterStoresOrderRecordsConfirm(request, session, query);
	}
	
	@RequestMapping("/backstage/store_orders_goods_data.html")
	public @ResponseBody String storeOrderGoodsData(HttpServletRequest request, HttpSession session, Orders query)
	{
		return orderService.storeOrderGoodsData(request, session, query);
	}
	
	/**
	 * 进入门店日结主界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_daliy_clearing.html")
	public String storeDailyClearing(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeDailyClearing(request, session);
	}
	
	/**
	 * 查看当日的商品销售统计页面
	 */
	@RequestMapping("/backstage/store_daily_clearing_details.html")
	public String storeDailyClearingDetails(HttpServletRequest request, HttpSession session, String createDateText)
	{
		return orderService.storeDailyClearingDetails(request, session, createDateText);
	}
	
	/**
	 * 查看当日的商品销售统计数据
	 */
	@RequestMapping("/backstage/store_daily_clearing_details_data.html")
	public @ResponseBody String storeDailyClearingDetailsData(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeDailyClearingDetailsData(request, session);
	}
	
	@RequestMapping("/backstage/store_daliy_clearing_data.html")
	public @ResponseBody String storeDailyClearingData(HttpServletRequest request, HttpSession session, StoreDailyClearing param)
	{
		return orderService.storeDailyClearingData(request, session, param);
	}
	
	@RequestMapping("/backstage/store_daliy_clearing_do.html")
	public @ResponseBody String storeDailyClearingDo(HttpServletRequest request, HttpSession session, String createDateText)
	{
		return orderService.storeDailyClearingDo(request, session, createDateText);
	}
	
	/**
	 * 临时刷新订单的毛利润
	 * @param request
	 * @param session
	 * @param createDateText
	 * @return
	 */
	@RequestMapping("/backstage/store_daliy_clearing_refresh_profit.html")
	public @ResponseBody String storeDailyClearingRereshProfit(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeDailyClearingRereshProfit(request, session);
	}
	
	/**
	 * 商品销售排行
	 */
	@RequestMapping("/backstage/stores_goods_sale_sort.html")
	public String storeGoodsSaleSort(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeGoodsSaleSort(request, session);
	}
	
	/**
	 * 商品时段销售排行
	 */
	@RequestMapping("/backstage/stores_time_sale_sort.html")
	public String storeTimeSaleSort(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeTimeSaleSort(request, session);
	}
	
	/**
	 * 商品时段销售排行
	 */
	@RequestMapping("/backstage/stores_time_sale_data.html")
	public @ResponseBody String storeTimeSaleData(HttpServletRequest request, HttpSession session, OrderDetails param)
	{
		return orderService.storeTimeSaleData(request, session, param);
	}
	
	/**
	 * 商品销售排行
	 */
	@RequestMapping("/backstage/stores_goods_sale_sort_data.html")
	public @ResponseBody String storeGoodsSaleSortData(HttpServletRequest request, HttpSession session, OrderDetails param)
	{
		return orderService.storeGoodsSaleSortData(request, session, param);
	}
	
	/**
	 * 门店销售排行
	 */
	@RequestMapping("/backstage/stores_sale_sort.html")
	public String storeSaleSort(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeSaleSort(request, session);
	}
	
	/**
	 * 门店销售排行数据
	 */
	@RequestMapping("/backstage/stores_sale_sort_data.html")
	public @ResponseBody String storeSaleSortData(HttpServletRequest request, HttpSession session, Orders param)
	{
		return orderService.storeSaleSortData(request, session, param);
	}
	
	@RequestMapping("/backstage/stores_sale_sort_chart_data.html")
	public @ResponseBody String storeSaleSortChartData(HttpServletRequest request, HttpSession session)
	{
		return orderService.storeSaleSortChartData(request, session);
	}
	
	/**
	 * 选择供应商主界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/providers_sale_stat_select.html")
	public String providersSaleStatSelect(HttpServletRequest request, HttpSession session)
	{
		return orderService.providersSaleStatSelect(request, session);
	}
	
	/**
	 * 选择供应商销售报表界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/providers_sale_stat_report.html")
	public String providersSaleStatReport(HttpServletRequest request, HttpSession session, String merUserId)
	{
		return orderService.providersSaleStatReport(request, session, merUserId);
	}
	
	@RequestMapping("/backstage/providers_sale_stat_report_data.html")
	public @ResponseBody String providersSaleStatReportData(HttpServletRequest request, HttpSession session, OrderDetails orderDetail)
	{
		return orderService.providersSaleStatReportData(request, session, orderDetail);
	}
	
	/**
	 * 进入门店销售报表主页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/stores_sale_reports.html")
	public String storesSaleReports(HttpServletRequest request, HttpSession session)
	{
		return orderService.storesSaleReports(request, session);
	}
	
	/**
	 * 门店销售报表数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/stores_sale_reports_data.html")
	public @ResponseBody String storesSaleReportsData(HttpServletRequest request, HttpSession session, StoreSalesReports param)
	{
		return orderService.storesSaleReportsData(request, session, param);
	}
	
	@RequestMapping("/backstage/stores_sale_reports_data_export.html")
	public ResponseEntity<byte[]> downloadStoreSalesReport(HttpServletRequest request, HttpSession session, StoreSalesReports param)
	{
		return orderService.downloadStoreSalesReport(request, session, param);
	}
}
