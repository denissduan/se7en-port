package com.se7en.service.md;

import org.apache.commons.lang3.CharUtils;

/**
 * Created by Administrator on 17-5-24.
 */
public class TemplateStringUtil {

    private TemplateStringUtil(){
    }

    /**
     * 根据类名转表名
     * @param str
     * @return
     */
    public static String getTableNameFromClassName(String str) {

        return getDBNameFromModelName(str);
    }

    /**
     * Gets the nick name from class name.
     * 根据类名转别名
     * @param str the str
     * @return the nick name from class name
     */
    public static String getNickNameFromClassName(String str){

        return str.toLowerCase();
    }

    private static String getDBNameFromModelName(String str) {
        StringBuilder ret = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (CharUtils.isAsciiAlphaUpper(c)) {
                if (ret.length() > 0
                        /*&& CharUtils.isAsciiAlphaLower(ret
                        .charAt(ret.length() - 1))*/)
                    ret.append("_");
            }
            ret.append(c);
        }

        return ret.toString().toLowerCase();
    }

    public static String getFieldNameFromPropertyName(String str){

        return getDBNameFromModelName(str);
    }

    /**
     * Return the String with the first letter uppercase. Useful for class name.
     */
    public static String firstUpper(String s) {
        if (s.length() > 0)
            s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        return s;
    }

    /**
     * Return the String with the first letter lowercase. Useful for attribute
     * name.
     */
    public static String firstLower(String s) {
        if (s.length() > 0)
            s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
        return s;
    }

    /**
     * Transform a String with notation aa.bb.cc to aa/bb/cc<br/>
     * Usefull for package translating.
     */
    public static String toPath(String packageName) {
        return packageName.trim().replaceAll("\\.", "/");
    }

    public static String getPathFromNamespace(String str){
        return str.replaceAll("\\.", "\\/");
    }

}
