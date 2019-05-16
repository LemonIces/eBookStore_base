package com.le.ebook.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Admin;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Order;

public interface AdminDao{

	Admin getAdminByNameAndPwd(String name, String pwd);

	List get(String hql, Object[] objects);

	Object getOne(String hql, Object[] objects);

	void updateObj(Object obj);

	void saveObj(Object obj);

	Integer getTotalCount(DetachedCriteria dc);

	List<Object> get(DetachedCriteria dc, int start, int max);

	/**
	 * 删除
	 * @param one
	 */
	void deleteObj(Object obj);

}
