package com.le.ebook.domain;

//用户收货地址
public class User_address {
	
	private Long user_address_id;
	
	private Long user_id;
	
	private String name;
	
	private String phone;
	
	private String address;
	private String gender;
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getUser_address_id() {
		return user_address_id;
	}

	public void setUser_address_id(Long user_address_id) {
		this.user_address_id = user_address_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


}
