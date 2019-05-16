package com.le.ebook.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.internal.compiler.flow.LoopingFlowContext;

public class Order {
	
	private Long order_id;
	
	private String order_no;
	
	private Long user_id;
	
	private String time;
	
	private String statu;

	private String statu0;
	
	private String momo;
	
	private String pay_way;
	
	private String uu_code;
	
	private String username;
	
	
	public String getPay_way() {
		return pay_way;
	}



	public String getStatu0() {
		return statu0;
	}



	public void setStatu0(String statu0) {
		this.statu0 = statu0;
	}



	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	//总金额
	private String sum_money;
	//总商品数
	private String product_count;
	
	
	private String address;
	private String name;
	private String phone;
	
	
	//该订单包含书籍
	private Set<Order_item> items = new LinkedHashSet<>();

	public Long getOrder_id() {
		return order_id;
	}



	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}


	

	public String getOrder_no() {
		return order_no;
	}



	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}



	public Long getUser_id() {
		return user_id;
	}



	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getStatu() {
		return statu;
	}

	


	public String getUu_code() {
		return uu_code;
	}



	public void setUu_code(String uu_code) {
		this.uu_code = uu_code;
	}



	public void setStatu(String statu) {
		this.statu = statu;
	}



	public String getMomo() {
		return momo;
	}



	public void setMomo(String momo) {
		this.momo = momo;
	}



	public String getSum_money() {
		return sum_money;
	}



	public void setSum_money(String sum_money) {
		this.sum_money = sum_money;
	}



	public String getProduct_count() {
		return product_count;
	}



	public void setProduct_count(String product_count) {
		this.product_count = product_count;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}






	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Set<Order_item> getItems() {
		return items;
	}



	public void setItems(Set<Order_item> items) {
		this.items = items;
	}
	
		double sum = 0;
		public void calSumMoney(){
	if(items!=null){
			Iterator<Order_item> iterator = items.iterator();
			while(iterator.hasNext()){
				Order_item next = iterator.next();
				sum += Double.valueOf(next.getMoney());
			}
	}
		this.sum_money = String.format("%.2f", sum);
	}
	
	public void calCount(){
		long count = 0L;
		if(items!=null){
			Iterator<Order_item> iterator = items.iterator();
			while(iterator.hasNext()){
				Order_item next = iterator.next();
				count += Long.valueOf(next.getCount());
			}
	}
		this.product_count = count+"";
	}
}
