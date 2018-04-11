package com.fuhuitong.applychain.utils;

import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScfsCharacterEncodingFilter extends CharacterEncodingFilter {
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
		if(!path.endsWith("notifyYeePayOrder")){
			super.doFilterInternal(request, response, filterChain);
		}else{
			filterChain.doFilter(request, response);
		}
	}
}
