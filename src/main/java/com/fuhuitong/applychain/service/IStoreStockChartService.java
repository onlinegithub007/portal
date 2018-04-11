package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.chart.StoreStockChart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IStoreStockChartService extends IBaseService{
	public String storeStockChart(HttpServletRequest request, HttpSession session);
	
	public String storeStockChartData(HttpServletRequest request, HttpSession session, StoreStockChart chart);
	
	public String storeStockChartLastData(HttpServletRequest request, HttpSession session);
	
	public String storeStockChartGoodsData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, boolean show);
}
