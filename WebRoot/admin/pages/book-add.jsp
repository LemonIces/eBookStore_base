<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="js/jqueryui/css/jquery-ui-1.10.2.css" />
<script src="js/jqueryui/js/jquery-1.9.1.js"></script>
<script src="js/jqueryui/js/jquery-ui-1.10.2.js"></script>
<script src="js/jqueryui/js/datepicker-zh-CN.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<script>
$(function() {
	// 初始化日期插件
	$( "#datepicker" ).datepicker({
		//showButtonPanel: true,//显示按钮栏
		//showOtherMonths: true,//显示其他月份
		//changeMonth: true,//显示月份菜单
		//changeYear: true,//显示年份菜单
		//numberOfMonths: 3,//显示月份数量
		//showOn: "button",//显示按钮
		//buttonImage: "jqueryui/css/images/calendar.gif",//图片地址
		//buttonImageOnly: true,//是否按钮只显示为图片
		//minDate: -3,//开始日期(当天为0)
		//maxDate: "+1Y +1M +1D",//今天之后的天数
	});
	$( "#datepicker" ).datepicker( "option", "zh-CN");
	$( "#datepicker" ).datepicker('setDate', new Date());	//设置初识日期
});
 </script>
 <style type="text/css">
 	#error_img{
 		font-size: 12px;
 		color: red;
 	}
 	#error_span_price{
 		font-size: 12px;
 		color: red;
 	}
 	 #error_span_name{
 		font-size: 12px;
 		color: red;
 	}

 	#error_span_count{
 		font-size: 12px;
 		color: red;
 	}
 	 #error_span_memo{
 		font-size: 12px;
 		color: red;
 	}
 	 	#error_span_discount{
 		font-size: 12px;
 		color: red;
 	}

 </style>
