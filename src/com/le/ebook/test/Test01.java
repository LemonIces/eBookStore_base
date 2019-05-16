package com.le.ebook.test;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.le.ebook.dao.UserDao;
import com.le.ebook.domain.User;
import com.le.ebook.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test01 {
	@Resource(name="sessionFactory")
	private SessionFactory sf;
	@Test
	//单独测试hibernate
	public void test01(){
		
		Configuration conf = new Configuration().configure();
		
		SessionFactory sf = conf.buildSessionFactory();
		
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		//------------------------------------
		
		User u = new User();
		
		u.setUsername("zs");
		u.setPassword("123");
		session.save(u);
		
		//------------------------------------
		tx.commit();
		
		session.close();
		
		sf.close();
		
	}	
	@Test
	//测试spring管理的sessionFactory
	public void test02(){
		
		
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		//------------------------------------
		
		User u = new User();
		
		u.setUsername("lin");
		u.setPassword("123");
		session.save(u);
		
		//------------------------------------
		tx.commit();
		
		session.close();
		
		
	}
	
	@Resource(name="userDao")
	private UserDao userDao;
	@Test
	//测试hibernateTemplate
	public void test03(){
		
		User user = userDao.getByUsername("zs");
		System.out.println(user);
		
		
	}
	
	@Resource(name="userService")
	private UserService userService;
/*	@Test
	//测试事务
	public void test04(){
		
		User u = new User();
		
		u.setUsername("lin123");
		u.setPassword("123");
		u.setEmail("linn@lin.com");
		
		userService.addUser(u);
		
	}*/
	
	@Test
	public void test05(){
		String email = "userd@user.com";
		boolean matches = email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$");
		System.out.println(matches);
	}

}
