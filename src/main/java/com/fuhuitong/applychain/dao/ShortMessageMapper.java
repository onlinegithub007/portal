package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.ShortMessage;

import java.util.ArrayList;

public interface ShortMessageMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(ShortMessage record);

    int insertSelective(ShortMessage record);

    ShortMessage selectByPrimaryKey(Integer msgId);
    
    ArrayList<ShortMessage> selectByDate(String sendDateText);

    int updateByPrimaryKeySelective(ShortMessage record);

    int updateByPrimaryKey(ShortMessage record);
}