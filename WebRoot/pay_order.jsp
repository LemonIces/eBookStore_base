<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>支付订单</title>
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty1.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sty.css" />
<link rel="Stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/mallcar.css" />
<script type="text/javascript">
</script>

<script language='javascript' src='${pageContext.request.contextPath }/js/find.js'></script>
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
				<span style="
	float: left;
	margin-left: 10px;
	margin-top: 5px;
				">支付订单</span>
			</div>
			<div class="cls"></div>
		</div>
		<div class="main">
		
		
			<s:if test="#session.loginUser==null">
				<a href="${pageContext.request.contextPath }/testAction_loginPage">请登录</a>
			</s:if>

			<s:if test="#session.loginUser!=null">
			
			<s:if test="#msgMap.pay_msg!=null">
				<div>
					<p><s:property value="#msgMap.pay_msg" /></p>
				</div>				
			</s:if>
			
				
			<s:if test="#order.statu ==1">
				<div>
					<p>订单已成功提交！</p>
				</div>				
			</s:if>
			
			<s:if test="#order.pay_way=='支付宝' && #order.statu ==0">
				<div>
					<a href="${pageContext.request.contextPath }/userAction_re_pay_order?order_id=${order.order_id}">点击完成支付宝支付</a>
					
				</div>				
			</s:if>
			<s:if test="#order.pay_way=='微信' && #order.statu == 0">
				<div>
					<a href="${pageContext.request.contextPath }/userAction_re_pay_order?order_id=${order.order_id}">点击完成微信支付</a>
				</div>				
			</s:if>

			
			<a href="index.jsp">返回首页</a>
			<a href="${pageContext.request.contextPath}/testAction_usercenter_order_listPage1">我的订单</a>
			
			</s:if>
			

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
		<div class="cls10"></div>
		<div class="foot">
			<div class="info"></div>
		</div>

	</div>
</body>
</html>