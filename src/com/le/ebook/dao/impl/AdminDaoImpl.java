package com.le.ebook.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.le.ebook.dao.AdminDao;
import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Admin;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Order;

public class AdminDaoImpl extends HibernateDaoSupport implements AdminDao{

	@Override
	public Admin getAdminByNameAndPwd(String name, String pwd) {
		return getHibernateTemplate().execute(new HibernateCallback<Admin>() {

			@Override
			public Admin doInHibernate(Session session) throws HibernateException {
				String hql = " from com.le.ebook.domain.Admin where admin_name=? and admin_password=?";
				
				Query query = session.createQuery(hql);
				query.setParameter(0, name);
				query.setParameter(1, pwd);
				
				return (Admin) query.uniqueResult();
			}
		});
	}
	
	@Override
	public Integer getTotalCount(DetachedCriteria dc) {
		//设置只查询条目数
		dc.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) getHibernateTemplate().findByCriteria(dc);
		dc.setProjection(null);
		if(list!=null && list.size() > 0){
			return list.get(0).intValue();
		}
		return null;
	}
	

	@Override
	public List get(String hql, Object[] params) {
	return	getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException {
				
				Query query = session.createQuery(hql);
				
				if(params!=null && params.length>0){
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
		
	
	}

	@Override
	public Object getOne(String hql, Object[] params) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				Query query = session.createQuery(hql);
				
				if(params!=null && params.length>0){
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				
				return query.uniqueResult();
			}
		});
		
	}

	@Override
	public void updateObj(Object obj) {
		getHibernateTemplate().update(obj);
	}

	@Override
	public void saveObj(Object obj) {
		getHibernateTemplate().save(obj);
		
	}

	@Override
	public List<Object> get(DetachedCriteria dc, int start, int max) {
		return (List<Object>) getHibernateTemplate().findByCriteria(dc, start, max);
	}

	@Override
	public void deleteObj(Object obj) {
		getHibernateTemplate().delete(obj);
	}


}
