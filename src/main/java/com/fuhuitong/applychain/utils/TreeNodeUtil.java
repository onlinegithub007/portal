package com.fuhuitong.applychain.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;

public abstract class TreeNodeUtil{

	protected String treeId;
	protected String treeName;
	protected String extAttrField;
	protected String extAttrValue;
	
	protected ArrayList<TreeNodeUtil> children;
	
	public TreeNodeUtil() {
		children = new ArrayList<TreeNodeUtil>();
	}
	
	public void addChild(TreeNodeUtil child)
	{
		this.children.add(child);
	}
	
	public void addChildren(ArrayList<TreeNodeUtil> children)
	{
		this.children.addAll(children);
	}
	
	public  String toTreeString()
	{
		StringBuffer treeBuffer = new StringBuffer();
    	
    	treeBuffer.append("{")
			.append("'id' : '").append(this.treeId).append("',")
			.append("'text' : '").append(this.treeName).append("',");
			if (!StringUtils.isEmpty(this.extAttrField))
			{
				treeBuffer.append("'"+this.extAttrField+"' : ").append(this.extAttrValue).append(",");
			}
			treeBuffer.append("'state' : {").append("'opened' : true}");
    	
    	if (this.children.size() > 0)
    	{
    		treeBuffer.append(",")
    			.append("'children' : [");
    		for (TreeNodeUtil child : this.children)
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
