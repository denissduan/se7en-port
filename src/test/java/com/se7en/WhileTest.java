package com.se7en;

import com.se7en.common.util.DateUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/2.
 */
public class WhileTest {

    public static void main(String[] args) {
        long startMills = 1496181600000l,endMills = 1496185200000l;
        long curMills = startMills;
        Set<Long> ret = new LinkedHashSet<>();
        while(curMills < endMills) {
            ret.add(curMills);
            Date date = new Date(curMills);
            date = DateUtils.addMinutes(date, 5);

            curMills = date.getTime();
        }
        for(long mill : ret){
            System.out.println(DateFormatUtils.format(mill, "yyyy-MM-dd HH:mm:ss"));
        }
    }

}
