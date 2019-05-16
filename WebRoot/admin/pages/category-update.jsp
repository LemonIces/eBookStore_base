<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin.js"></script>
</head>
<body>

	<form action="${pageContext.request.contextPath }/admin/adminAction_categoryUpdate.action" method="post" >
	
		<input type="hidden" name="page" value="${param.page}"/>

		<input type="hidden" name="bookcategory1_id" value="${category.bookcategory1_id}"/>
		
		名称： <input type="text" name="category_name" value="${category.name}" required="required"/><br>
		介绍：
		<textarea rows="2" cols="30" name="category_memo">${category.memo}</textarea>
		<%-- <input type="text" name="category_memoe" value="${category.memo}"/> --%><br>
						   <br>
	   <input type="button" onclick="confirm_back_btn();" value="返回"/>
		<input type="submit" onclick="return confirm_btn_tf('确定要修改吗？')" value="修改"/>
		
		${msg}
		
	</form>
	
</body>
</html>
