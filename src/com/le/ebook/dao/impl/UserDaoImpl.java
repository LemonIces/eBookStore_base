package com.le.ebook.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.le.ebook.dao.UserDao;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.MallCar;
import com.le.ebook.domain.Order;
import com.le.ebook.domain.Order_item;
import com.le.ebook.domain.User;
import com.le.ebook.domain.User_address;
import com.le.ebook.exception.BookCountNotEnoughException;
import com.le.ebook.exception.OrderException;
import com.le.ebook.utils.MyMD5EncodeUtil;

//使用是要注入sessionFactory
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User getByUsername(String username) {
		
		//Criteria
/*		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("username", username));
		List<User> list = (List<User>) getHibernateTemplate().findByCriteria(dc);
		if(list!=null && list.size() >=1){
			return list.get(0);
		}else{
			return null;
		}*/
		//hql
		return getHibernateTemplate().execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session session) throws HibernateException {
				String hql = "from com.le.ebook.domain.User where username = ?";
				Query query = session.createQuery(hql);
				query.setParameter(0, username);
				User user = (User) query.uniqueResult();
				return user;
			}
		});
		
	}

	@Override
	public void addNewUser(User user) {
		getHibernateTemplate().save(user);
		
	}
	
	
	@Override
	public void update(User user) {
		
		getHibernateTemplate().update(user);
		
	}
	

	@Override
	public User findOneUser(User user) {
		return  getHibernateTemplate().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
				//书写hql语句
				String hql = " from com.le.ebook.domain.User where username = ? and password = ? and statu=1";
				Query query = session.createQuery(hql);
//				设置参数
				query.setParameter(0, user.getUsername());
				query.setParameter(1, user.getPassword());
//				得到唯一的user
				User login = (User) query.uniqueResult();
				return login;
			}
		});
	}

	@Override
	public void add_address(User_address address) {
		getHibernateTemplate().save(address);
		
	}

	@Override
	public void addOrUpdate(Order order) {
			getHibernateTemplate().execute(new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session) throws HibernateException {
					session.save(order);
					if(order!=null && order.getItems()!=null){
						Iterator<Order_item> iterator = order.getItems().iterator();
						while(iterator.hasNext()){
							session.saveOrUpdate(iterator.next());
						}
					}
					return null;
				}
			});
	}

	@Override
	public void addOrder(Order order) throws OrderException {
		boolean r =  getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
			@Override
			public Boolean doInHibernate(Session session) throws HibernateException {
				Iterator<Order_item> it = order.getItems().iterator();
				while(it.hasNext()){
					Order_item order_item = it.next();
					session.save(order_item);
				}
				session.save(order);
				return true;
			}
		});
		if (!r) {
			throw new OrderException("添加失败！");
		}
		
	}
}
