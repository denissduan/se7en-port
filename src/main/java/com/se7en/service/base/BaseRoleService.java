package com.se7en.service.base;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.base.BaseRole;
import com.se7en.dao.base.IBaseRoleDao;

/**
 * 用户角色 BaseRole service
 */
@Service
public class BaseRoleService extends AbstractEjbService<BaseRole> {
	@Resource
	private IBaseRoleDao dao;

	@Override
	public IBaseRoleDao getDao() {
		return dao;
	}

	public List<BaseRole> queryAll() {
		return dao.queryAll();
	}

	public PageView pageViewQueryS(BaseRole baseRole, QueryView view) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" baserole.id ");
		sql.append(" ,baserole.name as baserolename ");

		sql.append(" ,baserole.create_Time as baserolecreateTime ");

		sql.append(" ,baserole.update_Time as baseroleupdateTime ");

		sql.append(" ,baserole.describ as baseroledescrib ");

		sql.append(" from base_role baserole ");
		sql.append(" where 1 = 1 ");
		if (StringUtil.isNotBlank(baseRole.getName())) {
			sql.append(" and baserole.name like ? ");
			params.add("%" + baseRole.getName() + "%");
		}
		if (baseRole.getUnit() != null && baseRole.getUnit().getId() != null) {
			sql.append(" and unit.id = ? ");
			params.add(baseRole.getUnit().getId());
		}
		if (StringUtil.isNotBlank(baseRole.getDescrib())) {
			sql.append(" and baserole.describ like ? ");
			params.add("%" + baseRole.getDescrib() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(),
				params.toArray(), view, dao);
	}
}