package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.GoodsMemberPrice;

public interface GoodsMemberPriceMapper {
    int deleteByPrimaryKey(Long goodsMembPriceId);

    int insert(GoodsMemberPrice record);

    int insertSelective(GoodsMemberPrice record);

    GoodsMemberPrice selectByPrimaryKey(Long goodsMembPriceId);
    
    GoodsMemberPrice selectByMemberLevel(GoodsMemberPrice query);

    int updateByPrimaryKeySelective(GoodsMemberPrice record);

    int updateByPrimaryKey(GoodsMemberPrice record);
}