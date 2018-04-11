package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.MerchantUsers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public interface IBaseService {
	
	public static final String CURRENT_MER_USER = "CurrentMerUser";
	
	public static final String SYSTEM_TITLE = "SystemTitle";
	
	public static final String SYSTEM_COPYRIGHT = "SystemCopyright";
	
	public String getMessage(String key);
	
	public String getRetCodeWithJson(int retCode);
	
	public String getCodeWithJson(int retCode, String msg);
	
	public String getRetCodeWithJson(int retCode, Map<String, Object> values);
	
	public String getRetCodeWithJson(int retCode, String msg);
	
	public <T> String getTableData(ArrayList<T> dataList, String msg, int totalDataCount);
	
	public boolean merUserHasAccessRight(HttpServletRequest request, HttpSession session);
	
	public MerchantUsers getMerUser();
}
