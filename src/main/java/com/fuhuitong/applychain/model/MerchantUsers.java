package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.model.request.POSParam;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

public class MerchantUsers extends POSParam
{
    private String merUserId;

    private String merGroupId;

    private String merId;

    private String userAccount;
    
    private String merCode;

    private String userPassword;

    private String userName;

    private String groupName;

    private String merName;

    private String userPhone;
    
    private String userEmail;

    private int userRole;
    
    private String userRoleName;
    
    private boolean superAdmin;

    private String userMemo;

    private Date lastLoginDate;
    
    private String lastLoginDateText;

    private String lastLoginIp;
    
    private Integer status;
    
    private String oldPwd;
	private String newPwd1;
	private String newPwd2;
	
	private String verifyCode;

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId == null ? null : merUserId.trim();
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo == null ? null : userMemo.trim();
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }
    
    public void setUserEmail(String userEmail) {
		this.userEmail = userEmail == null ? null : userEmail.trim();
	}
    
    public String getUserEmail() {
		return userEmail;
	}
    
    public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
    
    public boolean isSuperAdmin() {
		return superAdmin;
	}
    
    public void setMerCode(String merCode) {
		this.merCode = merCode == null ? null : merCode.trim();
	}
    
    public String getMerCode() {
		return merCode;
	}
    
    public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName == null ? null : userRoleName.trim();
	}
    
    public String getUserRoleName() {
		return userRoleName;
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
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
    
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode == null ? null : verifyCode.trim();
	}
	
	public String getVerifyCode() {
		return verifyCode;
	}
	
    public String getLastLoginDateText() {
    	
    	if (!StringUtils.isEmpty(lastLoginDate))
    	{
    		lastLoginDateText = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    	}
    	
		return lastLoginDateText;
	}
}