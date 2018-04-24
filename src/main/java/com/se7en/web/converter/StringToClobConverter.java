package com.se7en.web.converter;

import java.sql.Clob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import com.se7en.common.util.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToClobConverter implements Converter<String, Clob> {

	public Clob convert(final String str) {
		Clob clob = null;
		if(StringUtil.isBlank(str)){
			return clob;
		}
		try {
			clob = new SerialClob(str.toCharArray());
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clob;
	}

}
