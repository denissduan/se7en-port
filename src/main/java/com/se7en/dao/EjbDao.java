package com.se7en.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class EjbDao<T> extends PageDao implements IEjbDao<T> {

	protected Class<T> entityClass = getGenericClass();

	/**
	 * 查询所有
	 */
	public List<T> queryAll() {
		Query query = initHqlQuery("from " + entityClass.getSimpleName(), null);
		List<T> list = query.list();
		return list;
	}

	/**
	 * 获取全部对象, 支持按属性行序.
	 */
	public List<T> queryAll(String orderField, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderField));
		} else {
			c.addOrder(Order.desc(orderField));
		}
		return (List<T>) c.list();
	}

	/**
	 * 查询指定hql语句
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> queryHql(String hql, Object[] params) {
		Query query = this.initHqlQuery(hql, params);
		return (List<T>) query.list();
	}
	
	/**
	 * 分页查询,hql语句不包含起始记录数和查询记录数
	 * @param hql
	 * @param params
	 * @param start 开始记录数
	 * @param limit 查询记录数
	 * @return
	 */
	public List<T> pageQueryH2List(String hql, Object[] params, int start, int limit) {
		Query query = initPageQuery(hql, params, start, limit, true);
		return (List<T>) query.list();
	}

	/**
	 * 分页查询hql,hql语句不包含起始记录数和查询记录数
	 * @param hql
	 * @param params
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page pageQueryH2Page(String hql, Object[] params, int start, int limit){
		List<T> list = pageQueryH2List(hql, params, start, limit);
		long total = totalHql(hql,params);
		page.setList(list);
		page.setTotalCount(total);
		return page;
	}
	
	/**
	 * 查询sql转换为List<T>
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 */
	public List<T> querySql(String sql, Object[] params, Class<T> cls) {
		SQLQuery query = initSqlQuery(sql, params);
		if (cls != null) {
			query.addEntity(cls);
		}
		return (List<T>) query.list();
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T get(final Serializable id) {
		return (T) curSession().get(this.entityClass, id);
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T load(final Serializable id) {
		return (T) curSession().load(this.entityClass, id);
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 * @return
	 */
	public boolean save(final T bean) {
		boolean sign = false;
		try {
			curSession().save(bean);
			sign = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}
	
	/**
	 * 删除对象
	 * 
	 * @param bean
	 */
	public boolean delete(final T bean) {
		boolean sign = false;
		try {
			curSession().delete(bean);
			sign = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public boolean update(T bean) {
		boolean sign = false;
		try {
			curSession().update(bean);
			sign = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * 保存或更新
	 * 
	 * @param bean
	 * @return
	 */
	public boolean saveOrUpdate(T bean) {
		boolean sign = false;
		try {
			curSession().saveOrUpdate(bean);
			sign = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}
	
	/**
	 * 批量保存
	 * @param beans
	 * @return
	 */
	public boolean batchSave(final Collection<T> beans){
		return batchExecute(beans, 1);
	}
	
	/**
	 * 批量更新
	 * @param beans
	 * @return
	 */
	public boolean batchUpdate(final Collection<T> beans){
		return batchExecute(beans, 2);
	}
	
	/**
	 * 批量删除
	 * @param beans
	 * @return
	 */
	public boolean batchDel(final Collection<T> beans){
		return batchExecute(beans, 3);
	}
	
	/**
	 * 批量更新或保存
	 * @param beans
	 * @return
	 */
	public boolean batchSaveOrUpdate(final Collection<T> beans){
		return batchExecute(beans, 4);
	}
	
	/**
	 * 批量处理
	 * @param beans
	 * @param optype 操作类型(1.save,2.update,3.delete,4.saveOrUpdate)
	 * @return
	 */
	private boolean batchExecute(final Collection<T> beans,int optype){
		boolean sign = true;
		try {
			int index = 0;
			for (T bean : beans) {
				switch(optype){
				case 1 :
					curSession().save(bean);
					break;
				case 2:
					curSession().update(bean);
					break;
				case 3:
					curSession().delete(bean);
					break;
				case 4:
					curSession().saveOrUpdate(bean);
					break;
				default :
					break;
				}
				if(index++ % 100 == 0){
					curSession().flush();
					curSession().clear();
				}
			}
		} catch (Exception e) {
			sign = false;
			e.printStackTrace();
		}
		return sign;
	}
	
	/**
	 * hql 查询总数
	 * @param hql
	 * @param params
	 * @return
	 */
	public long totalHql(String hql,Object[] params){
		String innersql = null;
		int pos = hql.indexOf("from");
		if(pos != -1){
			pos += 4;
			innersql = new String(hql.substring(pos));
		}else{
			innersql = hql; 
		}
		String countSql = "select count(*) from " + innersql;
		Query query = initHqlQuery(countSql, params);
		
		return Long.parseLong(query.uniqueResult().toString());
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
	 * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	protected Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = curSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
}
