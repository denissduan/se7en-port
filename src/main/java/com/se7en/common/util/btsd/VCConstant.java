package com.se7en.common.util.btsd;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 16-9-12.
    虚拟币常量
 */
public final class VCConstant {

    private VCConstant(){
    }

    public final static int BUY = 1;

    public final static int SEIL = 2;

    public final static int STATE_BUY = 1;

    public final static int STATE_BUY_SUCCESS = 2;

    public final static int STATE_SEIL = 3;

    public final static int STATE_SEIL_SUCCESS = 4;

    public final static Map<String,String> COIN_MAP = new HashMap(30);

    public final static Map<String,String> TRADE_MAP = new HashMap(30);

    static {

        COIN_MAP.put("cny", "人民币");
        COIN_MAP.put("btc", "比特币");
        COIN_MAP.put("ltc", "莱特币");
        COIN_MAP.put("doge", "狗狗币");
        COIN_MAP.put("xrp", "瑞波币");
        COIN_MAP.put("bts", "比特股");
        COIN_MAP.put("xlm", "恒星币");
        COIN_MAP.put("nxt", "未来币");
        COIN_MAP.put("blk", "黑币");
        COIN_MAP.put("xem", "新经币");
        COIN_MAP.put("emc", "崛起币");
        COIN_MAP.put("dash", "达世币");
        COIN_MAP.put("vash", "微币");
        COIN_MAP.put("xzc", "零币");
        COIN_MAP.put("eac", "地球币");
        COIN_MAP.put("bils", "比例股");
        COIN_MAP.put("bost", "增长币");
        COIN_MAP.put("ppc", "点点币");
        COIN_MAP.put("zcc", "招财币");
        COIN_MAP.put("xpm", "质数币");
        COIN_MAP.put("ybc", "元宝币");
        COIN_MAP.put("apc", "苹果币");
        COIN_MAP.put("dgc", "数码币");
        COIN_MAP.put("mec", "美卡币");
        COIN_MAP.put("bec", "比奥币");
        COIN_MAP.put("anc", "阿侬币");
        COIN_MAP.put("unc", "联合币");
        COIN_MAP.put("ric", "黎曼币");
        COIN_MAP.put("src", "安全币");
        COIN_MAP.put("tag", "悬赏币");
        COIN_MAP.put("mgc", "众合币");
        COIN_MAP.put("hlb", "活力币");
        COIN_MAP.put("ncs", "资产股");
//        COIN_MAP.put("xcn", "氪石币");
//        COIN_MAP.put("sys", "系统币");
//        COIN_MAP.put("med", "地中海币");
//        COIN_MAP.put("tmc", "时代币");
//        COIN_MAP.put("anc", "阿侬币");
//        COIN_MAP.put("ardr", "阿朵");
//        COIN_MAP.put("wdc", "世界币");
//        COIN_MAP.put("qrk", "夸克币");

        TRADE_MAP.putAll(COIN_MAP);
        TRADE_MAP.remove("btc");
        TRADE_MAP.remove("cny");
    }

    public final static String BTSD_SUFFIX = "2cny";

    public final static String MYBALANCE_NAME = "name";
    public final static String MYBALANCE_BALANCE = "balance";
    public final static String MYBALANCE_LOCK = "lock";
    public final static String MYBALANCE_IMMA = "imma";
    public final static String MYBALANCE_CODE = "code";
    public final static String MYBALANCE_CNY = "cny";

}
