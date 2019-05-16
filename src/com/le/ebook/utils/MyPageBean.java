package com.le.ebook.utils;

import java.util.List;

public class MyPageBean {

	private Integer page;
	
	private Integer page_size;
	
	private Integer total_count;
	
	private Integer total_page;
	
	private List<Object> list;

	public MyPageBean(Integer page, Integer page_size, Integer total_count) {
		super();
		
		//检查当前页
		if (page== null || page <1) {
			page = 1;
		}
		//检查每页条数
		if(page_size == null || page_size <  1 ){
			page_size = 20;
		}
		if (total_count == null || total_count <1) {
			total_count = 0;
		} 
		this.page = page;
		this.page_size = page_size;
		this.total_count = total_count;
		//计算页数
		calTotal_page();
		
	}

	private void calTotal_page(){
		//计算页数
		this.total_page = (int)((this.total_count *1.0 / this.page_size)+0.9998);
		
		if (page > total_page) {
			page = total_page;
		}
		
	}
	
	/**
	 * 获取开始索引
	 * @return
	 */
	public Integer getStartIndex(){
		int start = (page - 1)*page_size;
		return start;
	}
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
		calTotal_page();
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
		calTotal_page();
	}

	public Integer getTotal_page() {
		return total_page;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "MyPageBean [page=" + page + ", page_size=" + page_size + ", total_count=" + total_count
				+ ", total_page=" + total_page + ", list=" + list + "]";
	}
	
	
	
	
	
}
