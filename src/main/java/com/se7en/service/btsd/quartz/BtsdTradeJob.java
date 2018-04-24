package com.se7en.service.btsd.quartz;


import com.se7en.common.util.StringUtil;
import com.se7en.common.util.btsd.BtsdDataUtil;
import com.se7en.common.util.btsd.BtsdHttpResult;
import com.se7en.common.util.btsd.BtsdHttpUtil;
import com.se7en.model.btsd.BtsdCoin;
import com.se7en.model.btsd.BtsdDayLine;
import com.se7en.model.btsd.BtsdHisTrade;
import com.se7en.service.btsd.BtsdCoinService;
import com.se7en.service.btsd.BtsdHisTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 17-5-21.
 */
public class BtsdTradeJob {

    private static Logger log = LoggerFactory.getLogger(BtsdTradeJob.class);

    @Resource
    private BtsdCoinService coinService;

    @Resource
    private BtsdHisTradeService hisTradeService;

    public void getTradeData(){
        try {
            long spendGetTrade = 0;
            long spendFilter = 0;
            long spendSave = 0;

            long startTradeData = System.currentTimeMillis();
            log.debug("---------------------------开始请求历史成交数据");
            Map<String, BtsdCoin> coins = coinService.queryAll2Map();
            List saveList = new LinkedList<>();
            for(Map.Entry<String,BtsdCoin> entry : coins.entrySet()){
                String code = entry.getKey();
                BtsdCoin coin = entry.getValue();
                BtsdHttpResult result = BtsdHttpUtil.getTradeLine(code);
                int errCount = 0;
                while(errCount++ < 10){
                    String lineStr = result.getData();
                    if("input_error3".equals(lineStr)){
                        log.debug("---------------------------开始请求历史成交数据,code=" + code + ",data=" + lineStr + ",错误次数=" + errCount);
                        result = BtsdHttpUtil.getTradeLine(code);
                        continue;
                    }
                    break;
                }
                if(StringUtil.isEmpty(result.getData()) || "input_error3".equals(result.getData())){
                    log.debug("---------------------------开始请求历史成交数据失败次数已满,跳过该请求,code=" + code + ",data=" + result.getData() + ",错误次数=" + errCount);
                    continue;
                }
                List<BtsdHisTrade> lineList = BtsdDataUtil.parseHisTrade(code, result.getData());
                //过滤数据库已存在的
                Iterator<BtsdHisTrade> ite = lineList.iterator();
                long startFilter = System.currentTimeMillis();
                while (ite.hasNext()){
                    BtsdHisTrade btsdHisTrade = ite.next();
                    if(hisTradeService.isExist(code, btsdHisTrade.getDate(), btsdHisTrade.getPrice(), btsdHisTrade.getVolume(), btsdHisTrade.getType()) == true){
                        log.debug("---------------------------历史成交数据已存在:" + btsdHisTrade.toString());
                        ite.remove();
                    }
                }
                long endFilter = System.currentTimeMillis();
                spendFilter = (endFilter - startFilter) / 1000;
                saveList.addAll(lineList);
            }
            long startSave = System.currentTimeMillis();
            boolean ret = hisTradeService.batchSave(saveList);
            long endSave = System.currentTimeMillis();
            if(ret == false){
                log.error("---------------------------历史成交数据批量保存失败");
            }
            long endTradeData = System.currentTimeMillis();
            spendGetTrade = (endTradeData - startTradeData) / 1000;
            spendSave = (endSave - startSave) / 1000;
            log.info("--------------------------------tradeJob 全部花时=" + spendGetTrade + "秒,过滤已存在花时=" + spendFilter + "秒,批量保存花时=" + spendSave + "秒");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
