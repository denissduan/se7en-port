/*
package com.se7en.dao;

import java.sql.Connection;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class BaseJdbcDao {

	protected JdbcTemplate jdbcTemplate;
	
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	
	protected SimpleJdbcInsert simpleJdbcInsert;
	
	*/
/**
	 * 获取连接集
	 *//*

	public Connection getConnection() {

		return DataSourceUtils.getConnection(this.getJdbcTemplate()
				.getDataSource());
	}

	*/
/**
	 * 释放连接集
	 *//*

	public void closeConnection(Connection con) {

		DataSourceUtils.releaseConnection(con, this.getJdbcTemplate()
				.getDataSource());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	*/
/**
	 * 关联数据源
	 * 
	 * @param dataSource
	 *//*

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate);
		this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
	}

}
*/
