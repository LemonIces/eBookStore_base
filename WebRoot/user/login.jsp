<%@page import="com.le.ebook.utils.VerifyCode"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<title>登录界面</title>
<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath }/css/logincss.css" >
<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath }/css/sty.css" >
</head>
<body>
<div class="loginbox">
	<div class="top">
			<div class="logo_img">
				<a href="${pageContext.request.contextPath }/index.jsp"><img class="" style="width: 160px;height: 60px; cursor: pointer;" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
	</div>
	<div class="main">
		<div class="left"></div>
		<div class="right">

			<div class="userinfo">
				<form action="${pageContext.request.contextPath}/userAction_login" method="post">
					<div class="title1">用户登录</div>
					<div class="divin1"></div>
					<div class="divin2"><input class="in1" value='<s:property value="#tempInfoMap['username']"  />' name="username" id="username"  type="text"></div>
					<div class="divin1">
					</div>
					<div class="divin2"><input class="in1" name="password" type="password" id="password"></div>
					<div class="divin1"></div>
					<div  style="text-align: left;line-height: 35px;height: 35px;" >验证码：
						<input  style="width: 80px;height: 25px;" type="text" name="check_code" id="check_code" />
						<img id="code_img"  align="top" height="35" width="70" src="${pageContext.request.contextPath}/testAction_verifyCodeServlet">
						<a  href="javascript:changeCode()">看不清,换一张</a>
						<script type="text/javascript">
							function changeCode(){
								console.log("changeCode....");
								var code_img = document.getElementById('code_img');
								code_img.src="${pageContext.request.contextPath}/testAction_verifyCodeServlet?a="+new Date().getTime();
								
							}
							
							function checkCode(){
								console.log("checkCode....");
								var check_code = document.getElementById('check_code');
								var errorCode = document.getElementById('errorCode');
								
								var password_in = document.getElementById('password');
								var username = document.getElementById('username').value;
								
								errorCode.innerText="";
								var check_code_str  = check_code.value.toLowerCase();
								var password  = password_in.value;
								
								if(username == null ||username == ""){
									errorCode.innerText="用户名为空!";
									return false;
								}
								
								if(password == null ||password == ""){
									errorCode.innerText="密码为空!";
									return false;
								}
								
								if(check_code_str == null ||check_code_str == ""){
									errorCode.innerText="验证码为空!";
									return false;
								}

								return true;
							}
						</script>												
					</div>
					<div class="divin1">
					</div>
					<div class="divin1">
						<span class="fl font12"><input type="checkbox" name="autoLogin" value="autoLogin">七天内自动登录?</span>
						<span class="fr a1"><a href="${pageContext.request.contextPath}/user/reset_pwd.jsp">忘记密码?</a></span>
					</div>
					<div class="divin3"><input class="in1"  onclick="return checkCode()" type="submit" value="登录"></div>
					<div class="divin1"><span class="fr a1"><a href="${pageContext.request.contextPath}/testAction_registPage">注册新用户</a></span></div>
					<div class="divin1" style="color: red;text-align: center;">
						<span id="errorCode">${errorMap.error} </span>
					</div>
				</form>
			</div>
		</div>
		<div class="cls"></div>
	</div>
	<div class="foot"></div>
</div>
</body>
</html>