package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.SysDictionary;

import java.util.ArrayList;


public interface SysDictionaryMapper {
    int deleteByPrimaryKey(String dicCode);

    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(String dicCode);

    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);
    
    ArrayList<SysDictionary> selectAllSysDictionarys();
}