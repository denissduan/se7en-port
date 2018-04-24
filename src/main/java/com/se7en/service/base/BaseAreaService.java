package com.se7en.service.base;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.base.BaseArea;
import com.se7en.dao.base.IBaseAreaDao;
import com.se7en.service.BaseHierarchyService;
/**
 * 
 * BaseArea service
 */
@Service
public class BaseAreaService extends BaseHierarchyService<BaseArea> {
	@Resource
	private IBaseAreaDao dao;
	@Override
	public IBaseAreaDao getDao() {
		return dao;
	}
	public List<BaseArea> queryAll(){
		return dao.queryAll();
	}
	public PageView pageViewQueryS(BaseArea baseArea,QueryView view){
		StringBuilder sql = new StringBuilder(100);
		List<Object> params = new ArrayList<Object>();
		sql.append("select ");
		sql.append(" id ");
		sql.append(" ,name ");
		sql.append(" from base_area ");
		sql.append(" where 1 = 1 ");
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
}