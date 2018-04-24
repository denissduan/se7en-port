package com.se7en.common.util;


/**
 * StringBuilder功能扩展工具类
 * 
 * @author dl
 * 
 */
public final class StringBuilderUtil {
	
	private StringBuilderUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}

	/**
	 * 替换StringBuilder中所有
	 * 匹配matchStr的值为replacement
	 * @param sb
	 * @param matchStr
	 * @param replacement
	 */
	public static void replaceAll(final StringBuilder sb,final String matchStr,
			final String replacement) {
		int index = -1;
		while ((index = sb.indexOf(matchStr)) != -1) {
			final int startIndex = index;
			final int endIndex = startIndex + matchStr.length();
			sb.replace(startIndex, endIndex, replacement);
		}
	}

	public static void replaceAll(final StringBuilder sb,final Object matchObj,
			final Object replaceObj) {
		replaceAll(sb, matchObj.toString(), replaceObj.toString());
	}
	
	/**
	 * Append line.
	 * 追加一行
	 * @param sb the sb
	 * @param str the str
	 */
	public static void appendLine(final StringBuilder sb,final String str){
		sb.append(str).append('\n');
	}
}
