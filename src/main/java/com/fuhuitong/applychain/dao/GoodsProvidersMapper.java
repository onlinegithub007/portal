package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsProviders;

import java.util.ArrayList;

public interface GoodsProvidersMapper {
    int deleteByPrimaryKey(String gpId);
    
    int deleteByProvider(GoodsProviders record);

    int insert(GoodsProviders record);

    int insertSelective(GoodsProviders record);

    GoodsProviders selectByPrimaryKey(String gpId);

    int updateByPrimaryKeySelective(GoodsProviders record);

    int updateByPrimaryKey(GoodsProviders record);
    
    int changeGpStatus(GoodsProviders record);
    
    ArrayList<GoodsProviders> selectGoodsWithProvider(String goodsId);
}