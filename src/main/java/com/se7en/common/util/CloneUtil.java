package com.se7en.common.util;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/**
 * 克隆工具类
 * 
 * @author dl
 */
public final class CloneUtil {

	private CloneUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}

	/**
	 * 深度复制对象
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T extends Serializable> T clone(final T obj) throws IOException,
			ClassNotFoundException {

		return SerializationUtils.clone(obj);
	}

}
