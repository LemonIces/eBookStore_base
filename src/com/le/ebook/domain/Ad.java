package com.le.ebook.domain;

public class Ad {
	private Long ad_id;
	
	private String ad_title;
	
	private String ad_img;
	
	private String ad_loc;
	
	private String ad_time;
	
	private String statu;

	public Long getAd_id() {
		return ad_id;
	}

	public void setAd_id(Long ad_id) {
		this.ad_id = ad_id;
	}

	public String getAd_title() {
		return ad_title;
	}
	
	

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}

	public String getAd_img() {
		return ad_img;
	}

	public void setAd_img(String ad_img) {
		this.ad_img = ad_img;
	}

	public String getAd_loc() {
		return ad_loc;
	}

	public void setAd_loc(String ad_loc) {
		this.ad_loc = ad_loc;
	}

	public String getAd_time() {
		return ad_time;
	}

	public void setAd_time(String ad_time) {
		this.ad_time = ad_time;
	}

	
	

}
