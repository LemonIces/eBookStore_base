<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.le.ebook.domain.User"%>
<!DOCTYPE html>
<html>
<head>
<title>我的书城</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/usercenter.css" />
<script type="text/javascript" src='${pageContext.request.contextPath }/js/find.js'></script>
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
				<div style="color: #666666; font-size: 20px;">修改邮箱
				<div style="height: 8px;"></div>
				</div>
				<div id="old_email_div"  <c:if test="${user.email == null || user.email == ''}">style="display: none;"</c:if> >
					<p>验证原邮箱</p>
					 邮 箱： <input type="email" name="old_email" id="old_email" value="${user.email }"  required="required" readonly="readonly" disabled="disabled"  />
					<input type="button" onclick="get_code(this,1)" value="获取" /><br>
					验证码：<input type="text" required="required" id="old_email_check_code" name="old_email_check_code" /><br>
					<input type="button" onclick="confirm_change(1)" value="确定" />
				</div>
				<div id="new_email_div"  <c:if test="${user.email != null && user.email != ''}">style="display: none;"</c:if> >
					<p>验证新邮箱</p>
					新邮箱：<input type="email" name="new_email" id="new_email"  required="required"  />
					<input type="button" onclick="get_code(this,2)" value="获取" /><br>
					验证码：<input type="text" required="required" id="new_email_check_code" name="new_email_check_code" /><br>
					<input type="button" onclick="confirm_change(2)" value="确定修改" />
				</div>
				<br />
				<div>
					<p id="errTd" style="color: red;"></p>
				</div>
				<script type="text/javascript">
				function get_code(obj,mode) {
					console.log(obj);
					console.log(mode);
					//清空提示信息
					var errTd = document.getElementById("errTd");
					errTd.innerHTML = "";	

					
					
					//给原始邮箱发送验证码
					if(mode==1){
						var email = document.getElementsByName("old_email")[0].value;
						
						if(email ==null || email == undefined ||""==email.trim()){
							errTd.innerHTML="邮箱为空!";
							return false;
						}
						
						else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
							errTd.innerHTML="邮箱格式不正确!";
							return false;
						}
						
						
						obj.disabled = "disabled";
						obj.value="(60s)";
						clocks(obj,60);
						
						//发送消息
						$.post("${pageContext.request.contextPath}/userAction_send_ChangeEmail2OldEmail",
									{"email":email
									}, function(data) {
										console.log(data['error']);
										var state = data["state"];
										
										if(state == "true"){
											alert("发送成功");

										}else{
											errTd.innerHTML= data['error'];
										}
										
								}, "json");
				
					}else if(mode == 2){//给信邮箱发送验证码
						
						var email = document.getElementsByName("new_email")[0].value;
						
						if(email ==null || email == undefined ||""==email.trim()){
							errTd.innerHTML="新邮箱为空!";
							return false;
						}
						
						else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
							errTd.innerHTML="新邮箱格式不正确!";
							return false;
						}
						
						
						obj.disabled = "disabled";
						obj.value="(60s)";
						clocks(obj,60);
						
						//发送消息
						$.post("${pageContext.request.contextPath}/userAction_send_ChangeEmail2NewEmail",
									{"email":email
									}, function(data) {
										console.log(data['error']);
										var state = data["state"];
										
										if(state == "true"){
											alert("发送成功");
											
										}else{
											errTd.innerHTML= data['error'];
										}
										
							}, "json");
						
					}
	
				}



				function clocks(obj,sed) {
					if(tId!=null && tId != undefined){
						clearInterval(tId);
					}
					var count = sed;
					var tId= setInterval(()=>{
						//计算秒数
						count--;
						obj.value="("+count+"s)";
						if(count <= 0){
							//停止计时
							clearInterval(tId);
							tId = null;
							obj.disabled = "";
							obj.value = "重新发送";
						}
					}, 1000);
				}

				//确定修改邮箱
				function confirm_change(mode) {
					console.log("confirm_change............"+mode);
					
					//获取显示控件
					var old_email_div = document.getElementById("old_email_div");
					var new_email_div = document.getElementById("new_email_div");
					
					if(mode==1){
						if (confirm("是否要进行下一步！")) {
							var checkcode = document.getElementById("old_email_check_code").value;
							if(checkcode ==null || checkcode == undefined ||""==checkcode.trim()){
								errTd.innerHTML="验证码为空!";
								return false;
							}
							
							//发送消息
							$.post("${pageContext.request.contextPath}/userAction_check_changeEmailOldCode",
										{"checkcode":checkcode
										}, function(data) {
											console.log(data['error']);
											var state = data["state"];
											
											if(state == "true"){
												alert("验证通过，请填写新邮箱地址！");
												//显示控件可见性
												old_email_div.style.display = "none";
												new_email_div.style.display = "block";
												
											}else{
												//有误
												errTd.innerHTML= data['error'];
											}
											
									}, "json");
							
						}
					}
					else if(mode == 2){
						
						if (confirm("确定要修改码？")) {
							
							var new_email = document.getElementsByName("new_email")[0].value;
							
							if(new_email ==null || new_email == undefined ||""==new_email.trim()){
								errTd.innerHTML="新邮箱为空!";
								return false;
							}
							
							else if(new_email != null && new_email !=undefined && !new_email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
								errTd.innerHTML="新邮箱格式不正确!";
								return false;
							}
							
							
							var checkcode = document.getElementById("new_email_check_code").value;
							if(checkcode ==null || checkcode == undefined ||""==checkcode.trim()){
								errTd.innerHTML="验证码为空!";
								return false;
							}
							
							//发送消息
							$.post("${pageContext.request.contextPath}/userAction_check_changeEmailNewCodeAndChangeEmail",
										{"checkcode":checkcode,"new_email":new_email
										}, function(data) {
											console.log(data['error']);
											var state = data["state"];
											
											if(state == "true"){
												alert("修改成功！");
												//刷新到个人中心页面
												window.location.href="${pageContext.request.contextPath}/testAction_usercenterPage";
											}else{
												//有误
												errTd.innerHTML= data['error'];
											}
											
							}, "json");
							
						}
						
					}
					
					
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