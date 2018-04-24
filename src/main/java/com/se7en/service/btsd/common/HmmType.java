package com.se7en.service.btsd.common;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017/7/4.
 * 1.日线,2.时线,3.五分线,4.历史交易
 */
public class HmmType {

    public final static int DAY = 1;

    public final static int HOUR = 2;

    public final static int MIN5 = 3;

    public final static int HIS_TRADE = 4;

    private static List<Integer> types = new LinkedList<>();

    static {
        types.add(DAY);
        types.add(HOUR);
        types.add(MIN5);
        types.add(HIS_TRADE);
    }

    public static boolean isLegal(int type){
        for(int entry : types){
            if(type == entry){
                return true;
            }
        }

        return false;
    }

}
