package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.SysUsers;

import java.util.ArrayList;

public interface SysUsersMapper 
{
    int deleteByPrimaryKey(String account);

    int insert(SysUsers record);

    int insertSelective(SysUsers record);

    SysUsers selectByPrimaryKey(String userId);
    
    SysUsers selectByAccount(String account);

    int updateByPrimaryKeySelective(SysUsers record);

    int updateByPrimaryKey(SysUsers record);
    
    ArrayList<SysUsers> selectAllUsers(SysUsers query);
    
    int getAllDataRecordCount(SysUsers query);
}