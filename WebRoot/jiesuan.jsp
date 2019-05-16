<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>确定订单</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/mallcar.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">


</script>
</head>
<body onload="init()">
	<div class="box">
		<div class="top">
			<div class="fl">
				欢迎光临网上图书商城,
				<s:if test="#session.loginUser==null">
				请<a id="top_long_a" target="_blank" href="${pageContext.request.contextPath}/testAction_loginPage">登录</a>
					<a target="_blank" href="${pageContext.request.contextPath}/testAction_registPage">
						成为会员</a>
				</s:if>

				<s:if test="#session.loginUser!=null">
					<a href="${pageContext.request.contextPath}/testAction_usercenterPage"><img style="border-radius: 20px;"  height="20" width="20" align="top" title="用户：${session.loginUser.nickname}" src="${pageContext.request.contextPath}/${session.loginUser.head_img}"/></a>
					<a href="${pageContext.request.contextPath}/userAction_logout">
						退出</a>
				</s:if>
			</div>
			<div class="cls"></div>
		</div>
		<div class="nav">
			<div class="logo_img">
				<a id="logo_a_index" href="index.jsp"><img class="" style="width: 160px;height: 60px; cursor: pointer;" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
			<div class="mallcar_sub_logo">
				<span style="
	float: left;
	margin-left: 10px;
	margin-top: 5px;
				">确定订单</span>
			</div>
			<div class="cls"></div>
		</div>
		<div class="main">
			<s:if test="#session.loginUser==null">
				<a href="${pageContext.request.contextPath }/testAction_loginPage">请登录</a>
			</s:if>

			<s:if test="#session.loginUser!=null">
				<!-- 收货地址选择 -->	
				<div>收货人信息</div>
				<div style=" padding: 10px;">
				<s:iterator value="#session.addressList" var="addr">
					<div class="addr_item" onclick='select_addr("<s:property value='#addr.user_address_id' />")' id="select_item_<s:property value='#addr.user_address_id' />">
						<div style="font-size: 18px; font-weight: bold;">
								<span  onclick='select_addr("<s:property value='#addr.user_address_id' />")'  class="fl"><s:property value='#addr.name' /></span>
								<span  class="fr" onclick='select_addr("<s:property value='#addr.user_address_id' />")' ><s:property value='#addr.phone' /></span>
							<div class="cls10"></div>
						</div>
						<div>
						<span  onclick='select_addr("<s:property value='#addr.user_address_id' />")' ><s:property value='#addr.address' /></span>
						</div>
						<input name="item_ids" type="hidden" value="<s:property value='#addr.user_address_id' />" />
						<input name="item_names" type="hidden" value="<s:property value='#addr.name' />" />
						<input name="item_addresss" type="hidden" value="<s:property value='#addr.address' />" />
						<input name="item_phones" type="hidden" value="<s:property value='#addr.phone' />" />
					</div>
				</s:iterator>
				<p style="color: red;" id="error"></p>
				<div class="cls20"></div>
				
				<span ><a href="${pageContext.request.contextPath}/testAction_add_addressPage" target="_blank" style=" border:1px solid grey; cursor:pointer; background-color:#f0f0f0; margin-left:15px;padding: 2px 5px;"  type="button" onclick="add_address()"  >+新建收货地址</a></span>
				<script type="text/javascript">
					//新建收件人地址
					function add_address() {
						
					}
					//选择地址
					function select_addr(id) {
						console.log("select_addr........"+id);
						var ids = document.getElementsByName("item_ids");
						//如果id=-1则选择第一个条目
						if(id == -1){
							id = ids[0].value;
						}
						for(var i = 0; i < ids.length ;i++){
							var item_i = document.getElementById('select_item_'+ids[i].value);
							item_i.style.border="none";
							item_i.style.height="150px";
							item_i.style.width="260px";
						}
						
						var select_item = document.getElementById('select_item_'+id);
						select_item.style.border="2px solid red";
						select_item.style.height="146px";
						select_item.style.width="256px";
						
						var name = "";
						var address = "";
						var phone = "";
						
						for(var i = 0; i < ids.length ;i++){
							if(ids[i].value == id){							
								var item_address = document.getElementsByName('item_addresss')[i];
								var item_name = document.getElementsByName('item_names')[i];
								var item_phone = document.getElementsByName('item_phones')[i];
								
								name = item_name.value;
								address = item_address.value;
								phone = item_phone.value;
							}
						}
						
						console.log("name="+name+", phone="+phone+", address="+address);
						document.getElementById('name_span').innerHTML = name;
						document.getElementById('address_span').innerHTML = address;
						document.getElementById('phone_span').innerHTML = phone;
						
					}
				</script>
				</div>

				<!-- 商品列表 -->
				<div>
			<div>商品列表</div>
			<div style="
	background-color: #f0f0f0;
