package com.se7en.common.util.btsd;

import com.se7en.model.btsd.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 16-9-19.
 */
public final class BtsdDataUtil {

    private final static String SUFFIX = "_balance";

    public final static String BUY_ORDER = "buyOrder";
    public final static String BUY_STR = "buyStr";
    public final static String SEIL_ORDER = "sellOrder";
    public final static String SEIL_STR = "sellStr";
    public final static String TRADE_ORDER = "trade";
    public final static String TRADE_STR = "tradeStr";

    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private BtsdDataUtil() {
    }

    /**
     * 最新行情
     *
     * @param dataStr
     * @return
     */
    public static Map<String, Map<String, String>> getNewstHQ2Map(String dataStr) {
        Map<String, Map<String, String>> ret = new HashMap<>();
        try {
            JSONObject json = JSONObject.fromObject(dataStr);
            Set<String> coinKeys = VCConstant.COIN_MAP.keySet();
            for (String key : coinKeys) {
                String zxjKey = key + VCConstant.BTSD_SUFFIX;
                if(json.has(zxjKey)){
                    double zxj = json.getDouble(zxjKey);
                    double jkj = json.getDouble(key + VCConstant.BTSD_SUFFIX + "_24h");
                    double je = json.getDouble(key + VCConstant.BTSD_SUFFIX + "_vol") / 10000;
                    String zdf = String.format("%.2f", ((zxj - jkj) / jkj) * 100) + "%";
                    String jeStr = String.format("%.2f", je) + "万";
                    String name = VCConstant.COIN_MAP.get(key);

                    Map<String, String> map = new HashMap<>();
                    map.put("code", key);
                    map.put("name", name);
                    map.put("zdf", zdf);
                    map.put("zxj", String.valueOf(zxj));
                    map.put("cje", jeStr);
                    map.put("cjeval", String.valueOf(je));
                    ret.put(key, map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 行情
     *
     * @param str
     * @return
     */
    public static List<Map<String, Object>> parseHQ(String str) {
        List<Map<String, Object>> ret = new LinkedList<>();

        try {
            JSONObject json = JSONObject.fromObject(str);
            Set<String> coinKeys = VCConstant.COIN_MAP.keySet();
            for (String key : coinKeys) {
                String zxjKey = key + VCConstant.BTSD_SUFFIX;
                if(json.has(zxjKey)){
                    String jkjKey = key + VCConstant.BTSD_SUFFIX + "_24h";
                    String jeKey = key + VCConstant.BTSD_SUFFIX + "_vol";
                    BigDecimal zxj = new BigDecimal(json.getString(zxjKey));
                    BigDecimal jkj = new BigDecimal(json.getString(jkjKey));
//                    double zxj = json.getDouble(zxjKey);
//                    double jkj = json.getDouble(jkjKey);
                    String zdf = String.format("%.2f", ((zxj.subtract(jkj)).divide(jkj, 4, RoundingMode.HALF_EVEN).doubleValue()) * 100) + "%";
                    double je = json.getDouble(jeKey) / 10000;
                    String jeStr = String.format("%.2f", je) + "万";
                    String name = VCConstant.COIN_MAP.get(key);

                    Map<String, Object> map = new HashMap<>();
                    map.put("code", key);
                    map.put("name", name);
                    map.put("zdf", zdf);
                    map.put("zxj", zxj);
                    map.put("cje", jeStr);
                    map.put("cjeval", je);
                    ret.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 资金
     *
     * @param str
     * @return
     */
    public static List<Map<String, Object>> parseMyBalance(String str) {
        List<Map<String, Object>> ret = new LinkedList<>();
        try {
            JSONObject json = JSONObject.fromObject(str);
            Set<String> keys = VCConstant.COIN_MAP.keySet();
            for (String key : keys) {
                Map<String, Object> map = new HashMap<>();

                String name = VCConstant.COIN_MAP.get(key);
                String balanceKey = key + SUFFIX;
                if(json.has(balanceKey)){
                    String balance = json.getString(balanceKey);
                    String lock = json.getString(key + SUFFIX + "_lock");
                    String imma = json.getString(key + SUFFIX + "_imma");

                    map.put(VCConstant.MYBALANCE_BALANCE, balance);
                    map.put(VCConstant.MYBALANCE_LOCK, lock);
                    map.put(VCConstant.MYBALANCE_IMMA, imma);
                    map.put(VCConstant.MYBALANCE_NAME, name);
                    map.put(VCConstant.MYBALANCE_CODE, key);

                    ret.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 委单
     *
     * @param str
     * @return
     */
    public static List<Map<String, Object>> parseUserOrder(String str) {
        List<Map<String, Object>> ret = new LinkedList<>();

        try {
            JSONArray jsonArray = JSONArray.fromObject(str);
            int size = jsonArray.size();
            for (int index = 0; index < size; index++) {
                JSONObject json = jsonArray.getJSONObject(index);

                String id = json.getString("id");
                String code = json.getString("coinname");
                String name = VCConstant.COIN_MAP.get(code);
                Object typeVal = json.get("type");
                String type = typeVal.equals("1") ? "买入" : "卖出";
                String price = json.getString("price");
                String amount = json.getString("amount");
                String time = json.getString("time");

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("code", code);
                map.put("name", name);
                map.put("type", type);
                map.put("typeVal", typeVal);
                map.put("price", price);
                map.put("amount", amount);
                map.put("time", time);
                map.put("je", Double.parseDouble(amount) * Double.parseDouble(price));

                ret.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Map<String, Map<String, Object>> parseUserOrder2Map(String str) {
        Map<String, Map<String, Object>> ret = new HashMap<>();
        String jsonStr = null;
        try {
            if (str.contains("|")) {
                String[] orders = str.split("\\|");
                for (String order : orders) {
                    if (order.contains("[")) {
                        jsonStr = order;
                    }
                }
            }

            if(StringUtils.isEmpty(jsonStr) == true){
                return ret;
            }

            JSONArray jsonArray = JSONArray.fromObject(jsonStr);
            int size = jsonArray.size();
            for (int index = 0; index < size; index++) {
                JSONObject json = jsonArray.getJSONObject(index);

                String id = json.getString("id");
                String code = json.getString("coinname");
                String name = VCConstant.COIN_MAP.get(code);
                int typeVal = json.getInt("type");
                String type = typeVal == 1 ? "买入" : "卖出";
                String price = json.getString("price");
                String amount = json.getString("amount");
                String time = json.getString("time");

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("code", code);
                map.put("name", name);
                map.put("type", type);
                map.put("typeVal", typeVal);
                map.put("price", price);
                map.put("amount", amount);
                map.put("time", time);
                map.put("je", Double.parseDouble(amount) * Double.parseDouble(price));

                ret.put(code, map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static List<Map<String, Object>> parseUserClinch(String str) {
        List<Map<String, Object>> ret = new LinkedList<>();

        try {
            JSONArray jsonArray = JSONArray.fromObject(str);
            int size = jsonArray.size();
            for (int index = 0; index < size; index++) {
                JSONObject json = jsonArray.getJSONObject(index);

                String id = json.getString("id");
                String buyer_id = json.getString("buyer_id");
                String seller_id = json.getString("seller_id");
                String amount = json.getString("volume");
                String price = json.getString("price");
                String coinname = json.getString("coinname");
                String time = json.getString("time");

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("buyer_id", buyer_id);
                map.put("seller_id", seller_id);
                map.put("volume", amount);
                map.put("price", price);
                map.put("coinname", coinname);
                map.put("name", VCConstant.COIN_MAP.get(coinname));
                map.put("time", time);
                map.put("je", Double.parseDouble(amount) * Double.parseDouble(price));
                map.put("type", buyer_id.equals("0") ? "卖出" : "买入");

                ret.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Map<String, Map<String, String>> parseMyBalance2Map(String str) {
        Map<String, Map<String, String>> ret = new HashMap<>();
        try {
            JSONObject json = JSONObject.fromObject(str);
            Set<String> keys = VCConstant.COIN_MAP.keySet();
            for (String key : keys) {
                Map<String, String> map = new HashMap<>();

                String name = VCConstant.COIN_MAP.get(key);
                String balanceKey = key + SUFFIX;
                if(json.has(balanceKey)){
                    String balance = json.getString(balanceKey);
                    String lock = json.getString(key + SUFFIX + "_lock");
                    String imma = json.getString(key + SUFFIX + "_imma");

                    map.put(VCConstant.MYBALANCE_BALANCE, balance);
                    map.put(VCConstant.MYBALANCE_LOCK, lock);
                    map.put(VCConstant.MYBALANCE_IMMA, imma);
                    map.put(VCConstant.MYBALANCE_NAME, name);
                    map.put(VCConstant.MYBALANCE_CODE, key);

                    ret.put(key, map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static BTSDTradeInfo getTradeInfoFromMap(Map<String, String> map) {
        BTSDTradeInfo info = new BTSDTradeInfo();
        info.setId(Integer.parseInt(map.get("id")));
        info.setAmount(new BigDecimal(map.get("amount")));
        String code = map.get("code");
        info.setCode(code);
        info.setName(VCConstant.COIN_MAP.get(code));
        if(StringUtils.isNotEmpty(map.get("price"))){
            info.setPrice(new BigDecimal(map.get("price")));
        }
        if(StringUtils.isNotEmpty(map.get("seilprice"))){
            info.setSeilPrice(new BigDecimal(map.get("seilprice")));
        }
        info.setState(Integer.parseInt(map.get("state")));
        info.setTime(map.get("time"));

        return info;
    }

    public static List<BtsdHisTrade> parseHisTrade(String code, String str) {
        List<BtsdHisTrade> ret = new LinkedList<>();

        try {
            List<Map<String, String>> tradeOrderList = new LinkedList<>();
            JSONObject json = JSONObject.fromObject(str);

            if(json.has(TRADE_STR)){
                JSONArray tradeOrders = json.getJSONArray(TRADE_STR);
                int tradeOrderSize = tradeOrders.size();
                for (int index = 0; index < tradeOrderSize; index++) {
                    JSONArray tradeOrder = tradeOrders.getJSONArray(index);
                    BtsdHisTrade btsdHisTrade = new BtsdHisTrade();
                    BtsdCoin btsdCoin = new BtsdCoin();
                    btsdCoin.setId(code);
                    btsdHisTrade.setCoin(btsdCoin);
                    btsdHisTrade.setDate(tradeOrder.getString(0));
                    btsdHisTrade.setPrice(new BigDecimal(tradeOrder.getString(1)).doubleValue());
                    btsdHisTrade.setVolume(new BigDecimal(tradeOrder.getString(2)).doubleValue());
                    btsdHisTrade.setType(tradeOrder.getString(3).equals("buy") ? 1 : 2);

                    ret.add(btsdHisTrade);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Map<String, List<Map<String, String>>> parseTradeLine(String str) {
        Map<String, List<Map<String, String>>> ret = new HashMap<>();

        try {
            List<Map<String, String>> buyOrderList = new LinkedList<>();
            List<Map<String, String>> seilOrderList = new LinkedList<>();
            List<Map<String, String>> tradeOrderList = new LinkedList<>();
            JSONObject json = JSONObject.fromObject(str);
            //买单
            if(json.has(BUY_ORDER)){
                JSONArray buyOrders = json.getJSONArray(BUY_ORDER);
                int buyOrderSize = buyOrders.size();
                for (int index = 0; index < buyOrderSize; index++) {
                    JSONObject buyOrder = buyOrders.getJSONObject(index);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("name", "买" + (index + 1));
                    map.put("price", buyOrder.getString("price"));
                    map.put("amount", buyOrder.getString("amount"));
                    buyOrderList.add(map);
                }
            }
            if(json.has(BUY_STR)){
                JSONArray buyOrders = json.getJSONArray(BUY_STR);
                int buyOrderSize = buyOrders.size();
                for (int index = 0; index < buyOrderSize; index++) {
                    JSONArray buyOrder = buyOrders.getJSONArray(index);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("name", "买" + (index + 1));
                    map.put("price", buyOrder.getString(0));
                    map.put("amount", buyOrder.getString(1));
                    buyOrderList.add(map);
                }
            }
            ret.put(BUY_ORDER, buyOrderList);
            //卖单
            if(json.has(SEIL_ORDER)){
                JSONArray seilOrders = json.getJSONArray(SEIL_ORDER);
                int seilOrderSize = seilOrders.size();
                for (int index = 0; index < seilOrderSize; index++) {
                    JSONObject seilOrder = seilOrders.getJSONObject(index);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("name", "卖" + (index + 1));
                    map.put("price", seilOrder.getString("price"));
                    map.put("amount", seilOrder.getString("amount"));
                    seilOrderList.add(map);
                }
            }
            if(json.has(SEIL_STR)){
                JSONArray seilOrders = json.getJSONArray(SEIL_STR);
                int seilOrderSize = seilOrders.size();
                for (int index = 0; index < seilOrderSize; index++) {
                    JSONArray seilOrder = seilOrders.getJSONArray(index);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("name", "卖" + (index + 1));
                    map.put("price", seilOrder.getString(0));
                    map.put("amount", seilOrder.getString(1));
                    seilOrderList.add(map);
                }
            }
            ret.put(SEIL_ORDER, seilOrderList);
            //成交单
            if(json.has(TRADE_ORDER)){
                JSONArray tradeOrders = json.getJSONArray(TRADE_ORDER);
                int tradeOrderSize = tradeOrders.size();
                for (int index = 0; index < tradeOrderSize; index++) {
                    JSONObject tradeOrder = tradeOrders.getJSONObject(index);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("price", tradeOrder.getString("price"));
                    map.put("volume", tradeOrder.getString("volume"));
                    map.put("time", tradeOrder.getString("time"));
                    map.put("type", tradeOrder.getString("type").equals("1") ? "买入" : "卖出");
                    tradeOrderList.add(map);
                }
            }
            if(json.has(TRADE_STR)){
                JSONArray tradeOrders = json.getJSONArray(TRADE_STR);
                int tradeOrderSize = tradeOrders.size();
                for (int index = 0; index < tradeOrderSize; index++) {
                    JSONArray tradeOrder = tradeOrders.getJSONArray(index);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("time", tradeOrder.getString(0));
                    map.put("price", tradeOrder.getString(1));
                    map.put("volume", tradeOrder.getString(2));
                    map.put("type", tradeOrder.getString(3).equals("buy") ? "买入" : "卖出");
                    tradeOrderList.add(map);
                }
            }
            ret.put(TRADE_ORDER, tradeOrderList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 解析日线数据
     * @param str
     * @return
     */
    public static List<BtsdDayLine> parseDayLine(String code, String str){
        List<BtsdDayLine> ret = new LinkedList<>();
        JSONArray jsonArray = JSONArray.fromObject(str);
        int size = jsonArray.size();
        //最高,最低
        for(int index = 0;index < size;index++){
            JSONArray data = jsonArray.getJSONArray(index);
            BtsdDayLine dayLine = new BtsdDayLine();
            long date = data.getLong(0);
            double cjl = data.getDouble(1);
            double kp = data.getDouble(2);
            double zg = data.getDouble(3);
            double zd = data.getDouble(4);
            double sp = data.getDouble(5);

            BtsdCoin bc = new BtsdCoin();
            bc.setId(code);
            dayLine.setCoin(bc);
            dayLine.setDate(date);
            dayLine.setDatefmt(org.apache.commons.lang.time.DateFormatUtils.format(date, DATETIME_FORMAT));
            dayLine.setCjl(cjl);
            dayLine.setKp(kp);
            dayLine.setZg(zg);
            dayLine.setZd(zd);
            dayLine.setSp(sp);

            ret.add(dayLine);
        }

        return ret;
    }

    public static List<BtsdHourLine> parseHourLine(String code, String str){
        List<BtsdHourLine> ret = new LinkedList<>();
        JSONArray jsonArray = JSONArray.fromObject(str);
        int size = jsonArray.size();
        //最高,最低
        for(int index = 0;index < size;index++){
            JSONArray data = jsonArray.getJSONArray(index);
            BtsdHourLine hourLine = new BtsdHourLine();
            long date = data.getLong(0);
            double cjl = data.getDouble(1);
            double kp = data.getDouble(2);
            double zg = data.getDouble(3);
            double zd = data.getDouble(4);
            double sp = data.getDouble(5);

            BtsdCoin bc = new BtsdCoin();
            bc.setId(code);
            hourLine.setCoin(bc);
            hourLine.setDate(date);
            hourLine.setDatefmt(org.apache.commons.lang.time.DateFormatUtils.format(date, DATETIME_FORMAT));
            hourLine.setCjl(cjl);
            hourLine.setKp(kp);
            hourLine.setZg(zg);
            hourLine.setZd(zd);
            hourLine.setSp(sp);

            ret.add(hourLine);
        }

        return ret;
    }


    public static List<BtsdMin5Line> parseMin5Line(String code, String str){
        List<BtsdMin5Line> ret = new LinkedList<>();
        JSONArray jsonArray = JSONArray.fromObject(str);
        int size = jsonArray.size();
        //最高,最低
        for(int index = 0;index < size;index++){
            JSONArray data = jsonArray.getJSONArray(index);
            BtsdMin5Line min5Line = new BtsdMin5Line();
            long date = data.getLong(0);
            double cjl = data.getDouble(1);
            double kp = data.getDouble(2);
            double zg = data.getDouble(3);
            double zd = data.getDouble(4);
            double sp = data.getDouble(5);

            BtsdCoin bc = new BtsdCoin();
            bc.setId(code);
            min5Line.setCoin(bc);
            min5Line.setDate(date);
            min5Line.setDatefmt(org.apache.commons.lang.time.DateFormatUtils.format(date, DATETIME_FORMAT));
            min5Line.setCjl(cjl);
            min5Line.setKp(kp);
            min5Line.setZg(zg);
            min5Line.setZd(zd);
            min5Line.setSp(sp);

            ret.add(min5Line);
        }

        return ret;
    }

    public static void main(String[] args) {
    }
}
