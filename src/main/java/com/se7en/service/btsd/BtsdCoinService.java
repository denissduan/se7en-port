package com.se7en.service.btsd;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.BtsdCoin;
import com.se7en.dao.btsd.IBtsdCoinDao;
/**
 * 
 * BtsdCoin service
 */
@Service
public class BtsdCoinService extends AbstractEjbService<BtsdCoin> {
	@Resource
	private IBtsdCoinDao dao;

	@Override
	public IBtsdCoinDao getDao() {
		return dao;
	}

	public List<BtsdCoin> queryAll(){
		return dao.queryAll();
	}

	public Map<String, BtsdCoin> queryAll2Map(){
		List<BtsdCoin> list = dao.queryAll();
		Map<String, BtsdCoin> ret = new HashMap<>(list.size());
		for(BtsdCoin coin : list){
			ret.put(coin.getId(), coin);
		}
		return ret;
	}

	public PageView pageViewQueryS(BtsdCoin btsdCoin,QueryView view){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" btsdcoin.id ");

		sql.append(" ,btsdcoin.name as btsdcoinname ");
						
		sql.append(" ,btsdcoin.mk as btsdcoinmk ");
						
		sql.append(" from btsd_coin btsdcoin ");
		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(btsdCoin.getId())){
		    sql.append(" and btsdcoin.id like ? ");
		    params.add("%" + btsdCoin.getId() + "%");
		}
		if(StringUtil.isNotBlank(btsdCoin.getName())){
		    sql.append(" and btsdcoin.name like ? ");
		    params.add("%" + btsdCoin.getName() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}
}