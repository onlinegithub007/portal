package com.fuhuitong.applychain.utils;

import java.util.UUID;

public class MyUUID {
	
	public static String serverNo = null;
	
	public static synchronized String randomUUID()
	{
		String uuid = UUID.randomUUID().toString();
		
		return serverNo + uuid;
	}
}
