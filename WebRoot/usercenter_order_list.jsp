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
				<div style="color: #666666; font-size: 20px;">我的订单
					<div style="height: 8px;"></div>
				</div>
					<s:if test="#order_bean.total_count <= 0">
						<div>您还没有任何订单哦...</div>
					</s:if>
					
					<s:iterator value="#order_bean.list" var="order">
						<div>
							<!-- 订单信息 -->
							<div style="background: #d4f0f2; padding: 5px 2px;">
								<table>
									<tr>
										<td class="order_list_item_top_table_td">订单号：<s:property
												value="#order.order_no" /></td>
										<td class="order_time_td"><s:property value="#order.time" /></td>
										<td class="order_list_item_top_table_td"><s:property
												value="#order.sum_money" /></td>
										<td class="order_list_item_top_table_td"><s:property
												value="#order.pay_way" /></td>
										<td class="order_list_item_top_table_td"><s:property
												value="#order.name" /></td>
									</tr>
								</table>
							</div>
							<!-- 订单商品信息 -->
							<div
								style="padding: 10px 10px 20px 10px; border: 1px solid #c6c6c6; margin-bottom: 20px;">
								<table>
									<tr>
										<td style="width: 640px;"><s:iterator
												value="#order.items" var="item">
												<span> <a target="_blank"
													href="${pageContext.request.contextPath}/userAction_order_detail?order_id=<s:property value='#order.order_id'/>"><img
														style="height: 100px; width: 90px; cursor: pointer; padding: 5px;"
														title="书名：<s:property value='#item.product_name' /> x <s:property value='#item.count' /> 价格：￥<s:property value='#item.price' />元  折扣：<s:property value='#item.discount' />折"
														src="${pageContext.request.contextPath }/<s:property value='#item.product_img' />" />
												</a></span>
											</s:iterator></td>
										<td>
								<%-- 			<s:if test="#order.statu==0">
												<span>订单未支付</span><br>
												<a href="${pageContext.request.contextPath}/userAction_go_pay_page?order_id=${order.order_id}">去支付</a>
											</s:if>  --%>
											<s:if test="#order.statu==1">
												<span>订单已支付</span>
											</s:if> 
											<s:if test="#order.statu==2">
												<span>订单已处理</span>
											</s:if> 
											<s:if test="#order.statu==3">
												<span>订单已完成</span>
											</s:if>
											<s:if test="#order.statu==5">
												<span>申请退货中</span>
											</s:if> 
											<s:if test="#order.statu==6">
												<span>已退货</span>
											</s:if> <br> 
											<a target="_blank"
											href="${pageContext.request.contextPath}/userAction_order_detail?order_id=<s:property value='#order.order_id'/>">订单详情</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</s:iterator><!-- list end -->
					<div>
						<s:if test="#order_bean.total_count > #order_bean.page_size">
					<div>
						<a href="javascript:void(0)" onclick="change_page(1)">上一页</a><a href="javascript:void(0)" onclick="change_page(2)">下一页</a>
						<span>第${order_bean.page }页</span>
						/
						<span>共${order_bean.total_page }页</span>
						<span>每页</span>
							<select id="page_size" onchange="change_page(0)">
								<option <c:if test="${order_bean.page_size==10}">selected="selected"</c:if> >10</option>
								<option <c:if test="${order_bean.page_size==20}">selected="selected"</c:if> >20</option>
								<option <c:if test="${order_bean.page_size==50}">selected="selected"</c:if> >50</option>
								<option <c:if test="${order_bean.page_size==100}">selected="selected"</c:if> >100</option>
							</select>
						<span>条</span>
						
						<span>到</span>
							<input style="width: 50px;" type="number" onblur="check_page_change()" onkeydown="check_page_change()"  onkeyup="check_page_change()" onmousedown="check_page_change()" onmouseout="check_page_change()"  onmouseup="check_page_change()"  onmouseover="check_page_change()" id="page" value="${order_bean.page }"/>
						<span>页</span>
						<input type="button" value="跳转" onclick="change_page(0)" />
						
						<input id="total_page"  type="hidden" value="${order_bean.total_page }"/>
						
					</div>
					
					</s:if>
					
					<script type="text/javascript">
					//输入页码改变
					function check_page_change() {
						var page = document.getElementById("page").value*1;
						var total_page = document.getElementById("total_page").value*1;
						
						if(page < 1){
							page = 1;
							document.getElementById("page").value = page;
							return;
						}
						if(page > total_page){
							page = total_page;
							document.getElementById("page").value = page;
							return;
						}
						
					}

					//页面改变
					function change_page(mode){
						console.log("change_page.....");
						var page_size = document.getElementById("page_size").value*1;
						var total_page = document.getElementById("total_page").value*1;
						var page = document.getElementById("page").value*1;
						
						if(mode == 0){
						}else if(mode == 1){
							page--;
							if(page < 1){
								return;
							}
							
						}else if(mode == 2){
							page++;
							if(page > total_page){
								return;
							}
						}
						window.location.href = "${pageContext.request.contextPath }/testAction_usercenter_order_listPage1.action?statu=1&page="
							+ page
							+ "&page_size="
							+ page_size+
							"&statu=1";
					}
					</script>
					
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