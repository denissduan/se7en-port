package com.se7en.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface ICommonDao {
	
	List<Object[]> querySql2Array(String sql, Object[] params);
	
	List<Object> querySql2Single(String sql,Object[] params);
	
	List<Map<String,Object>> querySql2Map(String sql, Object[] params);
	
	int execute(final String sql, final Object[] params);
	
	long totalSql(String sql,Object[] params);
	
}
