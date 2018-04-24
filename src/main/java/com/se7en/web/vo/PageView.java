package com.se7en.web.vo;

import java.util.List;

import com.se7en.common.ColInfo;
import com.se7en.service.IDisplayable;
/**
 * 视图层表格页封装
 * @author dl
 *
 */
public class PageView implements IDisplayable{
	/**
	 * 当前页
	 */
	private int curPage;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 每页数量
	 */
	private int limit;
	/**
	 * 表格数据
	 */
	private List<?> tbody;
	/**
	 * 表头
	 */
	private List<List<ColInfo>> thead;

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<?> getTbody() {
		return tbody;
	}

	public void setTbody(List<?> tbody) {
		this.tbody = tbody;
	}
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<List<ColInfo>> getThead() {
		return thead;
	}

	public void setThead(List<List<ColInfo>> thead) {
		this.thead = thead;
	}
	
}
