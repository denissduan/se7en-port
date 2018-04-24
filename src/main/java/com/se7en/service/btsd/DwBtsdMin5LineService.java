package com.se7en.service.btsd;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.annotation.Resource;

import com.se7en.common.Format;
import com.se7en.model.btsd.*;
import com.se7en.service.btsd.common.HmmDimension;
import com.se7en.service.btsd.common.HmmResult;
import com.se7en.service.btsd.common.HmmStateWrapper;
import com.se7en.service.btsd.common.HmmType;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.encog.ml.hmm.HiddenMarkovModel;
import org.encog.ml.hmm.distributions.ContinousDistribution;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.dao.btsd.IDwBtsdMin5LineDao;

/**
 *  * DwBtsdMin5Line service
 */
@Service
public class DwBtsdMin5LineService extends AbstractDwLineService<DwBtsdMin5Line> {

	@Resource
	private IDwBtsdMin5LineDao dao;

	@Resource
	private BtsdCoinService coinService;

	@Resource
	private DwBtsdHourLineService dwBtsdHourLineService;
	
	@Override
	public IDwBtsdMin5LineDao getDao() {
	
		return dao;
	}
	
	public List<DwBtsdMin5Line> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS(DwBtsdMin5Line dwBtsdMin5Line,QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" dwbtsdmin5line.id ");
		sql.append(" ,dwbtsdmin5line.id as dwbtsdmin5line_id ");
		sql.append(" ,dwbtsdmin5line.date as dwbtsdmin5line_date ");
		sql.append(" ,dwbtsdmin5line.datefmt as dwbtsdmin5line_datefmt ");
		sql.append(" ,dwbtsdmin5line.kp as dwbtsdmin5line_kp ");
		sql.append(" ,dwbtsdmin5line.zg as dwbtsdmin5line_zg ");
		sql.append(" ,dwbtsdmin5line.zd as dwbtsdmin5line_zd ");
		sql.append(" ,dwbtsdmin5line.sp as dwbtsdmin5line_sp ");
		sql.append(" from dw_btsd_min5_line dwbtsdmin5line ");

		sql.append(" where 1 = 1 ");
		if(dwBtsdMin5Line.getId() != null){
			sql.append(" and dwbtsdmin5line.id = ? ");
			params.add(dwBtsdMin5Line.getId());
		}
		if(StringUtil.isNotBlank(dwBtsdMin5Line.getDatefmt())){
			sql.append(" and dwbtsdmin5line.datefmt like ? ");
			params.add("%" + dwBtsdMin5Line.getDatefmt() + "%");
		}
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

	public boolean processData(){
		boolean ret = false;
		Map<String, BtsdCoin> coins = coinService.queryAll2Map();

		for(Map.Entry<String,BtsdCoin> entry : coins.entrySet()) {
			String code = entry.getKey();
			BtsdCoin coin = entry.getValue();
			if(coin.getMk().toLowerCase().equals("cny") == false){
				continue;
			}
			log.info("------------------------------------加工" + code + "," + coin.getName());
			//清空数据
			dao.execute("delete from dw_btsd_min5_line where coin_id = ?", new Object[]{code});
			String hql = " from BtsdMin5Line where coin.id = ? order by date asc";
			Object[] param = {code};

			QueryView queryView = new QueryView();
			queryView.setCurPage(1);
			queryView.setLimit(PAGE_LIMIT);

			PageView pageView = getPageQueryService().pageQueryH(hql, param,queryView, getDao());
			ret = saveProcessData(pageView);
			while(queryView.getCurPage() < pageView.getTotalPage()){
				queryView.setCurPage(queryView.getCurPage() + 1);
				pageView = getPageQueryService().pageQueryH(hql, param,queryView, getDao());
				ret = saveProcessData(pageView);
			}
		}

		log.info("--------------------------------------加工结束");
		return ret;
	}

	private boolean saveProcessData(PageView pageView){
		log.info("----------------------加工第" + pageView.getCurPage() + "批,数据量=" + pageView.getTbody().size());
		List<BtsdMin5Line> list = (List<BtsdMin5Line>)pageView.getTbody();

		return saveProcessData(list);
	}

