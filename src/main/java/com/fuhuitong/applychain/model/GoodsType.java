package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.TreeNodeUtil;

import java.util.Date;

public class GoodsType  extends TreeNodeUtil
{
    private String goodsTypeId;

    private String merId;

    private String goodsTypeName;

    private String parentTypeId;

    private Integer status;

    private Date createDate;

    private Date updateDate;
    
    private String selected = "";

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName == null ? null : goodsTypeName.trim();
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId == null ? null : parentTypeId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public void setSelected(String selected) {
		this.selected = selected;
	}
    
    public String getSelected() {
		return selected;
	}
    
    @Override
    public String toTreeString() {
    	
    	this.treeId = this.goodsTypeId;
    	this.treeName = this.goodsTypeName;
    	
    	return super.toTreeString();
    }
}