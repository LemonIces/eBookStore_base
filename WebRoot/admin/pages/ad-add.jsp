<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin.js"></script>
</head>
<body>

	<form
		action="${pageContext.request.contextPath }/admin/adminAction_adAdd.action"
		method="post" enctype="multipart/form-data">


		封面：<input type="file" name="ad_img" size="12" required="required" /><br>
		标题：<input type="text" name="ad_title" required="required" /><br>
		网址：<input type="text" name="ad_loc" required="required" /><br>
		状态：<input type="radio" name="ad_statu" checked="checked" value="1" />显示<input
			type="radio" name="ad_statu" value="2" />不显示<br> <input
			type="button" onclick="confirm_back_btn();" value="返回"> <input
			type="submit" onclick="return confirm_btn_tf('确定要添加吗？');" value="添加" />

	</form>

	${msg}

</body>
</html>
