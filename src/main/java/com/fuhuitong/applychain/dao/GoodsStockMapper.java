package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsStock;

import java.util.ArrayList;

public interface GoodsStockMapper {
    int deleteByPrimaryKey(Long goodsStockId);

    int insert(GoodsStock record);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(Long goodsStockId);

    int updateByPrimaryKeySelective(GoodsStock record);

    int updateByPrimaryKey(GoodsStock record);
    
    int updateMerchantStock(GoodsStock record);
    
    int updateMerchantStock2(GoodsStock record);
    
    int statUpdateStoreStock(GoodsStock record);
    
    ArrayList<GoodsStock> selectStoreStockByGoodsId(String goodsId);
    
    ArrayList<GoodsStock> selectByStore(String storeId);
    
    GoodsStock selectStoreStockByGoodsId2(GoodsStock stock);
    
    int selectStoreStockCountByGoodsId(String goodsId);
}