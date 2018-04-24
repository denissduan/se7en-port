package com.se7en.common.util;

import java.util.Locale;

/**
 * String 工具类
 * @author dl
 */
public final class StringUtil extends org.apache.commons.lang3.StringUtils {

	private StringUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * 表字段转换为模型字段
	 * @param fieldName
	 * @return
	 */
	public static String tableFieldName2ModelFieldName(final String fieldName){
		String ret = fieldName.toLowerCase(Locale.ENGLISH);
		if (ret.contains("_")) {
			final String[] ary = ret.split("_");
			ret = "";
			for (int index = 0; index < ary.length; index++) {
				String str = ary[index];
				if (index == 0) {
					ret = ret.concat(str);
				}else{
					ret = ret.concat(capitalize(str));
				}
			}
		}
		return ret;
	}
	
	/**
	 * 表名转换模型名
	 * @param tableName
	 * @return
	 */
	public static String tableName2ModelName(String tableName){
		String ret = tableName.toLowerCase();
		if (ret.contains("_")) {
			String[] ary = ret.split("_");
			ret = ary[ary.length - 1];
		}
		return ret;
	}

	public static String firstLower(String s) {
		if (s.length() > 0)
			s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
		return s;
	}

}
