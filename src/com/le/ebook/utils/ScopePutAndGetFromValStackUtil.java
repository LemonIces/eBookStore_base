package com.le.ebook.utils;
import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class ScopePutAndGetFromValStackUtil {
	
	/**
	 * 放入值站中
	 * @param msgKey
	 * @param msgVal
	 */
	public static void putMsg2ValStack(String msgKey,Object msgVal){
		ActionContext.getContext().put(msgKey, msgVal);
	}
	
	/**
	 * 从session雨中获取对象
	 * @param objKey
	 * @return
	 */
	public static Object getAnObjFromSessionScope(String objKey){
		return ActionContext.getContext().getSession().get(objKey);
	}
	/**
	 * 给session域中设置一个值
	 * @param objKey
	 * @param objValue
	 */
	public static void setAnObj2SessionScope(String objKey,Object objValue){
		ActionContext.getContext().getSession().put(objKey,objValue);
	}
	/**
	 * 给session域中删除一个值
	 * @param objKey
	 */
	public static void remAnObjFromSessionScope(String objKey){
		ActionContext.getContext().getSession().remove(objKey);
	}
	
	/**
	 * 添加一个cookie
	 * @param bookStoreAutoLoginCookie
	 */
	public static void addAnCook2RespCookieScope(Cookie bookStoreAutoLoginCookie){
		ServletActionContext.getResponse().addCookie(bookStoreAutoLoginCookie);
	}	
	
	

	/**
	 * 获取context中的真实路径
	 * @param bookStoreAutoLoginCookie
	 */
	public static String getRealPathFromCtextVirtPath(String virtPath){
		String path = ServletActionContext.getServletContext().getRealPath(virtPath);
		return path;
	}	
	
	
	
}
