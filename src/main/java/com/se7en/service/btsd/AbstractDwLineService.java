package com.se7en.service.btsd;

import com.se7en.common.EhCache;
import com.se7en.common.util.DateUtils;
import com.se7en.model.btsd.DwBtsdMin5Line;
import com.se7en.model.btsd.HmmBtsd;
import com.se7en.service.AbstractEjbService;
import com.se7en.service.btsd.common.HmmDimension;
import net.sf.ehcache.Cache;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/7/10.
 */
public abstract class AbstractDwLineService<T> extends AbstractEjbService<T> {

    @Resource
    protected HmmBtsdService hmmBtsdService;

    protected final int PAGE_LIMIT = 5000;

    protected final String HQL_QUERY_BY_CODE = "from BtsdHourLine where coin.id = ? order by date asc";

    protected final String HQL_QUERY_DW_BY_CODE = "from DwBtsdHourLine where coin.id = ? order by date asc";

    protected Cache hmmCache = EhCache.getInstance().getHmmCache();

    public double[][] getDataMatrix(List<double[]> datas) {
        double[][] ret = new double[datas.size()][HmmDimension.getDimensions().size()];
        for (int index = 0; index < datas.size(); index++) {
            double[] data = datas.get(index);
            ret[index] = data;
        }

        return ret;
    }

    public double[] getMeansArray(double[][] matrix, int colIndex) {
        int size = matrix.length;
        double[] ret = new double[size];

        for (int index = 0; index < size; index++) {
            double[] row = matrix[index];
            ret[index] = row[colIndex];
        }

        return ret;
    }

    public Set<Long> getLeageDateRange(long startMills, long endMills) {
        Set<Long> ret = new LinkedHashSet<>();

        long curMills = startMills;
        while (curMills < endMills) {
            ret.add(curMills);
            Date date = new Date(curMills);
            date = DateUtils.addMinutes(date, 5);

            curMills = date.getTime();
        }

        return ret;
    }

    public boolean saveHmm(HmmBtsd hmmBtsd) {
        boolean flag;
        String hql = "from HmmBtsd where type = ? and coin.id = ? and startDate = ? and endDate = ?";
        Object[] params = {hmmBtsd.getType(), hmmBtsd.getCoin().getId(), hmmBtsd.getStartDate(), hmmBtsd.getEndDate()};
        List<HmmBtsd> olds = hmmBtsdService.queryHql(hql, params);
        if (olds.size() > 0) {
            for (HmmBtsd old : olds) {
                hmmBtsdService.delete(old);
            }
        }

        flag = hmmBtsdService.saveOrUpdate(hmmBtsd);
        return flag;
    }

}
