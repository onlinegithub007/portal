package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.FuHuiTongContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// 创建实例，初始化系统变量
		FuHuiTongContext.getInstance();
	}
}
