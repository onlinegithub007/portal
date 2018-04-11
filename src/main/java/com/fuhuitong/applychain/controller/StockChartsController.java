package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.chart.StoreStockChart;
import com.fuhuitong.applychain.service.IStoreStockChartService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class StockChartsController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IStoreStockChartService storeStockService;
	
	/**
	 * 进入门店库存排行主页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_chart.html")
	public String storeStockChart(HttpServletRequest request, HttpSession session)
	{
		return storeStockService.storeStockChart(request, session);
	}
	
	@RequestMapping("/backstage/store_stock_chart_data.html")
	public @ResponseBody String storeStockChartData(HttpServletRequest request, HttpSession session, StoreStockChart chart)
	{
		return storeStockService.storeStockChartData(request, session, chart);
	}
	
	/**
	 * 得到数据表格查询结果集合给前端CHART
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_chart_last_data.html")
	public @ResponseBody String storeStockChartLastData(HttpServletRequest request, HttpSession session)
	{
		return storeStockService.storeStockChartLastData(request, session);
	}
	
	@RequestMapping("/backstage/store_stock_chart_goods_data.html")
	public @ResponseBody String storeStockChartGoodsData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, boolean show)
	{
		return storeStockService.storeStockChartGoodsData(request, session, goodsInfo, show);
	}
}
