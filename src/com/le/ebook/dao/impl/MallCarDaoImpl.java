package com.le.ebook.dao.impl;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.le.ebook.dao.MallCarDao;
import com.le.ebook.domain.MallCar;

public class MallCarDaoImpl extends BaseDaoImpl implements MallCarDao {

	@Override
	public Object getOne(String hql,Object[] params) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				Query query = session.createQuery(hql);
				for (int i = 0; params!=null && i < params.length; i++) {
					Object str = params[i];
					query.setParameter(i, str);
					
				}
				return query.uniqueResult();
			}
		});
	}

	@Override
	public void save(MallCar mallCar) {
		getHibernateTemplate().save(mallCar);
	}

	@Override
	public void update(MallCar existMall) {
		
		getHibernateTemplate().update(existMall);
		
	}


}
