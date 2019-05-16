<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册新用户</title>
<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath }/css/sty.css" >
<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath }/css/regist.css" >
<script type="text/javascript" src="${pageContext.request.contextPath }/js/registuser.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
</head>
<body>
	<div class="box">
		<div class="top">
			<div class="logo_img">
				<a href="${pageContext.request.contextPath }/index.jsp"><img class="" style="width: 160px;height: 60px; cursor: pointer;" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
		</div>
		<div class="content">
			<div class="title1 textcenter">用户注册</div>
			<div class="cls20"></div>
			<form action="${pageContext.request.contextPath }/userAction_regist1" class="form1" method="post">
				<table class="tinfo">
					<tr>
						<td class="tdtitle">用户名</td>
						<td class="tdinput" colspan="2"><input type="text" name="username" value="${tempInfoMap.username }" onblur="checkUsername()" /></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
					<tr>
						<td class="tdtitle">密码</td>
						<td class="tdinput" colspan="2"><input type="password"  name="password"  value="${tempInfoMap.password }" onblur="checkPwd()" /></td>
						<td></td>
						
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
					<tr>
						<td class="tdtitle">确认密码</td>
						<td class="tdinput" colspan="2"><input type="password" name="password1"  onblur="checkPwd1()" /></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg">
						</td>
					</tr>
					<tr>
						<td class="tdtitle">邮箱</td>
						<td class="tdinput" colspan="2"><input type="text" name="email"  value="${tempInfoMap.email }"  onblur="checkEamil()" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
					<tr>
						<td class="tdtitle">验证码</td>
						<td class="tdchackcodeinput"><input  type="text" name="check_code"  /></td>
						<td class="tdchackcode a1" style="text-align: left;">
							<%-- 	<img id="code_img" src="${pageContext.request.contextPath}/testAction_verifyCodeServlet" /><a href="javascript:changeCode()">看不清换一张</a> --%>
							<input style="padding: 5px 10px;" type="button"
							onclick="sendMsg(this)" id="send_btn" value="发送" />
						</td>

					</tr>
					<tr>
						<td class="tdtipmsg" ></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
					<tr>
					<td>
						<div class="divin1"><span class="fr a1"><a href="${pageContext.request.contextPath}/user/login.jsp">返回登录</a></span></div>
					</td>
						<td colspan="3" class="submitbtn"><input class="" type="submit" onclick="return checkForm()" value="立即注册">
							
						</td>
						
					</tr>
					<tr>
						<td></td>
						<td colspan="3" class="tdtipmsg">
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="errTd" class="tdtipmsg" style="color: red;">
							${errorMap.error}
						</td>
					</tr>
				</table>
				<input  type="hidden" name="regist_uuid_code" value="${regist_uuid_code }"/>
			</form>			
			
		</div>
		<div class="foot"></div>
	</div>
</body>
</html>