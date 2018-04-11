package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.BindingGoodsDetails;

import java.util.ArrayList;

public interface BindingGoodsDetailsMapper {
    int deleteByPrimaryKey(Integer bindDetailId);
    
    int deleteByBindGoodsId(String bindGoodsId);

    int insert(BindingGoodsDetails record);

    int insertSelective(BindingGoodsDetails record);

    BindingGoodsDetails selectByPrimaryKey(Integer bindDetailId);
    
    BindingGoodsDetails selectByBindGoods(BindingGoodsDetails record);
    
    ArrayList<BindingGoodsDetails> selectByBindGoodsId(String bindGoodsId);

    int updateByPrimaryKeySelective(BindingGoodsDetails record);

    int updateByPrimaryKey(BindingGoodsDetails record);
}