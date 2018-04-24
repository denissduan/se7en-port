package com.se7en.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface IEjbDao<T> extends IPageDao{

	public List<T> queryAll();
	
	public List<T> queryAll(String orderField, boolean isAsc);
	
	public List<T> queryHql(String hql, Object[] params);
	
	public List<T> pageQueryH2List(String hql, Object[] params, int start, int limit);
	
	public Page pageQueryH2Page(String hql, Object[] params, int start, int limit);
	
	public List<T> querySql(String sql, Object[] params, Class<T> cls);
	
	public T get(final Serializable id);
	
	public T load(final Serializable id);
	
	public boolean save(final T bean);
	
	public boolean delete(final T bean);
	
	public boolean update(T bean);
	
	public boolean saveOrUpdate(T bean);
	
	public boolean batchSave(final Collection<T> beans);
	
	public boolean batchUpdate(final Collection<T> beans);
	
	public boolean batchDel(final Collection<T> beans);
	
	public boolean batchSaveOrUpdate(final Collection<T> beans);
	
	long totalHql(String hql,Object[] params);
	
}
