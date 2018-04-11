package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.SysGroups;

import java.util.ArrayList;

public interface SysGroupsMapper 
{
	int deleteByPrimaryKey(String groupId);

    int insert(SysGroups record);

    int insertSelective(SysGroups record);

    SysGroups selectByPrimaryKey(String groupId);
    
    SysGroups selectByName(String groupName);

    int updateByPrimaryKeySelective(SysGroups record);

    int updateByPrimaryKey(SysGroups record);
    
    public ArrayList<SysGroups> selectAllGroups();
    
    public ArrayList<SysGroups> selectAll(SysGroups record);
    
    public int getAllDataRecordCount();
}