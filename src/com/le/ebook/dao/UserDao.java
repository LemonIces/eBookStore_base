package com.le.ebook.dao;

import org.hibernate.criterion.DetachedCriteria;

import com.le.ebook.domain.Order;
import com.le.ebook.domain.User;
import com.le.ebook.domain.User_address;
import com.le.ebook.exception.OrderException;

public interface UserDao  extends BaseBao{
	
	/**
	 * 通过username查找一个用户
	 * @param username
	 * @return
	 */
	public User getByUsername(String username);
	
	/**
	 * 添加一个user
	 * @param user
	 */
	public void addNewUser(User user);
/**
 * 查找到唯一的user
 * @param user
 * @return
 */
	public User findOneUser(User user);
	

	void update(User user);

	/**
	 * 添加收货地址
	 * @param address
	 */
	public void add_address(User_address address);

	public void addOrUpdate(Order order);

	/**
	 * 
	 * @param order
	 * @throws OrderException 
	 */
	public void addOrder(Order order) throws OrderException;
	




}
