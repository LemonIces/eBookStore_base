package com.le.ebook.test;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.le.ebook.dao.BookDao;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1bak;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.domain.User;
import com.le.ebook.service.BookService;
import com.le.ebook.utils.HibernateUtils;
import com.le.ebook.utils.PageBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test02 {


	@Resource(name="sessionFactory")
	private SessionFactory sf;
	
	@Test
	//单独测试hibernate
	public void test02(){
		
		Configuration conf = new Configuration().configure();
		
		SessionFactory sf = conf.buildSessionFactory();
		
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
	
		//---------------------------------
		User u = new User();
		
		u.setUsername("zs");
		u.setPassword("123");
		session.save(u);
		
		//---------------------------------
		tx.commit();
		session.close();
		
		sf.close();
		
	}
	
	

	
	
	@Test
	//单独测试hibernate hibernateUtils
	//测试bookcategory1与2对应关系
	public void test02_2(){
		
		
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();

		//---------------------------------
		
		Bookcategory1bak bc1 = new Bookcategory1bak();
		
		bc1.setName("小说");
		
		Bookcategory1 bc2 = new Bookcategory1();
		
		bc2.setName("玄幻小说");
		
		bc1.getBookcategory2s().add(bc2);
		//bc2.setBookcategory1(bc1);
		
		session.save(bc1);
		session.save(bc2);
		

		//---------------------------------
		tx.commit();
		
		session.close();
		
		
	}
	
	
	@Test
	//单独测试hibernate hibernateUtils
	//测试book
	public void test02_3(){
		
		
		Session session = HibernateUtils.getCurrentSession();
		
		Transaction tx = session.beginTransaction();

		//---------------------------------
		
		Book b = new Book();
		
		b.setBook_name("斗破");
		session.save(b);
		

		//---------------------------------
		tx.commit();
		
		
	}
	
	
	@Resource(name="bookService")
	private BookService bookService;
	@Test
	//单独测试bookService
	//测试book
	public void test03(){
		Book book = new Book();
		book.setBook_name("斗");
		PageBean pageBean = bookService.getPageBean(book, 1, 1);
		System.out.println(pageBean);
		System.out.println(pageBean.getList());
		
	}
	
	
	
	@Resource(name="bookDao")
	private BookDao bookDao;
	@Test
	//单独测试bookDao
	//测试book
	public void test03_1(){
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		dc.add(Restrictions.like("book_name", "%%"));
		Integer count = bookDao.getTotalCount(dc);
		System.out.println(count);
		
		List list = bookDao.get(dc, 0, 10);
		System.out.println(list);
		
	}

	
}
