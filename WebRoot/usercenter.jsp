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
	
	<jsp:include page="in_top.jsp"></jsp:include>
	
		<div class="main">
			<div class="user_center_main_left">
			
			<jsp:include page="in_usercenter_nav.jsp"></jsp:include>

			</div>
			<div class="user_center_main_right">
				<div class="div_main_in_center">
				<form action="" id="form1" method="post" accept=".jpg,.jpeg" enctype="multipart/form-data">				
					<div>
					
						<img   id="head_img" style="height: 96px; width: 90px; float: left;display: none;" src="${pageContext.request.contextPath }/<s:property value='upload_img_temp' />" />
						
						<img   id="head_img1" style="height: 96px; width: 90px; float: left; display: none;" src="${pageContext.request.contextPath }/<s:property value='#session.loginUser.head_img' />" />
						
						
						
						<input type="hidden" name="head_img" value="${pageContext.request.contextPath }/<s:property value='upload_img_temp' />" />
					</div>
					<div style="float: left; height: 96px; margin-left: 20px;">
						<div style="height: 50px;"></div>
						
						
						<input type="file" name="img_file" accept="image/gif, image/jpeg"  onchange="file_select(this)" />
						
						<br /> 
						
						<input
							type="button" id="upload_btn" onclick="upload_img(this)"
							disabled="disabled" value="上传" /> 
							
							
						<input type="button"   value="保存头像" <s:if test="#upload_img_temp==null">disabled="disabled"</s:if>  onclick="save_img(this)" />
						<span id="error_img">
							${error}
						</span>
						
						<script type="text/javascript">
						var head_img = document.getElementById("head_img");
						var head_img1 = document.getElementById("head_img1");
						var temp = "${pageContext.request.contextPath}/"
						var val = document.getElementsByName("head_img")[0].value;

							if(val != head_img.src && temp == val){
								head_img.style.display = "none";
								head_img1.style.display = "";
							}else{
								head_img.style.display = "";
								head_img1.style.display = "none";
							}
							
							
						
							//上传图片
							function upload_img(obj) {
								console.log("upload_img "+ obj);
								
								//获取表单
								var form1 = document.getElementById('form1');
								
								//设置表单提交的路径
								var url1 = "${pageContext.request.contextPath}/userAction_uploadImg";
								form1.action=url1;
								
								
								
								console.log(form1.action);
								//
								//进行上传图片操作
								
								form1.submit();

								//上传成功后设置保存头像按钮可用

								//否则出现提示上传失败提示信息

							}

							//选择图片
							function file_select(obj){
								//保存暂时的路径
								temp_img_path = obj.value;
								var upload_btn = document.getElementById('upload_btn');
								var error_img = document.getElementById('error_img');
								console.log("temp_img_path=" + temp_img_path);
								error_img.innerHTML = "";
								var exet = temp_img_path.substr(temp_img_path.lastIndexOf("."));
								if(exet.toLowerCase() != ".jpg" &&  exet.toLowerCase() != ".jpeg" &&  exet.toLowerCase() != ".png" &&  exet.toLowerCase() != ".gif"&&  exet.toLowerCase() != ".bmp"){
									alert("不支持的图片的格式！");
									obj.value="";
									upload_btn.disabled = "disabled";
									error_img.innerHTML = "仅支持jpg,jpeg,gif,png,bmp格式";
									return;
								} 
								//设置上传按钮可用
								upload_btn.disabled = "";

							}
							
							//保存头像
							function save_img(obj) {
								console.log(obj);

								if(confirm("确定要修改头像吗？")){
									
									var head_img = document.getElementById("head_img");
									var form1 = document.getElementById('form1');
									save_url = "${pageContext.request.contextPath}/userAction_updateHead_img";
									form1.action = save_url;
									form1.submit();
								}

													
							}
						</script>

						<div class="cls"></div>
					</div>
					<div class="cls"></div>
					<div>
						<h2>基本信息</h2>
					</div>
					<div>
						<table>
							<tr>
								<td><p class="title1_p">用户名：</p></td>
								<td><input class="input_text1"  type="text" disabled="disabled"  onblur="check_one()"  value="<s:property value='#session.loginUser.username' />"  name="username" /></td>
								<td><p id="username_error_p"></p></td>
							</tr>
							<tr>
								<td><p class="title1_p">昵称：</p></td>
								<td><input class="input_text1"  type="text"   onblur="check_one()" value="<s:property value='#session.loginUser.nickname' />" name="nickname" /></td>
								<td><p id="nickname_error_p"></p></td>
							</tr>
							<tr>
								<td><p class="title1_p">性别：</p></td>
								<td><input type="radio" name="gender" value="男"  <% User u =(User) session.getAttribute("loginUser"); if("男".equals(u.getGender())){out.write("checked='checked'");} %> />男 
									<input	type="radio" name="gender" value="女"  <%  if("女".equals(u.getGender())){out.write("checked='checked'");} %>  />女</td>
								<td><p id="gender_error_p"></p></td>
							</tr>
							<tr>
								<td><p class="title1_p">邮箱：</p></td>
								<td><input onblur="check_one()" disabled="disabled" class="input_text1" type="text"  value="<s:property value='#session.loginUser.email' />" name="email" /></td>
								<td><p id="email_error_p"></p></td>
							</tr>
							<tr>
								<td><p class="title1_p">手机：</p></td>
								<td><input class="input_text1" type="text" onblur="check_one()"  value="<s:property value='#session.loginUser.phone' />" name="phone" /></td>
								<td><p id="phone_error_p"></p></td>
							</tr>

							<tr>
								<td><p class="title1_p">个人简介：</p></td>
								<td><textarea class="texta_1" onblur="check_one()"  name="introduce"><s:property value='#session.loginUser.introduce' /></textarea></td>
								<td><p id="introduce_error_p"></p></td>
							</tr>

							<tr>
								<td></td>
								<td><input type="button" onclick="save_info(this)" value="保存基本信息" /></td>
							</tr>


						</table>
						<script type="text/javascript">
						
						var username_input = document.getElementsByName("username")[0];
						var username_error_p = document.getElementById("username_error_p");
						
						var nickname_input = document.getElementsByName("nickname")[0];
						var nickname_error_p = document.getElementById("nickname_error_p");
						
						var email_input = document.getElementsByName("email")[0];
						var email_error_p = document.getElementById("email_error_p");
						
						var phone_input = document.getElementsByName("phone")[0];
						var phone_error_p = document.getElementById("phone_error_p");
						
						var introduce_input = document.getElementsByName("introduce")[0];
						var introduce_error_p = document.getElementById("introduce_error_p");
						
				
							//保存基本信息'
							function save_info(obj) {
								console.log("save_info..... " + obj);

								if (confirm("确定要修改基本信息吗？")) {
									//检查表单
									if (check()) {
										//修改表单提交路径
										var form1 = document
												.getElementById('form1');

										var url1 = "${pageContext.request.contextPath}/userAction_updateInfo";

										form1.action = url1;

										//表单提交
										form1.submit();
									}

								}

							}

							//检查表单
							function check() {
								console.log("check......");

								var flag = true;

								if (username_input.value == undefined
										|| username_input.value.trim() == "") {
									username_error_p.innerHTML = "<font style='color:red;font-size:12px;'>请输入用户名！</font>";
									flag = false;
								}
								if (nickname_input.value == undefined
										|| nickname_input.value.trim() == "") {
									nickname_error_p.innerHTML = "<font style='color:red;font-size:12px;'>请输入昵称！</font>";
									flag = false;
								}
								if (nickname_input.value == undefined
										|| nickname_input.value.length > 20) {
									nickname_error_p.innerHTML = "<font style='color:red;font-size:12px;'>昵称超过20个字符！</font>";
									flag = false;
								}
								if(phone_input.value == ""){
									
								}
								else if(parseInt(phone_input.value) != phone_input.value){
										phone_error_p.innerHTML = "<font style='color:red;font-size:12px;'>手机号只能是数字！</font>";
										flag = false;
								}else if (phone_input.value.length > 20) {
									phone_error_p.innerHTML = "<font style='color:red;font-size:12px;'>手机号超过20个数字！</font>";
									flag = false;
								}
								if (introduce_input.value.length > 100) {
									introduce_error_p.innerHTML = "<font style='color:red;font-size:12px;'>个人简介超过100个字符！</font>";
									flag = false;
								}
								return flag;
							}

							function check_one() {
								console.log("check_one......");

								username_error_p.innerHTML = "";
								nickname_error_p.innerHTML = "";
								email_error_p.innerHTML = "";
								phone_error_p.innerHTML = "";
								introduce_error_p.innerHTML = "";

								if (username_input.value == undefined
										|| username_input.value.trim() == "") {
									username_error_p.innerHTML = "<font style='color:red;font-size:12px;'>请输入用户名！</font>";
								}
								if (nickname_input.value == undefined
										|| nickname_input.value.trim() == "") {
									nickname_error_p.innerHTML = "<font style='color:red;font-size:12px;'>请输入昵称！</font>";
								}
								if(phone_input.value == ""){
									
								}
								else if(parseInt(phone_input.value) != phone_input.value){
									phone_error_p.innerHTML = "<font style='color:red;font-size:12px;'>手机号只能是数字！</font>";
									flag = false;
								}else if (phone_input.value.length > 20) {
								phone_error_p.innerHTML = "<font style='color:red;font-size:12px;'>手机号超过20个数字！</font>";
								flag = false;
								}
								if (introduce_input.value.length > 100) {
									introduce_error_p.innerHTML = "<font style='color:red;font-size:12px;'>个人简介超过100个字符！</font>";
								}

							}
						</script>
						
					</div>
				</form>

				</div>
				<s:fielderror></s:fielderror>
				<div class="cls20"></div>
				<div class="cls20"></div>
				<div class="cls20"></div>
				<div class="cls20"></div>
			</div>
			<div class="cls"></div>
		</div>
		<div class="cls"></div>
<jsp:include page="in_foot.jsp"></jsp:include>
	</div>
</body>
</html>