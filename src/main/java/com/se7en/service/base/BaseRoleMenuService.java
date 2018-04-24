package com.se7en.service.base;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.base.BaseRoleMenu;
import com.se7en.dao.base.IBaseRoleMenuDao;
/**
 * 角色菜单
 * BaseRoleMenu service
 */
@Service
public class BaseRoleMenuService extends AbstractEjbService<BaseRoleMenu> {
	@Resource
	private IBaseRoleMenuDao dao;
	@Override
	public IBaseRoleMenuDao getDao() {
		return dao;
	}
	public List<BaseRoleMenu> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(BaseRoleMenu baseRoleMenu,QueryView view){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" baserolemenu.id ");
		sql.append(" from base_role_menu baserolemenu ");
		sql.append(" where 1 = 1 ");
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
}