	private boolean saveProcessData(List<BtsdMin5Line> list) {
		List<DwBtsdMin5Line> saveList = new LinkedList<>();
		for(BtsdMin5Line hourLine : list){
			DwBtsdMin5Line dwBtsdMin5Line = new DwBtsdMin5Line();
			dwBtsdMin5Line.setCoin(hourLine.getCoin());
			dwBtsdMin5Line.setDate(hourLine.getDate());
			dwBtsdMin5Line.setDatefmt(hourLine.getDatefmt());
			dwBtsdMin5Line.setCjl(hourLine.getCjl());
			dwBtsdMin5Line.setKp(hourLine.getKp());
			dwBtsdMin5Line.setSp(hourLine.getSp());
			dwBtsdMin5Line.setZg(hourLine.getZg());
			dwBtsdMin5Line.setZd(hourLine.getZd());
			//
			BigDecimal bdkp = new BigDecimal(String.valueOf(hourLine.getKp()));
			BigDecimal bdzg = new BigDecimal(String.valueOf(hourLine.getZg()));
			BigDecimal bdzd = new BigDecimal(String.valueOf(hourLine.getZd()));
			BigDecimal bdsp = new BigDecimal(String.valueOf(hourLine.getSp()));
			if(bdkp.doubleValue() == 0){
				log.error(hourLine.getCoin().getName() + " 开盘值为0,id=" + hourLine.getId());
				continue;
			}
			dwBtsdMin5Line.setZgscale(bdzg.subtract(bdkp).divide(bdkp, 6, RoundingMode.HALF_EVEN).doubleValue());
			dwBtsdMin5Line.setZdscale(bdzd.subtract(bdkp).divide(bdkp, 6, RoundingMode.HALF_EVEN).doubleValue());
			dwBtsdMin5Line.setSpscale(bdsp.subtract(bdkp).divide(bdkp, 6, RoundingMode.HALF_EVEN).doubleValue());
			if(dwBtsdMin5Line.getZgscale() == 0 || dwBtsdMin5Line.getZdscale() == 0 || dwBtsdMin5Line.getSpscale() == 0){
				log.info("----------------------比例值=0,bdkp=" + bdkp + ",bdzg=" + bdzg + ",bdzd=" + bdzd + ",bdsp=" + bdsp);
			}
			saveList.add(dwBtsdMin5Line);
		}

		return dao.batchSave(saveList);
	}

