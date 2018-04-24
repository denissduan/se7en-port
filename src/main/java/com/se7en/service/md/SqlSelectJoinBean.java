package com.se7en.service.md;

import org.eclipse.uml2.uml.internal.impl.ClassImpl;

public class SqlSelectJoinBean {

	private ClassImpl cls;
	private String propName;
	private StringBuilder selectSql;
	private StringBuilder tableSql;

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

	public StringBuilder getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(StringBuilder selectSql) {
		this.selectSql = selectSql;
	}

	public StringBuilder getTableSql() {
		return tableSql;
	}

	public void setTableSql(StringBuilder tableSql) {
		this.tableSql = tableSql;
	}

	public SqlSelectJoinBean(ClassImpl cls, String propName,
			StringBuilder selectSql, StringBuilder tableSql) {
		super();
		this.cls = cls;
		this.propName = propName;
		this.selectSql = selectSql;
		this.tableSql = tableSql;
	}

	public SqlSelectJoinBean() {
	}
	
}
