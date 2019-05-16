<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
			<div style="margin: 0px auto; width: 180px;padding:20px 5px; ">
				<div
					style="text-align: center; font-size: 20px; font-weight: bold; background-color: #d3d3dc; padding: 10px 0px; border-bottom: 4px solid #f7faf1; margin: 2px 0px;">个人中心</div>
				<div class="left_title1">
					<a href="${pageContext.request.contextPath}/testAction_usercenterPage">个人信息</a>
				</div>
				<div class="left_title1">
					<a href="${pageContext.request.contextPath}/testAction_usercenter_order_listPage1">我的订单</a>
				</div>
				<div class="left_title1">
					<a href="${pageContext.request.contextPath}/testAction_addressPage">收货地址</a>
				</div>
				<div class="left_title1">
					<a href="${pageContext.request.contextPath}/testAction_changepwdPage">修改密码</a>
				</div>
				<div class="left_title1">
					<a href="${pageContext.request.contextPath}/testAction_change_email_Page">修改邮箱</a>
				</div>
		</div>