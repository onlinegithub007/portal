package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.service.IPinyinService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PinyinController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IPinyinService pinyinService;
	
	@RequestMapping("/backstage/pinyin.html")
	public @ResponseBody String getPinyin(HttpServletRequest request, HttpSession session, String chineseChars)
	{
		String pinyin = pinyinService.getPinyin(request, session, chineseChars);
		logger.info(chineseChars + " : " + pinyin);
		
		return pinyin;
	}
	
}
