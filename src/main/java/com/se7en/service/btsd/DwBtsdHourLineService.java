package com.se7en.service.btsd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.annotation.Resource;
import com.se7en.common.Format;
import com.se7en.common.ai.ml.algorithm.IncreaseScaleKMean;
import com.se7en.dao.btsd.IBtsdHourLineDao;
import com.se7en.dao.btsd.impl.HmmBtsdDao;
import com.se7en.model.btsd.*;
import com.se7en.service.btsd.common.HmmDimension;
import com.se7en.service.btsd.common.HmmResult;
import com.se7en.service.btsd.common.HmmStateWrapper;
import com.se7en.service.btsd.common.HmmType;
import net.sf.json.JSONArray;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.encog.ml.hmm.HiddenMarkovModel;
import org.encog.ml.hmm.distributions.ContinousDistribution;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.dao.btsd.IDwBtsdHourLineDao;

/**
 * * DwBtsdHourLine service
 */
@Service
public class DwBtsdHourLineService extends AbstractDwLineService<DwBtsdHourLine> {

    private final int[] DIMENSIONS = new int[3];

    @Resource
    private IDwBtsdHourLineDao dao;

    @Resource
    private IBtsdHourLineDao btsdHourLineDao;

    @Resource
    private BtsdCoinService coinService;

    @Resource
    private DwBtsdMin5LineService dwBtsdMin5LineService;

    @Resource
    private HmmBtsdDao hmmBtsdDao;

    @Override
    public IDwBtsdHourLineDao getDao() {

        return dao;
    }

    public List<DwBtsdHourLine> queryAll() {

        return dao.queryAll();
    }

    public PageView pageViewQueryS(DwBtsdHourLine dwBtsdHourLine, QueryView view) {
        List<Object> params = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder(" select ");
        sql.append(" dwbtsdhourline.id ");
        sql.append(" ,dwbtsdhourline.id as dwbtsdhourline_id ");
        sql.append(" ,dwbtsdhourline.date as dwbtsdhourline_date ");
        sql.append(" ,dwbtsdhourline.datefmt as dwbtsdhourline_datefmt ");
        sql.append(" ,dwbtsdhourline.kp as dwbtsdhourline_kp ");
        sql.append(" ,dwbtsdhourline.zg as dwbtsdhourline_zg ");
        sql.append(" ,dwbtsdhourline.zd as dwbtsdhourline_zd ");
        sql.append(" ,dwbtsdhourline.sp as dwbtsdhourline_sp ");
        sql.append(" from dw_btsd_hour_line dwbtsdhourline ");

        sql.append(" where 1 = 1 ");
        if (dwBtsdHourLine.getId() != null) {
            sql.append(" and dwbtsdhourline.id = ? ");
            params.add(dwBtsdHourLine.getId());
        }
        if (StringUtil.isNotBlank(dwBtsdHourLine.getDatefmt())) {
            sql.append(" and dwbtsdhourline.datefmt like ? ");
            params.add("%" + dwBtsdHourLine.getDatefmt() + "%");
        }
        return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
    }

    public boolean processData() {
        boolean ret = false;
        Map<String, BtsdCoin> coins = coinService.queryAll2Map();

        for (Map.Entry<String, BtsdCoin> entry : coins.entrySet()) {
            String code = entry.getKey();
            BtsdCoin coin = entry.getValue();
            if (coin.getMk().toLowerCase().equals("cny") == false) {
                continue;
            }
            log.info("------------------------------------加工" + code + "," + coin.getName());
            //清空数据
            dao.execute("delete from dw_btsd_hour_line where coin_id = ?", new Object[]{code});
            Object[] param = {code};

            QueryView queryView = new QueryView();
            queryView.setCurPage(1);
            queryView.setLimit(PAGE_LIMIT);

            PageView pageView = getPageQueryService().pageQueryH(HQL_QUERY_BY_CODE, param, queryView, getDao());
            ret = saveProcessData(pageView);
            while (queryView.getCurPage() < pageView.getTotalPage()) {
                queryView.setCurPage(queryView.getCurPage() + 1);
                pageView = getPageQueryService().pageQueryH(HQL_QUERY_BY_CODE, param, queryView, getDao());
                ret = saveProcessData(pageView);
            }
        }

        log.info("--------------------------------------加工结束");
        return ret;
    }

