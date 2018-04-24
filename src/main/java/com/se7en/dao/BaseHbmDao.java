package com.se7en.dao;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseHbmDao {

	@Autowired
	protected SessionFactory sessionFactory;
	
	/**
	 * 初始化page Query
	 * @param queryString
	 * @param params
	 * @param start
	 * @param limit
	 * @param isHql
	 * @return
	 */
	protected Query initPageQuery(String queryString, Object[] params, int start,
			int limit, boolean isHql) {
		Query query = null;
		if (isHql == true) {
			query = initHqlQuery(queryString, params);
		} else {
			query = initSqlQuery(queryString, params);
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query;
	}
	
	/**
	 * 创建Hql Query
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	protected Query initHqlQuery(final String queryString, final Object[] params) {
		return this.initQuery(queryString, params, true);
	}

	/**
	 * 创建Sql Query
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	protected SQLQuery initSqlQuery(final String queryString,
			final Object[] params) {
		return (SQLQuery) initQuery(queryString, params, false);
	}
	
	/**
	 * 创建Hql Query
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	protected Query initQuery(final String queryString, final Object[] params,
			boolean isHql) {
		Session session = curSession();
		Query query = null;
		if (isHql == true) {
			query = session.createQuery(queryString);
			query.setCacheable(true);
		} else {
			query = session.createSQLQuery(queryString);
		}
		this.setParameters(params, query);
		return query;
	}
	
	/**
	 * 设置参数
	 * 
	 * @param params
	 * @param query
	 */
	protected void setParameters(Object[] params, Query query) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}
	
	/**
	 * 获取Session
	 * 
	 * @return
	 */
	public Session curSession() {
		return sessionFactory.getCurrentSession();
	}
	
}
