package com.se7en.service;

import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.se7en.common.util.ContextUtil;
import com.se7en.dao.CommonDao;

/**
 * 通用（不含EJB的dao处理）Service
 * 
 * @author Administrator
 * 
 */
@Service("commonDaoService")
public abstract class CommonDaoService extends CommonService implements
		ICommonDaoService{

	protected Logger log = Logger.getRootLogger();
	
	/**
	 * Gets the common dao. 获取dao
	 * 
	 * @return the common dao
	 */
	private CommonDao getCommonDao() {
		return (CommonDao) ContextUtil.getBean("commonDao");
	}

	@Override
	public List<Object[]> querySql2Array(String sql, Object[] params) {
		
		return getCommonDao().querySql2Array(sql, params);
	}

	@Override
	public List<Map<String,Object>> querySql2Map(String sql, Object[] params) {
		
		return getCommonDao().querySql2Map(sql, params);
	}

	@Override
	public int execute(String sql, Object[] params) {
		
		return getCommonDao().execute(sql, params);
	}

	@Override
	public long totalSql(String sql, Object[] params) {
		
		return getCommonDao().totalSql(sql, params);
	}

	@Override
	public List<Object> querySql2Single(String sql, Object[] params) {
		
		return getCommonDao().querySql2Single(sql, params);
	}
}
