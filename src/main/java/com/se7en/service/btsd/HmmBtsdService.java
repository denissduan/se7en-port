package com.se7en.service.btsd;
import java.util.*;
import javax.annotation.Resource;

import com.se7en.common.EhCache;
import com.se7en.model.btsd.*;
import com.se7en.service.btsd.common.HmmDimension;
import com.se7en.service.btsd.common.HmmStateWrapper;
import com.se7en.service.btsd.common.HmmType;
import net.sf.ehcache.Cache;
import net.sf.json.JSONArray;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.MLSequenceSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.basic.BasicMLSequenceSet;
import org.encog.ml.hmm.HiddenMarkovModel;
import org.encog.ml.hmm.distributions.ContinousDistribution;
import org.springframework.stereotype.Service;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.dao.btsd.IHmmBtsdDao;

/**
 *  * HmmBtsd service
 */
@Service
public class HmmBtsdService extends AbstractEjbService<HmmBtsd> {

	@Resource
	private IHmmBtsdDao dao;

	@Resource
	private DwBtsdHourLineService dwBtsdHourLineService;

	@Resource
	private DwBtsdMin5LineService dwBtsdMin5LineService;

	private Cache hmmCache = EhCache.getInstance().getHmmCache();

	@Override
	public IHmmBtsdDao getDao() {
	
		return dao;
	}
	
	public List<HmmBtsd> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(HmmBtsd hmmBtsd,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" hmmbtsd.id ");
		sql.append(" from hmm_btsd hmmbtsd ");

		sql.append(" where 1 = 1 ");
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public void testHmm(BtsdCoin coin) {
		String code = coin.getId();

		HmmStateWrapper wrapper = HmmStateWrapper.wrap(HmmType.HOUR, code);
		int stateSize = wrapper.getStates().size();
		int dimensionSize = HmmDimension.getDimensions().size();

		log.info("----------------------------,code=" + code + "," + coin.getName() + " hmm hour建模");
		List<DwBtsdHourLine> dwBtsdHourLines = dwBtsdHourLineService.queryHql("from DwBtsdHourLine where coin.id = ? order by date asc", new Object[]{code});
		if (dwBtsdHourLines.size() == 0) {
			log.error("-------------------code=" + coin.getId() + ",name=" + coin.getName() + ",数据为空");
			return;
		}

		int matchRight = 0,matchErr = 0;
		for (DwBtsdHourLine dwBtsdHourLine : dwBtsdHourLines) {
			double spScale = dwBtsdHourLine.getSpscale();

			//判断涨幅类型
			BtsdHmmState st = wrapper.valueOf(dwBtsdHourLine.getSpscale());

			//获取hmm
			String hql = "from HmmBtsd where type = ? and coin.id = ?";
			Object[] param = {HmmType.HOUR,code};
			List<HmmBtsd> hmmBtsdList = queryHql(hql, param);
			HmmBtsd hmmBtsd = hmmBtsdList.get(0);

			//生成观测序列
			MLSequenceSet sequenceSet = new BasicMLSequenceSet();
			MLDataSet sequence = new BasicMLDataSet();
//			for(double[] data : dataSeq) {
//				sequence.add(new BasicMLDataPair(new BasicMLData(data)));
//			}
			double[] d = {dwBtsdHourLine.getZgscale(),dwBtsdHourLine.getZdscale(),dwBtsdHourLine.getSpscale()};
 			sequence.add(new BasicMLDataPair(new BasicMLData(d)));

			sequenceSet.add(sequence);

			//
			HmmMap hmmMap = getHmm(hmmBtsd);
			HiddenMarkovModel hmm = hmmMap.getHmm();
			int[] states = hmm.getStatesForSequence(sequenceSet);

			String name = wrapper.valueOf(hmmMap.getStateMap().get(states[0])).getName();
			boolean matchRet = st.getName().equals(name);
			log.info("answer=" + st.getName() + ",judge=" + name + ",states=" + Arrays.toString(states) + ", " + matchRet);
			if(matchRet == true){
				matchRight++;
			}else{
				matchErr++;
			}
		}
		log.info("--------------matchRight=" + matchRight + ",matchErr=" + matchErr);
	}

