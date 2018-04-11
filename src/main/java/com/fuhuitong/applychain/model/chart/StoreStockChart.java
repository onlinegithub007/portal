package com.fuhuitong.applychain.model.chart;

import com.fuhuitong.applychain.utils.MoneyUtils;

/**
 * 门店库存统计类
 * @author haoqingfeng
 *
 */
public class StoreStockChart {
	
	private String parentTypeId;
	
	private String storeId;
	
	private String storeName;
	
	private String goodsId;
	
	private String goodsName;
	
	private String goodsTypeName;
	
	private String goodsTypeId;
	
	private Integer stockCost;
	
	private String stockCostText;
	
	private Integer stockCount;
	
	private String action;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public Integer getStockCost() {
		return stockCost;
	}

	public void setStockCost(Integer stockCost) {
		this.stockCost = stockCost;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public String getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setStockCostText(String stockCostText) {
		this.stockCostText = stockCostText;
	}
	
	public String getStockCostText() {
		
		if (stockCost != null)
		{
			stockCostText = MoneyUtils.getMoneyText(stockCost);
		}
		
		return stockCostText;
	}
}
