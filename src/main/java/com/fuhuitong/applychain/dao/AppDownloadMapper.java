package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.AppDownload;

public interface AppDownloadMapper {
    int deleteByPrimaryKey(Long appDownId);

    int insert(AppDownload record);

    int insertSelective(AppDownload record);

    AppDownload selectByPrimaryKey(Long appDownId);

    int updateByPrimaryKeySelective(AppDownload record);

    int updateByPrimaryKey(AppDownload record);
}