package com.fuhuitong.applychain.model;

public class SysDictionary 
{
    private String dicCode;

    private String dicValues;
    
    private String err;

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }

    public String getDicValues() {
        return dicValues;
    }

    public void setDicValues(String dicValues) {
        this.dicValues = dicValues == null ? null : dicValues.trim();
    }
    
    public void setErr(String err) {
		this.err = err;
	}
    
    public String getErr() {
		return err;
	}
}