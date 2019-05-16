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
			<span class="STYLE4">订单详情</span></td>
		  <td width="14"><img src="images/tab_07.gif" width="14" height="30" /></td>
		</tr>
	  </table>
	</td>
  </tr>
  
      <!-- 搜索 -->
  <tr>
  <td>
  	<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
  	 <tr>
  	    <td width="9" background="images/tab_12.gif">&nbsp;</td>
  	    <td>
  	     <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
						<div align="center" class="STYLE2 STYLE1">订单总金额</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">留言</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">订单状态</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">收货人姓名</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">收货人电话</div></td>
					<td width="15%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">收货地址</div></td>
					<td width="15%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2">操作</div></td>
				</tr>
				
									<tr>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.order_no}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.time}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.username}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.product_count}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.sum_money}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.momo}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">
								<c:if test="${order.statu==0}">未支付</c:if>
								<c:if test="${order.statu==1}">未处理</c:if>
								<c:if test="${order.statu==2}">已处理</c:if>
								<c:if test="${order.statu==3}">已完成</c:if>
							</div>
						</td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.name}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.phone}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${order.address}</div></td>
								
						<td height="20" bgcolor="#FFFFFF">
							<div align="center">
								<%-- <img src="images/a1.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath}/admin/adminAction_listOrderItemDetail.action?order_id=${order.order_id}">查看详情</a><span class="STYLE1">]</span>
							 --%>								<c:if test="${order.statu==1}">
									<img src="images/037.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderDispose.action?order_id=${order.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要处理订单吗？')">标记处理</a><span class="STYLE1">]</span>
								<%-- 	<img src="images/083.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderDelete.action?order_id=${order.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要删除吗？')">删除</a><span class="STYLE1">]</span>
							 --%>	</c:if>
								<c:if test="${order.statu==2}">
									<img src="images/037.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_orderOk.action?order_id=${order.order_id}&statu=${statu}&page=${page}&page_size=${page_size}','确定要完成订单吗？')">标记完成</a><span class="STYLE1">]</span>
								</c:if>
								</div>
						</td>
					</tr>
	 	 </table>
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
					<td width="3%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">编号</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2">封面</div></td>
					<td width="15%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">书名</div></td>
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">单价</div></td>
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">折扣</div></td>
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">数量</div></td>
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">小计</div></td>
					
				</tr>
				
				<!-- 用struts的iterator标签遍历news集合, 并去处每一个name显示 -->
				<c:forEach var="item" items="${order.items}">
	
					<tr>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${item.item_id}</div></td>
						<td height="20" bgcolor="#FFFFFF" align="center">
							<img height="90" width="70" alt="" src="${pageContext.request.contextPath }/${item.product_img}">
						</td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${item.product_name}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${item.price}</div></td>
						<td height="20" bgcolor="#FFFFFF">
						<div align="center" class="STYLE2 STYLE1">${item.discount}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${item.count}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${String.format("%.2f",item.count*item.price*item.discount*0.1) }</div></td>

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
				 <input type="button" onclick="back_btn();" value="返回"/>
			</td>
			<td width="14"><img src="images/tab_22.gif" width="14" height="29" /></td>
		  </tr>
		  
		</table>
	</td>
  </tr>
</table>

</body>
</html>
