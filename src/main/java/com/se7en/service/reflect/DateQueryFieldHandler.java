package com.se7en.service.reflect;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class DateQueryFieldHandler extends AbstractQuerySqlFieldHandler {

	@SuppressWarnings("unchecked")
	@Override
	public void handle(final Field field,final Object... arguments) {
		final Object bean = arguments[0];
		final StringBuilder sql = (StringBuilder)arguments[1];
		final List<Object> params = (List<Object>)arguments[2];
		try {
			final Date dt = (Date)field.get(bean);
			if(dt != null){
				sql.append(" and " + field.getName() + " = ? ");
				params.add(dt);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
