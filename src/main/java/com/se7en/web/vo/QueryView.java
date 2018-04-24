package com.se7en.web.vo;

import java.util.List;

import com.se7en.common.ColInfo;

public class QueryView {
	
	public final static QueryView EMPTY_QUERYVIEW = new QueryView();

	private List<List<ColInfo>> thead;
	
	private String sort;
	
	private String order;
	
	private Integer curPage;

	private Integer limit;

	public List<List<ColInfo>> getThead() {
		return thead;
	}

	public void setThead(List<List<ColInfo>> thead) {
		this.thead = thead;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "QueryView [thead=" + thead + ", sort=" + sort + ", order="
				+ order + ", curPage=" + curPage + "]";
	}
}
