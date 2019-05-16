<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>忘记密码</title>
<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath }/css/sty.css" >
<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath }/css/regist.css" >
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
			<div class="title1 textcenter">重置密码</div>
			<div class="cls20"></div>
			<form action="${pageContext.request.contextPath }/userAction_resetPwd" class="form1" method="post">
				<table class="tinfo">
					<tr>
						<td class="tdtitle">用户名</td>
						<td class="tdinput" colspan="2"><input type="text" name="username"  value="${infoMap.username }" /></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
				
	
					<tr>
						<td class="tdtitle">邮箱</td>
						<td class="tdinput" colspan="2"><input type="text" name="email"  value="${infoMap.email }" /></td>
						<td></td>
					</tr>
					
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
					<tr>
						<td class="tdtitle">验证码</td>
						<td class="tdchackcodeinput"><input  type="text" name="checkcode"  /></td>
						<td class="tdchackcode a1" style="text-align: left;">
							<input  style="padding: 5px 10px;" type="button" onclick="sendMsg(this)" id="send_btn"  value="发送" />
						</td>
					</tr>
					<tr>
						<td class="tdtipmsg" ></td>
						<td  class="tdtipmsg" id="">
						</td>
						<td></td>
						<td></td>
					</tr>
						<tr>
						<td class="tdtitle">新密码</td>
						<td class="tdinput" colspan="2"><input type="password"  name="password"  value="${infoMap.password }" /></td>
						<td></td>
						
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg"></td>
					</tr>
					<tr>
						<td class="tdtitle">确认密码</td>
						<td class="tdinput" colspan="2"><input type="password" name="password1"  /></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg">
						</td>
					</tr>
					
					<tr>
					<td>
						<div class="divin1"><span class="fr a1"><a href="${pageContext.request.contextPath}/user/login.jsp">返回登录</a></span></div>
					</td>
						<td colspan="3" class="submitbtn"><input class="" type="submit" onclick="return checkForm()" value="重置密码">
						</td>
						
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="" class="tdtipmsg">
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" id="error" style="color: red;"  class="tdtipmsg">
							${errorMap.error }
						</td>
					</tr>
					
				</table>
			</form>		
			
			<script type="text/javascript">
			function checkForm(){
				//清空提示信息
				var error = document.getElementById("error");
				error.innerHTML="";
				var flag = true;
				var username = document.getElementsByName("username")[0].value;
				if(username == null || username ==undefined || username.trim()==""){
					error.innerHTML="用户名为空!";
					return false;
				}
				var reg = "^[a-zA-Z0-9]{5,20}$";
				if(!username.match(reg)){
					error.innerHTML="用户名必须为5-20个字符!";
					return false;
				}
				
				var email = document.getElementsByName("email")[0].value;
				if(email ==null || email == undefined ||""==email.trim()){
					error.innerHTML="邮箱为空!";
					return false;
				}
				else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
					error.innerHTML="邮箱格式有误!";
					return false;
				}
				
				var checkcode = document.getElementsByName("checkcode")[0].value;
				if(checkcode == null || checkcode ==undefined || checkcode.trim()==""){
					error.innerHTML="验证码为空!";
					return false;
				}
				
				var password = document.getElementsByName("password")[0].value;
				if(password == null || password ==undefined || password.trim()==""){
					error.innerHTML="新密码为空!";
					return false;
				}
				reg = "^[a-zA-Z0-9]{5,20}$";
				if(!password.match(reg)){
					error.innerHTML="新密码必须为5-20个字符!";
					return false;
				}
				var password1 = document.getElementsByName("password1")[0].value;
				if(password1 == null || password1 ==undefined || password1.trim()==""){
					error.innerHTML="确认密码为空!";
					return false;
				}
				if(password != password1){
					error.innerHTML="两次密码不一致!";
					return false;	
				}
				
				reg = "^[a-zA-Z0-9]{5,20}$";
				if(!password1.match(reg)){
					error.innerHTML="确认密码必须为5-20个字符!";
					return false;
				}
				
				return true;
			}
			
			function clocks(sed) {
				if(tId!=null && tId != undefined){
					clearInterval(tId);
				}
				var count = sed;
				var tId= setInterval(()=>{
					//计算秒数
					count--;
					document.getElementById("send_btn").value="("+count+"s)";
					if(count <= 0){
						//停止计时
						clearInterval(tId);
						tId = null;
						document.getElementById("send_btn").disabled = "";
						document.getElementById("send_btn").value = "重新发送";
					}
				}, 1000);
			}
			
			
			function sendMsg(obj) {
				console.log(obj);
				
				var error = document.getElementById("error");
				error.innerHTML="";
				
				var username = document.getElementsByName("username")[0].value;
				if(username == null || username ==undefined || username.trim()==""){
					error.innerHTML="用户名为空!";
					return false;
				}
				var reg = "^[a-zA-Z0-9]{5,20}$";
				if(!username.match(reg)){
					error.innerHTML="用户名必须为5-20个字符!";
					return false;
				}

				var email = document.getElementsByName("email")[0].value;
				if(email ==null || email == undefined ||""==email.trim()){
					error.innerHTML="邮箱为空!";
					return false;
				}
				
				else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
					error.innerHTML="邮箱格式有误!";
					return false;
				}
				
				obj.disabled = "disabled";
				obj.value="(60s)";
				clocks(60);
				
				//发送消息
				$.post("${pageContext.request.contextPath}/userAction_sendResetPwdCode",
							{
								"username":username,"email":email
							}, function(data) {
								if(data["state"] == "true"){
									alert("发送成功！");
								}else{
									console.log(data['error']);
									document.getElementById("error").innerHTML= data['error'];
									alert(data['error']);
								}															

								
						}, "json");
				}
			</script>	
			
		</div>
		<jsp:include page="../in_foot.jsp"></jsp:include>
	</div>
</body>
</html>