package com.le.ebook.utils;

public class MyEBStoCheckRandCodeUtils {
	
	/**
	 * 判断登录界面验证码是否比对成功
	 * @param code1
	 * @param code2
	 * @return
	 */
	public static boolean check4LoginPageFormRandCode(String code1,String code2){
		if (code1 != null && code2 != null && code1.toLowerCase().equals(code2.toLowerCase())) {
			// 比对成功，验证码正确
			return true;
		}
		return false;
	}

}
