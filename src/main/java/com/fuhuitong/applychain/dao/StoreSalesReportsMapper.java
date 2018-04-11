package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.StockDispatch;
import com.fuhuitong.applychain.model.StoreSalesReports;

import java.util.ArrayList;

public interface StoreSalesReportsMapper {
    int deleteByPrimaryKey(Integer saleReportId);
    
    int deleteByDay(StoreSalesReports record);

    int insert(StoreSalesReports record);
    
    int initReport(StoreSalesReports record);
    
    int updateGoodsPrice(StoreSalesReports record);

    int insertSelective(StoreSalesReports record);
    
    int updateGoodsDispathIn(StockDispatch record);
    
    int updateGoodsDispathOut(StockDispatch record);

    StoreSalesReports selectByPrimaryKey(Integer saleReportId);
    
    ArrayList<StoreSalesReports> selectByDate(StoreSalesReports param);

    int updateByPrimaryKeySelective(StoreSalesReports record);

    int updateByPrimaryKey(StoreSalesReports record);
}