">
			<table width="100%">
				<s:iterator value="#session.jiesuanList" var="item">
				<tr >
									<td><img style="height: 90px; width: 70px; padding: 10px;"
										src="${pageContext.request.contextPath }/<s:property value='#item.img' />" />
									</td>
									<td style="width: 300px;"><s:property
											value="#item.product_name" /> <input type="hidden"
										name="product_id"
										value="<s:property value='#item.product_id' />" /> <input
										type="hidden" name="mallcar_id"
										value="<s:property value='#item.mallcar_id' />" /></td>
									<td><span>x<s:property value="#item.count" /></span> <input
										type="hidden" name="count"
										value="<s:property value='#item.count' />" /></td>
									<td style="width: 200px; text-align: center;">单价：￥ <input
										style="height: 20px; width: 100px; border: none; background-color: #f0f0f0;"
										disabled="disabled" name="price"
										value="<s:property
										value='#item.price' />" /></td>
									<td style="width: 150px; text-align: center;">折扣：<input
										style="height: 20px; width: 100px; border: none; background-color: #f0f0f0;"
										disabled="disabled" name="discount"
										value="<s:property
										value='#item.discount' />" /></td>
									<td><span>小计：￥ <input
											style="height: 20px; width: 100px; border: none; background-color: #f0f0f0;"
											disabled="disabled"
											value="<s:property value='#item.money' />" name="sum1" /></span></td>
								</tr>
				</s:iterator>
			</table>
			<input type="hidden" name="uu_code" value="<s:property value='#session.uu_code' />" />
			</div>
			</div>
			
			<div style="
	background-color: #e3f2f2;padding: 5px;
