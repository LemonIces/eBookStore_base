<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<form action="userReset.action" method="post" >

		<input type="hidden" name="user.id" value="${user.id}"/>
		
		用户: ${user.username}<br>
		密码：<input type="text" name="user.password" value="" required="required"/>
		
		<input type="submit" value="重置"/>
		
		${msg}
		
	</form>
	
</body>
</html>
