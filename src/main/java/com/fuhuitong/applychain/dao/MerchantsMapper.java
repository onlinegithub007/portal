package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.Merchants;

import java.util.ArrayList;

public interface MerchantsMapper {
    int deleteByPrimaryKey(String merId);

    int insert(Merchants record);

    int insertSelective(Merchants record);

    Merchants selectByPrimaryKey(String merId);

    int updateByPrimaryKeySelective(Merchants record);

    int updateByPrimaryKey(Merchants record);
    
    ArrayList<Merchants> selectAllByRole(Merchants query);
    
    int selectCountByRole(Merchants query);
    
    int updateDelStatus(String merId);
    
    int updateMerStatus(Merchants param);
}