</head>
<body>

	<form action="${pageContext.request.contextPath }/admin/adminAction_addBook.action" method="post" enctype="multipart/form-data">

		封面： <input type="file" name="book_img" size="12" required="required" onchange="file_select(this)" /> <span id="error_img">${error}</span><br>
		书名： <input type="text" name="book_name" required="required" onblur="check_name()" /> <span id="error_span_name"></span><br>
		价格： <input type="text" name="book_price" required="required" onblur="check_price()" /> <span id="error_span_price"></span><br>
		介绍：<!-- <input type="text" name="book_memo" required="required"/><br> -->
		<textarea rows="3" cols="30"  name="book_memo" onblur="check_memo()" ></textarea><span id="error_span_memo"></span><br>
		作者： <input type="text" name="book_author"/><br>
		总字数：<input type="number" name="book_word_count"/><br>
		总页数：<input type="number" name="book_total_page"/><br>
		出版社：<input type="text" name="book_publishing"/><br>
		出版日期：<input type="text" id="datepicker" name="book_publish_time"/><br>
		数量：<input type="number" id="datepicker" value="1" name="book_count" required="required"  onblur="check_count()" /> <span id="error_span_count"></span><br>
		折扣：<input type="text" id="datepicker" value="1" name="book_discount" required="required"  onblur="check_discount()" /> <span id="error_span_discount"></span><br>
		最新：<input type="radio" checked="checked" value="1" name="book_news"/>是<input type="radio" value="2" name="book_news"/>否<br>
		热门：<input type="radio" value="1" name="book_hots"/>是<input type="radio" checked="checked" value="2" name="book_hots"/>否<br>
		图书分类:  <select name="book_bookcategory1_id">
						<c:forEach var="category" items="${bookcategoryList}">
							<option value="${category.bookcategory1_id}">${category.name}</option>
						</c:forEach>
					   </select>
					   <br/>
		<input type="button" onclick="confirm_back_btn();" value="返回"/>
		<input type="submit" onclick="return check();" value="添加"/>
		
	</form>
	
	${msg}
	<script type="text/javascript">
	
	//选择图片
	function file_select(obj){
		//保存暂时的路径
		temp_img_path = obj.value;
		var error_img = document.getElementById('error_img');
		console.log("temp_img_path=" + temp_img_path);
		error_img.innerHTML = "";
		var exet = temp_img_path.substr(temp_img_path.lastIndexOf("."));
		if(exet.toLowerCase() != ".jpg" &&  exet.toLowerCase() != ".jpeg" &&  exet.toLowerCase() != ".png" &&  exet.toLowerCase() != ".gif"&&  exet.toLowerCase() != ".bmp"){
			obj.value="";
			error_img.innerHTML = "仅支持jpg,jpeg,gif,png,bmp格式";
			return;
		} 
	}
	
	//检查价格
		function check_price() {
			document.getElementById('error_span_price').innerText="";
			var pri = -1;
			var pStr = document.getElementsByName('book_price')[0].value;
			pri = Number(pStr);
			if(!pri){
				document.getElementById('error_span_price').innerText="价格输入有误！";
				return false;
			}
			return true;
		}
		//检查书名
		function check_name() {
			document.getElementById('error_span_name').innerText="";
			var pStr = document.getElementsByName('book_name')[0].value;
			if(pStr==null || pStr == undefined || pStr.trim() == ""){
				document.getElementById('error_span_name').innerText="书名输入有误！";
				return false;
			}
			if(pStr.length > 200){
				document.getElementById('error_span_name').innerText="书名最多200个字符长度！";
				return false;
			}
			return true;
		}
		
		//检查简介
		function check_memo() {
			document.getElementById('error_span_memo').innerText="";
			var pStr = document.getElementsByName('book_memo')[0].value;
			if(pStr==null || pStr == undefined || pStr.trim() == ""){
				/* document.getElementById('error_span_memo').innerText="书名输入有误！";
				return false; */
			}
			if(pStr!=null && pStr != undefined && pStr.length > 10240){
				document.getElementById('error_span_memo').innerText="介绍最多10240个字符长度！";
				return false;
			}
			return true;
		}
		
		//检查数量
		function check_count() {
			document.getElementById('error_span_count').innerText="";
			var pStr = document.getElementsByName('book_count')[0].value;
			if( pStr!=null  && pStr == "0"){
				
			}else{			
				if(pStr==null || pStr == undefined || pStr.trim() == "" || !Number(pStr)){
					document.getElementById('error_span_count').innerText="数量输入有误！";
					return false;
				}
				if(Number(pStr) < 0){
					document.getElementById('error_span_count').innerText="数量必须大于等于0个！";
					return false;
				}
				if(Number(pStr) >=100000000){
					document.getElementById('error_span_count').innerText="数量必须小于100000000个！！";
					return false;
				}
			}
			return true;
		}
		
		//检查折扣
		function check_discount() {
			document.getElementById('error_span_discount').innerText="";
			var pStr = document.getElementsByName('book_discount')[0].value;
			if(pStr==null || pStr == undefined || pStr.trim() == "" || !Number(pStr)){
				document.getElementById('error_span_discount').innerText="折扣输入有误！";
				return false;
			}
			var index = pStr.indexOf(".");
			if(pStr.length > (index+3)){
				document.getElementById('error_span_discount').innerText="折扣最多保留两位有效数字！";
				return false;
			}
			if(Number(pStr) <=0 || Number(pStr) >10){
				document.getElementById('error_span_discount').innerText="折扣必须在0~10之间！";
				return false;
			}
			return true;
		}
		
		//检查表单
		function check() {
			
			if(!confirm("确定要添加吗？")){
				return false;
			}
			
			if(!check_name()){
				return false;
			}
			if(!check_price()){
				return false;
			}
			if(!check_memo()){
				return false;
			}
			if(!check_count()){
				return false;
			}
			if(!check_discount()){
				return false;
			}
			return true;
		}
	</script>
	
</body>
</html>
