package com.le.ebook.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

public class MyEBSJS0NUtil {
	/**
	 * 通过一个map获取一个jsonobj
	 * @param map
	 * @return
	 */
	public static JSONObject jObjFromAMap(Map map){
		return JSONObject.fromObject(map);
	}

	/**
	 * 把jsonObj写入到response雨中返回
	 * @param jobj
	 * @throws IOException
	 */
	public static void wriJObj2Resp(JSONObject jobj) throws IOException{
		wriJObj2Resp(jobj, null);
	}
	/**
	 *  把jsonObj写入到response雨中返回 编码是chaset
	 * @param jobj
	 * @param charset
	 * @throws IOException
	 */
	public static void wriJObj2Resp(JSONObject jobj,String charset) throws IOException{
		if(charset==null){
			ServletActionContext.getResponse().getOutputStream().write(jobj.toString().getBytes());
		}else{
			ServletActionContext.getResponse().getOutputStream().write(jobj.toString().getBytes(charset));
		}
		
	}
}
