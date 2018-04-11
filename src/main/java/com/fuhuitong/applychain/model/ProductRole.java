package com.fuhuitong.applychain.model;

import java.util.Date;

public class ProductRole 
{
    private Integer productRoleId;

    private String productRoleName;

    private Integer productRoleStatus;

    private Date createDate;
    
    private String checked = "";

    public Integer getProductRoleId() {
        return productRoleId;
    }

    public void setProductRoleId(Integer productRoleId) {
        this.productRoleId = productRoleId;
    }

    public String getProductRoleName() {
        return productRoleName;
    }

    public void setProductRoleName(String productRoleName) {
        this.productRoleName = productRoleName == null ? null : productRoleName.trim();
    }

    public Integer getProductRoleStatus() {
        return productRoleStatus;
    }

    public void setProductRoleStatus(Integer productRoleStatus) {
        this.productRoleStatus = productRoleStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public void setChecked(String checked) {
		this.checked = checked == null ? null : checked.trim();
	}
    
    public String getChecked() {
		return checked;
	}
   
   
}