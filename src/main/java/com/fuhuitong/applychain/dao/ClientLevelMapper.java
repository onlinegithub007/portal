package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.ClientLevel;

import java.util.ArrayList;

public interface ClientLevelMapper {
    int deleteByPrimaryKey(String clientLevelId);

    int insert(ClientLevel record);

    int insertSelective(ClientLevel record);

    ClientLevel selectByPrimaryKey(String clientLevelId);
    
    ClientLevel selectLowestLevel(ClientLevel record);

    int updateByPrimaryKeySelective(ClientLevel record);

    int updateByPrimaryKey(ClientLevel record);
    
    ArrayList<ClientLevel> selectAll(ClientLevel record);
}