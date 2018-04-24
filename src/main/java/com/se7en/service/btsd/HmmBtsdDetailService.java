package com.se7en.service.btsd;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.HmmBtsdDetail;
import com.se7en.dao.btsd.IHmmBtsdDetailDao;

/**
 *  * HmmBtsdDetail service
 */
@Service
public class HmmBtsdDetailService extends AbstractEjbService<HmmBtsdDetail> {

	@Resource
	private IHmmBtsdDetailDao dao;
	
	@Override
	public IHmmBtsdDetailDao getDao() {
	
		return dao;
	}
	
	public List<HmmBtsdDetail> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(HmmBtsdDetail hmmBtsdDetail,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" hmmbtsddetail.id ");
		sql.append(" from hmm_btsd_detail hmmbtsddetail ");

		sql.append(" where 1 = 1 ");
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

}