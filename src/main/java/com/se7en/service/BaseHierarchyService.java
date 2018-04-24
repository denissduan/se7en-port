package com.se7en.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;

import com.se7en.dao.BaseHbmDao;
import com.se7en.dao.IEjbDao;
import com.se7en.model.BaseHierarchy;
import com.se7en.service.md.StringServices;
import com.se7en.common.util.BeanUtil;
import com.se7en.common.util.StringUtil;

public abstract class BaseHierarchyService<T extends BaseHierarchy> extends AbstractEjbService<T> {

	@Resource
	private StringServices strSrv;
	
	private String tableName;
	
	protected String getTableName(){
		if(StringUtil.isEmpty(tableName)){
			tableName = strSrv.getTableNameFromClassName(getGenericClass().getSimpleName());
		}
		return tableName;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveOrUpdate(T bean) {
		boolean ret = false;
		
		BaseHierarchy sNode = bean;
		getTableName();
		try {
			IEjbDao<T> dao = getDao();
			if(sNode.getId() != null){
				int pid = sNode.getPid();
				BaseHierarchy oldNode = dao.get(sNode.getId());
				BeanUtil.copyNotNull2NullProperties(oldNode, sNode);	//复制不为空的属性到为空的属性中
				Session session = ((BaseHbmDao)dao).curSession();
				if(oldNode.getPid() == pid){	//编辑	
					sNode = (BaseHierarchy)session.merge(sNode);
					session.saveOrUpdate((T)sNode);
					session.flush();
					ret = true;
					return ret;
//					return dao.saveOrUpdate((T)sNode);
				}else{	//调动
					//查询下属节点
					String subHql = "from " + getGenericClass().getSimpleName() 
							+ " where lindex > " + sNode.getLindex() + " and rindex < "
							+ sNode.getRindex() + " order by lindex ";
					List<BaseHierarchy> nodes = (List<BaseHierarchy>)dao.queryHql(subHql, null);
					nodes.add(0, sNode);
					//旧的处理
					int lindex = oldNode.getLindex();
					int rindex = oldNode.getRindex();
					int width = rindex - lindex + 1;
					//3
					String updateSql1 = "update " + tableName + " set rindex = rindex - " + width + " where rindex > ? ";
					getDao().execute(updateSql1, new Object[]{rindex});
					//4
					String updateSql2 = "update " + tableName + " set lindex = lindex - " + width + " where lindex > ? ";
					getDao().execute(updateSql2, new Object[]{rindex});
					
					//按顺序从高到低插入
					for (BaseHierarchy bh : nodes) {
//						T saveBean = (T)CloneUtil.clone(bh);
						bh = (BaseHierarchy)session.merge(bh);
						ret = saveOrUpdateNode(bh);
						session.flush();
					}
				}
			}else{	//新增
				ret = saveOrUpdateNode(sNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return ret;
	}

	private boolean saveOrUpdateNode(BaseHierarchy sNode) {
		String tableName = getTableName();
		IEjbDao<T> dao = getDao();
		
		boolean ret;
		boolean isRoot = false;
		if(sNode.getPid() == null || sNode.getPid() == 1){
			isRoot = true;
		}
		Integer key = null;
		if(isRoot){
			String rindexSql = "select max(rindex) from " + tableName;
			key = (Integer)dao.querySql2Single(rindexSql, null).get(0);
			if(key == null)
				key = 0;
		}else{
			BaseHierarchy pNode = dao.get(sNode.getPid());
			key = pNode.getLindex();
		}
		String rindexSql = "update " + tableName + " set rindex = rindex + 2 where rindex > ? "; 
		dao.execute(rindexSql, new Object[]{key});
		String lindexSql = "update " + tableName + " set lindex = lindex + 2 where lindex > ? "; 
		dao.execute(lindexSql, new Object[]{key});
		sNode.setLindex(key + 1);
		sNode.setRindex(key + 2);
		ret = dao.saveOrUpdate((T)sNode);
		return ret;
	}
	
	@Override
	public boolean delete(T bean) {
		boolean ret = true;
		
		String tableName = getTableName();
		try {
			//1
			bean = get(bean.getId());
			if(bean.getLindex() != null && bean.getRindex() != null){
				int lindex = bean.getLindex();
				int rindex = bean.getRindex();
				int width = rindex - lindex + 1;
				//2
				String delSql = "delete from " + tableName + " where lindex between ? and ? ";
				this.getDao().execute(delSql, new Object[]{lindex,rindex});
				//3
				String updateSql1 = "update " + tableName + " set rindex = rindex - " + width + " where rindex > ? ";
				getDao().execute(updateSql1, new Object[]{rindex});
				//4
				String updateSql2 = "update " + tableName + " set lindex = lindex - " + width + " where lindex > ? ";
				getDao().execute(updateSql2, new Object[]{rindex});
			}else{
				ret = getDao().delete(bean);
			}
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
		}
	
		return ret;
	}
	
	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean batchSave(Collection<T> collection) {
		boolean ret = true;
		for (T t : collection) {
			ret = save(t);
		}
		
		return ret;
	}

	/**
	 * 批量修改
	 * 
	 * @param list
	 * @return
	 */
	public boolean batchUpdate(Collection<T> collection) {
		boolean ret = true;
		for (T t : collection) {
			ret = update(t);
		}
		
		return ret;
	}

	/**
	 * 批量删除
	 * 
	 * @param list
	 * @return
	 */
	public boolean batchDelete(Collection<T> collection) {
		boolean ret = true;
		for (T t : collection) {
			ret = delete(t);
		}
		
		return ret;
	}

	/**
	 * 批量保存或修改
	 * 
	 * @param list
	 * @return
	 */
	public boolean batchSaveOrUpdate(Collection<T> collection) {
		boolean ret = true;
		for (T t : collection) {
			ret = saveOrUpdate(t);
		}
		
		return ret;
	}
}
