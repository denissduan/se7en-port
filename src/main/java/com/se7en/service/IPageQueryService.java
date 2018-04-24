package com.se7en.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.se7en.dao.IEjbDao;
import com.se7en.dao.Page;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;

/**
 * The Interface ICommonQueryService.
 * 
 */
public interface IPageQueryService extends ICommonDaoService {
	
	List<Object[]> pageQuerySql2Array(String sql, Object[] params, int start,int limit);

	Page pageQuerySql2Page(String sql,Object[] params,int start,int limit);

	/**
	 * hql分页查询
	 * 
	 * @param hql
	 * @param params
	 * @param curPage
	 * @param dao
	 * @return
	 */
	PageView pageQueryH(String hql, Object[] params, QueryView view, IEjbDao<?> dao);

	/**
	 * sql分页查询
	 * 
	 * @param sql
	 * @param params
	 * @param curPage
	 * @param dao
	 * @return
	 */
	PageView pageQueryS(String sql, Object[] params, QueryView view, IEjbDao<?> dao);

	/**
	 * sql分页查询,不带表头,默认通用dao
	 * 
	 * @param sql
	 * @param params
	 * @param curPage
	 * @param dao
	 * @return
	 */
	PageView pageQueryS(String sql, Object[] params, QueryView view);


	/**
	 * 分页查询封装 不带表头
	 * 
	 * @param queryString
	 *            the query string
	 * @param params
	 *            the params
	 * @param curPage
	 *            the cur page
	 * @param dao
	 *            the dao
	 * @param way
	 *            the way
	 * @return the page view
	 */
	PageView pageQueryS(String queryString, Object[] params, QueryView view,
			IEjbDao<?> dao, int way);

	/**
	 * Merge query condition. 合并查询条件sql
	 * 
	 * @param <T>
	 *            the generic type
	 * @param cls
	 *            the cls
	 * @param vo
	 *            the vo
	 * @param req
	 *            the req
	 * @return the string
	 */
	void mergeQueryCondition(final Object vo, final StringBuilder sql,
			final List<Object> params, HttpServletRequest req);

}
