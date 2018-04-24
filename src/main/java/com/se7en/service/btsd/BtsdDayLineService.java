package com.se7en.service.btsd;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.BtsdDayLine;
import com.se7en.dao.btsd.IBtsdDayLineDao;

/**
 *  * BtsdDayLine service
 */
@Service
public class BtsdDayLineService extends AbstractEjbService<BtsdDayLine> {

	@Resource
	private IBtsdDayLineDao dao;
	
	@Override
	public IBtsdDayLineDao getDao() {
	
		return dao;
	}
	
	public List<BtsdDayLine> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(BtsdDayLine btsdDayLine,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" btsddayline.id ");
		sql.append(" ,btsddayline.date as btsddayline_date ");
		sql.append(" ,btsddayline.datefmt as btsddayline_datefmt ");
		sql.append(" ,btsddayline.kp as btsddayline_kp ");
		sql.append(" ,btsddayline.zg as btsddayline_zg ");
		sql.append(" ,btsddayline.zd as btsddayline_zd ");
		sql.append(" ,btsddayline.sp as btsddayline_sp ");
		sql.append(" from btsd_day_line btsddayline ");

		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(btsdDayLine.getDatefmt())){
			sql.append(" and btsddayline.datefmt like ? ");
			params.add("%" + btsdDayLine.getDatefmt() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public boolean isExist(String code, long date){
		Object[] param = {code, date};
		long count = dao.totalHql("from BtsdDayLine where coin.id = ? and date = ?", param);
		if(count > 0){
			return true;
		}
		return false;
	}

}