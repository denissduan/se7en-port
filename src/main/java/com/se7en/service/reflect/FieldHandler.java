package com.se7en.service.reflect;

import java.lang.reflect.Field;

public interface FieldHandler {

	/**
	 * Handle.
	 * 字段处理
	 * @param fldValue the fld value
	 * @param arguments the arguments 传参
	 */
	void handle(final Field field,final Object... arguments);
	
}
