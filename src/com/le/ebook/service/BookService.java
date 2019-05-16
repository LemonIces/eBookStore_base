package com.le.ebook.service;

import java.util.List;
import java.util.Map;

import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.utils.PageBean;

public interface BookService {

	/**
	 * 获取页面显示的数据封装在pageBean中
	 * @param book
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean getPageBean(Book book, Integer currentPage, Integer pageSize);

	Book getBook(Book book);

	PageBean getPageBeanByCategory1Name(String category1, int start, int max);

	Book getBookById(Object id);

	PageBean getPageBeanByTitle(String book_title, Integer currentPage, Integer pageSize);

	/**
	 * 获取分类；列表
	 * @return
	 */
	List<Bookcategory1> getCategory1List();

	/**
	 * 
	 * @param search_text
	 * @param search_category1
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean getPageBeanByNameAndCategory1(String search_text, String search_category1, Integer currentPage,
			Integer pageSize);

	/**
	 * 通过category1获取书本
	 * @param category1
	 * @return
	 */
	List<Book> getBookListByCatecary1(String category1);

	List<Book> getBookByCategory1Name(String category1, int currentPage, int pageSize);

	PageBean getPageBean(Book book, Integer currentPage, Integer pageSize, Object[] order);

	/**
	 * 获取广告列表
	 * @param start
	 * @param max
	 * @return
	 */
	List<Ad> getAd(int start, int max);

	PageBean getHotBoosByCategory(String categoryname, Integer currentPage, Integer pageSize, Object[] orderby);

	PageBean getNewBookPageBean(Book book, Integer currentPage, Integer pageSize);

	/**
	 * 获取书本用作分页
	 * @param book_name
	 * @param category_id
	 * @param statu
	 * @param is_news
	 * @param is_hots
	 * @param page
	 * @param page_size
	 * @return
	 */
	Map<String, Object> getBookPageMap(String book_name, Long category_id, String statu, String is_news, String is_hots,
			Integer page, Integer page_size);

	/**
	 * 获取分类
	 * @param category_id
	 * @return
	 */
	Bookcategory1 getCategoryById(Long category_id);

	/**
	 * 通过分类id获取热门
	 * @param category_id
	 * @param currentPage
	 * @param pageSize
	 * @param orderby
	 * @return
	 */
	PageBean getHotBoosByCategoryById(Long category_id, Integer currentPage, Integer pageSize, Object[] orderby);

	/**
	 * 获取分类重start开始，获取最大max个
	 * @param start
	 * @param max
	 * @return
	 */
	List<Bookcategory1> getCategory1List(Integer start, Integer max);

	void updBook(Object obj);

}
