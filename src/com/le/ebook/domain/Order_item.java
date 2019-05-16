package com.le.ebook.domain;

public class Order_item{
	
	private Long item_id;
	private Long product_id;
	private String product_name;
	private Long mallcar_id;
	private Long count;
	private String price; 
	private String discount;
	private String money;
	private String product_img;
	
	//属于哪个订单
	private Order order;

	
	public Long getMallcar_id() {
		return mallcar_id;
	}

	public void setMallcar_id(Long mallcar_id) {
		this.mallcar_id = mallcar_id;
	}
	
	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	public void calMoney(){
		this.money =  
				String.format("%.2f",( this.count * Double.valueOf(this.price) * Double.valueOf(this.discount) * 0.1));
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	

}
