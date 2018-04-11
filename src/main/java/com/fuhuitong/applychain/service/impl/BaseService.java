package com.fuhuitong.applychain.service.impl;

import com.alibaba.fastjson.JSON;
import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.dao.SysDictionaryMapper;
import com.fuhuitong.applychain.model.MerchantUsers;
import com.fuhuitong.applychain.service.IBaseService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseService implements IBaseService
{
	protected Logger logger = Logger.getLogger(getClass());
	
	protected MerchantUsers merUser = null;
	
	@Resource
	protected SysDictionaryMapper sysDictMapper;
	
	@Override
	public String getRetCodeWithJson(int retCode)
	{
		HashMap<String, String> value = new HashMap<String, String>();
		value.put("success", Integer.toString(retCode));
		
		String retStr = JSON.toJSONString(value);
		logger.info(retStr);
		
		return retStr;
	}
	
	@Override
	public String getCodeWithJson(int retCode, String msg)
	{
		HashMap<String, String> value = new HashMap<String, String>();
		value.put("code", Integer.toString(retCode));
		value.put("msg", msg);
		
		String retStr = JSON.toJSONString(value);
		logger.info(retStr);
		
		return retStr;
	}
	
	@Override
	public String getRetCodeWithJson(int retCode, String msg)
	{
		HashMap<String, String> value = new HashMap<String, String>();
		value.put("success", Integer.toString(retCode));
		value.put("msg", msg);
		
		String retStr = JSON.toJSONString(value);
		logger.info(retStr);
		
		return retStr;
	}
	
	@Override
	public <T> String getTableData(ArrayList<T> dataList, String msg, int totalDataCount)
	{
		HashMap<String, Object> value = new HashMap<String, Object>();
		
		if (dataList != null)
		{
			value.put("code", 0);
			value.put("count", Integer.toString(totalDataCount));
			value.put("msg", msg);
			value.put("data", dataList);
		}
		else
		{
			ArrayList<Object> nullList = new ArrayList<>();
			value.put("code", 0);
			value.put("count", 0);
			value.put("msg", msg);
			value.put("data", nullList);
		}
		
		String retStr = JSON.toJSONString(value);
		logger.info(retStr);
		
		return retStr;
	}

	@Override
	public String getMessage(String key)
	{
		String key2 = this.getClass().getSimpleName() + "." + key;
		
		return FuHuiTongContext.getInstance().getValidateText(key2);
	}

	@Override
	public boolean merUserHasAccessRight(HttpServletRequest request, HttpSession session) 
	{
		// 获取系统平台的名字
		if (session.getAttribute(SYSTEM_TITLE) == null)
		{
			String systemTitle = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_PRODUCT_LEGEND).getDicValues();
			session.setAttribute(SYSTEM_TITLE, systemTitle);
			
			String copyRight = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_COPYRIGHT).getDicValues();
			session.setAttribute(SYSTEM_COPYRIGHT, copyRight);
		}
		
		// 判断是否已经登录，如果已经登录，则重定向到主页，否则重定向到登录界面
		merUser = (MerchantUsers) session.getAttribute(CURRENT_MER_USER);
		if (merUser == null)
		{
			logger.warn("Current merchant user was not logged in.");
			
			return false;
		}

		// 这里后续会判断当前用户是否有当前模块访问的权限
		
		
		return true;
	}
	
	@Override
	public MerchantUsers getMerUser() {
		return merUser;
	}

	@Override
	public String getRetCodeWithJson(int retCode, Map<String, Object> values) {
		
		HashMap<String, Object> value = new HashMap<String, Object>();
		value.put("code", Integer.toString(retCode));
		value.putAll(values);

		String retStr = JSON.toJSONString(value);
		logger.info(retStr);
		
		return retStr;
	}
}
