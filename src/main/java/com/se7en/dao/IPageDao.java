package com.se7en.dao;

import java.util.List;

public interface IPageDao extends ICommonDao{
	
	List<Object[]> pageQuerySql2Array(String sql, Object[] params, int start,int limit);

	Page pageQuerySql2Page(String baseSql, String sorts, Object[] params,int start,int limit);
	
}
