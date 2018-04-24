package com.se7en.common.util;

import java.util.UUID;
/**
 * GUID工具
 * @author 段磊
 */
public final class Guid {
	
	private Guid(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * 生成32位无横线guid
	 * @return
	 */
	public static String generate(){
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	
}
