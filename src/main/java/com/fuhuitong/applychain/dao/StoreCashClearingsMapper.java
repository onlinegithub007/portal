package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StoreCashClearings;

public interface StoreCashClearingsMapper {
    int deleteByPrimaryKey(Integer cashClearingId);

    int insert(StoreCashClearings record);

    int insertSelective(StoreCashClearings record);

    StoreCashClearings selectByPrimaryKey(Integer cashClearingId);

    int updateByPrimaryKeySelective(StoreCashClearings record);

    int updateByPrimaryKey(StoreCashClearings record);
}