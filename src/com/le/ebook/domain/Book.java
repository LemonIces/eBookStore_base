package com.le.ebook.domain;

import java.util.HashSet;
import java.util.Set;

public class Book {
	

	
	private Long book_id;
	
	private String book_name;
	
	private String author;
	
	private String publishing;

	private String publish_time;
	
	private Long word_count;
	
	private Long total_page;
	
	private String memo;
	
	private String statu;
	
	private String price;
	
	private String img;
	
	private Long count;
	
	private String discount;
	
	private String news;
	
	private String hots;
	
	private String add_time;
	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	


	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getHots() {
		return hots;
	}

	public void setHots(String hots) {
		this.hots = hots;
	}




	private  Set<User> mall_users = new HashSet<User>();
	
	public Set<User> getMall_users() {
		return mall_users;
	}

	public void setMall_users(Set<User> mall_users) {
		this.mall_users = mall_users;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	//书籍分类
	private Bookcategory1 bookcategory1 = new Bookcategory1();
	




	public Bookcategory1 getBookcategory1() {
		return bookcategory1;
	}

	public void setBookcategory1(Bookcategory1 bookcategory1) {
		this.bookcategory1 = bookcategory1;
	}

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public Long getWord_count() {
		return word_count;
	}

	public void setWord_count(Long word_count) {
		this.word_count = word_count;
	}

	public Long getTotal_page() {
		return total_page;
	}

	public void setTotal_page(Long total_page) {
		this.total_page = total_page;
	}





	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	
	
}
