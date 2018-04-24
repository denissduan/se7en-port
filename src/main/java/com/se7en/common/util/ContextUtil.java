package com.se7en.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * spring 工具类
 * @author dl
 */
public final class ContextUtil {

	private static ApplicationContext ac;
	
	private static ApplicationContext webAc;
	
	static{
		ac = new ClassPathXmlApplicationContext("applicationContext-context.xml", "applicationContext-hibernate.xml", "applicationContext-quartz.xml");
	}
	
	private ContextUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * 获取Web上下文注入bean对象
	 * @param type
	 * @return
	 */
	public static <T> T getBean(final Class<T> type) {
		return ac.getBean(type);
	}

	/**
	 * 获取Web上下文注入bean对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(final String className){
		return (T)ac.getBean(className);
	}
	
}
