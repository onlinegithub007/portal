package com.fuhuitong.applychain.model;

import java.util.ArrayList;
import java.util.Date;


public class SysGroups extends Pagable
{
    private String groupId;

    private String groupName;

    private String parentGroupId;

    private Integer groupFlag;

    private Integer groupIndex;

    private Date createDate;
    
    private ArrayList<SysGroups> children;

    public SysGroups() {
		this.children = new ArrayList<SysGroups>();
	}
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId == null ? null : parentGroupId.trim();
    }

    public Integer getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Integer groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public void addChild(SysGroups childNode)
    {
    	this.children.add(childNode);
    }
    
    public void addChildren(ArrayList<SysGroups> children)
    {
    	this.children.addAll(children);
    }
    
    public String toTreeString()
    {
    	StringBuffer treeBuffer = new StringBuffer();
    	
    	/**
    	 * {
	         'text' : 'Root node 2',
	         'state' : {
	           'opened' : true,
	           'selected' : true
	         },
	         'children' : [
	           { 'text' : 'Child 1' },
	           'Child 2'
	         ]
	      }
    	 */
    	
    	treeBuffer.append("{")
    		.append("'id' : '").append(this.groupId).append("',")
    		.append("'text' : '").append(this.groupName).append("',")
    		.append("'groupIndex' : ").append(this.groupIndex).append(",")
    		.append("'state' : {")
    		.append("'opened' : true}");
    	
    	if (this.children.size() > 0)
    	{
    		treeBuffer.append(",")
    			.append("'children' : [");
    		for (SysGroups child : this.children)
    		{
    			treeBuffer.append(child.toTreeString()).append(",");
    		}
    		
    		// 删除最后一个,
    		treeBuffer.deleteCharAt(treeBuffer.length() - 1);
    		
    		treeBuffer.append("]");
    	}
    	
    	treeBuffer.append("}");
    	
    	return treeBuffer.toString();
    }
}