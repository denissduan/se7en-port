package com.se7en.service.btsd.quartz;

import com.se7en.common.util.StringUtil;
import com.se7en.common.util.btsd.BtsdDataUtil;
import com.se7en.common.util.btsd.BtsdHttpResult;
import com.se7en.common.util.btsd.BtsdHttpUtil;
import com.se7en.model.btsd.BtsdCoin;
import com.se7en.model.btsd.BtsdDayLine;
import com.se7en.model.btsd.BtsdHourLine;
import com.se7en.model.btsd.BtsdMin5Line;
import com.se7en.service.btsd.BtsdCoinService;
import com.se7en.service.btsd.BtsdDayLineService;
import com.se7en.service.btsd.BtsdHourLineService;
import com.se7en.service.btsd.BtsdMin5LineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 17-5-21.
 */
public class BtsdLineJob {

    private static Logger log = LoggerFactory.getLogger(BtsdLineJob.class);

    private final int LIMIT_DAY = 1;
    private final int LIMIT_HOUR = 2;
    private final int LIMIT_MIN5 = 3;

    @Resource
    private BtsdCoinService coinService;

    @Resource
    private BtsdDayLineService coinDayLineService;

    @Resource
    private BtsdHourLineService coinHourLineService;

    @Resource
    private BtsdMin5LineService coinMin5LineService;

    public void getDayLineData(){
        getLineData(LIMIT_DAY);
    }

    public void getHourLineData(){
        getLineData(LIMIT_HOUR);
    }

    public void getMin5LineData(){
        getLineData(LIMIT_MIN5);
    }

    private void getLineData(int lineType) {
        log.debug("---------------------------开始请求数据,请求类型=" + lineType);
        Map<String, BtsdCoin> coins = coinService.queryAll2Map();
        List saveList = new LinkedList<>();
        for(Map.Entry<String,BtsdCoin> entry : coins.entrySet()){
            String code = entry.getKey();
            BtsdCoin coin = entry.getValue();
            BtsdHttpResult result = null;
            result = getBtsdHttpResult(lineType, code, result);
            int errCount = 0;
            while(errCount++ < 10){
                String lineStr = result.getData();
                if("input_error3".equals(lineStr)){
                    log.debug("---------------------------请求数据失败,请求类型=" + lineType + ",code=" + code + ",data=" + lineStr + ",错误次数=" + errCount);
                    result = getBtsdHttpResult(lineType, code, result);
                    continue;
                }
                break;
            }
            if(StringUtil.isEmpty(result.getData()) || "input_error3".equals(result.getData())){
                log.debug("---------------------------请求失败次数已满,跳过该请求,请求类型=" + lineType + ",code=" + code + ",data=" + result.getData() + ",错误次数=" + errCount);
                continue;
            }
            List lineList = Collections.emptyList();
            if(lineType == LIMIT_DAY){
                lineList = getDayList(code, result);
            }else if(lineType == LIMIT_HOUR){
                lineList = getHourList(code, result);
            }else if(lineType == LIMIT_MIN5){
                lineList = getMin5List(code, result);
            }
            saveList.addAll(lineList);
        }
        boolean ret = false;
        if(lineType == LIMIT_DAY){
            ret = coinDayLineService.batchSave(saveList);
        }else if(lineType == LIMIT_HOUR){
            ret = coinHourLineService.batchSave(saveList);
        }else if(lineType == LIMIT_MIN5){
            ret = coinMin5LineService.batchSave(saveList);
        }
        if(ret == false){
            log.error("---------------------------线数据批量保存失败");
        }
    }

    private BtsdHttpResult getBtsdHttpResult(int lineType, String code, BtsdHttpResult result) {
        if(lineType == LIMIT_DAY){
            result = BtsdHttpUtil.getDayLineData(code);
        }else if(lineType == LIMIT_HOUR){
            result = BtsdHttpUtil.getHourLineData(code);
        }else if(lineType == LIMIT_MIN5){
            result = BtsdHttpUtil.getMin5LineData(code);
        }
        return result;
    }

    private List getDayList(String code, BtsdHttpResult result) {
        List lineList = null;
        try {
            lineList = BtsdDataUtil.parseDayLine(code, result.getData());
            //过滤数据库已存在的
            Iterator<BtsdDayLine> ite = lineList.iterator();
            while (ite.hasNext()){
                BtsdDayLine btsdDayLine = ite.next();
                if(coinDayLineService.isExist(code, btsdDayLine.getDate()) == true){
                    log.debug("---------------------------线数据已存在:" + btsdDayLine.toString());
                    ite.remove();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        return lineList;
    }

    private List getHourList(String code, BtsdHttpResult result) {
        List lineList = null;
        try {
            lineList = BtsdDataUtil.parseHourLine(code, result.getData());
            //过滤数据库已存在的
            Iterator<BtsdHourLine> ite = lineList.iterator();
            while (ite.hasNext()){
                BtsdHourLine btsdHourLine = ite.next();
                if(coinHourLineService.isExist(code, btsdHourLine.getDate()) == true){
                    log.debug("---------------------------线数据已存在:" + btsdHourLine.toString());
                    ite.remove();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        return lineList;
    }

    private List getMin5List(String code, BtsdHttpResult result) {
        List lineList = null;
        try {
            lineList = BtsdDataUtil.parseMin5Line(code, result.getData());
            //过滤数据库已存在的
            Iterator<BtsdMin5Line> ite = lineList.iterator();
            while (ite.hasNext()){
                BtsdMin5Line btsdMin5Line = ite.next();
                if(coinMin5LineService.isExist(code, btsdMin5Line.getDate()) == true){
                    log.debug("---------------------------线数据已存在:" + btsdMin5Line.toString());
                    ite.remove();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        return lineList;
    }

}
