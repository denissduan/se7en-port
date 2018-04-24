package com.se7en.service.reflect;

import java.lang.reflect.Field;
import java.util.List;

public class FloatQueryFieldHandler extends AbstractQuerySqlFieldHandler {

	@Override
	public void handle(final Field field,final Object... arguments) {
		Object bean = arguments[0];
		StringBuilder sql = (StringBuilder)arguments[1];
		List<Object> params = (List<Object>)arguments[2];
		try {
			final float val = field.getFloat(bean);
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
