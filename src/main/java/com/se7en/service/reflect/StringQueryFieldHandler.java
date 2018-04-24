package com.se7en.service.reflect;

import java.lang.reflect.Field;
import java.util.List;

public class StringQueryFieldHandler extends AbstractQuerySqlFieldHandler {

	@SuppressWarnings("unchecked")
	@Override
	public void handle(final Field field,final Object... arguments) {
		final Object bean = arguments[0];
		final StringBuilder sql = (StringBuilder)arguments[1];
		final List<Object> params = (List<Object>)arguments[2];
		try {
			final int val = field.getInt(bean);
			if(val != 0){
				sql.append(" and " + field.getName() + " like ? ");
				params.add("%" + val + "%");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
