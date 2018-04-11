package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.UserRoleMenus;

import java.util.ArrayList;

public interface UserRoleMenusMapper {
    int deleteByPrimaryKey(Integer userRoleMenusId);

    int deleteByUserRole(Integer userRoleId);
    
    int insert(UserRoleMenus record);
    
    int insertInit(UserRoleMenus record);

    int insertSelective(UserRoleMenus record);

    UserRoleMenus selectByPrimaryKey(Integer userRoleMenusId);
    
    ArrayList<UserRoleMenus> selectByUserRole(Integer userRoleId);

    int updateByPrimaryKeySelective(UserRoleMenus record);

    int updateByPrimaryKey(UserRoleMenus record);
}