<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
				<div>
				<a href="javascript:void(0)" onclick="change_page(1)">上一页</a><a href="javascript:void(0)" onclick="change_page(2)">下一页</a>
				<span>第${bookMap.page }页</span>
				/
				<span>共${bookMap.total_page }页</span>
				<span>每页</span>
					<select id="page_size" onchange="change_page(0)">
						<option <c:if test="${bookMap.page_size==10}">selected="selected"</c:if> >10</option>
						<option <c:if test="${bookMap.page_size==20}">selected="selected"</c:if> >20</option>
						<option <c:if test="${bookMap.page_size==50}">selected="selected"</c:if> >50</option>
						<option <c:if test="${bookMap.page_size==100}">selected="selected"</c:if> >100</option>
					</select>
				<span>条</span>
				
				<span>到</span>
					<input style="width: 50px;" type="number" onblur="check_page_change()" onkeydown="check_page_change()"  onkeyup="check_page_change()" onmousedown="check_page_change()" onmouseout="check_page_change()"  onmouseup="check_page_change()"  onmouseover="check_page_change()" id="page" value="${bookMap.page }"/>
				<span>页</span>
				<input type="button" value="跳转" onclick="change_page(0)" />
				
				<input id="total_page"  type="hidden" value="${bookMap.total_page }"/>
				
				<input id="news"  type="hidden" value="${news}"/>
				<input id="hots"  type="hidden" value="${hots}"/>
				
			</div>