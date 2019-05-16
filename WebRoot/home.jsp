<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>网上图书商城</title>
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/sty.css" />
<script language='javascript' src='${pageContext.request.contextPath }/js/js1.js'></script>
<script language='javascript' src='${pageContext.request.contextPath }/js/find.js'></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
</head>
<body>
	<div class="box">

		<jsp:include page="in_top.jsp"></jsp:include>
		<jsp:include page="in_nav.jsp"></jsp:include>
		<div class="main">
<!-- 			<div class="category_nav">
				<ul>
					<li><a href="javascript:void(0)">图书排行榜</a></li>
					<li><a href="javascript:void(0)">文学</a></li>
					<li><a href="javascript:void(0)">青春文学</a></li>
					<li><a href="javascript:void(0)">小说</a></li>
					<li><a href="javascript:void(0)">励志</a></li>
					<li><a href="javascript:void(0)">历史</a></li>
					<li><a href="javascript:void(0)">哲学宗教</a></li>
					<li><a href="javascript:void(0)">考试</a></li>
					<li><a href="javascript:void(0)">科技</a></li>
					<li><a href="javascript:void(0)">管理</a></li>
				</ul>
				<div class="cls"></div>
			</div> -->
			<div >
				
			</div>

			<div class="left">
				<div class="category_lab">图书分类</div>
				<div class="category_ul">
					<ul>
					<c:forEach var="item" items="${category1List }">
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,${item.bookcategory1_id})">${item.name}</a></span>
						</li>
					</c:forEach>
	<%-- 					<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,1)">经典著作</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,2)">社会科学</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,3)">自然哲学</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,4)">历史地理</a></span>
						</li>
						
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,5)">中国文学</a></span>
						</li>
						
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,6)">美术雕塑</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,7)">摄影影视</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,8)">书法篆刻</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,9)">医药卫生</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,10)">建筑工程</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,11)">生活休闲</a></span>
						</li>
						<li>
						<span class="li_title"><a href="javascript:void(0)" onclick="search_book(3,12)">少儿读物</a></span>
						</li> --%>
					</ul>
				</div>
			</div>
			<div class="center">
			
			<!-- 广告 -->
				<div class="ad">
				
					<s:iterator value="#listAd" var="aditem">
					<div id="ad_item_div_<s:property value='#aditem.ad_id'/>" style="display: none;">
					<a target="_blank" href="<s:property value='#aditem.ad_loc'/>" title="<s:property value='#aditem.ad_title'/>"><img   src="<s:property value='#aditem.ad_img'/>" height="300"
						width="760" /></a>
						<input name="ad_id" type="hidden" value="<s:property value='#aditem.ad_id'/>" />
					</div>
					</s:iterator>
					<div class="select_ad_div">
					<s:iterator value="#listAd" var="add_select_item">
						<input class="change_ad_btn" onmouseover="change_ad_btn_over(this)" onmouseout="change_ad_btn_out(this)"  name="change_ad_btn" type="button" onclick="changeAdBtn(this)" />
					</s:iterator>
					</div>
					
				</div>
				<div class="newBook">
				
				<form action="${pageContext.request.contextPath}/bookAction_list" name="listform1" id="listform1" method="post">
				
				<div class="bpre1 bor1" onclick="prePage1('')">&lt;</div>
				<div >
					<span class="font20">新书推荐</span>
					<div class="cls10"></div>
					<hr class="bor4"></hr>
				</div>
					<ul id="ul1"  style="dispaly:<s:property value="#pageBean.currentPage==1?'block':'none'" />">
						<s:iterator value="#pageBean.list" var="bean">
						<li class="lis" style="display: none;">
							<div class="point1" style="text-align: center;font-size: 12px;">
								<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#bean.book_id" />"><img  class="img_hoome_1" src="${pageContext.request.contextPath }/<s:property value='#bean.img'/>" height="100" width="85"></a>
								</div> 
								
								<div class="cls10"></div>
								<div style="text-align:center;font-size: 12px;overflow: hidden;height: 16px;">
								<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#bean.book_id" />"><s:property value="#bean.book_name" /></a>
								</div>
							<div style="text-align: center;font-size: 10px;overflow: hidden;height: 16px;"><font style="color: red;font-size: 12px;">￥${String.format("%.2f",bean.price * bean.discount *0.1)}</font> ￥<span class="textdelete">${bean.price }</span></div>
						</li>
						</s:iterator>

					</ul>
					<input type="hidden" id="pageSize" name="pageSize" value="<s:property value="#pageBean.pageSize" />">
					<input type="hidden" id="currentPage" name="currentPage" value="<s:property value="#pageBean.currentPage" />">
					<input type="hidden" id="totalPage" name="totalPage" value="<s:property value="#pageBean.totalPage" />">
					<div class="cls"></div>
					
					<div onclick="nextPage1('')" class="bnext1 bor1">&gt;</div>
					</form>
				</div>
			</div>
<!-- 			<div class="right">
				<div class="ad1"></div>
			</div> -->
			<div class="cls10"></div>
			<div class="bottom">
				<div class="left2">
					<div class="nav2">
					<div class="pop1 font20">
						热门推荐
					</div>
						<ul class="">
					<%int activeIndex = 0; %>
					<c:forEach var="item" items="${category1List }">
					<%activeIndex++; %>
						<li><a href="javascript:void(0)" <%if(activeIndex == 1)out.write("class='active1'"); %> onclick="show2index(${item.bookcategory1_id},this)">${item.name}</a></li>
					</c:forEach>
<!-- 						
							<li><a href="javascript:void(0)" class="active1" onclick="show2index(1,this)">经典著作</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(2,this)">社会科学</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(3,this)">自然科学</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(4,this)">历史地理</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(5,this)">中国文学</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(6,this)">美术雕塑</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(7,this)">摄影影视</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(8,this)">书法篆刻</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(9,this)">医药卫生</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(10,this)">建筑工程</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(11,this)">生活休闲</a></li>
							<li><a href="javascript:void(0)" onclick="show2index(12,this)">少儿读物</a></li> -->
						</ul>
						<div class="cis"></div>
					</div>
					<div class="cls10"></div>
					<div class="books2">
					<s:iterator value="#pageBean2" var="list1">
					<ul class="list2s" style="display: none;">
						<s:iterator value="#list1.list" var="bean">
						<li>
							<div style="text-align: center;">
								<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#bean.book_id" />"><img class="img_hoome_1" src="<s:property value='#bean.img'/>" height="100" width="85"></a>
							</div>
							<div class="cls10"></div>
							<div style="text-align: center; font-size: 12px;overflow: hidden;height: 16px;">	
							<a  target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#bean.book_id" />">	<s:property value="#bean.book_name" /></a>
							</div>
							 <div style="text-align: center;font-size: 10px;overflow: hidden;height: 16px;"><font style="color: red;font-size: 12px;">￥${String.format("%.2f",bean.price * bean.discount *0.1)}</font> ￥<span class="textdelete">${bean.price }</span></div>
					
						</li>
						
						</s:iterator>
					</ul>
						</s:iterator>
					</div>
				</div>
				<!-- <div class="right2"></div> -->
				<div class="cls"></div>
			</div>
		</div>
	<jsp:include page="in_foot.jsp"></jsp:include>
	</div>
</body>
</html>