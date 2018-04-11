package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.SysMenus;

public interface SysMenusMapper {
    int deleteByPrimaryKey(String menuId);

    int insert(SysMenus record);

    int insertSelective(SysMenus record);

    SysMenus selectByPrimaryKey(String menuId);

    int updateByPrimaryKeySelective(SysMenus record);

    int updateByPrimaryKey(SysMenus record);
}