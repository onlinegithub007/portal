package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.Members;

import java.util.ArrayList;

public interface MembersMapper 
{
    int deleteByPrimaryKey(Long memberId);

    int insert(Members record);

    int insertSelective(Members record);

    Members selectByPrimaryKey(Long memberId);
    
    Members selectByCode(Members member);

    int updateByPrimaryKeySelective(Members record);

    int updateByPrimaryKey(Members record);
    
    ArrayList<Members> selectByLevel(Members query);
    
    Integer selectCountByLevel(Members query);
}