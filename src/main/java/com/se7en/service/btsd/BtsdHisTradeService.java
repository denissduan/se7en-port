package com.se7en.service.btsd;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.btsd.BtsdHisTrade;
import com.se7en.dao.btsd.IBtsdHisTradeDao;

/**
 *  * BtsdHisTrade service
 */
@Service
public class BtsdHisTradeService extends AbstractEjbService<BtsdHisTrade> {

	@Resource
	private IBtsdHisTradeDao dao;
	
	@Override
	public IBtsdHisTradeDao getDao() {
	
		return dao;
	}
	
	public List<BtsdHisTrade> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(BtsdHisTrade btsdHisTrade,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" btsdhistrade.id ");
		sql.append(" ,btsdhistrade.date as btsdhistrade_date ");
		sql.append(" ,btsdhistrade.price as btsdhistrade_price ");
		sql.append(" ,btsdhistrade.volume as btsdhistrade_volume ");
		sql.append(" ,btsdhistrade.type as btsdhistrade_type ");
		sql.append(" from btsd_his_trade btsdhistrade ");

		sql.append(" where 1 = 1 ");
		if(StringUtil.isNotBlank(btsdHisTrade.getDate())){
			sql.append(" and btsdhistrade.date like ? ");
			params.add("%" + btsdHisTrade.getDate() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	/**
	 * private BtsdCoin coin;
	 private String date;
	 private Double price;
	 private Double volume;
	 private Integer type;
	 * @param code
	 * @param date
     * @return
     */
	public boolean isExist(String code, String date, double price, double volume,int type){
		Object[] param = {code, date, price, volume, type};
		long count = dao.totalHql("from BtsdHisTrade where coin.id = ? and date = ? and price = ? and volume = ? and type = ?", param);
		if(count > 0){
			return true;
		}
		return false;
	}

}