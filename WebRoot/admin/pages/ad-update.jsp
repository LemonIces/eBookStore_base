<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin.js"></script>
</head>
<body>

	<form action="${pageContext.request.contextPath}/admin/adminAction_updateAd" method="post" enctype="multipart/form-data">

		<input type="hidden" name="ad_id" value="${ad.ad_id}"/>
		<input type="hidden" name="ad_img_pre" value="${ad.ad_img}"/>

		封面：<img src="../${ad.ad_img}" width="150px" height="150px">
		<br>
		修改: <input type="file" name="ad_img" size="12"/><br>
		标题：<input type="text" name="ad_title" value="${ad.ad_title }" required="required"/><br>
		网址：<input type="text" name="ad_loc" value="${ad.ad_loc }" required="required"/><br>
		状态：<input type="radio" name="ad_statu" 
		<c:if test="${ad.statu==1}">checked="checked"
								</c:if>
		 value="1"/>显示<input type="radio" 
		 <c:if test="${ad.statu==2}">checked="checked"
								</c:if>
		 name="ad_statu" value="2"/>不显示<br>
		<input type="button" onclick="confirm_back_btn();" value="返回">
		<input type="submit" onclick="return confirm_btn_tf('确定要修改吗？');" value="修改"/>
		
	</form>
	
	${msg}
	
</body>
</html>
