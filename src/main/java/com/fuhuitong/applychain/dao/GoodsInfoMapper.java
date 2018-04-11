package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsInfo;

import java.util.ArrayList;

public interface GoodsInfoMapper 
{
    int deleteByPrimaryKey(String goodsId);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(String goodsId);
    
    GoodsInfo selectByGoodsCode(GoodsInfo goodsInfo);
    
    GoodsInfo selectStoreGoodsPriceBy(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectStoreGoodsListPriceBy(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectByGoodsIds(GoodsInfo goodsInfo);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKeyWithBLOBs(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);
    
    ArrayList<GoodsInfo> selectByGoodsType(GoodsInfo goodsInfo);
    
    int selectCountByGoodsType(GoodsInfo goodsInfo);
    
    int changeStatus(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectStorePrice(GoodsInfo goodsInfo);
    
    int selectStorePriceCount(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectStorePrice2(GoodsInfo goodsInfo);
    
    int selectStorePriceCount2(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectMemberPrice(GoodsInfo goodsInfo);
    
    int selectMemberPriceCount(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectMemberPrice2(GoodsInfo goodsInfo);
    
    int selectMemberPriceCount2(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectMerchantGoodsStock(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectMerProviderGoods(String merUserId);
    
    int selectMerchantGoodsStockCount(GoodsInfo goodsInfo);
    
    int updateWhenImportExcel(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectStoreGoodsStock(GoodsInfo goodsInfo);
    
    int selectStoreGoodsStockCount(GoodsInfo goodsInfo);
    
    ArrayList<GoodsInfo> selectStoreGoodsCheckStock(GoodsInfo goodsInfo);
    
    int selectStoreGoodsCheckStockCount(GoodsInfo goodsInfo);
    
}