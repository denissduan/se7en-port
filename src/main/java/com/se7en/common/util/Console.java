package com.se7en.common.util;

import org.apache.log4j.Logger;

/**
 * 控制台
 * @author dl
 */
public class Console {
	
	private final static Logger LOG = Logger.getRootLogger();
	
	private final static String LINE = "-------------------------------------";

	private final static String HALFLINE = "----------------";
	
	public  void divide(){
		LOG.info(LINE);
	}

	public static void divide(final String msg){
		LOG.info(HALFLINE + msg + HALFLINE);
	}
	/**
	 * 带分割线的对象打印
	 * @param obj
	 */
	public static void divide(final Object obj){
		LOG.info(HALFLINE + obj.toString() + HALFLINE);
	}
	
	public static void msg(final String msg){
		LOG.info(LINE + msg);
	}
	
	public static void msgln(final String msg){
		LOG.info(LINE + msg);
	}
	
	/**
	 * 打印错误信息
	 * @param msg
	 */
	public static void err(final String msg){
		LOG.info(LINE + msg);
	}
	
	public static void errln(final String msg){
		LOG.info(LINE + msg);
	}
	
	/**
	 * 不带分割线的对象打印
	 * @param obj
	 */
	public static void msg(final Object obj){
		LOG.info(LINE + obj);
	}
	
	public static void msgln(final Object obj){
		LOG.info(LINE + obj);
	}
}
