package com.se7en.service.sys;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.sys.SysParam;
import com.se7en.dao.Order;
import com.se7en.dao.sys.ISysParamDao;
/**
 * 系统参数
 * SysParam service
 */
@Service
public class SysParamService extends AbstractEjbService<SysParam> {
	@Resource
	private ISysParamDao dao;
	@Override
	public ISysParamDao getDao() {
		return dao;
	}
	public List<SysParam> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(SysParam sysParam,QueryView view){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" sysparam.id ");
		sql.append(" ,sysparam.module as sysparammodule ");
						
		sql.append(" ,sysparam.pname as sysparampname ");
						
		sql.append(" ,sysparam.pvalue as sysparampvalue ");
						
		sql.append(" ,sysparam.describ as sysparamdescrib ");
						
		sql.append(" ,sysparam.pindex as sysparampindex ");
						
		sql.append(" from sys_param sysparam ");
		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(sysParam.getModule())){
		    sql.append(" and sysparam.module like ? ");
		    params.add("%" + sysParam.getModule() + "%");
		}
		if(StringUtil.isNotBlank(sysParam.getPname())){
		    sql.append(" and sysparam.pname like ? ");
		    params.add("%" + sysParam.getPname() + "%");
		}
		if(StringUtil.isNotBlank(sysParam.getDescrib())){
		    sql.append(" and sysparam.describ like ? ");
		    params.add("%" + sysParam.getDescrib() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
	
	/**
	 * 根据参数名查找
	 * 
	 * @param pname
	 * @return
	 */
	public List<SysParam> getParamByPName(String module, String pname) {
		String hql = " from SysParam where module = ? and pname = ? ";
		Object[] params = { module, pname };
		return dao.queryHql(hql, params);
	}
	
	/**
	 * Gets the single param by p name.
	 * 获取单条参数，如果存在多条返回第一条
	 * @param module the module
	 * @param pname the pname
	 * @return the single param by p name
	 */
	public SysParam getSingleParamByPName(String module, String pname) {
		SysParam ret = null;
		
		List<SysParam> list = getParamByPName(module, pname);
		
		if(CollectionUtils.isNotEmpty(list)){
			ret = list.get(0);
		}
		
		return ret;
	}

	/**
	 * Gets the param by p name sort by value.
	 * 跟据参数查找加排序
	 * @param pname the pname
	 * @param order the order
	 * @return the param by p name sort by value
	 */
	public List<SysParam> getParamByPNameSortByValue(String module,String pname,Order order){
		String hql = " from SysParam where module = ? and pname = ? order by pindex ";
		switch(order){
		case ASC:
			hql += Order.ASC;
			break;
		case DESC:
		default:
			hql += Order.DESC;
			break;
		}
		
		return dao.queryHql(hql, new Object[]{module, pname});
	}
}