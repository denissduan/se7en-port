package com.se7en.service.base;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.base.BaseUnit;
import com.se7en.dao.base.IBaseUnitDao;
import com.se7en.service.BaseHierarchyService;
/**
 * 组织
 * BaseUnit service
 */
@Service
public class BaseUnitService extends BaseHierarchyService<BaseUnit> {
	@Resource
	private IBaseUnitDao dao;
	@Override
	public IBaseUnitDao getDao() {
		return dao;
	}
	public List<BaseUnit> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(BaseUnit baseUnit,QueryView view){
		StringBuilder sql = new StringBuilder(" select ");
		List<Object> params = new ArrayList<Object>();
		sql.append(" id ");
		sql.append(" ,name ");
						
		sql.append(" ,phone ");
						
		sql.append(" ,state ");
						
		sql.append(" from base_unit ");
		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(baseUnit.getName())){
		    sql.append(" and name like ? ");
		    params.add("%" + baseUnit.getName() + "%");
		}
		if(StringUtil.isNotBlank(baseUnit.getPhone())){
		    sql.append(" and phone like ? ");
		    params.add("%" + baseUnit.getPhone() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
}