<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<div>
		<div class="top">
			<div class="fl" style="line-height: 20px;">
				欢迎光临网上图书商城,
				<s:if test="#session.loginUser==null">
				请<a id="top_long_a" target="_blank" href="${pageContext.request.contextPath}/testAction_loginPage">登录</a>
				<a target="_blank" href="${pageContext.request.contextPath}/testAction_registPage"> 成为会员</a>
				</s:if>
				
				<s:if test="#session.loginUser!=null">
				<a href="${pageContext.request.contextPath}/testAction_usercenterPage"><img style="border-radius: 20px;"  height="20" width="20" align="top" title="用户：${session.loginUser.nickname}" src="${pageContext.request.contextPath}/${session.loginUser.head_img}"/></a>
				<%-- <a href="${pageContext.request.contextPath}/testAction_usercenterPage"><s:property value="#session.loginUser.nickname" /></a> --%>
				<a href="${pageContext.request.contextPath}/userAction_logout">退出 </a>
				</s:if>
			</div>
			<div class="cls"></div>
		</div>
		<div class="nav padding10">
		<!-- 搜索div -->
		<div>
		
			<div class="logo_img">
				<a href="${pageContext.request.contextPath }/testAction_index"><img class="" style="width: 160px;height: 60px; cursor: pointer;" align="top" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
			<div style="margin-top: 10px; margin-left: 260px;">
			<div class="search_input">
				<input class="searchinput" value="<s:property value='search_text' />" name="search_text" id="search_text" type="text">
			</div>
			<input name="currentPage" type="hidden" value="1" />
			<input name="pageSize" type="hidden" value="20" />
			<div class="btn_search"><a  href="javascript:void(0)" onclick="search_book(0)">搜索</a></div>
			<div class="my">
				<a  class="mallcar" target="_blank" href="${pageContext.request.contextPath }/mallCarAction_list" >购物车</a>
				<a class="my_order" target="_blank"href="${pageContext.request.contextPath}/testAction_usercenter_order_listPage1">我的订单</a>
			</div>
			</div>
			</div>
		<div class="cls"></div>
		</div>
</div>