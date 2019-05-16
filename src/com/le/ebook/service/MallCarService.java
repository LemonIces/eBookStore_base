package com.le.ebook.service;

import java.util.List;
import java.util.Map;

import com.le.ebook.domain.Book;
import com.le.ebook.domain.MallCar;
import com.le.ebook.exception.BookException;
import com.le.ebook.exception.MallCarException;
import com.le.ebook.utils.PageBean;

public interface MallCarService {

	PageBean getPage(MallCar mallCar);

	void addMallCar(MallCar mallCar) throws BookException, MallCarException;

	List getByUsernameForList(String username);

	void update(MallCar mallCar);

	Book getBookById(Long product_id);

	/**
	 * 分页的购物车
	 * @param username
	 * @param page
	 * @param page_size
	 * @return
	 */
	Map<String, Object> getByUsernameForList(String username, Integer page, Integer page_size);

	/**
	 * 删除
	 * @param mallcar_id
	 */
	void delete(Long mallcar_id);

}
