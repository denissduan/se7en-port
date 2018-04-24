package com.se7en.service.md;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.uml2.uml.internal.impl.ClassImpl;

public class SqlJoinFilterBean {

	private ClassImpl cls;
	private String propName;
	private HttpServletRequest req;
	private StringBuilder tableSql;
	private StringBuilder whereSql;
	private List<Object> params;
	private String value;
	public ClassImpl getCls() {
		return cls;
	}
	public void setCls(ClassImpl cls) {
		this.cls = cls;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public HttpServletRequest getReq() {
		return req;
	}
	public void setReq(HttpServletRequest req) {
		this.req = req;
	}
	public StringBuilder getTableSql() {
		return tableSql;
	}
	public void setTableSql(StringBuilder tableSql) {
		this.tableSql = tableSql;
	}
	public StringBuilder getWhereSql() {
		return whereSql;
	}
	public void setWhereSql(StringBuilder whereSql) {
		this.whereSql = whereSql;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SqlJoinFilterBean() {
	}
	
	public SqlJoinFilterBean(ClassImpl cls, String propName,
			HttpServletRequest req, StringBuilder tableSql,
			StringBuilder whereSql, List<Object> params) {
		super();
		this.cls = cls;
		this.propName = propName;
		this.req = req;
		this.tableSql = tableSql;
		this.whereSql = whereSql;
		this.params = params;
	}
	
}
