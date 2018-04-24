/*
package com.se7en.util;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

*/
/**
 * The Class PinYinUtil.
 * 拼音工具类
 *//*

public final class PinYinUtil {

	private PinYinUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}

	*/
/**
	 * 将汉字转换为全拼
	 * 
	 * @param src
	 * @return String
	 *//*

	public static String getPinYin(final String src) {
		char[] ca = src.toCharArray();
		String[] caCopy = new String[ca.length];
		// 设置汉字拼音输出的格式
		final HanyuPinyinOutputFormat hpof = new HanyuPinyinOutputFormat();
		hpof.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hpof.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder ret = new StringBuilder();
		int length = ca.length;
		try {
			for (int i = 0; i < length; i++) {
				// 判断能否为汉字字符
				// System.out.println(t1[i]);
				String string = Character.toString(ca[i]);
				if (string.matches("[\u4E00-\u9FA5]+")) {
					caCopy = PinyinHelper.toHanyuPinyinStringArray(ca[i], hpof);// 将汉字的几种全拼都存到t2数组中
					ret.append(caCopy[0]);// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					ret.append(string);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return ret.toString();
	}

	*/
/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str
	 * @return String
	 *//*

	public static String getPinYinHeadChar(final String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			final char word = str.charAt(j);
			// 提取汉字的首字母
			final String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray == null) {
				convert += word;
			} else {
				convert += pinyinArray[0].charAt(0);
			}
		}
		return convert;
	}

	*/
/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return String
	 *//*

	public static String getCnASCII(final String cnStr) {
		final StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		final byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			int i2 = bGBK[i] & 0xff;
			String hexString = Integer.toHexString(i2);
			strBuf.append(hexString);
		}
		return strBuf.toString();
	}
}
*/
