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
			<div class="user_center_main_right" >
				<div class="div_main_in_center">
				<div style="color: #666666; font-size: 20px;">我的收货地址
					<div style="height: 8px;"></div>
				</div>
					<!-- ------------------- -->
					<table style="width: 100%; background-color: #e0e0e0;">
						<tr>
							<td><a
								href="${pageContext.request.contextPath}/testAction_add_addressPage">+新增收货地址</a>
							</td>
						</tr>
					</table>

					<div class="address_div">
					<!-- 便利收货地址 -->
					<s:iterator value="addressList" var="item">
						<div class="address_item">
							<span class="font16 ">姓名：<s:property value='#item.name' /></span>&nbsp;&nbsp;&nbsp;
							<span class="font16 ">性别：<s:property value='#item.gender' /></span>&nbsp;&nbsp;&nbsp;
							<span class="font16 ">联系电话：<s:property value='#item.phone' /></span>&nbsp;&nbsp;&nbsp;
							<br />
							<div class="cls"></div>
							<div style="float: left;">收货地址：</div>
							<div style="float: left;"> 
							<textarea  style="resize: none;height:55px;width:600px;border: none;background-color: #f0eff0; overflow: hidden;" readonly="readonly">${ item.address}</textarea>
							</div>
								<!-- id -->
								<input  type="hidden" name="user_address_id" value="<s:property value='#item.user_address_id' />" />
							
							<span><a onclick='changeAddress(this,"<s:property value='#item.user_address_id' />")'
									href="javascript:void(0)" style="position: absolute; right: 50px; top: 5px;">修改</a></span> 
									<span>
									<a onclick='deleteAddress(this,"<s:property value='#item.user_address_id' />")' href="javascript:void(0)"
									style="position: absolute; right: 10px; top: 5px;">删除</a></span>
							</div>
							<!--  -->
							<script type="text/javascript">
								//修改地址
								function changeAddress(obj,id) {
									console.log("changeAddress.............");
									console.log("id="+id);
									console.log(obj);
									
									//调到修改页面
									var loc = "${pageContext.request.contextPath}/testAction_changeAddressPage";
									
									window.location.href=loc+"?user_address_id="+id;
									
									
								}	
								//删除地址
								function deleteAddress(obj,id) {
									console.log("deleteAddress.............");
									console.log("id="+id);
									console.log(obj);
									if (confirm("确定要删除吗？")) {
										//进行删除操作
										var loc = "${pageContext.request.contextPath}/userAction_delete_address";
										
										window.location.href=loc+"?user_address_id="+id;
									}

								}
							</script>
							
					</s:iterator>
					</div>

				</div>
			</div>
			<div class="cls"></div>
		</div>
		<div class="cls"></div>
<jsp:include page="in_foot.jsp"></jsp:include>
	</div>
</body>
</html>