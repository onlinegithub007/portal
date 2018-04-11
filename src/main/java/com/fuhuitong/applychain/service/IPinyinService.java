package com.fuhuitong.applychain.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IPinyinService {
	public String getPinyin(HttpServletRequest request, HttpSession session, String chineseChars);
}	
