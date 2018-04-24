package com.se7en.common.util;

/**
 * 枚举类型工具类
 * 
 * @author dl
 */
public final class EnumUtil {
	
	private EnumUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * 根据枚举序号获取枚举类型
	 * @param t
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(final Class<T> t,final int ordinal) {
		return t.getEnumConstants()[ordinal];
	}

}
