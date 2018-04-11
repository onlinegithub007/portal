package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.BankCardBin;


public interface BankCardBinMapper {
    int deleteByPrimaryKey(Integer cardBinId);

    int insert(BankCardBin record);

    int insertSelective(BankCardBin record);

    BankCardBin selectByPrimaryKey(Integer cardBinId);
    
    BankCardBin selectByCardNo(String cardNo);

    int updateByPrimaryKeySelective(BankCardBin record);

    int updateByPrimaryKey(BankCardBin record);
}