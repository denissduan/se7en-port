package com.se7en.service.md;

import java.util.Collections;
import java.util.List;

public class SqlParamBean {

	public final static SqlParamBean EMPTY_SQLPARAMBEAN = new SqlParamBean(
			new StringBuilder(), Collections.emptyList());

	private StringBuilder sql;

	private List<Object> params;

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

	public SqlParamBean() {
	}

	public SqlParamBean(StringBuilder sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}

}
