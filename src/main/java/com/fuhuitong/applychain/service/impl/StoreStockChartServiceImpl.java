package com.fuhuitong.applychain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fuhuitong.applychain.dao.GoodsInfoMapper;
import com.fuhuitong.applychain.dao.GoodsStockDetailMapper;
import com.fuhuitong.applychain.dao.GoodsTypeMapper;
import com.fuhuitong.applychain.dao.MerchantsGroupsMapper;
import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.GoodsStockDetail;
import com.fuhuitong.applychain.model.GoodsType;
import com.fuhuitong.applychain.model.MerchantsGroups;
import com.fuhuitong.applychain.model.chart.StoreStockChart;
import com.fuhuitong.applychain.service.IStoreStockChartService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

@Service
public class StoreStockChartServiceImpl extends BaseService implements IStoreStockChartService {

	@Resource
	private GoodsTypeMapper goodsTypeMapper;
	
	@Resource
	private GoodsStockDetailMapper goodsStockDetailMapper;
	
	@Resource
	private MerchantsGroupsMapper merchantsGroupsMapper;
	
	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	
	@Override
	public String storeStockChart(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 加载商品大类
		ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
		if (topGoodsType != null && !topGoodsType.isEmpty())
		{
			session.setAttribute("TopGoodsTypes", topGoodsType);
		}
		
		return "page/reports/store_stock_chart";
	}

	@Override
	public String storeStockChartData(HttpServletRequest request, HttpSession session, StoreStockChart chart) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		session.removeAttribute("CurrentStoreChartData");
		
		if (StringUtils.isEmpty(chart.getAction()))
		{
			return getTableData(null, "", 0);
		}
		
		// 先查询所有门店
		ArrayList<MerchantsGroups> allStores = this.merchantsGroupsMapper.selectAllStores(this.merUser.getMerId());
		// 放入到Hashtable中
		Hashtable<String, StoreStockChart> storeStockTable = new Hashtable<>();
		for (MerchantsGroups store : allStores)
		{
			StoreStockChart c = new StoreStockChart();
			c.setStoreName(store.getGroupName());
			c.setStoreId(store.getMerGroupId());
			c.setStockCount(0);
			c.setStockCost(0);
			
			storeStockTable.put(c.getStoreId(), c);
		}
		
		String chartData;
		String title = "整体库存排行";
		
		ArrayList<StoreStockChart> chartsTableData = new ArrayList<>();
		ArrayList<GoodsStockDetail> totalGoodsStocks = null;
		
		if (chart.getAction().equalsIgnoreCase("all"))
		{
			totalGoodsStocks = this.goodsStockDetailMapper.statTotalStockOfStore(this.merUser.getMerId());
			title = "整体库存排行";
		}
		else if (chart.getAction().equalsIgnoreCase("goodsType"))
		{
			logger.info("parentTypeId = " + chart.getParentTypeId());
			logger.info("goodsTypeId = " + chart.getGoodsTypeId());
			
			if (StringUtils.isEmpty(chart.getGoodsTypeId()))
			{
				chart.setGoodsTypeId(null);
			}
			
			GoodsStockDetail param = new GoodsStockDetail();
			param.setMerId(this.merUser.getMerId());
			param.setParentTypeId(chart.getParentTypeId());
			param.setGoodsTypeId(chart.getGoodsTypeId());
			
			totalGoodsStocks = this.goodsStockDetailMapper.statTotalStockOfStore2(param);
			
			if (StringUtils.isEmpty(chart.getGoodsTypeId()))
			{
				title = "品类库存排行 [" + this.goodsTypeMapper.selectByPrimaryKey(chart.getParentTypeId()).getGoodsTypeName() + "]";
			}
			else
			{
				title = "品类库存排行 [" + this.goodsTypeMapper.selectByPrimaryKey(chart.getGoodsTypeId()).getGoodsTypeName() + "]";
			}
		}
		else if (chart.getAction().equalsIgnoreCase("goodsId"))
		{
			logger.info("goodsId = " + chart.getGoodsId());
			
			totalGoodsStocks = this.goodsStockDetailMapper.statTotalStockOfStore3(chart.getGoodsId());
			
			title = "单品库存排行 [" + goodsInfoMapper.selectByPrimaryKey(chart.getGoodsId()).getGoodsName() + "]";
		}
		
		if (totalGoodsStocks != null && !totalGoodsStocks.isEmpty())
		{
			for (GoodsStockDetail detail : totalGoodsStocks)
			{
				StoreStockChart c = storeStockTable.get(detail.getOwnerId());
				c.setStockCost(detail.getGoodsCost().intValue());
				c.setStockCount(detail.getStockCount());
			}
			
			Iterator<String> iter = storeStockTable.keySet().iterator();
			while(iter.hasNext())
			{
				chartsTableData.add(storeStockTable.get(iter.next()));
			}
			
			chartData = getTableData(chartsTableData, "", chartsTableData.size());
			session.setAttribute("CurrentStoreChartData", getChartData(title, chartsTableData));
			
			return chartData;
		}
		
		return getTableData(null, "", 0);
	}

	private String getChartData(String title, ArrayList<StoreStockChart> chartsTableData)
	{
		JSONObject obj = new JSONObject();
		
		ArrayList<String> storeNames = new ArrayList<>();
		ArrayList<Integer> storeCosts = new ArrayList<>();
		ArrayList<Integer> storeCounts = new ArrayList<>();
		
		if (chartsTableData != null && !chartsTableData.isEmpty())
		{
			for (StoreStockChart chart : chartsTableData){
				storeNames.add(chart.getStoreName());
				storeCosts.add(chart.getStockCost() / 100);
				storeCounts.add(chart.getStockCount());
			}
		}
		
		obj.put("title", title);
		obj.put("storeName", storeNames);
		obj.put("stockCost", storeCosts);
		obj.put("stockCount", storeCounts);
		
		return obj.toJSONString();
	}
	
	@Override
	public String storeStockChartLastData(HttpServletRequest request, HttpSession session) {
		
		String chartData = (String) session.getAttribute("CurrentStoreChartData");
		if (chartData == null)
		{
			chartData = getChartData("库存排行", null);
		}
		
		logger.info(chartData);
		return chartData;
	}

	@Override
	public String storeStockChartGoodsData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, boolean show) {
		if (!this.merUserHasAccessRight(request, session) || !show)
		{
			return getTableData(null, null, 0);
		}
		
		if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId()))
		{
			goodsInfo.setGoodsTypeId(null);
		}
		
		if (StringUtils.isEmpty(goodsInfo.getParentTypeId()))
		{
			goodsInfo.setParentTypeId(null);
		}
		
		goodsInfo.setStatus(0);
		goodsInfo.setMerId(this.merUser.getMerId());
		
		int totalCount = this.goodsInfoMapper.selectCountByGoodsType(goodsInfo);
		logger.info("totalCount = " + totalCount);
		
		goodsInfo.setPage(1);
		goodsInfo.setLimit(totalCount);
		
		ArrayList<GoodsInfo> goodsInfoList = this.goodsInfoMapper.selectByGoodsType(goodsInfo);
		if (goodsInfoList != null && !goodsInfoList.isEmpty())
		{
			return getTableData(goodsInfoList, "", totalCount);
		}
		else
		{
			return getTableData(null, null, 0);
		}
	}
	
}
