package com.fuhuitong.applychain.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelCellUtils {
	
	@SuppressWarnings("deprecation")
	public static String readStringValue(HSSFCell cell)
	{
		String strValue = null;
		
		if (cell != null)
		{
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
			{
				strValue = cell.getStringCellValue();
			}
			else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
			{
				Double d = cell.getNumericCellValue();
				strValue = Long.toString(d.longValue());
			}
		}
		
		return strValue;
	}
	
	@SuppressWarnings("deprecation")
	public static Long readLongValue(HSSFCell cell)
	{
		Long longValue = null;
		
		if (cell != null)
		{
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_STRING)
			{
				longValue = Long.parseLong(cell.getStringCellValue());
			}
			else if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			{
				Double d = cell.getNumericCellValue();
				longValue = d.longValue();
			}
			else if (cellType == HSSFCell.CELL_TYPE_FORMULA)
			{
				System.out.println(cell.getCellFormula());
				Double d = cell.getNumericCellValue();
				longValue = d.longValue();
			}
			else if (cellType == HSSFCell.CELL_TYPE_BLANK)
			{
				System.out.println("here");
			}
			else if (cellType == HSSFCell.CELL_TYPE_ERROR)
			{
				System.out.println("here");
			}
		}
		
		return longValue;
	}
}
