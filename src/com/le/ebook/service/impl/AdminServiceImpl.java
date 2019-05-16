package com.le.ebook.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CountProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.le.ebook.dao.AdminDao;
import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Admin;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.domain.Order;
import com.le.ebook.domain.Order_item;
import com.le.ebook.domain.User;
import com.le.ebook.domain.User_address;
import com.le.ebook.service.AdminService;
import com.le.ebook.utils.MyPageBean;

public class AdminServiceImpl implements AdminService{

	private AdminDao adminDao;
	
	@Override
	public Admin login(String name, String pwd) {
		return adminDao.getAdminByNameAndPwd(name,pwd);
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	public List<Order> listOrder(String statu) {
		if (statu == null || statu.length() <=0) {
			statu = "0";
		}
		String hql = " from com.le.ebook.domain.Order where statu=? order by time desc";
		List list = adminDao.get(hql,new Object[]{statu});
		return list;
	}
	
	@Override
	public MyPageBean listOrder(String username,Integer page,Integer page_size,String statu) {
		if (statu == null || statu.length() <=0) {
			statu = "0";
		}
		if (username == null || username.length() <=0) {
			username = "";
		}
		//创建离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.and(Restrictions.eqOrIsNull("statu", statu),Restrictions.like("username", "%"+username+"%")));
		dc.addOrder(org.hibernate.criterion.Order.desc("time"));
		dc.setProjection(Projections.rowCount());
		
		Integer totalCount = adminDao.getTotalCount(dc);
		//创建pagebean
		MyPageBean myPageBean = new MyPageBean(page, page_size, totalCount);
		
		List<Object> list = adminDao.get(dc, myPageBean.getStartIndex(), myPageBean.getPage_size());
		
		myPageBean.setList(list);
		
		return myPageBean;
	}
	

	@Override
	public List<Order_item> listOrderItem(Long order_id) {
		
		String hql = " from com.le.ebook.domain.Order where order_id=?";
		
		
		List list = adminDao.get(hql,new Object[]{order_id});
		
		List<Order_item> listItem = new ArrayList<Order_item>();
		if(list!=null && list.size() >=1){
			Order order = (com.le.ebook.domain.Order) list.get(0);
			Iterator<Order_item> iterator = order.getItems().iterator();
			while(iterator.hasNext()){
				listItem.add(iterator.next());
			}
		}
		return listItem;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void disposeOrder(Long order_id) {
		String hql = " from com.le.ebook.domain.Order where order_id=?";
		Order one = (Order) adminDao.getOne(hql,new Object[]{order_id});		
		one.setStatu("2");
		one.setStatu0("2");
		adminDao.updateObj(one);
	}

	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void orderOk(Long order_id) {
		String hql = " from com.le.ebook.domain.Order where order_id=?";
		Order one = (Order) adminDao.getOne(hql,new Object[]{order_id});		
		one.setStatu("3");
		one.setStatu0("3");
		adminDao.updateObj(one);
	}
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteOrder(Long order_id) {
		String hql = " from com.le.ebook.domain.Order where order_id=?";
		Order one = (Order) adminDao.getOne(hql,new Object[]{order_id});		
		one.setStatu("4");
		adminDao.updateObj(one);
	}

	@Override
	public List<User> getUserList() {
		String hql = " from com.le.ebook.domain.User";
		List list = adminDao.get(hql, null);
		return list;
	}
	
	
	/**
	 * 分页用户pagebean
	 * @param statu
	 * @param page
	 * @param page_size
	 * @param statu
	 * @return
	 */
	@Override
	public MyPageBean getUserList(String username,Integer page,Integer page_size,String statu) {
		if(statu == null ||statu.length() <=0){
			statu = "1";
		}
		if(username == null ||username.length() <=0){
			username = "";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.and(Restrictions.like("statu", "%"+statu+"%"),Restrictions.like("username", "%"+username+"%")));
		dc.setProjection(Projections.rowCount());
		//获取总条目数
		Integer totalCount = adminDao.getTotalCount(dc);
		
		MyPageBean myPageBean = new MyPageBean(page, page_size, totalCount);
		List list = adminDao.get(dc, myPageBean.getStartIndex(), myPageBean.getPage_size());
		//设置列表
		myPageBean.setList(list);
		return myPageBean;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void userRePwd(Long user_id) {
		String hql = " from com.le.ebook.domain.User where user_id=?";
		User one = (User) adminDao.getOne(hql,new Object[]{user_id});		
		one.setPassword("123456");
		adminDao.updateObj(one);
	}

	@Override
	public User getUserById(Long user_id) {
		String hql = " from com.le.ebook.domain.User where user_id=?";
		User one = (User) adminDao.getOne(hql,new Object[]{user_id});		
		return one;
	}

	@Override
	public List<Book> getBookList() {
		return this.getBookList(null);
	}
	@Override
	public List<Book> getBookList(String statu) {
		String hql = " from com.le.ebook.domain.Book";
		
		if(statu!=null && statu.length()>0){
			hql+=" where statu="+statu;
		}
		
		List list = adminDao.get(hql, null);
		return list;
	}

	@Override
	public Map<String,Object> getBookPageMap(String book_name,String statu,String is_news,String is_hots,Integer page,Integer page_size) {
		HashMap<String, Object> retMap = new HashMap<>();
		//创建离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		//设置参数

		if(book_name == null || "".equals(book_name)){
			book_name = "";
		}
		if(statu == null || "".equals(statu)){
			statu = "";
			
		}
		if(is_hots == null || "".equals(is_hots)){
			is_hots = "";
		}
		if(is_news == null ||"".equals(is_news)){
			is_news = "";
		}
		dc.add(Restrictions.and(Restrictions.like("book_name", "%"+book_name+"%"),Restrictions.like("statu", "%"+statu+"%"),Restrictions.like("news", "%"+is_news+"%"),Restrictions.like("hots", "%"+is_hots+"%")));
		dc.addOrder(org.hibernate.criterion.Order.desc("add_time"));
		Integer totalCount = adminDao.getTotalCount(dc);
		if(page==null || page <=0){
			page = 1;
		}
		if(totalCount==null){
			totalCount =0;
		}if(page_size ==null || page_size<0){
			page_size = 20;
		}
		
	
		int total_page =(int) (totalCount * 1.0 / page_size + 0.9998) ;
	
		if(page > total_page){
			page = total_page;
		}
		int max =page_size;
		int start = (page-1)*page_size;
		List<Object> list = adminDao.get(dc, start,max);
		
		retMap.put("page", page);
		retMap.put("total_page", total_page);
		retMap.put("page_size", page_size);
		retMap.put("total_count", totalCount);
		retMap.put("list", list);
		
		
		return retMap;
	}
	

	@Override
	public List<Bookcategory1> getCategory1() {

		return this.getCategory1(null);
	}
	
	@Override
	public List<Bookcategory1> getCategory1(Long id) {
		String hql = " from com.le.ebook.domain.Bookcategory1  where statu=1";
		if(id!=null){
			hql += " and bookcategory1_id="+id;
		}
		List list = adminDao.get(hql, null);
		return list;
	}
	@Override
	public MyPageBean getCategory1(String category_name,Integer page,Integer page_size,String statu) {
		if(statu == null ||statu.length() <=0){
			statu = "1";
		}
		if(category_name == null ||category_name.length() <=0){
			category_name = "";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.and(Restrictions.eq("statu", statu),Restrictions.like("name", "%"+category_name+"%")));
		dc.setProjection(Projections.rowCount());
		//获取总条目数
		Integer totalCount = adminDao.getTotalCount(dc);
		
		MyPageBean myPageBean = new MyPageBean(page, page_size, totalCount);
		List list = adminDao.get(dc, myPageBean.getStartIndex(), myPageBean.getPage_size());
		//设置列表
		myPageBean.setList(list);
		return myPageBean;
	}
	

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void addBook(Book book) {
		adminDao.saveObj(book);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void addCategory(Bookcategory1 bookcategory1) {
		adminDao.saveObj(bookcategory1);
		
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void addUser(User user) {
		//判断用户是否已经存在
		 if(adminDao.getOne(" from com.le.ebook.domain.User where username =?",new Object[]{ user.getUsername()}) == null){
			 adminDao.saveObj(user);
		 }else{
			 
		 }
		
	}

	@Override
	@Deprecated
	public List<Ad> getAdList() {
		String hql = " from com.le.ebook.domain.Ad";
		List list = adminDao.get(hql, null);
		
		return list;
	}
	
	
	@Override
	public MyPageBean getAdList(Integer page,Integer page_size) {
		DetachedCriteria dc = DetachedCriteria.forClass(Ad.class);
		dc.setProjection(Projections.rowCount());
		//获取总条目数
		Integer totalCount = adminDao.getTotalCount(dc);
		
		MyPageBean myPageBean = new MyPageBean(page, page_size, totalCount);
		List list = adminDao.get(dc, myPageBean.getStartIndex(), myPageBean.getPage_size());
		//设置列表
		myPageBean.setList(list);
		return myPageBean;
	}

	@Override
	public Book getBookById(Long book_id) {
		String hql = " from com.le.ebook.domain.Book where book_id="+book_id;
		Book one = (Book) adminDao.getOne(hql, null);
		return one;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateBook(Book book) {
		adminDao.updateObj(book);
		
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteBook(Long book_id) {
		String hql = " from com.le.ebook.domain.Book where book_id="+book_id;
		Book one = (Book) adminDao.getOne(hql, null);
		one.setStatu("2");
		adminDao.updateObj(one);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void addAd(Ad ad) {
		adminDao.saveObj(ad);
	}

	@Override
	public Ad getAdById(Long ad_id) {
		String hql = " from com.le.ebook.domain.Ad where ad_id="+ad_id;
		Ad one = (Ad) adminDao.getOne(hql, null);
		return one;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateAd(Ad ad) {
		adminDao.updateObj(ad);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteAd(Long ad_id) {
		String hql = " from com.le.ebook.domain.Ad where ad_id="+ad_id;
		Ad one = (Ad) adminDao.getOne(hql, null);
		adminDao.deleteObj(one);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteUser(Long user_id) {
		String hql = " from com.le.ebook.domain.User where user_id="+user_id;
		User one = (User) adminDao.getOne(hql, null);
		one.setStatu("2");
		adminDao.updateObj(one);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void reStatueUser(Long user_id) {
		String hql = " from com.le.ebook.domain.User where user_id="+user_id;
		User one = (User) adminDao.getOne(hql, null);
		one.setStatu("1");
		adminDao.updateObj(one);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void setBookNews(Long book_id, String news) {
		String hql = " from com.le.ebook.domain.Book where book_id="+book_id;
		Book one = (Book) adminDao.getOne(hql, null);
		one.setNews(news);
		adminDao.updateObj(one);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void setBookhots(Long book_id, String hots) {
		String hql = " from com.le.ebook.domain.Book where book_id="+book_id;
		Book one = (Book) adminDao.getOne(hql, null);
		one.setHots(hots);
		adminDao.updateObj(one);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateCategory(Bookcategory1 bookcategory1) {
		adminDao.updateObj(bookcategory1);
		
	}
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteCategory(Long bookcategory1_id) {
		String hql = " from com.le.ebook.domain.Bookcategory1 where bookcategory1_id="+bookcategory1_id;
		Bookcategory1 one = (Bookcategory1) adminDao.getOne(hql, null);
		one.setStatu("2");
		adminDao.updateObj(one);
	}

	@Override
	public MyPageBean getUserAddress(Long user_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(User_address.class);
		dc.add(Restrictions.and(Restrictions.eq("user_id", user_id)));
		dc.setProjection(Projections.rowCount());
		//获取总条目数
		Integer totalCount = adminDao.getTotalCount(dc);
		
		MyPageBean myPageBean = new MyPageBean(1, 20, totalCount);
		List list = adminDao.get(dc, myPageBean.getStartIndex(), myPageBean.getPage_size());
		//设置列表
		myPageBean.setList(list);
		return myPageBean;
	}

	@Override
	public Order getOrderById(Long order_id) {
		return (Order) adminDao.getOne(" from com.le.ebook.domain.Order where order_id="+order_id, null);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void returnOrder(Order order) {
		order.setStatu("6");
		adminDao.updateObj(order);
	}

}
