package com.le.ebook.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class User {
	private Long user_id;
	private String username;
	private String password;
	private String gender;
	private String nickname;
	private String introduce;
	
	private String email;
	private String phone;
	
	private String head_img;
	
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	private String regist_time;
	
	private String statu;
	
	//用户购物车中的书籍
	private Set<Book> mall_books = new LinkedHashSet<Book>();
	
	
	
	
	
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public Set<Book> getMall_books() {
		return mall_books;
	}
	public void setMall_books(Set<Book> mall_books) {
		this.mall_books = mall_books;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegist_time() {
		return regist_time;
	}
	public void setRegist_time(String regist_time) {
		this.regist_time = regist_time;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", gender=" + gender
				+ ", nickname=" + nickname + ", introduce=" + introduce + ", email=" + email + ", phone=" + phone
				+ ", head_img=" + head_img + ", regist_time=" + regist_time + ", statu=" + statu + ", mall_books="
				+ mall_books + "]";
	}
	public User(Long user_id, String username, String password, String gender, String nickname, String introduce,
			String email, String phone, String head_img, String regist_time, String statu, Set<Book> mall_books) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.nickname = nickname;
		this.introduce = introduce;
		this.email = email;
		this.phone = phone;
		this.head_img = head_img;
		this.regist_time = regist_time;
		this.statu = statu;
		this.mall_books = mall_books;
	}
	public Map check(){
		Map<String,String> maperr = new HashMap<String,String>();
		if(this.username == null || "".equals(this.username.trim())){
			maperr.put("username", "用户名为空!");
		}
		
		if(this.password == null || "".equals(this.password.trim())){
			maperr.put("password", "密码为空!");
		}
		
		if(this.email == null || "".equals(this.email.trim())){
			maperr.put("email", "邮箱为空!");
		}
		
		if(email == null || email.equals("")){
			maperr.put("email", "邮箱为空!");
		}
		else if(email!=null && email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")){
			
		}else{
			maperr.put("email", "邮箱格式错误!");
		}
		
		return maperr;
	}
	
	
	
public String checkUserErrorData(){
		if(this.username == null || "".equals(this.username.trim())){
			return "用户名为空!";
		}
		
		if(this.password == null || "".equals(this.password.trim())){
			return "密码为空!";
		}
		
		if(email == null || email.equals("")){
			return  "邮箱为空!";
		}
		else if(email!=null && email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")){
			
		}else{
			return   "邮箱格式错误!";
		}
		return null;
	}

}