">
				<div class="fl" style="height: 100px;">
				<span>给商家留言: </span>
				</div>
				<div class="fl"   style="height: 100px;">
				<textarea name="memo" onblur="word_count(this)" onkeyup="word_count(this)" style=";height:60px; width: 350px;padding: 5px 10px; resize: none;"></textarea>
				<span id="memo_count">0/100</span>
				</div>
				<div class="cls"></div>
			</div>
			<div class="cls10"></div>
			
			<!-- 选择支付方式 -->
			<div>
			<div><h2>支付方式</h2></div>
				<span class="pay_way" id="pay_way_0" onclick="change_pay_way(0)">支付宝</span>
				<span class="pay_way" id="pay_way_1" onclick="change_pay_way(1)">微信</span>
				<input type="hidden" name="pay_way" value="支付宝" />
			</div>
			<script type="text/javascript">
				//改变支付方式
				function change_pay_way(index) {
					console.log("change_pay_way............"+index);
					var ids=new Array(0,1);
					var vals = new Array("支付宝","微信");
					for(var i= 0;i<ids.length;i++){
						var temp = document.getElementById('pay_way_'+ids[i]);
						temp.style.backgroundColor = "#f0f0f0";
					}
					var pay_way = document.getElementById('pay_way_'+index);
					pay_way.style.backgroundColor = "red";
					console.log("way"+vals[index]);
					document.getElementsByName('pay_way')[0].value=vals[index];
				}
			</script>
			
			<div class="cls10"></div>
			
			<!-- 订单详情 -->
			<div></div>
			
			<!-- 地址显示 -->
			<div class="address_deta_div">
				<table  class="address_deta_div_table">
					<tr>
						<td><h3 style="font-size=18px;">寄送至：</h3></td>
						
						<!-- 地址信息 -->
						<td><span id="address_span"></span></td>
						<td><span id="name_span"></span></td>
						<td><span id="phone_span"></span></td>
												
					</tr>
				</table>
			</div>
			
			</s:if>
			
		<div class="">

			<table class="fr">
				<tr>
					<td>总计:</td>
					<td>￥<input
						style="height: 30px; font-size: 20px; width: 80px; padding: 1px; color: #ff0b0b;"
						type="text" disabled="disabled" name="totalMoney" value="0.00" /></td>
					<td><input
						style="padding: 5px 15px; font-size: 15px; color: #f4dfd7; background-color: #ff160a; font-weight: bold; margin-right: 20px; cursor: pointer;"
						type="button" name="" onclick="pay_order(this)" value="支付订单" /></td>
				</tr>
			</table>
			<div class="cls"></div>
		</div>
			
			<script type="text/javascript">
			
			//留言文字个数
			function word_count(obj) {
				console.log(obj);
				
				var span_memo_count = document.getElementById('memo_count');
				
				var str = obj.value;
				var count = str.length;
				
				console.log("str="+str + ", count=" +count);
				
				if(count >= 100){
					str = str.substr(0,100);
					count = str.length;
					obj.value = str;
				}
				
				span_memo_count.innerHTML = count+"/100";
				
			}
			
			//支付订单
			function pay_order(obj) {
				console.log("pay_order....................");
				console.log(obj);
				
				
				if (!confirm("确定要支付吗？")) {
					return;
				}
				
				//var loc = "${pageContext.request.contextPath}/userAction_pay_order?";
				var loc = "${pageContext.request.contextPath}/userAction_pay_order1";
			
				//姓名 地址 手机号
						var name = document.getElementById('name_span').innerHTML;
						var address = document.getElementById('address_span').innerHTML;
						var phone = document.getElementById('phone_span').innerHTML;
						
						var error = document.getElementById('error');
						error.innerHTML = "";
						if(name == null || name == undefined || name == ""){
							alert("请选择收货地址！");
							error.innerHTML = "请选择收货地址！";
							return;
						}
						if(address == null || address == undefined || address == ""){
							alert("请选择收货地址！");
							error.innerHTML = "请选择收货地址！";
							return;
						}
						if(phone == null || phone == undefined || phone == ""){
							alert("请选择收货地址！");
							error.innerHTML = "请选择收货地址！";
							return;
						}
				
				//商品id
				var ids = document.getElementsByName('product_id');
				//数量
				var counts = document.getElementsByName('count');
				//单价
				var prices = document.getElementsByName('price');
		
				//mallcar_id
				var mallcar_ids = document.getElementsByName('mallcar_id');
				
				//小计
				var sum1s = document.getElementsByName('sum1');
				
				//留言
				var memo = document.getElementsByName('memo')[0].value;
				
				var pay_way = document.getElementsByName('pay_way')[0].value;
				
				if(pay_way == null || pay_way == undefined || pay_way == ""){
					alert("请选择支付方式！");
					return;
				}
				
				var uu_code = document.getElementsByName('uu_code')[0].value;
				
/* 				var info = "pay_name=";
				info += name + "&";
				info +="pay_phone="+ phone + "&";
				info +="pay_address="+ address + "&"
				info +="pay_memo=" + memo + "&";
				info +="pay_way=" + pay_way + "&";
				info +="pay_uu_code=" + uu_code + "&"; */
				//var str = "pay_products=";
				var pay_products = "";
				for (var i = 0; i < ids.length; i++) {
		
						//获取选中的商品id和商品个数
						var product_id = ids[i].value;
						var count = counts[i].value;
						var mallcar_id = mallcar_ids[i].value;
						console.log("  product_id="+product_id+", count="+count+", mallcar_id="+mallcar_id);
						
						var tem = product_id+","+count+","+mallcar_id+"@";	
						//拼凑信息
						pay_products += tem;
						
				}
				//把最后面的%号去掉
				//str = str.substr(0,str.length-1);
				pay_products = pay_products.substr(0,pay_products.length-1);
				//console.log(info+str);
				//开始向服务发送消息

				//	window.location.href=loc + info + str;
				
				$.post(loc,
						{
							"pay_products":pay_products,
							"pay_name":name,
							"pay_phone":phone,
							"pay_address":address,
							"pay_memo":memo,
							"pay_way":pay_way,
							"pay_uu_code":uu_code
							
						}, function(data) {
							console.log(data['state']);
							if(data["state"] == "true"){
								var order_no = data['order_no'];
								if(confirm("订单已成功提交！ "+"订单号："+order_no+", 马上查看订单？")){
									//alert("查看订单"+order_no);
									//usercenter_order_listPage1
									window.location.href="${pageContext.request.contextPath}/testAction_usercenter_order_listPage1";
								
								}else{
									document.getElementById("logo_a_index").click();
								}
							}else if(data["state"] == "false_unlogined"){
								console.log(data['error']);
								//alert("添加失败！"+ data["error"]);
								if(confirm("您还未登录，立即登录！")){
									document.getElementById("top_long_a").click();
								}
							}else if(data["state"] == "false_count"){
								console.log(data['error']);
								alert(data['error']);
							}else if(data["state"] == "false_book"){
								console.log(data['error']);
								alert(data['error']);
							}else if(data["state"] == "false_order"){
								console.log(data['error']);
								alert(data['error']);
							}
							
					}, "json"); 
				
			}
			//计算
			function calMoney() {
				//zong jia ge
				var totalMoney_d = document.getElementsByName('totalMoney')[0];
				
				var money_d = document.getElementsByName('price');
				var count_i = document.getElementsByName('count');
				var discounts = document.getElementsByName('discount');
				var total = 0;
				for(var i = 0;i<money_d.length;i++){
					var tem =(money_d[i].value * 1) *(count_i[i].value*1)*(discounts[i].value*1*0.1);
					
					total += tem;
				}
				
				console.log(total);
				totalMoney_d.value=total.toFixed(2);
			}
			
			//setTimeout('calMoney()',1000);
			 function init(){
				 calMoney();
				 change_pay_way(0);
				 //默认训责第一条目
				 select_addr(-1);
			 }
			</script>

		</div>
		<div class="cls"></div>
		<div class="foot">
			<div class="info"></div>
		</div>
	</div>
</body>
</html>