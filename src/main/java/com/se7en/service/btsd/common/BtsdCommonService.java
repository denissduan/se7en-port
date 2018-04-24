package com.se7en.service.btsd.common;

import com.se7en.common.Format;
import com.se7en.common.util.BeanUtil;
import com.se7en.common.util.ContextUtil;
import com.se7en.common.util.DateUtils;
import com.se7en.service.AbstractEjbService;
import com.se7en.service.CommonDaoService;
import com.se7en.service.btsd.BtsdDayLineService;
import com.se7en.service.btsd.BtsdHisTradeService;
import com.se7en.service.btsd.BtsdHourLineService;
import com.se7en.service.btsd.BtsdMin5LineService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Service
public class BtsdCommonService extends CommonDaoService {

    /**
     * 获取蜡烛图数据
     * @param type
     * @param coin
     * @param startDate
     * @param endDate
     * @return
     */
    public CandleStickResult getCandleStickData(int type,String coin,String startDate,String endDate){
        CandleStickResult result = new CandleStickResult();
        try {
            AbstractEjbService abstractEjbService = null;
            StringBuilder hql = new StringBuilder(" from ");
            List<Object> params = new LinkedList<>();

            if(HmmType.isLegal(type) == false){
                return CandleStickResult.EMPTY_ENTITY;
            }
            switch (type){
                case HmmType.DAY:
                    hql.append(" BtsdDayLine ");
                    abstractEjbService = ContextUtil.getBean(BtsdDayLineService.class);
                    break;
                case HmmType.HOUR:
                    hql.append(" BtsdHourLine ");
                    abstractEjbService = ContextUtil.getBean(BtsdHourLineService.class);
                    break;
                case HmmType.MIN5:
                    hql.append(" BtsdMin5Line ");
                    abstractEjbService = ContextUtil.getBean(BtsdMin5LineService.class);
                    break;
                case HmmType.HIS_TRADE:
                    hql.append(" BtsdHisTrade ");
                    abstractEjbService = ContextUtil.getBean(BtsdHisTradeService.class);
                    break;
            }
            //必要条件
            if(StringUtils.isEmpty(coin) == true){
                return CandleStickResult.EMPTY_ENTITY;
            }

            hql.append(" where coin.id = ? ");
            params.add(coin);

            if(StringUtils.isNotEmpty(startDate)){
                hql.append(" and date >= ? ");
                Date dtStart = DateUtils.parseDate(startDate, Format.DATETIME);
                params.add(dtStart.getTime());
            }

            if(StringUtils.isNotEmpty(endDate)){
                hql.append(" and date <= ? ");
                Date dtEnd = DateUtils.parseDate(endDate, Format.DATETIME);
                params.add(dtEnd.getTime());
            }

            hql.append(" order by date asc ");

            List<Object> datas = abstractEjbService.queryHql(hql.toString(), params.toArray());

            //注入屬性
            List<CandleStickResult.CandleStickData> candleStickDataList = result.getData();
            dataLoop:
            for(int index = 0,size = datas.size();index < size;index++){
                Object data = datas.get(index);
                CandleStickResult.CandleStickData candleStickData = result.new CandleStickData();
//                private String open;
//                private String high;
//                private String low;
//                private String close;
//                private String x;
//                private String volume;
//                private String date;
//                private String color;
//                private String tooltext;
                BeanUtil.setProperty(candleStickData, "open", BeanUtil.getProperty(data, "kp"));
                BeanUtil.setProperty(candleStickData, "high", BeanUtil.getProperty(data, "zg"));
                BeanUtil.setProperty(candleStickData, "low", BeanUtil.getProperty(data, "zd"));
                BeanUtil.setProperty(candleStickData, "close", BeanUtil.getProperty(data, "sp"));
                BeanUtil.setProperty(candleStickData, "x", String.valueOf(index + 1));
                BeanUtil.setProperty(candleStickData, "volume", BeanUtil.getProperty(data, "cjl"));
                BeanUtil.setProperty(candleStickData, "date", BeanUtil.getProperty(data, "date"));

                //时间校验
                if(index > 0){
                    boolean isComplete = checkDateComplete(type, datas, index);
                }

                candleStickDataList.add(candleStickData);

                //标记关键信息
                int center = index;
                if(center >= 5 && center <= datas.size() - 6){
                    //校验数据完整性
                    List<Object> completeList = datas.subList(center - 5, center + 6);
                    for(int checkIndex = 1;checkIndex < completeList.size();checkIndex++){
                        boolean completeFlag = checkDateComplete(type, datas, checkIndex);
                        if(completeFlag == false){
                            log.error("---------数据校验不完整");
                            continue dataLoop;
                        }
                    }
                    //判断最高
                    int maxIndex = getRightIndex(center, datas, true);
                    if(maxIndex == index){
                        candleStickData.setColor("#008ee4");
                        candleStickData.setPoint("seil");
                    }
                    //判断最低
                    int minIndex = getRightIndex(center, datas, false);
                    if(minIndex == index){
                        candleStickData.setColor("#9b59b6");
                        candleStickData.setPoint("buy");
                    }
                }

                //消息提示
                StringBuilder toolText = new StringBuilder();
                toolText.append("Open:" + candleStickData.getOpen());
                toolText.append("{br}High:" + candleStickData.getHigh());
                toolText.append("{br}Low:" + candleStickData.getLow());
                toolText.append("{br}Close:" + candleStickData.getClose());
                toolText.append("{br}Volume:" + candleStickData.getVolume());
                toolText.append("{br}Date:" + DateFormatUtils.format(Long.parseLong(candleStickData.getDate()), Format.DATETIME));
                toolText.append("{br}Point:" + candleStickData.getPoint());

                candleStickData.setTooltext(toolText.toString());

            }

            if(candleStickDataList.size() == 0){
                return CandleStickResult.EMPTY_ENTITY;
            }

            //横轴数据
            List<CandleStickResult.AxisData> axisDatas = result.getxAxis();
            int limit = candleStickDataList.size() / 10;
            for(int index = 0;index < 10;index++){
                CandleStickResult.AxisData axisData = result.new AxisData();
                int x = index * limit;
                CandleStickResult.CandleStickData candleStickData = candleStickDataList.get(x);
                long date = Long.parseLong(candleStickData.getDate());
                axisData.setLabel(DateFormatUtils.format(date, Format.DATETIME));
                axisData.setX(candleStickData.getX());

                axisDatas.add(axisData);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 校验日期完整性
     * @param type
     * @param datas
     * @param index
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private boolean checkDateComplete(int type, List<Object> datas, int index) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object data = datas.get(index);
        Object lastData = datas.get(index - 1);
        long curDateMills = Long.parseLong(BeanUtil.getProperty(data, "date"));
        long lastDateMills = Long.parseLong(BeanUtil.getProperty(lastData, "date"));
        Date afterAddDate = null;
        switch (type){
            case HmmType.DAY:
                 afterAddDate = DateUtils.addDays(new Date(lastDateMills), 1);
                break;
            case HmmType.HOUR:
                afterAddDate = DateUtils.addHours(new Date(lastDateMills), 1);
                break;
            case HmmType.MIN5:
                afterAddDate = DateUtils.addMinutes(new Date(lastDateMills), 5);
                break;
            case HmmType.HIS_TRADE:

                break;
        }
        if(curDateMills > afterAddDate.getTime()){
            String lastDate = DateFormatUtils.format(lastDateMills, Format.DATETIME);
            String curDate = DateFormatUtils.format(curDateMills, Format.DATETIME);
            log.error("-------------------------数据不完整,数据类型=" + type + ",上条记录日期=" + lastDate + ",当前数据日期=" + curDate);
            return false;
        }
        return true;
    }

    private int getRightIndex(int center, List<Object> datas, boolean isMax) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object centerData = datas.get(center);

        List<Object> leftList = datas.subList(center - 5, center);
        List<Object> rightList = datas.subList(center + 1, center + 6);

        double maxOrMinSp = 0;
        double centerSp = Double.parseDouble(BeanUtil.getProperty(centerData, "sp"));
        if(isMax == true){
            maxOrMinSp = Double.MIN_VALUE;
        }else{
            maxOrMinSp = Double.MAX_VALUE;
        }
        int maxOrMinIndex = -1;
        for(int leftIndex = 0;leftIndex < leftList.size();leftIndex++){
            Object leftData = leftList.get(leftIndex);
            double val = Double.parseDouble(BeanUtil.getProperty(leftData, isMax ? "zg" : "zd"));
            boolean flag = isMax ? (val >= maxOrMinSp) : (val <= maxOrMinSp);
            if(flag == true){
                maxOrMinSp = val;
                maxOrMinIndex = center - (leftList.size() - leftIndex);
            }
            //收盘必须大于/小于所有
//            double leftSp = Double.parseDouble(BeanUtil.getProperty(leftData, "sp"));
//            boolean spFlag = isMax ? (centerSp > leftSp) : (centerSp < leftSp);
//            if(spFlag == false){
//                return maxOrMinIndex;
//            }
        }
        double centerVal = Double.parseDouble(BeanUtil.getProperty(centerData, isMax ? "zg" : "zd"));
        boolean centerFlag = isMax ? (centerVal >= maxOrMinSp) : (centerVal <= maxOrMinSp);
        if(centerFlag == true){
            maxOrMinSp = centerVal;
            maxOrMinIndex = center;
        }
        for(int rightIndex = 0;rightIndex < rightList.size();rightIndex++){
            Object rightData = rightList.get(rightIndex);
            double val = Double.parseDouble(BeanUtil.getProperty(rightData, isMax ? "zg" : "zd"));
            boolean flag = isMax ? (val >= maxOrMinSp) : (val <= maxOrMinSp);
            if(flag == true){
                maxOrMinSp = val;
                maxOrMinIndex = center + (rightIndex + 1);
            }
            //收盘必须大于/小于所有
//            double rightSp = Double.parseDouble(BeanUtil.getProperty(rightData, "sp"));
//            boolean spFlag = isMax ? (centerSp > rightSp) : (centerSp < rightSp);
//            if(spFlag == false){
//                return maxOrMinIndex;
//            }
        }

        return maxOrMinIndex;
    }

}
