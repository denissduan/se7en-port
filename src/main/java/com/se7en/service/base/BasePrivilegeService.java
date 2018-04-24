package com.se7en.service.base;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.se7en.dao.base.IBasePrivilegeDao;
import com.se7en.model.base.BasePrivilege;
import com.se7en.service.BaseObjectService;
import com.se7en.common.util.StringUtil;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 按钮权限
 * BasePrivilege service
 */
@Service
public class BasePrivilegeService extends BaseObjectService<BasePrivilege> {
	@Resource
	private IBasePrivilegeDao dao;
	@Override
	public IBasePrivilegeDao getDao() {
		return dao;
	}
	public List<BasePrivilege> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(BasePrivilege basePrivilege,QueryView view){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" baseprivilege.id ");
		sql.append(" ,baseprivilege.name as baseprivilegename ");
						
		sql.append(" ,baseprivilege.icon as baseprivilegeicon ");
						
		sql.append(" ,baseprivilege.css as baseprivilegecss ");
						
		sql.append(" from base_privilege baseprivilege ");
		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(basePrivilege.getName())){
		    sql.append(" and baseprivilege.name like ? ");
		    params.add("%" + basePrivilege.getName() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
}