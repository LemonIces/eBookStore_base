<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详情</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/mallcar.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
</head>
<body onload="init()">
	<div class="box">
		<div class="top">
			<div class="fl">
				欢迎光临网上图书商城,
				<s:if test="#session.loginUser==null">
				请<a href="${pageContext.request.contextPath}/testAction_loginPage">登录</a>
					<a href="${pageContext.request.contextPath}/testAction_registPage">
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
				<a href="index.jsp"><img class="" style="width: 160px;height: 60px; cursor: pointer;" src="${pageContext.request.contextPath }/imgs/logo1.jpeg"></a>
			</div>
			<div class="mallcar_sub_logo">
				<span style="float: left; margin-left: 10px; margin-top: 5px;">订单详情</span>
			</div>
			<div class="cls"></div>
		</div>
		<div class="main" style="min-height: 500px;padding: 5px 8px;">
			<s:if test="#session.loginUser!=null">
			<div style="background-color: #f9f9f9;padding: 10px;">
			<p>
				<font style="font-size: 16px;">订单号：</font>
				<s:property value="#detailOrder.order_no" />
			</p>
			<p>
				<font style="font-size: 16px;">订单时间：</font>
				<s:property value="#detailOrder.time" />
			</p>
			<p>
				<font style="font-size: 16px;">支付方式：</font>
				<s:property value="#detailOrder.pay_way" />
			</p>
			<p>
				<font style="font-size: 16px;">收货人姓名：</font>
				<s:property value="#detailOrder.name" />
			</p>
			<p>
				<font style="font-size: 16px;">收货人联系电话：</font>
				<s:property value="#detailOrder.phone" />
			</p>
			<p>
				<font style="font-size: 16px;">收货人地址：</font>
				<s:property value="#detailOrder.address" />
			</p>
			<p>
				<font style="font-size: 16px;">总金额：</font>
				￥<s:property value="#detailOrder.sum_money" />元
			</p>
			<p>
				<font style="font-size: 16px;">给商家留言：</font>
				<s:property value="#detailOrder.momo" />
			</p>
			<p>
				<font style="font-size: 16px;">	订单状态：</font>
				<s:if test="#detailOrder.statu==1"><span>订单已支付</span></s:if>
				<s:if test="#detailOrder.statu==2"><span>订单已处理</span></s:if>
				<s:if test="#detailOrder.statu==3"><span>订单已完成</span></s:if>
				<s:if test="#detailOrder.statu==5"><span>订单退货中</span></s:if>
				<s:if test="#detailOrder.statu==6"><span>退货成功</span></s:if>
			</p>
				<p>
					<font style="font-size: 16px;">	售后服务：</font>
					<s:if test="#detailOrder.statu==1"><a href="javascript:orderReturn('${pageContext.request.contextPath}/userAction_returnOrderGoods','<s:property value="#detailOrder.order_id" />',5)">申请退货退款</a></s:if>
					<s:if test="#detailOrder.statu==2"><a href="javascript:orderReturn('${pageContext.request.contextPath}/userAction_returnOrderGoods','<s:property value="#detailOrder.order_id" />',5)">申请退货退款</a></s:if>
					<s:if test="#detailOrder.statu==3"><a href="javascript:orderReturn('${pageContext.request.contextPath}/userAction_returnOrderGoods','<s:property value="#detailOrder.order_id" />',5)">申请退货退款</a></s:if>
					
					<s:if test="#detailOrder.statu==5"><a href="javascript:orderReturn('${pageContext.request.contextPath}/userAction_returnOrderGoods','<s:property value="#detailOrder.order_id" />',6)">取消退货</a></s:if>
					<s:if test="#detailOrder.statu==6">已退货</s:if>
					
				</p>
			</div>
			<div style="background-color: #f9f9f9;padding: 10px;min-height: 300px;">	
			<p>
				<font style="font-size: 16px;">书本详情：</font>
			</p>
			<table class="table_detail_1" style="text-align: center;width: 100%;">
			<tr>
				<th>封面</th>
				<th>书名</th>
				<th>数量</th>
				<th>价格</th>
				<th>折扣</th>
				<th>小计</th>
			</tr>
			
			<s:iterator value="#detailOrder.items" var="item">
				<tr>
				<td> 
				<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#item.product_id" />">
					<img title="<s:property value='#item.product_name'/>" width="70" height="90" src="<s:property value='#item.product_img'/>" />
				</a>
				<td>
					<a target="_blank" href="${pageContext.request.contextPath }/bookAction_bookDetail?book_id=<s:property value="#item.product_id" />">
					<s:property value="#item.product_name" />
					</a>
				</td>
				<td><s:property value="#item.count" /></td>
				<td>￥<s:property value="#item.price" />元</td>
				<td><s:property value="#item.discount" />折</td>
				<td>￥<s:property value="#item.money" />元</td>
				</tr>
			</s:iterator>
			</table>
			</div>
			</s:if>
			

			<script type="text/javascript">
			
			function orderReturn(loc,id,sta) {
				console.log("orderReturn");
				console.log("loc="+loc);
				console.log("id="+id);
				console.log("sta="+sta);
				var ms = "确定要退货吗？";
				if(sta==6)ms = "确定要取消退货吗？";
				if(confirm(ms)){
					$.post(loc,
							{
								"order_id":id,
								"state":sta
							}, function(data) {
								console.log(data['state']);
								if(data["state"] == "true"){
									var order_no = data['order_no'];
										alert("已经成功申请退货！");
										//alert("查看订单"+order_no);
										window.location.href="${pageContext.request.contextPath}/userAction_order_detail?order_id="+id;
								}else if(data["state"] == "false_unlogined"){
									console.log(data['error']);
									//alert("添加失败！"+ data["error"]);
									if(confirm("您还未登录，立即登录！")){
										document.getElementById("top_long_a").click();
									}
								}else if(data["state"] == "false_orderstate"){
									console.log(data['error']);
									//alert("添加失败！"+ data["error"]);
									alert(data['error']);
								}
								
						}, "json"); 
				}
			}
			
			
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
				
				
				var loc = "${pageContext.request.contextPath}/userAction_pay_order?";
				
				//姓名 地址 手机号
						var name = document.getElementById('name_span').innerHTML;
						var address = document.getElementById('address_span').innerHTML;
						var phone = document.getElementById('phone_span').innerHTML;
				
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
				
				var info = "pay_name=";
				info += name + "&";
				info +="pay_phone="+ phone + "&";
				info +="pay_address="+ address + "&"
				info +="pay_memo=" + memo + "&";
				info +="pay_way=" + pay_way + "&";
				
				var str = "pay_products=";
				for (var i = 0; i < ids.length; i++) {
		
						//获取选中的商品id和商品个数
						var product_id = ids[i].value;
						var count = counts[i].value;
						var mallcar_id = mallcar_ids[i].value;
						console.log("  product_id="+product_id+", count="+count+", mallcar_id="+mallcar_id);
						
						var tem = product_id+","+count+","+mallcar_id+"@";	
						//拼凑信息
						str += tem;
						
				}
				//把最后面的%号去掉
				str = str.substr(0,str.length-1);
				console.log(info+str);
				//开始向服务发送消息
				
				
				window.location.href=loc + info + str;
				
				
			}
			
			
			//计算
			function calMoney() {
				//zong jia ge
				var totalMoney_d = document.getElementsByName('totalMoney')[0];
				
				var money_d = document.getElementsByName('price');
				
				var total = 0;
				for(var i = 0;i<money_d.length;i++){
					var tem = money_d[i].value * 1;
					
					total += tem;
				}
				
				console.log(total);
				totalMoney_d.value=total;
			}
			
			//setTimeout('calMoney()',1000);
			
			
			
			 function init(){
				 calMoney();
				 //默认训责第一条目
				 select_addr(-1);
				 
				 change_pay_way(1);
				 
			 }
			</script>

		</div>
		<div class="cls"></div>
<jsp:include page="in_foot.jsp"></jsp:include>

	</div>
</body>
</html>