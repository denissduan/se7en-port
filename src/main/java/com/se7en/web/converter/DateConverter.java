package com.se7en.web.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.se7en.common.util.StringUtil;
import org.springframework.core.convert.converter.Converter;

import com.se7en.common.Format;
/**
 * 日期转换器
 * @author dl
 */
public class DateConverter implements Converter<String, Date> {
	
	final private SimpleDateFormat sdf = new SimpleDateFormat(Format.DATE);

	public Date convert(String str) {
		Date dt = null;
		if(StringUtil.isNoneBlank(str)){
			dt = new Date();
		}else{
			try {
				dt = sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

}
