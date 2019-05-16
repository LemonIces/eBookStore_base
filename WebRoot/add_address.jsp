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
					<!-- ------------------- -->
				<div style="color: #666666; font-size: 20px;">添加收货地址
					<div style="height: 8px;"></div>
				</div>
					<form action="${pageContext.request.contextPath}/userAction_add_address" name="form1" method="get" >
					<table >
						<tr>
							<td>
								<span>姓名:</span>
							</td>
							<td>
								<input class="input_add_address" type="text" name="name_addrform" />
							</td>														
							<td>
								
							</td>
						</tr>
						<tr>
							<td>
								<span>性别:</span>
							</td>
							<td>
								<input type="radio" name="gender_addrform" checked="checked" value="男"/>男
								<input type="radio" name="gender_addrform" value="女"/>女
								
							</td>														
							<td>
								
							</td>
						</tr>
						<tr>
							<td>
								<span>联系电话:</span>
							</td>
							<td>
								<input class="input_add_address" type="text" name="phone_addrform" />
							</td>														
							<td>
							</td>
						</tr>

							<tr>
								<td style="vertical-align: top;"><span>收货地址:</span></td>
								<td>
									<!-- <input type="text" name="address" /> --> <textarea
										style="height:130px;width:500px; padding: 5px;" name="address_addrform"></textarea>
								</td>
								<td></td>
							</tr>
							<tr>
							<td></td>
							<td>
							<input type="button" onclick="back()" value="返回" />
							<input type="button" onclick="add_address()" value="添加" />
							<script type="text/javascript">
								//返回
								function back() {
									if(confirm("确定返回吗？")){
										window.location.href="${pageContext.request.contextPath}/testAction_addressPage";
									}
								
								}
								//添加
								function add_address() {
									if(confirm("确定要添加吗？")){
										var form1 = document.getElementsByName("form1")[0];
										
										var  name_addrform =  form1.name_addrform.value;
										var  phone_addrform =  form1.phone_addrform.value;
										var  address_addrform =  form1.address_addrform.value;
										var error = document.getElementById("error");
										error.innerHTML = "";
										if(name_addrform==null ||name_addrform==undefined || name_addrform.length ==0){
											error.innerHTML = "收货人姓名为空！";
											return;
										}
										if(name_addrform.length > 20){
											error.innerHTML = "收货人姓名超过20个字符！";
											return;
										}
										if(phone_addrform==null ||phone_addrform==undefined || phone_addrform.length ==0){
											error.innerHTML = "收货人联系电话为空！";
											return;
										}
										if(!Number(phone_addrform)){
											error.innerHTML = "收货人联系电话输入有误！";
											return;
										}
										if(phone_addrform.length >20){
											error.innerHTML = "收货人联系电话超过20个字符！";
											return;
										}
										if(address_addrform==null ||address_addrform==undefined || address_addrform.length ==0){
											error.innerHTML = "收货地址为空！";
											return;
										}
										if(address_addrform.length > 200){
											error.innerHTML = "收货地址超过200个字符！";
											return;
										}
										document.getElementsByName('form1')[0].submit();
									}
								}
								
							</script>
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td id="error">
								${error }
							</td>
						</tr>
					</table>
				</form>
				</div>
			</div>
			<div class="cls"></div>
		</div>
		<div class="cls"></div>
		<div class="foot">
			<div class="info"></div>
		</div>
	</div>
</body>
</html>