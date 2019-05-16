package com.le.ebook.service;

import java.util.List;
import java.util.Map;

import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Admin;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.domain.Order;
import com.le.ebook.domain.Order_item;
import com.le.ebook.domain.User;
import com.le.ebook.utils.MyPageBean;

public interface AdminService {

	/**
	 * 登录
	 * @param name
	 * @param pwd
	 */
	Admin login(String name, String pwd);

	/**
	 * 显示指定状态的订单
	 * @param statu
	 * @return
	 */
	List<Order> listOrder(String statu);

	List<Order_item> listOrderItem(Long order_id);

	/**
	 * 处理订单
	 * @param order_id
	 */
	void disposeOrder(Long order_id);

	/**
	 * 删除订单
	 * @param order_id
	 */
	void deleteOrder(Long order_id);

	List<User> getUserList();

	/**
	 * 重置用户密码
	 * @param user_id
	 */
	void userRePwd(Long user_id);

	/**
	 * 获取用户信息
	 * @param user_id
	 * @return
	 */
	User getUserById(Long user_id);

	/**
	 * 显示图书列表
	 * @return
	 */
	List<Book> getBookList();

	/**
	 * 获取分类
	 * @return
	 */
	List<Bookcategory1> getCategory1();

	List<Bookcategory1> getCategory1(Long id);

	void addBook(Book book);

	void addCategory(Bookcategory1 bookcategory1);

	void addUser(User user);

	List<Ad> getAdList();

	Book getBookById(Long book_id);

	
	void updateBook(Book book);

	List<Book> getBookList(String statu);

	void deleteBook(Long book_id);

	void addAd(Ad ad);

	Ad getAdById(Long ad_id);

	void updateAd(Ad ad);

	void deleteAd(Long ad_id);

	void deleteUser(Long user_id);

	void reStatueUser(Long user_id);

	void setBookNews(Long book_id, String news);

	void setBookhots(Long book_id, String hots);

	Map<String, Object> getBookPageMap(String book_name, String statu, String is_news, String is_hots, Integer page,
			Integer page_size);

	/**
	 * 分页订单
	 * @param page
	 * @param page_size
	 * @param statu
	 * @return
	 */
	MyPageBean listOrder(String likeusername,Integer page, Integer page_size, String statu);

	/**
	 * 完成订单
	 * @param order_id
	 */
	void orderOk(Long order_id);

	/**
	 * 分页用户
	 * @param likeusername
	 * @param page
	 * @param page_size
	 * @param statu
	 * @return
	 */
	MyPageBean getUserList(String likeusername,Integer page, Integer page_size, String statu);

	/**
	 * 分页ad
	 * @param page
	 * @param page_size
	 * @return
	 */
	MyPageBean getAdList(Integer page, Integer page_size);

	/**
	 * 分页分类
	 * @param page
	 * @param page_size
	 * @param statu
	 * @return
	 */
	MyPageBean getCategory1(String like_category_name,Integer page, Integer page_size, String statu);

	/**
	 * 修改分类
	 * @param bookcategory1
	 */
	void updateCategory(Bookcategory1 bookcategory1);

	/**
	 * 删除分类
	 * @param bookcategory1_id
	 */
	void deleteCategory(Long bookcategory1_id);

	MyPageBean getUserAddress(Long user_id);

	/**
	 * 通过ID获取订单
	 * @param order_id
	 * @return
	 */
	Order getOrderById(Long order_id);

	/**
	 * 退货
	 * @param order_id
	 */
	void returnOrder(Order order);


}
