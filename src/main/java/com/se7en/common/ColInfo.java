package com.se7en.common;
/**
 * 表头信息
 * @author dl
 *
 */
public class ColInfo {
	/**
	 * 字段名
	 */
	private String name;
	
	private String tableName;
	/**
	 * 列名
	 */
	private String title;
	/**
	 * 排序字段
	 */
	private String sort;
	/**
	 * 升降序
	 */
	private Short order;
	/**
	 * 列表现形式,默认为文本
	 * 可以加html代码
	 */
	private String format;
	/**
	 * 可编辑单元格
	 */
	private Boolean editable;
	/**
	 * 列合并
	 */
	private Integer colspan;

	public ColInfo() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Short getOrder() {
		return order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ColInfo(String title) {
		super();
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public ColInfo(String name, String title, String sort, Short order,
			String format) {
		super();
		this.name = name;
		this.title = title;
		this.sort = sort;
		this.order = order;
		this.format = format;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
