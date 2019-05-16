package com.le.ebook.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action基类
 * 为子类提供request, session对象
 * 提供分页工具变量等
 */
public class BaseAction implements ServletRequestAware {
	
	private HttpServletRequest request;

	protected int page = 1;
	protected String pageTool;
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public HttpSession getSession(){
		return request.getSession();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPageTool() {
		return pageTool;
	}

	public void setPageTool(String pageTool) {
		this.pageTool = pageTool;
	}
	
	/**
	 * 返回响应数据
	 * @param msg
	 */
	protected void sendResponseMsg(String msg) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setDateHeader("Expires", 0);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(msg);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
