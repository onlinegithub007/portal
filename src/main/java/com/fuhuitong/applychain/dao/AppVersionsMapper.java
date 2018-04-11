package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.AppVersions;

import java.util.ArrayList;

public interface AppVersionsMapper {
    int deleteByPrimaryKey(Integer appVersionId);

    int insert(AppVersions record);

    int insertSelective(AppVersions record);

    AppVersions selectByPrimaryKey(Integer appVersionId);
    
    AppVersions selectLastedVersion(Integer softId);
    
    AppVersions selectByVersion(AppVersions query);

    int updateByPrimaryKeySelective(AppVersions record);

    int updateByPrimaryKey(AppVersions record);
    
    ArrayList<AppVersions> selectAllVersion(AppVersions query);
}