package com.se7en.dao;

import java.util.List;

import com.se7en.common.util.StringUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository("pageDao")
public class PageDao extends CommonDao implements IPageDao{

	/**
	 * 分页查询sql,sql语句不包含起始记录数和查询记录数
	 * @param sql
	 * @param params
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Object[]> pageQuerySql2Array(String sql, Object[] params, int start,
			int limit) {
		SQLQuery query = (SQLQuery) initPageQuery(sql, params, start, limit,
				false);
		return (List<Object[]>) query.list();
	}
	
	/**
	 * 分页查询sql,sql语句不包含起始记录数和查询记录数
	 * @param baseSql
	 * @param params
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page pageQuerySql2Page(String baseSql, String sorts, Object[] params, int start, int limit){
		String querySql = baseSql;
		if(StringUtil.isNotEmpty(sorts)){
			querySql += sorts;
		}
		List<Object[]> list = pageQuerySql2Array(querySql, params, start, limit);
		long total = totalSql(baseSql, params);
		page.setList(list);
		page.setTotalCount(total);
		return page;
	}
	
}
