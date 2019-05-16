package com.le.ebook.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.le.ebook.dao.BookDao;
import com.le.ebook.dao.MallCarDao;
import com.le.ebook.dao.UserDao;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.MallCar;
import com.le.ebook.domain.User;
import com.le.ebook.exception.BookException;
import com.le.ebook.exception.MallCarException;
import com.le.ebook.pagebean.MallCarPage;
import com.le.ebook.service.MallCarService;
import com.le.ebook.utils.PageBean;
import com.sun.corba.se.spi.oa.ObjectAdapterBase;
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=true)
public class MallCarServiceImpl implements MallCarService {

	private MallCarDao mallCarDao;
	

	public MallCarDao getMallCarDao() {
		return mallCarDao;
	}



	public void setMallCarDao(MallCarDao mallCarDao) {
		this.mallCarDao = mallCarDao;
	}



	@Override
	public PageBean getPage(MallCar mallCar) {
		
		
		DetachedCriteria dc = DetachedCriteria.forClass(MallCar.class);
		
		dc.add(Restrictions.eq("username", mallCar.getUsername()));
		
		Integer totalCount = mallCarDao.getTotalCount(dc);
		
		
	
		
		if (totalCount!=null && totalCount>0) {
			PageBean pb = new PageBean(1, totalCount, totalCount);
			
			List<MallCar> list =  mallCarDao.get(dc);
				
			
			pb.setList(list);
			

			return pb;
		}else{
			return null;
		}


	}
	
	@Override
	
	public List getByUsernameForList(String username) {
		DetachedCriteria dc = DetachedCriteria.forClass(MallCar.class);
		dc.add(Restrictions.and(Restrictions.eq("username", username)));
		
		List<MallCar> list = mallCarDao.get(dc);
		List<MallCarPage> pages = new ArrayList<MallCarPage>();
		if (list!=null && list.size()>0) {
			for (MallCar m : list) {
				MallCarPage mp = new MallCarPage();
				BeanUtils.copyProperties(m, mp);
				Book book = (Book) mallCarDao.get("from Book where book_id=?", new Object[]{mp.getProduct_id()}).get(0);
				mp.setProduct_img(book.getImg());
				mp.setPrice(book.getPrice());
				pages.add(mp);
				
			}
		}
		
		return pages;

	}
	
	/**
	 * 获取分页的购物车
	 * @param username
	 * @param page
	 * @param page_size
	 * @return
	 */
	@Override
	public Map<String, Object> getByUsernameForList(String username,Integer page,Integer page_size) {
		DetachedCriteria dc = DetachedCriteria.forClass(MallCar.class);
			if(page == null || page <1){
			page = 1;
		}
		if(page_size == null || page_size <1){
			page_size = 20;
		}
		dc.add(Restrictions.and(Restrictions.eq("username", username)));
		dc.addOrder(Order.desc("time"));
		//获取总条目数
		Integer total_count = mallCarDao.getTotalCount(dc);
		
		int total_page = (int)((total_count/page_size)+0.9998);
		
		int start = (page - 1)*page_size;
		
		int max = page_size;
		
		List<MallCar> list = mallCarDao.get(dc,start,max);
		
		List<MallCarPage> pages = new ArrayList<MallCarPage>();
		if (list!=null && list.size()>0) {
			for (MallCar m : list) {
				MallCarPage mp = new MallCarPage();
				BeanUtils.copyProperties(m, mp);
				Book book = (Book) mallCarDao.get("from Book where book_id=?", new Object[]{mp.getProduct_id()}).get(0);
				mp.setProduct_img(book.getImg());
				mp.setPrice(book.getPrice());
				mp.setDiscount(book.getDiscount());
				pages.add(mp);
				
			}
		}
		
		Map <String,Object> map = new HashMap<String,Object>();
		
		map.put("page", page);
		map.put("page_size", page_size);
		map.put("total_page", total_page);
		map.put("total_count", total_count);
		map.put("list", pages);
		
		return map;

	}
	


	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void addMallCar(MallCar mallCar) throws BookException, MallCarException {
		
		//先判断书籍的库存是否足够
		Book one_book = (Book) mallCarDao.getOne(" from com.le.ebook.domain.Book where book_id="+ mallCar.getProduct_id(),null);
		if(one_book == null){
			throw new BookException("书本不存在！");
		}
		if (one_book.getCount() == null || one_book.getCount() < mallCar.getCount()) {
			throw new MallCarException("书本库存不足！");
		}
		//判断是否已经在购物车
		String hql = "from com.le.ebook.domain.MallCar where user_id=? and product_id=?";
		MallCar existMall =  (MallCar) mallCarDao.getOne(hql,new Object[]{mallCar.getUser_id(),mallCar.getProduct_id()});
		if(existMall == null){
			DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
			dc.add(Restrictions.eq("book_id", mallCar.getProduct_id()));
			List<Book> list = mallCarDao.get(dc);
			if (list!=null && list.size() > 0) {
				mallCar.setProduct_name(list.get(0).getBook_name());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mallCar.setTime(sdf.format(new Date()));
			mallCarDao.save(mallCar);
		}else{
			existMall.setCount( existMall.getCount() + mallCar.getCount());
			if (one_book.getCount() < existMall.getCount()) {
				throw new MallCarException("书本库存不足！");
			}
			mallCarDao.update(existMall);
		}
		
	}



	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void update(MallCar mallCar) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(MallCar.class);
		
		dc.add(Restrictions.eq("mallcar_id",mallCar.getMallcar_id()));
		MallCar one = (MallCar) mallCarDao.getOne(dc);
		
		one.setCount(mallCar.getCount());
		
		mallCarDao.update(one);
		
	}



	@Override
	public Book getBookById(Long product_id) {
		String hql = " from com.le.ebook.domain.Book where book_id=?";
			Book one = (Book) mallCarDao.getOne(hql, new Object[]{product_id});
		return one;
	}



	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void delete(Long mallcar_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(MallCar.class);
		dc.add(Restrictions.eq("mallcar_id", mallcar_id));
		Object one = mallCarDao.getOne(dc);
		mallCarDao.deleteObj(one);
	}

}
