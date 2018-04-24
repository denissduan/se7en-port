package com.se7en.service.base;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.base.BaseUser;
import com.se7en.dao.base.IBaseUserDao;
/**
 * 
 * BaseUser service
 */
@Service
public class BaseUserService extends AbstractEjbService<BaseUser> {
	@Resource
	private IBaseUserDao dao;
	
	@Override
	public IBaseUserDao getDao() {
		return dao;
	}
	public List<BaseUser> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(BaseUser baseUser,QueryView view){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" baseuser.id ");
		sql.append(" ,baseuser.name as baseusername ");
						
		sql.append(" ,baseuser.phone as baseuserphone ");
						
		sql.append(" ,baseuser.number as baseusernumber ");
						
		sql.append(" ,baseunit.name as baseunitname ");						
		sql.append(" from base_user baseuser ");
		sql.append(" left join base_unit baseunit on baseuser.unit_id = baseunit.id ");
		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(baseUser.getName())){
		    sql.append(" and baseuser.name like ? ");
		    params.add("%" + baseUser.getName() + "%");
		}
		if(StringUtil.isNotBlank(baseUser.getPhone())){
		    sql.append(" and baseuser.phone like ? ");
		    params.add("%" + baseUser.getPhone() + "%");
		}
		if(StringUtil.isNotBlank(baseUser.getNumber())){
		    sql.append(" and baseuser.number like ? ");
		    params.add("%" + baseUser.getNumber() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public BaseUser getByUsername(String username){
		String hql = "from BaseUser where name = ?";

		List<BaseUser> users = dao.queryHql(hql, new Object[]{username});
		if(users.size() == 1){
			return users.get(0);
		}

		return null;
	}

}