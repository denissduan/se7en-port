package com.se7en.service.btsd;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.BtsdMin5Line;
import com.se7en.dao.btsd.IBtsdMin5LineDao;

/**
 *  * BtsdMin5Line service
 */
@Service
public class BtsdMin5LineService extends AbstractEjbService<BtsdMin5Line> {

	@Resource
	private IBtsdMin5LineDao dao;
	
	@Override
	public IBtsdMin5LineDao getDao() {
	
		return dao;
	}
	
	public List<BtsdMin5Line> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(BtsdMin5Line btsdMin5Line,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" btsdmin5line.id ");
		sql.append(" ,btsdmin5line.date as btsdmin5line_date ");
		sql.append(" ,btsdmin5line.datefmt as btsdmin5line_datefmt ");
		sql.append(" ,btsdmin5line.kp as btsdmin5line_kp ");
		sql.append(" ,btsdmin5line.zg as btsdmin5line_zg ");
		sql.append(" ,btsdmin5line.zd as btsdmin5line_zd ");
		sql.append(" ,btsdmin5line.sp as btsdmin5line_sp ");
		sql.append(" from btsd_min5line btsdmin5line ");

		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(btsdMin5Line.getDatefmt())){
			sql.append(" and btsdmin5line.datefmt like ? ");
			params.add("%" + btsdMin5Line.getDatefmt() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public boolean isExist(String code, long date){
		Object[] param = {code, date};
		long count = dao.totalHql("from BtsdMin5Line where coin.id = ? and date = ?", param);
		if(count > 0){
			return true;
		}
		return false;
	}

}