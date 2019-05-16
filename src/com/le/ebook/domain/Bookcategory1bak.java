package com.le.ebook.domain;

import java.util.HashSet;
import java.util.Set;

public class Bookcategory1bak {
	
	private Long bookcategory1_id;
	
	private String name;
	
	private String memo;
	
	private String statu;
	
	private Set<Bookcategory1> bookcategory2s = new HashSet<Bookcategory1>();

	public Long getBookcategory1_id() {
		return bookcategory1_id;
	}

	public void setBookcategory1_id(Long bookcategory1_id) {
		this.bookcategory1_id = bookcategory1_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<Bookcategory1> getBookcategory2s() {
		return bookcategory2s;
	}

	public void setBookcategory2s(Set<Bookcategory1> bookcategory2s) {
		this.bookcategory2s = bookcategory2s;
	}
	
	
	

}
