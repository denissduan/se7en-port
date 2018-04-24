package com.se7en.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.se7en.common.ColInfo;
import org.apache.commons.collections.CollectionUtils;

import com.se7en.dao.IEjbDao;
import com.se7en.service.md.ModelSqlService;
import com.se7en.service.md.SqlParamBean;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;

/**
 * 包含EJB的分页处理
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractEjbService<T> extends CommonDaoService implements IEjbService<T>{

	@Resource
	private PageQueryService pageQueryService;

	@Resource
	protected ModelSqlService modelSqlSrv;

	@Resource
	private GridService gridSrv;
	
	/**
	 * 获取dao
	 * 
	 * @return
	 */
	public abstract IEjbDao<T> getDao();

	/**
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> queryHql(String hql, Object[] params){
		return getDao().queryHql(hql, params);
	}

	/**
	 * 获取Bean
	 * 
	 * @param id
	 * @return
	 */
	// @Transactional(readOnly = true)
	public T get(Serializable id) {
		return getDao().get(id);
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean save(T bean) {
		return getDao().save(bean);
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean update(T bean) {
		return getDao().update(bean);
	}

	/**
	 * 新增或修改保存
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean saveOrUpdate(T bean) {
		return getDao().saveOrUpdate(bean);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean delete(T bean) {
		return getDao().delete(bean);
	}

	/**
	 * 批量保存
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean batchSave(Collection<T> collection) {
		return getDao().batchSave(collection);
	}

	/**
	 * 批量修改
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean batchUpdate(Collection<T> collection) {
		return getDao().batchUpdate(collection);
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean batchDelete(Collection<T> collection) {
		return getDao().batchDel(collection);
	}

	/**
	 * 批量保存或修改
	 * 
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean batchSaveOrUpdate(Collection<T> collection) {
		return getDao().batchSaveOrUpdate(collection);
	}

	public PageQueryService getPageQueryService() {
		return pageQueryService;
	}

	/**
	 * 获取泛型类型
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getGenericClass() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	/**
	 * 模型驱动生成查询sql
	 * @param request
	 * @param view
	 * @return
	 */
	public PageView modelQuery(HttpServletRequest request, QueryView view) {
		Class<T> entity = getGenericClass();
		
		SqlParamBean sqlParam = modelSqlSrv.createQuerySql(entity,request);

		return getPageQueryService().pageQueryS(sqlParam.getSql().toString(),
				sqlParam.getParams().toArray(), view, getDao());
	}
	
}
