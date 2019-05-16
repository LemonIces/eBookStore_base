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
			<span class="STYLE4">图书列表</span></td>
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
			<td  width="" height="26">书名：</td>
			<td  width="" height="26"><input type="text" id="find_book_name" value="${find_book_name }"></td>
			<td  width="" height="26"><input type="button" onclick="find()" value="搜索" /></td>
			</tr>
	 	 </table>
	 	 <script type="text/javascript">
	 	 	function find(){
				console.log("find.........");
				var find_book_name =  document.getElementById("find_book_name").value;
				window.location.href = "${pageContext.request.contextPath }/admin/adminAction_listBook2.action?statu=1&page=1&find_book_name="+find_book_name;
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
					<td width="3%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">编号</div></td>
					<td width="7%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">封面</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">书名</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">介绍</div></td>
					<td width="3%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">作者</div></td>
					<td width="3%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">价格</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">数量</div></td>
					<td width="2%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">折扣</div></td>
					<td width="7%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">出版社</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">出版日期</div></td>
					<td width="5%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2 STYLE1">图书分类</div></td>
					<td width="20%" height="26" background="images/tab_14.gif" class="STYLE1">
						<div align="center" class="STYLE2">操作</div></td>
				</tr>
				
				<!-- 用struts的iterator标签遍历news集合, 并去处每一个name显示 -->
				<c:forEach var="book" items="${bookMap.list}">
	
					<tr>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.book_id}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1"><img src="../${book.img}" width="150px" height="150px"></div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.book_name}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.memo}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.author}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.price}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.count}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.discount}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.publishing}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.publish_time}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center" class="STYLE2 STYLE1">${book.bookcategory1.name}</div></td>
						<td height="20" bgcolor="#FFFFFF">
							<div align="center">
							<c:if test="${book.hots=='2'}"><img src="images/001.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath }/admin/adminAction_bookSet.action?book_id=${book.book_id}&flag=11&status=${statu}&page=${page}&page_size=${page_size}&news=${news}&hots=${hots}">设为热门</a><span class="STYLE1">]</span></c:if>
								<c:if test="${book.hots=='1'}"><img src="images/010.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath }/admin/adminAction_bookSet.action?book_id=${book.book_id}&flag=12&status=${statu}&page=${page}&page_size=${page_size}&news=${news}&hots=${hots}">取消热门</a><span class="STYLE1">]</span></c:if>
								<c:if test="${book.news=='2'}"><img src="images/001.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath }/admin/adminAction_bookSet.action?book_id=${book.book_id}&flag=21&status=${statu}&page=${page}&page_size=${page_size}&news=${news}&hots=${hots}">设为最新</a><span class="STYLE1">]</span></c:if>
								<c:if test="${book.news=='1'}"><img src="images/010.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath }/admin/adminAction_bookSet.action?book_id=${book.book_id}&flag=22&status=${statu}&page=${page}&page_size=${page_size}&news=${news}&hots=${hots}">取消最新</a><span class="STYLE1">]</span></c:if>
								<%-- <c:if test="${!book.sale}"><img src="images/001.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="bookSet.action?id=${book.book_id}&flag=31&status=${status}&page=${page}">设为促销</a><span class="STYLE1">]</span></c:if>
								<c:if test="${book.sale}"><img src="images/010.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="bookSet.action?id=${book.book_id}&flag=30&status=${status}&page=${page}">取消促销</a><span class="STYLE1">]</span></c:if> --%>
								<br> 
								<img src="images/037.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="${pageContext.request.contextPath }/admin/adminAction_bookUpPage.action?book_id=${book.book_id}&page=${page}&page_size=${page_size}&news=${news}&hots=${hots}">修改</a><span class="STYLE1">]</span>
								<img src="images/083.gif" width="9" height="9" /><span class="STYLE1">[</span><a href="javascript:confirm_href('${pageContext.request.contextPath }/admin/adminAction_bookDelete.action?book_id=${book.book_id}&page=${page}&page_size=${page_size}&news=${news}&hots=${hots}','确定要删除吗？');">删除</a><span class="STYLE1">]</span>
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
				<c:if test="${bookMap.total_count <= 0}">
					没有更多的结果了...
				</c:if>
				
				<c:if test="${bookMap.total_count > 0}">
					<jsp:include page="in_page_book_list_div.jsp"></jsp:include>
				</c:if>
					
				</td>
			<td width="14"><img src="images/tab_22.gif" width="14" height="29" /></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>
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
		var news = document.getElementById("news").value;
		var hots = document.getElementById("hots").value;
		
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

		
		window.location.href="${pageContext.request.contextPath }/admin/adminAction_listBook2.action?statu=1&page="+page+"&page_size="+page_size+"&news="+news+"&hots="+hots;
	}
</script>
</body>
</html>
