package com.se7en.service;

import java.lang.reflect.Field;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.se7en.common.ColInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.se7en.dao.IEjbDao;
import com.se7en.dao.IPageDao;
import com.se7en.dao.Page;
import com.se7en.dao.PageDao;
import com.se7en.service.reflect.ByteQueryFieldHandler;
import com.se7en.service.reflect.CharQueryFieldHandler;
import com.se7en.service.reflect.DateQueryFieldHandler;
import com.se7en.service.reflect.DoubleQueryFieldHandler;
import com.se7en.service.reflect.FieldHandler;
import com.se7en.service.reflect.FloatQueryFieldHandler;
import com.se7en.service.reflect.IntQueryFieldHandler;
import com.se7en.service.reflect.LongQueryFieldHandler;
import com.se7en.service.reflect.ObjectQueryFieldHandler;
import com.se7en.service.reflect.ShortQueryFieldHandler;
import com.se7en.service.reflect.StringQueryFieldHandler;
import com.se7en.common.util.ContextUtil;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;

/**
 * The Class PageQueryService. 分页查询封装service
 */
@Service("pageQueryService")
public class PageQueryService extends CommonDaoService implements
		IPageQueryService {
	
	public final static int WAY_HQL = 1;
	public final static int WAY_SQL = 2;
	
	@Resource(name="pageDao")
	private IPageDao pageDao;
	
	protected int limit = 15;

	protected int totalPage;

	protected PageView pv = new PageView();

	private Map<Class, FieldHandler> handlerMap = new HashMap<Class, FieldHandler>();

	public PageQueryService() {
		handlerMap.put(int.class, new IntQueryFieldHandler());
		handlerMap.put(byte.class, new ByteQueryFieldHandler());
		handlerMap.put(char.class, new CharQueryFieldHandler());
		handlerMap.put(Date.class, new DateQueryFieldHandler());
		handlerMap.put(double.class, new DoubleQueryFieldHandler());
		handlerMap.put(float.class, new FloatQueryFieldHandler());
		handlerMap.put(long.class, new LongQueryFieldHandler());
		handlerMap.put(short.class, new ShortQueryFieldHandler());
		handlerMap.put(String.class, new StringQueryFieldHandler());
		handlerMap.put(Object.class, new ObjectQueryFieldHandler());
	}
	
	@Override
	public List<Object[]> pageQuerySql2Array(String sql, Object[] params,
			int start, int limit) {
		
		return pageDao.pageQuerySql2Array(sql, params, start, limit);
	}

	@Override
	public Page pageQuerySql2Page(String sql, Object[] params, int start,
			int limit) {

		return pageDao.pageQuerySql2Page(sql, null, params, start, limit);
	}

	/**
	 * hql分页查询
	 * 
	 * @param hql
	 * @param params
	 * @param dao
	 * @return
	 */
	public PageView pageQueryH(String hql, Object[] params, QueryView view,
			IEjbDao<?> dao) {
		return wrapPage(hql, params, view, dao, WAY_HQL);
	}

	/**
	 * sql分页查询
	 * 
	 * @param sql
	 * @param params
	 * @param dao
	 * @return
	 */
	public PageView pageQueryS(String sql, Object[] params, QueryView view,
			IEjbDao<?> dao) {
		return wrapPage(sql, params, view, dao, WAY_SQL);
	}

	/**
	 * sql分页查询,不带表头,默认通用dao
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public PageView pageQueryS(String sql, Object[] params, QueryView view) {

		return wrapPage(sql, params, view, null, WAY_SQL);
	}

	/**
	 * sql分页查询,带表头,默认通用dao
	 * 
	 *            the query string
	 * @param params
	 *            the params
	 *            the cur page
	 * @param thead
	 *            the thead
	 * @return the page view
	 */
	public PageView pageQueryS(String sql, Object[] params, QueryView view,
			List<ColInfo> thead) {

		return wrapPage(sql, params, view, null, WAY_SQL);
	}

	/**
	 * 分页查询封装 不带表头
	 * 
	 * @param queryString
	 *            the query string
	 * @param params
	 *            the params
	 *            the cur page
	 * @param dao
	 *            the dao
	 * @param way
	 *            the way
	 * @return the page view
	 */
	public PageView pageQueryS(String queryString, Object[] params,
			QueryView view, IEjbDao<?> dao, int way) {

		return wrapPage(queryString, params, view, dao, way);
	}

	/**
	 * Page query s.
	 *
	 * @param queryString the query string
	 * @param params the params
	 * @param curPage the cur page
	 * @param thead the thead
	 * @param dao the dao
	 * @return the page view
	 */
	public PageView pageQueryS(String queryString, Object[] params,
			int curPage, List<List<ColInfo>> thead, IEjbDao<?> dao) {
		QueryView view = new QueryView();
		view.setCurPage(curPage);
		view.setThead(thead);

		return wrapPage(queryString, params, view, dao, WAY_SQL);
	}

	/**
	 * 分页查询封装 带表头
	 * 
	 * @param baseSql
	 * @param params
	 * @param dao
	 * @param way
	 *            1.hql,2.sql
	 * @return
	 */
	public PageView wrapPage(String baseSql, Object[] params,
							 QueryView view, IEjbDao<?> dao, int way) {
		int curPage = view.getCurPage();
		if (curPage == 0) {
			curPage = 1;
		}
		int limit = this.limit;
		if(view.getLimit() != null && view.getLimit() > 0){
			limit = view.getLimit();
		}
		int start = (curPage - 1) * limit;
		Page page = null;
		
		String sort = view.getSort();
		String sortSql = null;
		if(StringUtils.isNotEmpty(sort)){
			sortSql = " order by " + sort;
			if(StringUtils.isNotEmpty(view.getOrder())){
				sortSql = sortSql + " " + view.getOrder();
			}
		}
		
		if (way == WAY_HQL) {
			page = dao.pageQueryH2Page(baseSql, params, start, limit);
		} else {
			if (dao == null) {
				PageDao pageDao = (PageDao) ContextUtil.getBean("pageDao");
//				pageDao.openSession();
				page = pageDao.pageQuerySql2Page(baseSql,sortSql, params, start, limit);
//				pageDao.closeSession();
			} else {
				page = dao.pageQuerySql2Page(baseSql, sortSql, params, start, limit);
			}
		}
		long totalCount = page.getTotalCount();
		totalPage = (int) (totalCount % limit == 0 ? totalCount / limit : totalCount / limit + 1);

		pv.setCurPage(curPage);
		pv.setThead(view.getThead());

		pv.setTbody(page.getList());
		pv.setTotalPage(totalPage);
		pv.setLimit(limit);
		return pv;
	}

	/**
	 * 基本类型不做处理，交由具体实现类处理,引用类型做非空 判断
	 * 
	 * @param vo
	 * @param req
	 * @return
	 */
	@Override
	public void mergeQueryCondition(final Object vo, final StringBuilder sql,
			final List<Object> params, HttpServletRequest req) {
		Enumeration<String> names = req.getParameterNames();
		final Set<String> fldNames = new HashSet<String>();
		while (names.hasMoreElements()) {
			fldNames.add(names.nextElement());
		}
		ReflectionUtils.doWithFields(vo.getClass(),
				new ReflectionUtils.FieldCallback() {

					@Override
					public void doWith(Field field)
							throws IllegalArgumentException,
							IllegalAccessException {
						Class<?> fldType = field.getType();
						if (handlerMap.containsKey(fldType)) {
							handlerMap.get(fldType).handle(field, vo, sql,
									params);
						} else {
							handlerMap.get(Object.class).handle(field, vo, sql,
									params);
						}
					}
				}, new ReflectionUtils.FieldFilter() {

					@Override
					public boolean matches(Field field) {
						// 如果包含字段名
						Class<?> type = field.getType();
						if (!fldNames.contains(field.getName())) {
							return false;
						}
						if (type.isPrimitive()) { // 基本数据类型,不做处理,交由具体的FieldHandler处理
							return true;
						} else { // 引用类型做非空判断
							try {
								if (field.get(vo) != null)
									return true;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
						return false;
					}
				});
	}

}
