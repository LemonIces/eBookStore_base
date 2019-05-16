<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的购物车</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/mallcar.css" />
	
	<script language='javascript' src='${pageContext.request.contextPath }/js/find.js'></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
	
<script type="text/javascript">
	//初始化方法
	function init() {
		calProductCountAndMoney();

	}
	
	function onChangeCount(obj){
		if(!obj.value.match("^[0-9]+$")){
			alert("数量只能是数字！");
			obj.value= 1;
			
		}
	}

	function changeCount1(mode, id) {
		var count = document.getElementById('count_id_' + id).value;
		count *= 1;
		if (mode == 1) {
			count++;
		} else if (mode == 0) {
			if (count > 1)
				count--;
		}
		if (count < 1) {
			count = 1;
		}
		document.getElementById('count_id_' + id).value = count;
		calProductCountAndMoney();

	}

	//全部选中
	function checkAllChange(obj) {
		console.log(obj);
		if (obj.checked) {

			var items = document.getElementsByName('select_item');
			for (var i = 0; i < items.length; i++) {
				items[i].checked = "checked";
			}

			document.getElementById('check_all1').checked = 'checked';
			document.getElementById('check_all2').checked = 'checked';

		} else {
			var items = document.getElementsByName('select_item');
			for (var i = 0; i < items.length; i++) {
				items[i].checked = "";
			}

			document.getElementById('check_all1').checked = '';
			document.getElementById('check_all2').checked = '';
		}
		calProductCountAndMoney();

	}

	//单个条目选中改变
	function checkItemChange(obj) {
		console.log(obj);
		if (obj.checked) {
			var items = document.getElementsByName('select_item');
			var check_count = 0;
			for (var i = 0; i < items.length; i++) {
				if (items[i].checked) {
					check_count++;
				}
			}
			//如果全部选中把全部选中按钮给选中
			if (check_count == items.length) {
				document.getElementById('check_all1').checked = 'checked';
				document.getElementById('check_all2').checked = 'checked';
			}

		} else {
			document.getElementById('check_all1').checked = '';
			document.getElementById('check_all2').checked = '';
		}

		calProductCountAndMoney();
	}

	function calProductCountAndMoney() {
		console.log("calProductCountAndMoney");
		var items = document.getElementsByName('select_item');
		var count1s = document.getElementsByName('count1');

		var price1s = document.getElementsByName('price1');
		
		var discounts = document.getElementsByName('discount');

		var money1s = document.getElementsByName('money1');

		var totalMoney = 0;

		var totalCount = 0;
		for (var i = 0; i < items.length; i++) {
			if (items[i].checked) {
				totalCount += (count1s[i].value * 1);
				money1s[i].value = (price1s[i].value * 1)
						* (count1s[i].value * 1 )*(discounts[i].value *1*0.1);
				totalMoney += (money1s[i].value * 1);
			} else {
				money1s[i].value = "0.00";
			}
		}
		console.log(totalCount);
		document.getElementsByName("totalMoney")[0].value = totalMoney.toFixed(2);
		document.getElementById('totalCount_span').innerHTML = totalCount;

	}

	function change1(loc, id1) {
		
		if(confirm("确定修改吗？")){
			var countEle = document.getElementById('count_id_' + id1);
			loc = loc + "?mallcar_id=" + id1 + "&count=" + countEle.value;
			console.log(loc);
			window.location.href = loc;
		}
	}
	
	//通过id从购物车删除指定商品
	function deleteOne(id) {
		if(confirm("确定删除吗？")){
			var page_size = 20;
			var page = 1;
			if(Number("${mallMap.total_count}") > Number("${mallMap.page_size}")){
				page_size = document.getElementById("page_size").value*1;
				page = document.getElementById("page").value*1;
			}
	
			
			var loc = "${pageContext.request.contextPath}/mallCarAction_deleteOne"
			loc = loc + "?mallcar_id=" + id+"&page="+page+"&page_size="+page_size+"&statu=1";
			console.log(loc);
			window.location.href = loc;
		}
	}
	
	
	function deleteMulti() {
		if(confirm("确定删除吗？")){
			var page_size = 20;
			var page = 1;
			if(Number("${mallMap.total_count}") > Number("${mallMap.page_size}")){
				page_size = document.getElementById("page_size").value*1;
				page = document.getElementById("page").value*1;
			}
			
			var items = document.getElementsByName('select_item');
			var mallcar_ids_s = document.getElementsByName('m_id_name');
			var mallcar_ids = "mallcar_ids=";
			for (var i = 0; i < items.length; i++) {
				//判断是否选中
				if(items[i].checked){
					var id = mallcar_ids_s[i].value;
					mallcar_ids += id+",";
				}
			}

			if(mallcar_ids!="mallcar_ids="){
				mallcar_ids = mallcar_ids.substr(0,mallcar_ids.length-1);
			}
			
			var loc = "${pageContext.request.contextPath}/mallCarAction_deleteMulti"
			loc = loc + "?" + mallcar_ids+"&page="+page+"&page_size="+page_size+"&statu=1";
			console.log(loc);
			window.location.href = loc;
		}
	}
	

