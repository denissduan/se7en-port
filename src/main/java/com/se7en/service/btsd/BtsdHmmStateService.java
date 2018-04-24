package com.se7en.service.btsd;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.BtsdHmmState;
import com.se7en.dao.btsd.IBtsdHmmStateDao;

/**
 *  * BtsdHmmState service
 */
@Service
public class BtsdHmmStateService extends AbstractEjbService<BtsdHmmState> {

	@Resource
	private IBtsdHmmStateDao dao;
	
	@Override
	public IBtsdHmmStateDao getDao() {
	
		return dao;
	}
	
	public List<BtsdHmmState> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(BtsdHmmState btsdHmmState,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" btsdhmmstate.id ");
		sql.append(" ,btsdhmmstate.id as btsdhmmstate_id ");
		sql.append(" ,btsdhmmstate.type as btsdhmmstate_type ");
		sql.append(" ,btsdhmmstate.name as btsdhmmstate_name ");
		sql.append(" ,btsdhmmstate.no as btsdhmmstate_no ");
		sql.append(" ,btsdhmmstate.minBound as btsdhmmstate_minBound ");
		sql.append(" ,btsdhmmstate.maxBound as btsdhmmstate_maxBound ");
		sql.append(" from btsd_hmm_state btsdhmmstate ");

		sql.append(" where 1 = 1 ");
		if(btsdHmmState.getId() != null){
			sql.append(" and btsdhmmstate.id = ? ");
			params.add(btsdHmmState.getId());
		}
		if(btsdHmmState.getType() != null){
			sql.append(" and btsdhmmstate.type = ? ");
			params.add(btsdHmmState.getType());
		}
		if(StringUtil.isNotBlank(btsdHmmState.getName())){
			sql.append(" and btsdhmmstate.name like ? ");
			params.add("%" + btsdHmmState.getName() + "%");
		}
		if(btsdHmmState.getNo() != null){
			sql.append(" and btsdhmmstate.no = ? ");
			params.add(btsdHmmState.getNo());
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public List<BtsdHmmState> queryByTypeAndCode(int type, String code){
		String hql = "from BtsdHmmState where type = ? and coin.id = ?";
		Object[] params = {type, code};
		return dao.queryHql(hql, params);
	}

}