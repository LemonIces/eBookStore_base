package com.le.ebook.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.le.ebook.domain.User;

public interface BaseBao{

	/**
	 * 获取总条目数
	 * @param book
	 * @return
	 */
	Integer getTotalCount(DetachedCriteria dc);
		
	List get(DetachedCriteria dc);
		
		/**
		 * 获取数据封装到list集合中
		 * @param dc
		 * @param start
		 * @param pageSize
		 * @return
		 */
	List get(DetachedCriteria dc, Integer start, Integer pageSize);

	<T>List <T>get(String hql, Object [] params);

	<T> List<T> get(String hql, Object[] params, int start, int max);

	Object getOne(DetachedCriteria dc);


	/**
	 * 修改
	 * @param obj
	 */
	public void updateObj(Object obj);
	
	/**
	 * shanchu 
	 * @param obj
	 */
	public void deleteObj(Object obj);


	
}
