<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.le.ebook.domain.Book" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>《${book.book_name }》</title>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/sty.css" />
<script language='javascript' src='${pageContext.request.contextPath }/js/find.js'></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	function changeCount(mode){
		var count = document.getElementsByName('count')[0].value;
		count*=1;
		if(mode==1){
			count++;
		}else if(mode==0){
			if(count>1)
			count--;
		}
		if(count < 1){
			count = 1;
		}
		document.getElementsByName('count')[0].value=count;
	}
	
	function addMallCar() {
		var form1 = document.getElementsByName('form1')[0];
		form1.action="${pageContext.request.contextPath}/mallCarAction_add";
		var product_id = form1.product_id.value;
		//var book_id = form1.book_id;
		var count = form1.count.value;
		
		console.log("product_id="+product_id+", count="+count);
 		//post请求修服务器完成添加操作
		$.post("${pageContext.request.contextPath}/mallCarAction_add",
					{
						"product_id":product_id,"count":count
					}, function(data) {
						console.log(data['state']);
						if(data["state"] == "true"){
							alert("添加到购物车成功！");
						}else if(data["state"] == "false_unlogined"){
							console.log(data['error']);
							//document.getElementById("top_long_a").click();
 							if(confirm("您还未登录，立即登录！")){
								document.getElementById("top_long_a").click();
							} 
						}else if(data["state"] == "false_book"){
							console.log(data['error']);
							alert(data['error']);
						}else if(data["state"] == "false_book"){
							console.log(data['error']);
							alert(data['error']);
						}else if(data["state"] == "false_mall"){
							console.log(data['error']);
							alert(data['error']);
						}
						
				}, "json"); 
		
	}
	
	function buy() {
		var count_input = document.getElementsByName('count')[0];
		
		var product_id_input = document.getElementsByName('product_id')[0];
		
		//var loc = "${pageContext.request.contextPath}/userAction_jiesuan?";
		var loc = "${pageContext.request.contextPath}/userAction_jiesuan";
		
		//var str = "products=";
		var str = "";
		var tem = product_id_input.value+","+count_input.value;	
				//拼凑信息
		str += tem;
		console.log(str);
		//开始向服务发送消息
		 		//post请求修服务器完成添加操作
		$.post(loc,
					{
						"products":str
					}, function(data) {
						console.log(data['state']);
						if(data["state"] == "true"){
							//跳转到确定订单页面
							window.location.href="${pageContext.request.contextPath}/jiesuan.jsp";
							
						}else if(data["state"] == "false_unlogined"){
							console.log(data['error']);
							if(confirm("您还未登录，立即登录！")){
								document.getElementById("top_long_a").click();
							}
						}else if(data["state"] == "false_count"){
							console.log(data['error']);
							alert(data['error']);
						}else if(data["state"] == "false_down"){
							console.log(data['error']);
							alert(data['error']);
						}
						
				}, "json");
/* 				var buy_now_a = document.getElementById("buy_now_a");
				buy_now_a.href=loc+str;
				buy_now_a.click(); */
				//window.location.href=loc+str;
		
	}
</script>
</head>
<body>
	<div class="box">
	<jsp:include page="in_top.jsp"></jsp:include>
		<div class="main">
		<div>
			<div class="fl detail1 padding10">
				<img class="img1"  src="${pageContext.request.contextPath}/<s:property value="#book.img"/>">
			</div>
			<div class="fl detail2  padding10">
			
			<form action="" name="form1" method="post">
				<div class="font20 padding5" style="color: black;font-weight: bold;">${book.book_name}</div>
				<div class="font16 padding5" style="color: #666666;font-weight: bold;max-height: 100px;overflow: hidden;"><% Book boo =(Book)request.getAttribute("book");if(boo!=null && boo.getMemo().length()>140){out.write( boo.getMemo().substring(0,140)+"......");}else{out.write( boo.getMemo());}; %></div>
				<div style="height: 8px;"></div>
			<div class="info1">
			<span>作者： <s:property value="#book.author"/></span>
			<span>出版社： <s:property value="#book.publishing"/></span>
			<span>出版时间： <s:property value="#book.publish_time"/></span>
			<input type="hidden" name="book_id" value="<s:property value="#book.book_id"/>"/>
			<input type="hidden" name="product_id" value="<s:property value="#book.book_id"/>"/>
			</div>
			<div class="padding5">
				<div>
					价格： <span class="red font30">￥${String.format("%.2f",book.price*book.discount*0.1) }</span> 
					<span class="red font14">(<s:property value="#book.discount" />折)</span>
					定价：<span class="font14 textdelete">￥<s:property value="#book.price" /></span>
					&nbsp;&nbsp;&nbsp;
					库存：<span class="font14"><s:property value="#book.count" />本</span>
				</div>
			</div>
			<div>
				<div style="height: 8px;"></div>
				<span>
					<a onclick="changeCount('0')" href="javascript:void(0)">-</a>
					<input class="countin"  type="text" name="count"  value="1"/>
					<a onclick="changeCount('1')" href="javascript:void(0)">+</a>			
					&nbsp;&nbsp;
					<input onclick="addMallCar()" style="background-color: #ff2832;padding: 5px 15px; color: #fff;border: 1px solid #ff2832;border-radius: 5px;cursor: pointer;" type="button"  value="加入购物车"/>
					&nbsp;&nbsp;<input onclick="buy()"  style="background-color: #ffedee;padding: 5px 15px; color: #ff2832;border: 1px solid #ff2832;border-radius: 5px;cursor: pointer;" type="button"   value="立即购买"/>
				</span>
			</div>
			</form>
			</div>
			</div>
			<div class="cls10"></div>
			<div style="padding: 10px 20px;min-height: 220px;">
			<span class="span_sty1">详情介绍</span>
				<div style="border:1px solid #dddddd;min-height: 220px; padding: 5px;10px;">
					<div  >
						<div class="font16 padding5" style=" color: #666666;font-weight: bold;"><s:property value="#book.memo"/></div>
					</div>
				</div>
			</div>
		</div>
		<div class="cls"></div>
<jsp:include page="in_foot.jsp"></jsp:include>
	</div>
</body>
</html>