package com.se7en.dao;

import java.util.List;

public class Page {
	private List<?> list ;
	private long totalCount;
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public Page(List<?> list, long totalCount) {
		super();
		this.list = list;
		this.totalCount = totalCount;
	}
	@Override
	public String toString() {
		return "Page [list=" + list + ", totalCount=" + totalCount + "]";
	}
	public Page() {
	}
}