	class HmmMap{

		private HiddenMarkovModel hmm;

		private Map<Integer,Integer> stateMap = new HashMap<>();

		public HiddenMarkovModel getHmm() {
			return hmm;
		}

		public void setHmm(HiddenMarkovModel hmm) {
			this.hmm = hmm;
		}

		public Map<Integer, Integer> getStateMap() {
			return stateMap;
		}

		public void setStateMap(Map<Integer, Integer> stateMap) {
			this.stateMap = stateMap;
		}
	}

	public HmmMap getHmm(HmmBtsd hmmBtsd){
		HmmMap result = new HmmMap();

		List<HmmBtsdDetail> detaills = new LinkedList<>(hmmBtsd.getDetails());

		Iterator<HmmBtsdDetail> ite = detaills.iterator();
		while(ite.hasNext()){
			HmmBtsdDetail hmmBtsdDetail = ite.next();
			double pi = JSONArray.fromObject(hmmBtsdDetail.getPi()).getDouble(0);
			if(pi == 0){
				ite.remove();
			}
		}

		JSONArray transProbJson = JSONArray.fromObject(hmmBtsd.getTransProbability());
		double[][] oldTransProb = new double[transProbJson.size()][transProbJson.size()];
		for(int rowIndex = 0;rowIndex < transProbJson.size();rowIndex++){
			JSONArray row = transProbJson.getJSONArray(rowIndex);
			for(int colIndex = 0;colIndex < row.size();colIndex++){
				oldTransProb[rowIndex][colIndex] = row.getDouble(colIndex);
			}
		}

		int size = detaills.size();
		HiddenMarkovModel hmm = new HiddenMarkovModel(size);
		double[][] transProb = new double[size][size];
		for(int fromIndex = 0;fromIndex < size;fromIndex++){
			HmmBtsdDetail hmmBtsdDetail = detaills.get(fromIndex);
			Integer stateIndex = hmmBtsdDetail.getStateIndex();
			double pi = JSONArray.fromObject(hmmBtsdDetail.getPi()).getDouble(0);
			hmm.setPi(fromIndex, pi);
			JSONArray jsonMeans = JSONArray.fromObject(hmmBtsdDetail.getMean());
			double[] means = new double[jsonMeans.size()];
			for(int meanIndex = 0;meanIndex < jsonMeans.size();meanIndex++){
				means[meanIndex] = jsonMeans.getDouble(meanIndex);
			}
			JSONArray jsonCovs = JSONArray.fromObject(hmmBtsdDetail.getCovariance());
			int dimSize = HmmDimension.getDimensions().size();
			double[][] covs = new double[dimSize][dimSize];
			for(int rowIndex = 0;rowIndex < jsonCovs.size();rowIndex++){
				JSONArray row = jsonCovs.getJSONArray(rowIndex);
				for(int colIndex = 0;colIndex < row.size();colIndex++){
					covs[rowIndex][colIndex] = row.getDouble(colIndex);
				}
			}
			hmm.setStateDistribution(fromIndex, new ContinousDistribution(means, covs));
			//
			for(int toIndex = 0;toIndex < size;toIndex++){
				HmmBtsdDetail toHmmBtsdDetail = detaills.get(toIndex);
				transProb[fromIndex][toIndex] = oldTransProb[hmmBtsdDetail.getStateIndex()][toHmmBtsdDetail.getStateIndex()];
			}
			//
			result.getStateMap().put(fromIndex, hmmBtsdDetail.getStateIndex());
		}
		hmm.setTransitionProbability(transProb);

		result.setHmm(hmm);
		return result;
	}

}