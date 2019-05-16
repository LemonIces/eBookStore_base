package com.le.ebook.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.le.ebook.dao.BaseBao;
import com.le.ebook.domain.User;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseBao{

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
	public List get(DetachedCriteria dc, Integer start, Integer pageSize) {
		return  getHibernateTemplate().findByCriteria(dc, start, pageSize);
	}

	@Override
	public List get(DetachedCriteria dc) {
		 return  getHibernateTemplate().findByCriteria(dc);
	}

	@Override
	public Object getOne(DetachedCriteria dc) {
		 return  getHibernateTemplate().findByCriteria(dc).get(0);
	}
	
	@Override
	public <T>  List<T> get(String hql,Object[] params,int start,int max) {

		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				
				Query query = session.createQuery(hql);
				
				if(start >0){
					query.setFirstResult(start);
				}
				if (max > 0) {
					query.setMaxResults(max);
				}

				if(params!=null){
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				
				List list = query.list();
				
				return list;
			}
		});
	}

	@Override
	public <T> List<T> get(String hql, Object[] params) {
		return this.get(hql, params,-1,-1);
	}

	public void deleteObj(Object obj) {
		
		getHibernateTemplate().delete(obj);
	}

	@Override
	public void updateObj(Object obj) {
		
		getHibernateTemplate().update(obj);
	}
	
	
	

}
