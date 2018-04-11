package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.MemberTokens;

public interface MemberTokensMapper {
    int deleteByPrimaryKey(Long memTokenId);

    int insert(MemberTokens record);

    int insertSelective(MemberTokens record);

    MemberTokens selectByPrimaryKey(Long memTokenId);
    
    MemberTokens selectByToken(String token);

    int updateByPrimaryKeySelective(MemberTokens record);

    int updateByPrimaryKey(MemberTokens record);
}