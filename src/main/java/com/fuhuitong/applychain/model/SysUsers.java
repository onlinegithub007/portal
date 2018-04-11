package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class SysUsers extends Pagable
{
	private String userId;
	
    private String account;
    
    private String groupId;

    private String password;

    private String userName;
    
    private String groupName;

    private Date lastLogDate;
    
    private String lastLogDateText;
    
    private String lastLogIp;

    private Integer level;

    private Boolean needModPasswd;

    private String mobilePhone;

    private String email;

    private String memo;
    
    private String oldPwd;
	private String newPwd1;
	private String newPwd2;
    
    private String err;
	private String err1;
	private String err2;
	private String err3;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getLastLogDate() {
        return lastLogDate;
    }

    public void setLastLogDate(Date lastLogDate) {
        this.lastLogDate = lastLogDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getNeedModPasswd() {
        return needModPasswd;
    }

    public void setNeedModPasswd(Boolean needModPasswd) {
        this.needModPasswd = needModPasswd;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getErr1() {
		return err1;
	}

	public void setErr1(String err1) {
		this.err1 = err1;
	}

	public String getErr2() {
		return err2;
	}

	public void setErr2(String err2) {
		this.err2 = err2;
	}

	public String getErr3() {
		return err3;
	}

	public void setErr3(String err3) {
		this.err3 = err3;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd == null ? null : oldPwd.trim();
	}

	public String getNewPwd1() {
		return newPwd1;
	}

	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1 == null ? null : newPwd1.trim();
	}

	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2 == null ? null : newPwd2.trim();
	}

	public String getLastLogIp() {
		return lastLogIp;
	}

	public void setLastLogIp(String lastLogIp) {
		this.lastLogIp = lastLogIp == null ? null : lastLogIp.trim();
	}
    
    public String getGroupId() {
		return groupId;
	}
    
    public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
    
    public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    public String getGroupName() {
		return groupName;
	}
    
    public String getUserId() {
		return userId;
	}
    
    public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}
    
    public String getLastLogDateText() {
		
    	if (this.lastLogDate != null)
    	{
    		lastLogDateText = DateFormatUtils.format(lastLogDate, "yyyy-MM-dd HH:mm");
    	}
    	else
    	{
    		lastLogDateText = "";
    	}
    	return lastLogDateText;
	}
}