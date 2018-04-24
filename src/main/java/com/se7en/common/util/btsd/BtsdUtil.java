package com.se7en.common.util.btsd;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by admin on 2017/5/8.
 */
public final class BtsdUtil {

    private BtsdUtil(){
    }

    public static boolean isLegalRate(String rate){
        if(NumberUtils.isNumber(rate) == false){
            return false;
        }
        if(Double.parseDouble(rate) <= 0){
            return false;
        }
        return true;
    }

}
