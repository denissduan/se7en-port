package com.se7en.service.base;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.se7en.service.AbstractEjbService;
import org.springframework.stereotype.Service;

import com.se7en.dao.base.IBaseMenuDao;
import com.se7en.model.base.BaseMenu;
import com.se7en.common.util.StringUtil;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 菜单
 * BaseMenu service
 */
@Service
public class BaseMenuService extends AbstractEjbService<BaseMenu> {
	@Resource
	private IBaseMenuDao dao;
	
	@Override
	public IBaseMenuDao getDao() {
		return dao;
	}
	public List<BaseMenu> queryAll(){
		return dao.queryAll();
	}
	public List<BaseMenu> queryMenuTree(){
		return dao.queryHql("from BaseMenu where state = 1 order by sindex", null);
	}
	
	public PageView pageViewQueryS(BaseMenu baseMenu,QueryView view){
		StringBuilder sql = new StringBuilder(" select ");
		List<Object> params = new ArrayList<Object>();
		sql.append(" id ");
		sql.append(" ,name ");
						
		sql.append(" ,icon ");
						
		sql.append(" ,open ");
						
		sql.append(" ,sindex ");
						
		sql.append(" ,state ");
						
		sql.append(" ,data ");
						
		sql.append(" from base_menu ");
		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(baseMenu.getName())){
		    sql.append(" and name like ? ");
		    params.add("%" + baseMenu.getName() + "%");
		}
		if(StringUtil.isNotBlank(baseMenu.getIcon())){
		    sql.append(" and icon like ? ");
		    params.add("%" + baseMenu.getIcon() + "%");
		}
		if(baseMenu.getState() != null){
		    sql.append(" and state = ? ");
		    params.add(baseMenu.getState());
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
	
	public List<BaseMenu> getRoots(){
		String hql = "from BaseMenu where pid != 0 order by sindex";
		
		return dao.queryHql(hql, null);
	}
	
}