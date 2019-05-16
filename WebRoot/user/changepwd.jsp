<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.le.ebook.domain.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的书城</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />

<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/usercenter.css" />
	
<script language='javascript' src='${pageContext.request.contextPath }/js/find.js'></script>

<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
</head>
<body>
	<div class="box">
	<jsp:include page="../in_top.jsp"></jsp:include>
		<div class="main">
			<div class="user_center_main_left">
		<jsp:include page="../in_usercenter_nav.jsp"></jsp:include>

			</div>
			<div class="user_center_main_right">
				<div class="div_main_in_center">
				<div style="color: #666666; font-size: 20px;">修改密码
				<div style="height: 8px;"></div>
				</div>
				<form name="form1" action="${pageContext.request.contextPath}/userAction_changePwd" method="post">
					原始密码：<input type="password" name="old_pwd" /><br>
					新 密 码 ：<input type="password" name="pwd" /><br>
					确认密码：<input type="password" name="pwd1" /><br>
					<input type="hidden" value="${tempUser.user_id }" name="user_id"/>
					<input type="button" onclick="return checkPwd()" value="修改" />
					<br>
					<br>
					<p id="error_p" style="color:red;">${errorMap.error}</p>
					
				</form>
				<script type="text/javascript">
					function checkPwd() {
						if (!confirm("确定要修改吗？")){
							return false;
						}
						
						console.log("checkPwd");
						
						var old_pwd = document.getElementsByName('old_pwd')[0];
						var pwd = document.getElementsByName('pwd')[0];
						var pwd1 = document.getElementsByName('pwd1')[0];
						
						var error_p = document.getElementById('error_p');
						
						error_p.innerText="";
						
						if(old_pwd.value == null || old_pwd.value.trim() == ""){
							error_p.innerText="原始密码不能为空！";
							return;
						}
						
						if(pwd.value == null || pwd.value.trim() == ""){
							error_p.innerText="新密码不能为空！";
							return;
						}
						
						var reg = "^[a-zA-Z0-9]{5,20}$";
						if(!pwd.value.match(reg)){
							error_p.innerHTML="新密码必须为5-20个字符!";
							return;
						}
						if(pwd.value != pwd1.value){
							error_p.innerText="两次密码不一致！";
							return;
						}
						reg = "^[a-zA-Z0-9]{5,20}$";
						if(!pwd1.value.match(reg)){
							error_p.innerHTML="确认密码必须为5-20个字符!";
							return false;
						}
						
						var form1 = document.getElementsByName('form1')[0];
						form1.submit();
						
					}
				</script>
				</div>
			</div>
			<div class="cls"></div>
		</div>
		<div class="cls"></div>
<jsp:include page="../in_foot.jsp"></jsp:include>
	</div>
</body>
</html>