    private boolean saveProcessData(PageView pageView) {
        log.info("----------------------加工第" + pageView.getCurPage() + "批,数据量=" + pageView.getTbody().size());
        List<BtsdHourLine> list = (List<BtsdHourLine>) pageView.getTbody();

        return saveProcessData(list);
    }

    private boolean saveProcessData(List<BtsdHourLine> list) {
        List<DwBtsdHourLine> saveList = new LinkedList<>();
        for (BtsdHourLine hourLine : list) {
            DwBtsdHourLine dwBtsdHourLine = new DwBtsdHourLine();
            dwBtsdHourLine.setCoin(hourLine.getCoin());
            dwBtsdHourLine.setDate(hourLine.getDate());
            dwBtsdHourLine.setDatefmt(hourLine.getDatefmt());
            dwBtsdHourLine.setCjl(hourLine.getCjl());
            dwBtsdHourLine.setKp(hourLine.getKp());
            dwBtsdHourLine.setSp(hourLine.getSp());
            dwBtsdHourLine.setZg(hourLine.getZg());
            dwBtsdHourLine.setZd(hourLine.getZd());
            //
            BigDecimal bdkp = new BigDecimal(String.valueOf(hourLine.getKp()));
            BigDecimal bdzg = new BigDecimal(String.valueOf(hourLine.getZg()));
            BigDecimal bdzd = new BigDecimal(String.valueOf(hourLine.getZd()));
            BigDecimal bdsp = new BigDecimal(String.valueOf(hourLine.getSp()));
            if (bdkp.doubleValue() == 0) {
                log.error(hourLine.getCoin().getName() + " 开盘值为0,id=" + hourLine.getId());
                continue;
            }
            dwBtsdHourLine.setZgscale(bdzg.subtract(bdkp).divide(bdkp, 6, RoundingMode.HALF_EVEN).doubleValue());
            dwBtsdHourLine.setZdscale(bdzd.subtract(bdkp).divide(bdkp, 6, RoundingMode.HALF_EVEN).doubleValue());
            dwBtsdHourLine.setSpscale(bdsp.subtract(bdkp).divide(bdkp, 6, RoundingMode.HALF_EVEN).doubleValue());
            if (dwBtsdHourLine.getZgscale() == 0 || dwBtsdHourLine.getZdscale() == 0 || dwBtsdHourLine.getSpscale() == 0) {
                log.info("----------------------比例值=0,bdkp=" + bdkp + ",bdzg=" + bdzg + ",bdzd=" + bdzd + ",bdsp=" + bdsp);
            }
            saveList.add(dwBtsdHourLine);
        }

        return dao.batchSave(saveList);
    }

