package com.fuhuitong.applychain.model;

public class Pagable 
{
	protected int page;
	
	protected int limit;
	
	protected int offset;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getOffset() {
		
		offset = (page - 1) * limit;
		
		return offset;
	}
	
}
