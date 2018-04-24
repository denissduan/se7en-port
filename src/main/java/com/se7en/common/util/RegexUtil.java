package com.se7en.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class RegexUtil.
 * 正则表达式工具类
 */
public final class RegexUtil {

	private RegexUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * Capture match string.
	 * 捕获输入字符串(input)中符合正则表达式
	 * (regex)的子字符串数组
	 * @param input the input
	 * @param regex the regex
	 * @return the string[]
	 */
	public static String[] captureMatchString(final String input,final String regex){
		List<String> ret = Collections.emptyList();
		if(StringUtils.isEmpty(input) || StringUtils.isEmpty(regex))
			return new String[0];
		ret = new ArrayList<String>();
		final Pattern ptn = Pattern.compile(regex);
		final Matcher mat = ptn.matcher(input);
		while(mat.find()){
			ret.add(mat.group());
		}
		
		return ret.toArray(new String[ret.size()]);
	}
	
}
