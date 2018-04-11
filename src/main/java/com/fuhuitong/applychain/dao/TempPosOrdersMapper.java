package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.TempPosOrders;

public interface TempPosOrdersMapper {
    int deleteByPrimaryKey(String tmpOrderId);

    int insert(TempPosOrders record);

    int insertSelective(TempPosOrders record);

    TempPosOrders selectByPrimaryKey(String tmpOrderId);

    int updateByPrimaryKeySelective(TempPosOrders record);

    int updateByPrimaryKey(TempPosOrders record);
}