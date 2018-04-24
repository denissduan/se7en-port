package com.se7en.common.util.btsd;

import com.se7en.common.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 17-5-21.
 * 比特时代接口工具类
 */
public final class BtsdHttpUtil {

    private static Logger log = LoggerFactory.getLogger(BtsdHttpUtil.class);

    private BtsdHttpUtil(){
    }

    /**
     * 5分钟分时数据
     * @param coinName
     * @return
     */
    public static BtsdHttpResult getMin5LineData(String coinName) {
        BtsdHttpResult ret = null;
        try {
            String url = "http://www.btc38.com/trade/getTrade5minLine.php?coinname=" + coinName.toUpperCase() + "&mk_type=CNY&n=" + Math.random();

            ret = wrapGetResult(url);
        } catch (Exception e) {
            log.error("5分钟分时数据异常" + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 小时分时数据
     * @param coinName
     * @return
     */
    public static BtsdHttpResult getHourLineData(String coinName) {
        BtsdHttpResult ret = null;
        try {
            String url = "http://www.btc38.com/trade/getTradeTimeLine.php?coinname=" + coinName.toUpperCase() + "&mk_type=CNY&n=" + Math.random();

            ret = wrapGetResult(url);
        } catch (Exception e) {
            log.error("小时分时数据异常" + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 日线数据
     * @param coinName
     * @return
     */
    public static BtsdHttpResult getDayLineData(String coinName) {
        BtsdHttpResult ret = null;
        try {
            String url = "http://www.btc38.com/trade/getTradeDayLine.php?coinname=" + coinName.toUpperCase() + "&mk_type=CNY&n=" + Math.random();

            ret = wrapGetResult(url);
        } catch (Exception e) {
            log.error("日线数据异常" + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 买1
     * 卖1
     * 成交记录
     * @return
     */
    public static BtsdHttpResult getTradeLine(String coinname){
        BtsdHttpResult ret = null;
        try {
            String url = "http://www.btc38.com/trade/getTradeList30.php?coinname=" + coinname.toUpperCase() + "&mk_type=CNY&n=" + Math.random() + "";

            ret = wrapGetResult(url);
        } catch (Exception e) {
            log.error("交易数据异常" + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private static BtsdHttpResult wrapGetResult(String url) {
        BtsdHttpResult ret = BtsdHttpResult.EMPTY_ENTITY;
        Object[] result = HttpClientUtil.get(url);
        if(result.length == 0){
            return ret;
        }
        String retJsonStr = (String)result[0];
        String code = (String)result[1];
        Map<String,String> map = (Map<String,String>)result[2];

        ret = BtsdHttpResult.valueOf(code, retJsonStr, map);
        return ret;
    }

}
