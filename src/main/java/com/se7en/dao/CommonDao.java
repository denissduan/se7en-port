package com.se7en.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * The Class CommonDao.
 * 通用dao
 */
@Repository("commonDao")
public class CommonDao extends BaseHbmDao implements ICommonDao {

	protected Page page = new Page();
	
	/**
	 * 查询sql返回List<Object[]>
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> querySql2Array(String sql, Object[] params) {
		SQLQuery query = initSqlQuery(sql, params);
		return (List<Object[]>) query.list();
	}
	
	/**
	 * 查询sql转换为List<Map>
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> querySql2Map(String sql, Object[] params) {
		SQLQuery query = initSqlQuery(sql, params);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String,Object>>) query.list();
	}
	
	/**
	 * 执行SQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int execute(final String sql, final Object[] params) {
		Query query = initSqlQuery(sql, params);
		return query.executeUpdate();
	}
	
	/**
	 * sql查询总数
	 * @param sql
	 * @param params
	 * @return
	 */
	public long totalSql(String sql,Object[] params){
		String totalSql = "select count(*) from (" + sql + ") as t";
		SQLQuery query = initSqlQuery(totalSql, params);
		
		return Long.parseLong(query.uniqueResult().toString());
	}
	
	@Override
	public List<Object> querySql2Single(String sql, Object[] params) {
		SQLQuery query = initSqlQuery(sql, params);
		return (List<Object>) query.list();
	}
	
}
