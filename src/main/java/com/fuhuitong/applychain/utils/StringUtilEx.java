package com.fuhuitong.applychain.utils;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtilEx {

	public StringUtilEx() {
		// TODO Auto-generated constructor stub
	}

	public static String getRandString()
	{
		String str = "";
		
		String tmp = UUID.randomUUID().toString() + (new Date().getTime());
		
		String temp2 = Base64Utils.encodeToString(tmp.getBytes());
		
		int max = temp2.length();
		int i = 0;
		
		Random random = new Random(new Date().getTime());
		
		while(i < 8)
		{
			int index = random.nextInt(max);
			String ch = Character.toString(temp2.charAt(index));
			if (ch.equals("="))
			{
				continue;
			}
			else
			{
				str += ch;
				i++;
			}
		}
		
		return str;
	}
	
	public static String formatAgentName(String str)
	{
		String input = str;
		
		if (input.contains(" "))
		{
			input = StringUtils.replace(input, " ", "");
		}
		
		return input;
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtilEx.getRandString());
	}
}
