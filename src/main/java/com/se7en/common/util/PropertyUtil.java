package com.se7en.common.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/2/21.
 */
public final class PropertyUtil {

    private PropertyUtil(){
    }

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyUtil.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return prop.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertyUtil.getProperty("model.list"));
    }
}
