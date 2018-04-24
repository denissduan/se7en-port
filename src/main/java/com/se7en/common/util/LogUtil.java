package com.se7en.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * @author Administrator
 *
 */
public final class LogUtil {
	
	private final static Logger log = Logger.getRootLogger();

	private LogUtil(){
	}
	
	public static void addError(String message){
		log.error(message);
	}
	
	public static void addError(String message,Throwable exp){
		log.error(message, exp);
	}
	
	public static void addError(Throwable exp){
		log.error(throwable2Str(exp));
	}
	
	public static void addInfo(String message){
		log.info(message);
	}
	
	public static void addWarning(String message){
		log.warn(message);
	}
	
	public static void addDebug(String message){
		log.debug(message);
	}
	
	/**
	 * 获取错误的信息 
	 * @param exp
	 * @return
	 */
	private static String throwable2Str(Throwable exp) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		exp.printStackTrace(pw);
		pw.close();
		String error= writer.toString();
		return error;
	}
	
}
