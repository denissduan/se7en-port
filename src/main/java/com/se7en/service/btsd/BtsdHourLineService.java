package com.se7en.service.btsd;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.BtsdHourLine;
import com.se7en.dao.btsd.IBtsdHourLineDao;

/**
 *  * BtsdHourLine service
 */
@Service
public class BtsdHourLineService extends AbstractEjbService<BtsdHourLine> {

	@Resource
	private IBtsdHourLineDao dao;
	
	@Override
	public IBtsdHourLineDao getDao() {
	
		return dao;
	}
	
	public List<BtsdHourLine> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(BtsdHourLine btsdHourLine,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" btsdhourline.id ");
		sql.append(" ,btsdhourline.date as btsdhourline_date ");
		sql.append(" ,btsdhourline.datefmt as btsdhourline_datefmt ");
		sql.append(" ,btsdhourline.kp as btsdhourline_kp ");
		sql.append(" ,btsdhourline.zg as btsdhourline_zg ");
		sql.append(" ,btsdhourline.zd as btsdhourline_zd ");
		sql.append(" ,btsdhourline.sp as btsdhourline_sp ");
		sql.append(" from btsd_hour_line btsdhourline ");

		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(btsdHourLine.getDatefmt())){
			sql.append(" and btsdhourline.datefmt like ? ");
			params.add("%" + btsdHourLine.getDatefmt() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public boolean isExist(String code, long date){
		Object[] param = {code, date};
		long count = dao.totalHql("from BtsdHourLine where coin.id = ? and date = ?", param);
		if(count > 0){
			return true;
		}
		return false;
	}
}