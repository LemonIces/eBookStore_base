package com.le.ebook.domain;

import java.util.HashSet;
import java.util.Set;

public class Bookcategory1 {

	private Long bookcategory1_id;
	
	private String name;
	
	private String memo;
	
	private String statu;
	
	
	//该分类的书籍
	private Set<Book> books = new HashSet<Book>();

	
	
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}



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

	
	
}
