package com.fuhuitong.applychain.model;

import java.util.HashMap;

public abstract class BaseModel {

	protected HashMap<String, String> validMessages;
	
	public BaseModel() {
		validMessages = new HashMap<String, String>();
	}

	public void putFieldValidMessage(String fieldName, String message)
	{
		this.validMessages.put(fieldName, message);
	}
	
	public String getErr(String fieldName)
	{
		String message = this.validMessages.get(fieldName);
		if (message != null)
		{
			return message;
		}
		else
		{
			return "";
		}
	}
	
	public String getValidStatus(String fieldName)
	{
		if (this.validMessages.get(fieldName) != null)
		{
			return " is-invalid ";
		}
		else
		{
			return "";
		}
	}
	
	public void clearErrMessage()
	{
		this.validMessages.clear();
	}
}