	/**
	 * 生成hmm模型
	 *
	 * @return
	 */
	public HmmResult processHmm(BtsdCoin coin) {
		HmmResult result = new HmmResult();
		boolean flag = false;

		String code = coin.getId();
		HmmStateWrapper wrapper = HmmStateWrapper.wrap(HmmType.MIN5, code);
		int stateSize = wrapper.getStates().size();
		int dimensionSize = HmmDimension.getDimensions().size();

		log.info("----------------------------,code=" + code + "," + coin.getName() + " hmm hour建模");
		List<DwBtsdHourLine> dwBtsdHourLines = dwBtsdHourLineService.queryHql(HQL_QUERY_DW_BY_CODE, new Object[]{code});
		if (dwBtsdHourLines.size() == 0) {
			log.error("-------------------code=" + coin.getId() + ",name=" + coin.getName() + ",数据为空");
			return result;
		}

		Map<Integer, List<double[]>> stateDataMap = new HashMap<>(stateSize);
		for (BtsdHmmState state : wrapper.getStates()) {
			stateDataMap.put(state.getNo(), new LinkedList<double[]>());
		}
		//待计算单位变量
		double[] pi = new double[stateSize];
		long[] piCounts = new long[stateSize];
		double[][] means = new double[stateSize][dimensionSize];
		double[][][] covs = new double[stateSize][dimensionSize][dimensionSize];
		double[][] transProbability = new double[stateSize][stateSize];
		long[][] transCounts = new long[stateSize][stateSize];
		BtsdHmmState lastTrend = null;

		//收集cov,transCount集合
		for (DwBtsdHourLine dwBtsdHourLine : dwBtsdHourLines) {
			double spScale = dwBtsdHourLine.getSpscale();
			BtsdHmmState scaleTrend = wrapper.valueOf(spScale);
			//设置日期范围
			long startMills = dwBtsdHourLine.getDate();
			Date startDate = new Date(startMills);
			Date endDate = DateUtils.addHours(startDate, 1);
			long endMills = endDate.getTime();

			Set<Long> leageDateRange = getLeageDateRange(startMills, endMills);

			//先判断缓存是否有
			String key = code + "_" + startMills + "_" + endMills;
			Element element = hmmCache.get(key);
			if (element == null) {
				String hql = "from DwBtsdMin5Line where coin.id = ? and date >= ? and date < ? order by date asc";
				Object[] params = {code, startMills, endMills};
				List<DwBtsdMin5Line> dwBtsdMin5Lines = queryHql(hql, params);
				element = new Element(key, dwBtsdMin5Lines);
				hmmCache.put(element);
			}
			List<DwBtsdMin5Line> dwBtsdMin5Lines = (List<DwBtsdMin5Line>) hmmCache.get(key).getObjectValue();

			Set<Long> lineRange = new LinkedHashSet<>();
			for (DwBtsdMin5Line dwBtsdMin5Line : dwBtsdMin5Lines) {
				lineRange.add(dwBtsdMin5Line.getDate());
			}
			//校验数据完整性
			if (lineRange.equals(leageDateRange) == false) {
				List<String> dates = new LinkedList<>();
				for (int index = 0; index < dwBtsdMin5Lines.size(); index++) {
					dates.add(DateFormatUtils.format(dwBtsdMin5Lines.get(index).getDate(), Format.DATETIME));
				}
				log.info("---------------数据不完整,代码=" + code + ",开始时间=" + DateFormatUtils.format(startMills, Format.DATETIME) + ", " + dates.toString());
				continue;
			}

			List<double[]> dataSeq = getDataSequence(dwBtsdMin5Lines);

			stateDataMap.get(scaleTrend.getNo()).addAll(dataSeq);        //并入集合

			piCounts[scaleTrend.getNo()]++;                //初始概率次数
			if (lastTrend != null) {                                //状态转换次数
				transCounts[lastTrend.getNo()][scaleTrend.getNo()]++;
			}
			lastTrend = scaleTrend;

		}
		log.info("------------收集cov,transCount数据集,code=" + code + "," + coin.getName());

		//计算总数
		long totalPi = 0;
		for (long piCount : piCounts) {
			totalPi += piCount;
		}

		//遍历每个状态计算mean,cov
		for (BtsdHmmState stateFrom : wrapper.getStates()) {
			int index = stateFrom.getNo();

			long[] totalTrans = new long[stateSize];
			long[] rows = transCounts[index];
			long totalTran = 0;
			for (long transCount : rows) {
				totalTran += transCount;
			}
			totalTrans[index] = totalTran;

			//初始化概率
			BigDecimal bdTotalPi = new BigDecimal(String.valueOf(totalPi));
			BigDecimal bdPiCount = new BigDecimal(String.valueOf(piCounts[index]));
			pi[index] = bdPiCount.divide(bdTotalPi, 6, RoundingMode.HALF_EVEN).doubleValue();
			log.info("------------state=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),pi=" + pi[index]);
			//mean
			double[][] datas = getDataMatrix(stateDataMap.get(index));
			if (datas.length == 0) {
				log.info("------------state=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),数据为空");
				continue;
			}
			for (HmmDimension.DimensionEntry dimEntry : HmmDimension.getDimensions()) {
				int dimIndex = dimEntry.getIndex();
				double[] mean = getMeansArray(datas, dimIndex);
				double meanVal = StatUtils.mean(mean);
				means[index][dimIndex] = meanVal;
			}
			log.info("------------state=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),means=" + Arrays.toString(means[index]));
			//cov
			Covariance covariance = new Covariance(datas);
			RealMatrix matrix = covariance.getCovarianceMatrix();
			covs[index] = matrix.getData();
			log.info("------------state=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),cov=" + Arrays.deepToString(covs[index]));
			//转换概率
			BigDecimal bdTotalTran = new BigDecimal(String.valueOf(totalTrans[index]));
			long[] row = transCounts[index];
			for (BtsdHmmState stateTo : wrapper.getStates()) {
				BigDecimal bdTransCount = new BigDecimal(String.valueOf(row[stateTo.getNo()]));
				transProbability[stateFrom.getNo()][stateTo.getNo()] = bdTransCount.divide(bdTotalTran, 6, RoundingMode.HALF_EVEN).doubleValue();
			}
			log.info("------------state=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),transProbability=" + Arrays.toString(transProbability[stateFrom.getNo()]));
		}
		log.info("------------计算mean,cov,pi,transProbability,code=" + code + "," + coin.getName());

		//生成hmm
		HiddenMarkovModel hmm = new HiddenMarkovModel(stateSize);
		for (BtsdHmmState state : wrapper.getStates()) {
			int index = state.getNo();
			if (pi[index] == 0) {
				log.info("------------state=" + state.getName() + ",bound=(" + state.getMinBound() + "," + state.getMaxBound() + "),数据为空");
				continue;
			}
			hmm.setPi(index, pi[index]);
			hmm.setStateDistribution(index, new ContinousDistribution(means[index], covs[index]));
		}
		hmm.setTransitionProbability(transProbability);
		log.info("------------生成hmm,code=" + code + "," + coin.getName() + ",stateSize=" + stateSize + ",pi=" + Arrays.toString(pi) + ",means=" + Arrays.toString(means) + ",covs=" + Arrays.deepToString(covs) + ",transProbability=" + Arrays.deepToString(transProbability));

		result.setHmm(hmm);

		//入库hmm
		HmmBtsd hmmBtsd = new HmmBtsd();
		hmmBtsd.setCoin(coin);
		//日期范围
		long startDate = dwBtsdHourLines.get(0).getDate();
		long endDate = dwBtsdHourLines.get(dwBtsdHourLines.size() - 1).getDate();
		hmmBtsd.setStartDate(startDate);
		hmmBtsd.setStartDatefmt(DateFormatUtils.format(startDate, Format.DATETIME));
		hmmBtsd.setEndDate(endDate);
		hmmBtsd.setEndDatefmt(DateFormatUtils.format(endDate, Format.DATETIME));

		hmmBtsd.setStateSize(stateSize);
		hmmBtsd.setTransProbability(JSONArray.fromObject(transProbability).toString());
		hmmBtsd.setType(HmmType.MIN5);
		for (BtsdHmmState state : wrapper.getStates()) {
			int index = state.getNo();

			HmmBtsdDetail detail = new HmmBtsdDetail();
			detail.setHmm(hmmBtsd);
			detail.setName(state.getName());
			detail.setMinBound(state.getMinBound());
			detail.setMaxBound(state.getMaxBound());
			detail.setStateIndex(index);
			detail.setPi(JSONArray.fromObject(pi[index]).toString());
			detail.setMean(JSONArray.fromObject(means[index]).toString());
			detail.setCovariance(JSONArray.fromObject(covs[index]).toString());

			hmmBtsd.getDetails().add(detail);
		}
		flag = saveHmm(hmmBtsd);
		log.info("------------入库hmm,code=" + code + "," + coin.getName() + ",flag=" + flag);
		if (flag == false) {
			log.error("---------------保存hmm失败,code=" + code + ",开始时间=" + hmmBtsd.getStartDatefmt() + ",结束时间=" + hmmBtsd.getEndDatefmt());
			return result;
		}

		result.setSuccess(flag);
		return result;
	}

	public List<double[]> getDataSequence(List<DwBtsdMin5Line> dwBtsdMin5Lines) {
		int size = dwBtsdMin5Lines.size();
		List<double[]> dataSeq = new LinkedList<>();
		for (int index = 0; index < size; index++) {
			DwBtsdMin5Line dwBtsdMin5Line = dwBtsdMin5Lines.get(index);
			double[] data = new double[3];
			data[0] = dwBtsdMin5Line.getZgscale();
			data[1] = dwBtsdMin5Line.getZdscale();
			data[2] = dwBtsdMin5Line.getSpscale();

			dataSeq.add(data);
		}

		return dataSeq;
	}

}