    public void incrementScaleKMean() {
        List<DwBtsdHourLine> dwBtsdHourLines = dao.queryAll();
//		int size = dwBtsdHourLines.size();
        int rows = 13;
//		double[][] data = new double[dwBtsdHourLines.size()][1];
        double[][] data = new double[rows][1];
        /*for(int index = 0;index < 100;index++){
			DwBtsdHourLine dwBtsdHourLine = dwBtsdHourLines.get(index);
			System.out.println(dwBtsdHourLine.getSpscale());
		}*/
        for (int index = 0; index < rows; index++) {
            DwBtsdHourLine dwBtsdHourLine = dwBtsdHourLines.get(index);
//			data[index][0] = new BigDecimal(String.valueOf(dwBtsdHourLine.getSpscale())).multiply(new BigDecimal("100")).doubleValue();
            data[index][0] = dwBtsdHourLine.getSpscale();
//			data[index][1] = dwBtsdHourLine.getZdscale();
//			data[index][2] = dwBtsdHourLine.getZgscale();
        }

        IncreaseScaleKMean increaseScaleKMean = new IncreaseScaleKMean(data);
        increaseScaleKMean.processData();
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
        HmmStateWrapper wrapper = HmmStateWrapper.wrap(HmmType.HOUR, code);
        int stateSize = wrapper.getStates().size();
        int dimensionSize = HmmDimension.getDimensions().size();

        log.info("----------------------------,code=" + code + "," + coin.getName() + " hmm hour建模");
        List<DwBtsdHourLine> dwBtsdHourLines = dao.queryHql(HQL_QUERY_DW_BY_CODE, new Object[]{code});
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
        BtsdHmmState lastState = null;

        //收集cov,transCount集合
        for (DwBtsdHourLine dwBtsdHourLine : dwBtsdHourLines) {
            double spScale = dwBtsdHourLine.getSpscale();
            BtsdHmmState state = wrapper.valueOf(spScale);

            if(dwBtsdHourLine.getZgscale() == 0 && dwBtsdHourLine.getZdscale() == 0 && dwBtsdHourLine.getSpscale() == 0){
                continue;
            }
            double[] data = {dwBtsdHourLine.getZgscale(),dwBtsdHourLine.getZdscale(),dwBtsdHourLine.getSpscale()};

            stateDataMap.get(state.getNo()).add(data);              //并入集合

            piCounts[state.getNo()]++;                              //初始概率次数
            if (lastState != null) {                                //状态转换次数
                transCounts[lastState.getNo()][state.getNo()]++;
            }
            lastState = state;

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

            double[][] datas = getDataMatrix(stateDataMap.get(index));
            if (datas.length == 0) {
                log.info("------------stateFrom=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),数据为空");
                continue;
            }
            if(datas.length < datas[0].length + 1){
                continue;
            }
            log.info("index=" + index + ",datas=" + datas.length);
            //初始化概率
            BigDecimal bdTotalPi = new BigDecimal(String.valueOf(totalPi));
            BigDecimal bdPiCount = new BigDecimal(String.valueOf(piCounts[index]));
            pi[index] = bdPiCount.divide(bdTotalPi, 6, RoundingMode.HALF_EVEN).doubleValue();
            log.info("------------stateFrom=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),pi=" + pi[index]);

            //mean
            for (HmmDimension.DimensionEntry dimEntry : HmmDimension.getDimensions()) {
                int dimIndex = dimEntry.getIndex();
                double[] mean = getMeansArray(datas, dimIndex);
                double meanVal = StatUtils.mean(mean);
                means[index][dimIndex] = meanVal;
            }
            log.info("------------stateFrom=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),means=" + Arrays.toString(means[index]));
            //cov
            Covariance covariance = new Covariance(datas);
            RealMatrix matrix = covariance.getCovarianceMatrix();
            covs[index] = matrix.getData();
            log.info("------------stateFrom=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),cov=" + Arrays.deepToString(covs[index]));
            //转换概率
            BigDecimal bdTotalTran = new BigDecimal(String.valueOf(totalTrans[index]));
            long[] row = transCounts[index];
            for (BtsdHmmState stateTo : wrapper.getStates()) {
                BigDecimal bdTransCount = new BigDecimal(String.valueOf(row[stateTo.getNo()]));
                transProbability[stateFrom.getNo()][stateTo.getNo()] = bdTransCount.divide(bdTotalTran, 6, RoundingMode.HALF_EVEN).doubleValue();
            }
            log.info("------------stateFrom=" + stateFrom.getName() + ",bound=(" + stateFrom.getMinBound() + "," + stateFrom.getMaxBound() + "),transProbability=" + Arrays.toString(transProbability[stateFrom.getNo()]));
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

            log.info("index=" + index + ",name=" + state.getName());
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
        hmmBtsd.setType(HmmType.HOUR);
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

}