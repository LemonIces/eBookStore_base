<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE4 {
	font-size: 12px;
	color: #1F4A65;
	font-weight: bold;
}

a:link {
	font-size: 12px;
	color: #06482a;
	text-decoration: none;

}
a:visited {
	font-size: 12px;
	color: #06482a;
	text-decoration: none;
}
a:hover {
	font-size: 12px;
	color: #FF0000;
	text-decoration: underline;
}
a:active {
	font-size: 12px;
	color: #FF0000;
	text-decoration: none;
}

-->
</style>
</head>

<body>

<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td width="15" height="30"><img src="images/tab_03.gif" width="15" height="30" /></td>
		  <td background="images/tab_05.gif"><img src="images/311.gif" width="16" height="16" /> 
			<span class="STYLE4">订单列表</span></td>
		  <td width="14"><img src="images/tab_07.gif" width="14" height="30" /></td>
		</tr>
	  </table>
	</td>
  </tr>
  
    <!-- 搜索 -->
  <tr>
  <td>
  	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  	 <tr>
  	    <td width="9" background="images/tab_12.gif">&nbsp;</td>
  	    <td>
  	     <table width="" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td  width="" height="26">用户名：</td>
			<td  width="" height="26"><input type="text" id="find_username" value="${find_username }"></td>
			<td  width="" height="26"><input type="button" onclick="find()" value="搜索" /></td>
			</tr>
	 	 </table>
	 	 <script type="text/javascript">
	 	 	function find(){
				console.log("find.........");
				var find_username =  document.getElementById("find_username").value;
				window.location.href = "${pageContext.request.contextPath }/admin/adminAction_listOrder1.action?statu=${statu}&page=1&find_username="+find_username;
	 	 	}
		</script>
  	    </td>
	 	 <td width="9" background="images/tab_16.gif">&nbsp;</td>
	  </tr>
	  </table>
  </td>
  </tr>
  
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="9" background="images/tab_12.gif">&nbsp;</td>
          <td bgcolor="e5f1d6">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CECECE">
				<tr>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">订单编号</div></td>
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">下单时间</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">下单用户</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">商品数量</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">订单总价</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">留言</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">订单状态</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">订单流通状态</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">收货人姓名</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">收货人电话</div></td>
					<td width="15%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">收货地址</div></td>
					<td width="15%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2">操作</div></td>
				</tr>
				
				<!-- 用struts的iterator标签遍历news集合, 并去处每一个name显示 -->
				<c:forEach var="indent" items="${order_bean.list}">
	
					<tr>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.order_no}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.time}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.username}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.product_count}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.sum_money}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.momo}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">
								<c:if test="${indent.statu==0}">未支付</c:if>
								<c:if test="${indent.statu==1}">未处理</c:if>
								<c:if test="${indent.statu==2}">已处理</c:if>
								<c:if test="${indent.statu==3}">已完成</c:if>
								<c:if test="${indent.statu==5}">退货中</c:if>
								<c:if test="${indent.statu==6}">已退货</c:if>
							</div>
						</td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">
								<c:if test="${indent.statu0==1}">未处理</c:if>
								<c:if test="${indent.statu0==2}">已处理</c:if>
								<c:if test="${indent.statu0==3}">已完成</c:if>
							</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.name}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.phone}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${indent.address}</div></td>
								
						<td height="20" bgcolor="#FFFFFF">
							<div align="center">
								<img src="images/a1.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath}/admin/adminAction_listOrderItemDetail.action?order_id=${indent.order_id}">查看详情</a><span class="STYLE1">]</span>
								<c:if test="${indent.statu==1}">
									<img src="images/037.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderDispose.action?order_id=${indent.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要处理订单吗？')">标记处理</a><span class="STYLE1">]</span>
									</c:if>
								<c:if test="${indent.statu==2}">
									<img src="images/037.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderOk.action?order_id=${indent.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要完成订单吗？')">标记完成</a><span class="STYLE1">]</span>
								</c:if>
<%-- 								<c:if test="${indent.statu==3}">
								<img src="images/083.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderDelete.action?order_id=${indent.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要删除吗？')">删除</a><span class="STYLE1">]</span>
								
								</c:if> --%>
							<c:if test="${indent.statu==5}">
								<img src="images/083.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderReturn.action?order_id=${indent.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要完成吗？')">完成退货</a><span class="STYLE1">]</span>
							</c:if> 
<%-- 								<c:if test="${indent.statu==6}">
								<img src="images/083.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderDelete.action?order_id=${indent.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要删除吗？')">删除</a><span class="STYLE1">]</span>
								
								</c:if> --%>
								</div>
						</td>
					</tr>
					
				</c:forEach>
				
			</table>
		  </td>
		  <td width="9" background="images/tab_16.gif">&nbsp;</td>
		</tr>
	  </table>
	</td>
  </tr>
  <tr>
    <td height="29">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="15" height="29"><img src="images/tab_20.gif" width="15" height="29" /></td>
						<td background="images/tab_21.gif">
				<c:if test="${order_bean.total_count <= 0}">
					没有更多的结果了...
				</c:if>
				
				<c:if test="${order_bean.total_count > 0}">
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

									<script type="text/javascript">
										//输入页码改变

										function check_page_change() {
											var page = document
													.getElementById("page").value * 1;
											var total_page = document
													.getElementById("total_page").value * 1;

											if (page < 1) {
												page = 1;
												document.getElementById("page").value = page;
												return;
											}
											if (page > total_page) {
												page = total_page;
												document.getElementById("page").value = page;
												return;
											}

										}

										//页面改变
										function change_page(mode) {
											console.log("change_page.....");
											var page_size = document
													.getElementById("page_size").value * 1;
											var total_page = document
													.getElementById("total_page").value * 1;
											var page = document
													.getElementById("page").value * 1;

											if (mode == 0) {
											} else if (mode == 1) {
												page--;
												if (page < 1) {
													return;
												}

											} else if (mode == 2) {
												page++;
												if (page > total_page) {
													return;
												}
											}

											window.location.href = "${pageContext.request.contextPath }/admin/adminAction_listOrder1.action?statu=${statu}&page="
													+ page
													+ "&page_size="
													+ page_size
										}
									</script>

								</div>
				</c:if>
					
				</td>				
			<td width="14"><img src="images/tab_22.gif" width="14" height="29" /></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>

</body>
</html>