</script>
</head>
<body onload="init()">
	<div class="box">
	<jsp:include page="in_top.jsp"></jsp:include>				
		<div class="main">
			<s:if test="#session.loginUser==null">
				<a href="${pageContext.request.contextPath }/testAction_loginPage">请登录</a>
			</s:if>

			<s:if test="#session.loginUser!=null">
				<s:if test="#mallMap['total_count']<=0">
					<div>
						您的购物车空空如也...
						<a href="index.jsp">马上去添加商品!..</a>
					</div>
				</s:if>
				<s:if test="#mallMap['total_count']>0">
				<table class="goods_table">
					<tr>
						<th class=""><input type="checkbox" id="check_all1"
							onchange="checkAllChange(this)" />全选</th>
						<th class="th1">封面</th>
						<th class="th1">书本名</th>
						<th class="th1">价格(元)</th>
						<th class="th1">折扣</th>
						<th class="th1">数量</th>
						<th class="th1">金额(元)</th>
						<th>修改</th>
						<th class="th1">删除</th>
					</tr>
					<s:iterator value="#mallMap.list" var="item">
						<tr class="mallcar_item_tr">
							<td class="mallcar_item_td_check td1">
							<input type="checkbox"	onchange="checkItemChange(this)" name="select_item"
							id="select_item_<s:property value='#item.mallcar_id'/>"
							></td>
							<td class="td1">
									<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#item.product_id" />">
										<img class="mall_td_info_img" 
										src="${pageContext.request.contextPath }/<s:property value="#item.product_img"/>" />
									</a>
									<!-- 商品id -->
									<input type="hidden" name="p_id_name" id="id_<s:property  value='#item.product_id'/>" value="<s:property  value='#item.product_id'/>" />
									<!-- 购物车id -->
									<input type="hidden" name="m_id_name" id="id_<s:property  value='#item.mallcar_id'/>" value="<s:property  value='#item.mallcar_id'/>" />

							</td>
							
							<td class="td1" width="300">
									<p class="info_book_name" >
										<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#item.product_id" />">
											<s:property value="#item.product_name" />
										</a>
									</p>
							</td>
							
							<td><input class="price1_input"
								value='<s:property value="#item.price"/>' type="text"
								name="price1" disabled="disabled"></td>
								
							<td><s:property value="#item.discount"/>
								<input type="hidden" class="discount" name="discount" value='<s:property value="#item.discount"/>'>
							</td>
							<td class="mallcar_item_td_price td1">
							<a class="" onclick="changeCount1('0','<s:property value="#item.mallcar_id"/>')"
								href="javascript:void(0)">-</a> 
								
								<!-- 数量 -->
								<input class="count1 countin" id="count_id_<s:property value='#item.mallcar_id'/>" 
								type="text" name="count1" value="<s:property value='#item.count'/>" onchange="onChangeCount(this)" /> 
								
								<a onclick="changeCount1('1','<s:property value="#item.mallcar_id"/>')"
								href="javascript:void(0)">+</a>
							</td>
							<td class="td1"><input class="money1_input"
								disabled="disabled" type="text" name="money1" /></td>
							<td class="td1"><a
								onclick="change1('${pageContext.request.contextPath}/mallCarAction_updateCount','<s:property value="#item.mallcar_id"/>')"
								href="javascript:void(0)">修改</a></td>
							<td class="td1"><a href="javascript:void(0)" onclick="deleteOne('<s:property value="#item.mallcar_id"/>')">删除</a></td>
						</tr>
					</s:iterator>
				</table>
				
				<s:if test="#mallMap['total_count'] > #mallMap['page_size']">
				
				<div><!-- page -->
				<a href="javascript:void(0)" onclick="change_page(1)">上一页</a><a href="javascript:void(0)" onclick="change_page(2)">下一页</a>
				<span>第${mallMap.page }页</span>
				/
				<span>共${mallMap.total_page }页</span>
				<span>每页</span>
					<select id="page_size" onchange="change_page(0)">
						<option <c:if test="${mallMap.page_size==20}">selected="selected"</c:if> >20</option>
						<option <c:if test="${mallMap.page_size==50}">selected="selected"</c:if> >50</option>
						<option <c:if test="${mallMap.page_size==100}">selected="selected"</c:if> >100</option>
						<option <c:if test="${mallMap.page_size==200}">selected="selected"</c:if> >200</option>
					</select>
				<span>条</span>
				
				<span>到</span>
					<input style="width: 50px;" type="number" onblur="check_page_change()" onkeydown="check_page_change()"  onkeyup="check_page_change()" onmousedown="check_page_change()" onmouseout="check_page_change()"  onmouseup="check_page_change()"  onmouseover="check_page_change()" id="page" value="${mallMap.page }"/>
				<span>页</span>
				<input type="button" value="跳转" onclick="change_page(0)" />
				
				<input id="total_page"  type="hidden" value="${mallMap.total_page }"/>
				
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
					window.location.href = "${pageContext.request.contextPath }/mallCarAction_list.action?statu=1&page="
						+ page
						+ "&page_size="
						+ page_size+
						"&statu=1";
				}
				</script>
			</div><!-- ----page -->
			</s:if><!-- total_count>page_size -->
				</s:if><!-- total_count>0 -->
			</s:if><!-- user!=null -->
			
			<script type="text/javascript">
			
			
			//生成订单
			function jiesuan(obj) {
				console.log("jiesuan....................");
				console.log(obj);
				
				
				var loc = "${pageContext.request.contextPath}/userAction_jiesuan?";
				
				//选中
				var items = document.getElementsByName('select_item');
				//数量
				var count1s = document.getElementsByName('count1');
				//商品id
				var product_ids = document.getElementsByName('p_id_name');
				
				//购物车id
				var mallcar_ids = document.getElementsByName('m_id_name');
				
				var str = "";
				for (var i = 0; i < items.length; i++) {
					//判断是否选中
					if(items[i].checked){
						//获取选中的商品id和商品个数
						var product_id = product_ids[i].value;
						var count = count1s[i].value;
						var mallcar_id = mallcar_ids[i].value;
						console.log( items[i].checked+"  product_id="+product_id+", count="+count+", mallcar_id="+mallcar_id);
						
						var tem = product_id+","+count+","+mallcar_id+"@";	
						//拼凑信息
						str += tem;
						
					}else{
						//没有选中
					}
				}
				
				if(str == "products="){
					alert("没有选中任何商品！");
					return;
				}
				
				//把最后面的%号去掉
				str = str.substr(0,str.length-1);
				console.log(str);
				
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
				
				
				//开始向服务发送消息
			//	window.location.href=loc+str;
				
			}
			
			</script>
		<div style="height:120px;"></div>
		</div>
		<div class="cls10"></div>

		<div style="height: 100px;width:1000px;background-color: #e9edf1; position: fixed; bottom: 24px;">
		<div class="pay_detail_div">
			<table class="table1_left">
				<tr>
					<td><input type="checkbox" onchange="checkAllChange(this)"
						id="check_all2" name="" />全选</td>
					<td><a href="javascript:deleteMulti();">批量删除</a></td>
					<td>已选择<span id="totalCount_span">0</span>件商品
					</td>
				</tr>
			</table>
			<table class="table1_right">
				<tr>
					<td>总计:</td>
					<td>￥<input
						style="height: 30px; font-size: 20px; width: 80px; padding: 1px; color: #ff0b0b;"
						type="text" disabled="disabled" name="totalMoney" value="0.00" /></td>
					<td><input
						style="padding: 5px 15px; font-size: 15px; color: #f4dfd7; background-color: #ff160a; font-weight: bold; margin-right: 20px; cursor: pointer;"
						type="button" name="" onclick="jiesuan(this)" value="结算" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>