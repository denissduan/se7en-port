package com.se7en.common;

/**
 * The Class Regex.
 * 正则表达式
 */
public final class Format {

	/** The Constant datetime. */
	public final static String DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	/** The Constant date. */
	public final static String DATE = "yyyy-MM-dd";
	
	public final static String MONTH_DAY = "MM-dd";
	
	public final static String TIME = "HH:mm:ss";
	
	public final static String DATE_MINUTE = "yyyy-MM-dd HH:mm";
	
	public final static String MYSQL_DATETIME = "%Y-%m-%d %H:%i:%S";
	
	public final static String MYSQL_DATE = "%Y-%m-%d";
	
	public final static String MYSQL_TIME = "%H:%i:%s";
	
	public final static String MYSQL_DATE_MINUTE = "%Y-%m-%d %H:%i";
	
	/** The Constant REGEX_COMMON_CAPTURE. 通用字符串正则表达式捕获组*/
	public final static String REGEX_COMMON_CAPTURE = "@\\w*@";
	
	/**
	 * Instantiates a new regex.
	 */
	private Format(){
	}
}



