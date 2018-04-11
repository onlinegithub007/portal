package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.AppSoftware;

import java.util.ArrayList;

public interface AppSoftwareMapper {
    int deleteByPrimaryKey(int softId);

    int insert(AppSoftware record);

    int insertSelective(AppSoftware record);

    AppSoftware selectByPrimaryKey(int softId);
    
    ArrayList<AppSoftware> selectAll();

    int updateByPrimaryKeySelective(AppSoftware record);

    int updateByPrimaryKey(AppSoftware record);
    
    int updateDownloadCount(int softId);
}