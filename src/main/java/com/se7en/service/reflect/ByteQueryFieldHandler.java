package com.se7en.service.reflect;

import java.lang.reflect.Field;
import java.util.List;

public class ByteQueryFieldHandler extends AbstractQuerySqlFieldHandler {

	@SuppressWarnings("unchecked")
	@Override
	public void handle(Field field, Object... arguments) {
		final Object bean = arguments[0];
		final StringBuilder sql = (StringBuilder)arguments[1];
		final List<Object> params = (List<Object>)arguments[2];
		try {
			final byte val = field.getByte(bean);
			if(val != 0){
				sql.append(" and " + field.getName() + " = ? ");
				params.add(val);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
