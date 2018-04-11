package com.fuhuitong.applychain.model;

import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class ProductMenus {
	
    private Integer productMenuId;

    private Integer topProductMenuId;

    private String menuName;

    private String menuUrl;

    private String menuIconCls;

    private Integer menuIndex;
    
    private boolean selected;
    
    private ProductMenus parent;
    
    private ArrayList<ProductMenus> children = null;
    
    public void addChild(ProductMenus child)
    {
    	if (this.children == null)
    	{
    		children = new ArrayList<ProductMenus>();
    	}
    	
    	this.children.add(child);
    	
//    	// 子节点设置父亲节点
//    	child.setParent(this);
    }

    public void addChildren(ArrayList<ProductMenus> children)
    {
    	if (this.children == null)
    	{
    		children = new ArrayList<ProductMenus>();
    	}
    	
    	for (ProductMenus child : children)
    	{
    		addChild(child);
    	}
    }
    
    public ArrayList<ProductMenus> getChildren() {
		return children;
	}
    
    public Integer getProductMenuId() {
        return productMenuId;
    }

    public void setProductMenuId(Integer productMenuId) {
        this.productMenuId = productMenuId;
    }
    
    public void setSelected(boolean selected) {
		this.selected = selected;
	}
    
    public boolean isSelected() {
		return selected;
	}

    public Integer getTopProductMenuId() {
        return topProductMenuId;
    }

    public void setTopProductMenuId(Integer topProductMenuId) {
        this.topProductMenuId = topProductMenuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuIconCls() {
        return menuIconCls;
    }

    public void setMenuIconCls(String menuIconCls) {
        this.menuIconCls = menuIconCls == null ? null : menuIconCls.trim();
    }

    public Integer getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }
    
    public void setParent(ProductMenus parent) {
		this.parent = parent;
	}
    
    public ProductMenus getParent() {
		return parent;
	}
    
    /**
     * 返回可以在前台使用的菜单JSON
     * @return
     */
    public String toJsonMenuString()
    {
    	/**
		 * "id": "1",
		"pid": "0",
		"title": "系统管理",
		"icon": "&#xe75c;",
		"url": "",
		"spread": false,
		"children": [{
		 */
    	
    	StringBuffer treeBuffer = new StringBuffer();
    	
    	treeBuffer.append("{")
    		.append("\"id\" : \"").append(this.productMenuId).append("\",");
    	
    	if (this.parent != null)
    	{
    		treeBuffer.append("\"pid\": \""+this.parent.getProductMenuId()+"\",");
    	}
    	else
    	{
    		treeBuffer.append("\"pid\": \"0\",");
    	}
    	
    	treeBuffer.append("\"title\": \"" + this.menuName + "\",")
    		.append("\"icon\": \"" + this.menuIconCls + "\",");
    	
    	if (StringUtils.isEmpty(this.menuUrl))
    	{
    		treeBuffer.append("\"url\":\"\",");
    	}
    	else
    	{
    		treeBuffer.append("\"url\":\"" + this.menuUrl + "\",");
    	}
    	
    	treeBuffer.append("\"spread\": true");

    	if (this.children != null && this.children.size() > 0)
    	{
    		treeBuffer.append(",\"children\": [");
    		
    		for (ProductMenus child : this.children)
    		{
    			treeBuffer.append(child.toJsonMenuString()).append(",");
    		}
    		treeBuffer.deleteCharAt(treeBuffer.length() - 1);
    		treeBuffer.append("]");
    	}
    	
    	treeBuffer.append("}");
    	
    	return treeBuffer.toString();
    }
    
    /**
     * 返回可以在管理界面上树形显示的JSON
     * @return
     */
    public  String toTreeString()
	{
		StringBuffer treeBuffer = new StringBuffer();
		
    	treeBuffer.append("{")
			.append("id' : '").append(this.productMenuId).append("',")
			.append("'text' : '").append(this.menuName).append("',")
			.append("'state' : {").append("'opened' : true, 'selected' : ").append(this.selected).append("}");
    	
    	if (this.children != null && this.children.size() > 0)
    	{
    		treeBuffer.append(",").append("'children' : [");
    		
    		for (ProductMenus child : this.children)
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