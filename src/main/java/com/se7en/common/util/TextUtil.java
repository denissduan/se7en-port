package com.se7en.common.util;


/**
 * 脚本处理工具类
 * @author dl
 *
 */
public final class TextUtil {

	private TextUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * 过滤敏感关键字，转换为json文本
	 * @param source
	 * @return
	 */
	public static String jsonTextFormat(final String source) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			final char c = source.charAt(i);
			switch (c) {
			case '\"':
				sb.append('\"');
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '{' :
				sb.append("\\{");
				break;
			case '}' :
				sb.append("\\}");
				break;
			case '[' :
				sb.append("\\[");
				break;
			case ']' :
				sb.append("\\]");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 剪贴板修剪首尾
	 * @param text
	 * @return
	 */
	public static String clipBoardTrim(final String text){
		final StringBuilder sBuilder = new StringBuilder(text.trim());
		if(sBuilder.length() > 0){
			char firstC = sBuilder.charAt(0),lastC = sBuilder.charAt(sBuilder.length() - 1);
			//首字母过滤
			while(firstC == '\n' || firstC == '\r' 
					|| firstC == '\t' || firstC == ' '){
				sBuilder.deleteCharAt(0);
				firstC = sBuilder.charAt(0);
			}
			//尾字母过滤
			while(lastC == '\n' || lastC == '\r' 
					|| lastC == '\t' || lastC == ' '){
				sBuilder.deleteCharAt(sBuilder.length() - 1);
				lastC = sBuilder.charAt(sBuilder.length() - 1);
			}
		}
		return sBuilder.toString();
	}
}
