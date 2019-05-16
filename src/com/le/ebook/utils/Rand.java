package com.le.ebook.utils;

public class Rand {
	/**
	 * 生成6位数的验证码
	 * @return
	 */
	public static String genRandCodeStr(){
		int length=6;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			sb.append(((int)(Math.random()*10))+"");
		}
		return sb.toString();
	  }

}

