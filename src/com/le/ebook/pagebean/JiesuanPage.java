package com.le.ebook.pagebean;

public class JiesuanPage {
	private Long product_id;
	
	private Long count;
	
	private Long mallcar_id;
	private String price;
	private String discount;
	private String statu;
	private String memo;
	private String product_name;
	private String money;
	private String img;
	private String publishing;
	private String author;
	private String author_nation;
	
	
	
public void calMoney(){
	this.money = String.format("%.2f",(Double.valueOf(price).doubleValue()* Long.valueOf(count).longValue()* Double.valueOf(discount).doubleValue() * 0.1));
}

	public String getAuthor_nation() {
		return author_nation;
	}

	public void setAuthor_nation(String author_nation) {
		this.author_nation = author_nation;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getAuthor() {
		return author;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Long getMallcar_id() {
		return mallcar_id;
	}

	public void setMallcar_id(Long mallcar_id) {
		this.mallcar_id = mallcar_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	

}
