<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>${search_text } 搜索结果</title>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/find_result.css" />

<script type="text/javascript" src='${pageContext.request.contextPath }/js/find.js'></script>
</head>
<body>
	<div class="box">
			<jsp:include page="in_top.jsp"></jsp:include>
		<div class="main">
			<div>
			
			<div class="top_category_div">
				<div class=""  style="
	background-color: #f0f9f6;
	padding: 5px;
">
					<span><a href="javascript:search_book(3,0)">全部商品</a></span>&gt;
					<s:if test="#category!=null"><span><a href="javascript:void(0)" onclick='search_book(3,${category.bookcategory1_id})'><s:property value='#category.name' /></a></span>&gt;</s:if>
					<s:if test="#search_text!=''"><span><a href="javascript:void(0)" onclick="search_book(0)"><s:property value='search_text' /></a></span></s:if>
					
					<input type="hidden" id="search_msg_temp" name="search_msg_temp"  value="${search_text }"/>
					<input type="hidden" id="search_category1_temp" name="search_category1_temp" value="${category.bookcategory1_id }" />
					
				</div>
				<div class="" style="
	background-color: #ffffff;
	padding: 5px;
">					<span class="category_span_title">选择分类：</span>
					<s:iterator value="#category1List" var="item">
						<span class="category_span">
							<a href="javascript:void(0)" onclick='search_book(1,<s:property value="#item.bookcategory1_id" />)'><s:property value='#item.name' /></a>
						</span>
					</s:iterator>
					<div class="cls10"></div>
				</div>
			</div> 
			<div style="
		background-color: #e5eaef;
			">
				<table class="top_table">
					<tr>
					<td>
						 <font style="color: black;font-size: 16px;">${bookMap.total_count}</font>个结果
					</td>
					<!-- 	<td><a href="">综合排序</a></td>
						<td><a href="">销量↓</a></td>
						<td><a href="">好评↓</a></td>
						<td><a href="">出版时间↓</a></td>
						<td><a href="">价格</a></td>
						<td>￥<input class="price_style" type="text" /> ￥<input class="price_style" type="text" /></td> -->
						<%-- <td>
						<span onclick="changePage(0,<s:property value='#findPage.currentPage'/>)" class="change_page_span">&lt;</span>
						<span><s:property value='#findPage.currentPage'/>/<s:property value='#findPage.totalPage'/></span>
						<span onclick="changePage(1,<s:property value='#findPage.currentPage'/>)" class="change_page_span">&gt;</span>
						</td> --%>
					</tr>
				</table>
			</div>
			</div>
			<div class="cls20"></div>
			<div style="min-height: 400px;">
				<ul>
					<c:forEach items="${ bookMap.list}" var="item">
					<li class="line1" >
						<div style="width: 160px;height: 200px;float: left;">
						<a	title="${item.book_name}"
						class="pic"
						  href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=${item.book_id}" target="_blank">
						<img
								style="height: 180px; width: 150px; border: 1px solid rebeccapurple;"
								src="${pageContext.request.contextPath}/${item.img}"
								alt="${item.book_name}">
						</a>
						
						</div >
						
						<div style="float: left;margin-left: 10px;">
						<p class="name" style="
						width: 600px;

						">
								<a href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=${item.book_id}"
								 target="_blank"> 
									${item.book_name}
								</a>
							</p>
							<p style="height: 10px;" ></p>
							<p style="width: 600px;line-height: 20px;max-height: 80px;overflow: hidden;">
								${item.memo}
							</p>
							<p style="height: 10px;" ></p>
						<div class="price" style="width: 600px;max-height: 20px;" >
							<div>
								价格： <span class="red font20">￥${String.format("%.2f",item.price*item.discount*0.1) }</span> 
								<span class="red font14">(	${item.discount}折)</span>
								定价：<span class="font14 textdelete">￥${item.price}</span>
							</div>
						</div>
						<p style="height: 10px;"> </p>
						<p class="search_book_author" style="max-height: 20px;">
							<span> ${item.author}  著
							</span>
							<span> 出版日期：${item.publish_time}</span>
							<span> 出版社： ${item.publishing}
							</span>
						</p>
						<p style="height: 10px;"> </p>
						<div class="shop_button">
							<p class="bottom_p">
								<a class="search_btn_cart "
									href="javascript:addMallCar( ${item.book_id})">加入购物车</a>
							</p>
							<script type="text/javascript">
							
							function addMallCar(id) {
								
								var product_id =id;
								//var book_id = form1.book_id;
								var count = 1;
								
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
							</script>
						</div>
						</div>
						<div class="cls"></div>
						</li>	
						</c:forEach>

				</ul>

				<c:if test="${bookMap.total_count <= 0}">
					没有更多的结果了...
				</c:if>
			</div>
				<c:if test="${bookMap.total_count > 0}">
					<jsp:include page="in_page.jsp"></jsp:include>
				</c:if>

			
			<!-- main end -->
		</div>
		<jsp:include page="in_foot.jsp"></jsp:include>
	</div>
</body>
</html>