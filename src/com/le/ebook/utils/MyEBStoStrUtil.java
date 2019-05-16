package com.le.ebook.utils;

public class MyEBStoStrUtil {
	/**
	 * 获取对应字符串在整个字符串中的位置
	 * @param fromStr
	 * @param ochs
	 * @return
	 */
	public static int getStrIndex0Chs(String fromStr,String ochs){
		if(fromStr != null && fromStr.length() > 0){
			return fromStr.indexOf(ochs);
		}
		return -1;
	}
	
	/**
	 * 获取对应字符串在整个字符串中最后出的位置
	 * @param fromStr
	 * @param ochs
	 * @return
	 */
	public static int getStrLaIndex0Chs(String fromStr,String ochs){
		if(fromStr != null && fromStr.length() > 0){
			return fromStr.lastIndexOf(ochs);
		}
		return -1;
	}
	
	/**
	 * 截取字符串from to to
	 * @param fromStr
	 * @param from
	 * @param to
	 * @return
	 */
	public static String subChsFAnd2(String fromStr,int from,int to){
			return fromStr.substring(from, to);
	}
	/**
	 * 截取字符串from to end
	 * @param fromStr
	 * @param from
	 * @param to
	 * @return
	 */
	public static String subChsF(String fromStr,int from){
		return fromStr.substring(from);
}
	
	/**
	 * 返回字符串转换成小写字母
	 * @param chs
	 * @return
	 */
	public static String chs2LCase(String chs){
		String lowerCase = chs.toLowerCase();
		return lowerCase;
	}
	
	/**
	 * 返回字符串转换成大写字母
	 * @param chs
	 * @return
	 */
	public static String chs2UCase(String chs){
		String up = chs.toUpperCase();
		return up;
	}
}
