package com.se7en.service.base;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.base.BaseRoleMenuPrivilege;
import com.se7en.dao.base.IBaseRoleMenuPrivilegeDao;
/**
 * 角色菜单权限
 * BaseRoleMenuPrivilege service
 */
@Service
public class BaseRoleMenuPrivilegeService extends AbstractEjbService<BaseRoleMenuPrivilege> {
	@Resource
	private IBaseRoleMenuPrivilegeDao dao;
	@Override
	public IBaseRoleMenuPrivilegeDao getDao() {
		return dao;
	}
	public List<BaseRoleMenuPrivilege> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(BaseRoleMenuPrivilege baseRoleMenuPrivilege,QueryView view){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" baserolemenuprivilege.id ");
		sql.append(" from base_role_menu_privilege baserolemenuprivilege ");
		sql.append(" where 1 = 1 ");
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
}