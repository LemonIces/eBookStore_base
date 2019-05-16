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

<table width="80%" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td width="15" height="30"><img src="images/tab_03.gif" width="15" height="30" /></td>
		  <td background="images/tab_05.gif"><img src="images/311.gif" width="16" height="16" /> 
			<span class="STYLE4">广告列表</span></td>
		  <td width="14"><img src="images/tab_07.gif" width="14" height="30" /></td>
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
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">编号</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">标题</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">网址</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">图片</div></td>
					<td width="10%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">状态</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2">操作</div></td>
				</tr>
				
				<!-- 用struts的iterator标签遍历news集合, 并去处每一个name显示 -->
				<c:forEach var="ad" items="${pb.list}">
	
					<tr>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${ad.ad_id}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${ad.ad_title}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${ad.ad_loc}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">
								<img height="60" width="152" alt="" src="${pageContext.request.contextPath }/${ad.ad_img}">
							</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">
							<c:if test="${ad.statu==1}">显示</c:if>
							<c:if test="${ad.statu==2}">不显示</c:if>
							</div></td>
						<td height="20" bgcolor="#FFFFFF">
								<div align="center">
									<img src="images/037.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath}/admin/adminAction_adUpPage.action?ad_id=${ad.ad_id}&page=${page}">修改</a><span class="STYLE1">]</span>
									<img src="images/083.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath}/admin/adminAction_adDelete.action?ad_id=${ad.ad_id}&page=${page}','确定要删除吗？')">删除</a><span class="STYLE1">]</span>
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
							
				<a href="pages/ad-add.jsp">添加广告</a>
				
					<div>
				<a href="javascript:void(0)" onclick="change_page(1)">上一页</a><a href="javascript:void(0)" onclick="change_page(2)">下一页</a>
				<span>第${pb.page }页</span>
				/
				<span>共${pb.total_page }页</span>
				<span>每页</span>
					<select id="page_size" onchange="change_page(0)">
						<option <c:if test="${pb.page_size==10}">selected="selected"</c:if> >10</option>
						<option <c:if test="${pb.page_size==20}">selected="selected"</c:if> >20</option>
						<option <c:if test="${pb.page_size==50}">selected="selected"</c:if> >50</option>
						<option <c:if test="${pb.page_size==100}">selected="selected"</c:if> >100</option>
					</select>
				<span>条</span>
				
				<span>到</span>
					<input style="width: 50px;" type="number" onblur="check_page_change()" onkeydown="check_page_change()"  onkeyup="check_page_change()" onmousedown="check_page_change()" onmouseout="check_page_change()"  onmouseup="check_page_change()"  onmouseover="check_page_change()" id="page" value="${pb.page }"/>
				<span>页</span>
				<input type="button" value="跳转" onclick="change_page(0)" />
				
				<input id="total_page"  type="hidden" value="${pb.total_page }"/>
				
				
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

	window.location.href = "${pageContext.request.contextPath }/admin/adminAction_listAd2.action?page="
				+ page
				+ "&page_size="
				+ page_size;
	}
</script>
								
			</div>
	
			</td>
			<td width="14"><img src="images/tab_22.gif" width="14" height="29" /></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>

</body>
</html>
