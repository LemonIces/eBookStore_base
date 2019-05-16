<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>重置密码成功</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/mallcar.css" />
<script type="text/javascript">
</script>
</head>
<body onload="init()" style="background-color: white;">
			<script type="text/javascript">
			function init() {
			if(confirm("重置密码成功！立即登录？")){
				window.location.href="${pageContext.request.contextPath}/testAction_loginPage";
			}else{
				window.location.href="${pageContext.request.contextPath}/index.jsp";
			} 
			}
			//	window.location.href="${pageContext.request.contextPath}/testAction_loginPage";
			</script>

	<%-- <div class="box">
		<div class="top">
			<div class="fl">
				欢迎光临网上图书商城,
				<s:if test="#session.loginUser==null">
				请<a href="${pageContext.request.contextPath}/testAction_loginPage">登录</a>
					<a href="${pageContext.request.contextPath}/testAction_registPage">
						成为会员</a>
				</s:if>

				<s:if test="#session.loginUser!=null">
					<a
						href="${pageContext.request.contextPath}/testAction_usercenterPage"><s:property
							value="#session.loginUser.username" /></a>
					<a href="${pageContext.request.contextPath}/userAction_logout">
						退出</a>
				</s:if>
			</div>
			<div class="cls"></div>
		</div>
		<div class="nav">
			<div class="logo_img">
				<a href="${pageContext.request.contextPath }/testAction_product"><img class="" style="width: 160px;height: 60px; cursor: pointer;" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
			<div class="cls"></div>
		</div>
		<div class="main">
			重置密码成功！
				<a href="${pageContext.request.contextPath }/testAction_loginPage">前往登录</a>
			
		</div>
		<div class="cls"></div>
		<div class="foot">
			<div class="info"></div>
		</div>

	</div> --%>
</body>
</html>