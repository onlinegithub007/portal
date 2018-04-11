package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.SysUsersMenus;

public interface SysUsersMenusMapper {
    int deleteByPrimaryKey(Long sysUserMenuId);

    int insert(SysUsersMenus record);

    int insertSelective(SysUsersMenus record);

    SysUsersMenus selectByPrimaryKey(Long sysUserMenuId);

    int updateByPrimaryKeySelective(SysUsersMenus record);

    int updateByPrimaryKey(SysUsersMenus record);
}