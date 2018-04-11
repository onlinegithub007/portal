package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.Areas;

import java.util.ArrayList;

public interface AreasMapper {
    int deleteByPrimaryKey(String areaId);

    int insert(Areas record);

    int insertSelective(Areas record);

    Areas selectByPrimaryKey(String areaId);
    
    ArrayList<Areas> selectTopAreas();
    
    ArrayList<Areas> selectLevelAreas(String parentAreaId);

    int updateByPrimaryKeySelective(Areas record);

    int updateByPrimaryKey(Areas record);
}