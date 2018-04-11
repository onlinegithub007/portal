package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsStockDetail;

import java.util.ArrayList;

public interface GoodsStockDetailMapper {
	
    int deleteByPrimaryKey(Integer goodsStockDetailId);

    int insert(GoodsStockDetail record);

    int insertSelective(GoodsStockDetail record);

    GoodsStockDetail selectByPrimaryKey(Integer goodsStockDetailId);
    
    ArrayList<GoodsStockDetail> statTotalStockOfStore(String merId);
    
    ArrayList<GoodsStockDetail> selectByStoreGoods(GoodsStockDetail record);

    ArrayList<GoodsStockDetail> statTotalStockOfStore2(GoodsStockDetail record);
    
    ArrayList<GoodsStockDetail> statTotalStockOfStore3(String goodsId);
    
    GoodsStockDetail selectBuyAmount(GoodsStockDetail record);
    
    int updateByPrimaryKeySelective(GoodsStockDetail record);

    int updateByPrimaryKey(GoodsStockDetail record);
    
    ArrayList<GoodsStockDetail> selectByGoodsId(GoodsStockDetail query);
    
    ArrayList<GoodsStockDetail> selectValidByGoodsId(GoodsStockDetail query);
    
    ArrayList<GoodsStockDetail> selectTempByBillId(String stockBillId);
    
    ArrayList<GoodsStockDetail> selectExpiredStock(GoodsStockDetail query);
    
    int updateTempStatus(GoodsStockDetail query);
    
    int deCreaseStock(GoodsStockDetail query);
    
    Integer selectStockCountByGoodsId(GoodsStockDetail query);
    
    int lock(GoodsStockDetail query);
}