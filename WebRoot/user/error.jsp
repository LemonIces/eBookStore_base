<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="">

<title>网上图书商城提示界面</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/mallcar.css" />
<script type="text/javascript">
</script>
</head>
<body onload="init()">
	<div class="box">
		<div class="top">
			<div class="fl">
				欢迎光临网上图书商城,
				<s:if test="#session.loginUser==null">
				请<a href="${pageContext.request.contextPath}/testAction_loginPage">登录</a>
					<a href="${pageContext.request.contextPath}/testAction_registPage">
						成为会员</a>
				</s:if>

				<s:if test="#session.loginUser!=null">
			<a href="${pageContext.request.contextPath}/testAction_usercenterPage"><img style="border-radius: 20px;"  height="20" width="20" align="top" title="用户：${session.loginUser.nickname}" src="${pageContext.request.contextPath}/${session.loginUser.head_img}"/></a>
				
					<a href="${pageContext.request.contextPath}/userAction_logout">
						退出</a>
				</s:if>
			</div>
			<div class="cls"></div>
		</div>
		<div class="nav">
			<div class="logo_img">
				<a href="${pageContext.request.contextPath}/index.jsp"><img class="" style="width: 160px;height: 60px; cursor: pointer;" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
			<div class="cls"></div>
		</div>
		<div class="main">
		
		<!-- 直接跳转 -->
		
			<%
				String url = request.getContextPath() + "/testAction_loginPage";
				response.sendRedirect(url);
			%>
		
			${error}
			<br>
			<s:if test="#session.loginUser==null">
			您还未登陆，
				请<a href="${pageContext.request.contextPath}/testAction_loginPage">登录</a>
			</s:if>
		</div>
		<div class="cls"></div>
		<div class="foot">
			<div class="info"></div>
		</div>

	</div>
	<s:debug></s:debug>
</body>
</html>