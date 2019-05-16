package com.le.ebook.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.le.ebook.dao.BookDao;
import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1bak;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.service.BookService;
import com.le.ebook.utils.PageBean;
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=true)
public class BookServiceImpl implements BookService {
	
	private BookDao bookDao;
	
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public PageBean getPageBean(Book book, Integer currentPage, Integer pageSize) {
		return this.getPageBean(book, currentPage, pageSize, null);
	}
	
	
	@Override
	public PageBean getNewBookPageBean(Book book, Integer currentPage, Integer pageSize) {
		System.out.println("BookServiceImpl.getNewBookPageBean...........");
		//获取记录数
		//创建离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		
		//优化查找条件
		String bookname = book==null ? "" : book.getBook_name()==null?"":book.getBook_name();
		//设置查询条件
		dc.add(Restrictions.and( Restrictions.like("book_name", "%"+bookname+"%"),Restrictions.eq("statu", "1"),Restrictions.eq("news", "1")));
		dc.addOrder(Order.desc("publish_time"));
		
		Integer totalCount = bookDao.getTotalCount(dc);
		//封装pb对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		
		//计算开始索引
		int start = pb.getStart();
		
		//查询得到集合
		List bookList = bookDao.get(dc,start,pb.getPageSize());
		
		//把集合封装到pb中
		pb.setList(bookList);
		
		return pb;
	}
	
	@Override
	public PageBean getPageBean(Book book, Integer currentPage, Integer pageSize,Object[] order){
		System.out.println("BookServiceImpl.getPageBean...........");
		//获取记录数
		//创建离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		
		//优化查找条件
		String bookname = book.getBook_name()==null ? "" : book.getBook_name();
		//设置查询条件
		dc.add(Restrictions.like("book_name", "%"+bookname+"%"));
		if(order!=null && order.length >=2){
			if("asc".equals(order[0].toString())){
				dc.addOrder(Order.asc(order[1].toString()));
			}
			
			if("desc".equals(order[0].toString())){
				dc.addOrder(Order.desc(order[1].toString()));
			}
			
		}else{
			dc.addOrder(Order.desc("publish_time"));
		}
		
		Integer totalCount = bookDao.getTotalCount(dc);
		//封装pb对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		
		//计算开始索引
		int start = pb.getStart();
		//查询得到集合
		List bookList = bookDao.get(dc,start,pb.getPageSize());
		
		//把集合封装到pb中
		pb.setList(bookList);
		
		return pb;
	}
	
	@Override
	public PageBean getHotBoosByCategory(String categoryname, Integer currentPage, Integer pageSize,Object[] orderby){
		System.out.println("BookServiceImpl.getHotBoosByCategory...........");
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.eq("name", categoryname));
		if(orderby!=null && orderby.length >=2){
			if(orderby[0].equals("desc")){
				dc.addOrder(Order.desc((String) orderby[1]));
			} if(orderby[0].equals("asc")){
				dc.addOrder(Order.asc((String) orderby[1]));
			}
		}
		Bookcategory1 tempCate = (Bookcategory1) bookDao.getOne(dc);
		
		Set<Book> setBooks = tempCate.getBooks();
		Iterator<Book> iterator = setBooks.iterator();
	
		if(currentPage==null){
			currentPage = 1;
		}
		if(pageSize==null){
			pageSize = 20;
		}
		int start = ((currentPage.intValue()-1)*pageSize.intValue());
		int max = start + pageSize.intValue();
		
		int count = 0;
		
		List<Book> list = new ArrayList<Book>();
		
		while(iterator.hasNext()){
			Book next = iterator.next();			
			if("1".equals(next.getStatu())&& "1".equals(next.getHots())){
				count++;
				if(count >= start && count<max){
					list.add(next);
				}
			}
		}
		PageBean pb = new PageBean(currentPage, list.size(), pageSize);
		pb.setList(list);
		
		return pb;
	}
	
	@Override
	public PageBean getHotBoosByCategoryById(Long category_id, Integer currentPage, Integer pageSize,Object[] orderby){
		System.out.println("BookServiceImpl.getHotBoosByCategoryByid...........");
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.eq("bookcategory1_id", category_id));
		if(orderby!=null && orderby.length >=2){
			if(orderby[0].equals("desc")){
				dc.addOrder(Order.desc((String) orderby[1]));
			} if(orderby[0].equals("asc")){
				dc.addOrder(Order.asc((String) orderby[1]));
			}
		}
		Bookcategory1 tempCate = (Bookcategory1) bookDao.getOne(dc);
		
		Set<Book> setBooks = tempCate.getBooks();
		Iterator<Book> iterator = setBooks.iterator();
	
		if(currentPage==null){
			currentPage = 1;
		}
		if(pageSize==null){
			pageSize = 20;
		}
		int start = ((currentPage.intValue()-1)*pageSize.intValue());
		int max = start + pageSize.intValue();
		
		int count = 0;
		
		List<Book> list = new ArrayList<Book>();
		
		while(iterator.hasNext()){
			Book next = iterator.next();			
			if("1".equals(next.getStatu())&& "1".equals(next.getHots())){
				count++;
				if(count >= start && count<max){
					list.add(next);
				}
			}
		}
		PageBean pb = new PageBean(currentPage, list.size(), pageSize);
		pb.setList(list);
		
		return pb;
	}
	
	
	@Override
	public PageBean getPageBeanByTitle(String book_title, Integer currentPage, Integer pageSize) {
		System.out.println("BookServiceImpl.getPageBean...........");
		//获取记录数
		//创建离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		
		//优化查找条件
		book_title =book_title ==null ? "" : book_title;
		//设置查询条件
		dc.add(Restrictions.like("book_title", "%"+book_title+"%"));
		Integer totalCount = bookDao.getTotalCount(dc);
		//封装pb对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		
		//计算开始索引
		int start = pb.getStart();
		//查询得到集合
		List bookList = bookDao.get(dc,start,pb.getPageSize());
		
		//把集合封装到pb中
		pb.setList(bookList);
		
		return pb;
	}

	@Override
	public Book getBook(Book book) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		
		
		List list = bookDao.get(dc);
		
		if(list!=null && list.size() > 0){
			return (Book) list.get(0);
		}
		
		return null;
	}
	@Override
	public Book getBookById(Object id) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		
		dc.add(Restrictions.eq("book_id",id));
		
		List list = bookDao.get(dc);
		
		if(list!=null && list.size() > 0){
			return (Book) list.get(0);
		}
		
		return null;
	}
	

	
	@Override
	public List<Bookcategory1> getCategory1List(){
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.eq("statu", "1"));
		List list = bookDao.get(dc);
		return list;
	}
	
	/**
	 * 查询从开始索引，查询max项
	 * @param start
	 * @param max
	 * @return
	 */
	@Override
	public List<Bookcategory1> getCategory1List(Integer start,Integer max){
		if(start == null || start <=0){
			start = 0;
		}
		if(max == null || max <=0){
			max = 12;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.eq("statu", "1"));
		dc.addOrder(Order.asc("bookcategory1_id"));
		List list = bookDao.get(dc,start,max);
		return list;
	}
	
	
	@Override
	public PageBean getPageBeanByCategory1Name(String category1, int currentPage, int pageSize) {
		
		
		String hql = "from com.le.ebook.domain.Bookcategory1 where name = ?";
		
		List<Bookcategory1bak> list = bookDao.get(hql, new Object[]{category1});

		List<Book> listbook = new ArrayList<Book>();
		
		if(list!=null&&list.size()>0) {
			for (int j = 0; list.get(0).getBookcategory2s() != null &&j < list.get(0).getBookcategory2s().size(); j++) {
				Set<Bookcategory1> bookcategory2s = list.get(0).getBookcategory2s();
				Iterator<Bookcategory1> iterator = bookcategory2s.iterator();
				while(iterator.hasNext()){
					Bookcategory1 bookcategory2 = iterator.next();
					Set<Book> books = bookcategory2.getBooks();
					listbook.addAll(books);
				}
			}
			
		}
		
		int total = listbook.size();
		PageBean pageBean = new PageBean(currentPage, total, pageSize);
		
		
		pageBean.setList(listbook);
		
		return pageBean;
	}
	
	
	@Override
	public List<Book> getBookByCategory1Name(String category1, int currentPage, int pageSize) {
		
		
		String hql = "from com.le.ebook.domain.Bookcategory1 where name = ?";
		
		int start = (currentPage -1 )*pageSize;
		
		int max = pageSize;
		
		List<Bookcategory1> list = bookDao.get(hql, new Object[]{category1},start,max);

		List<Book> listbook = new ArrayList<Book>();
		
		for (int i = 0; list!=null &&i < list.size(); i++) {
			Set<Book> books = list.get(i).getBooks();
			
			Iterator<Book> iterator = books.iterator();
			while(iterator.hasNext()){
				listbook.add(iterator.next());
			}
		}
	
		return listbook;
	}
	
	
	@Override
	public List<Book> getBookListByCatecary1(String category1){
		
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.eqOrIsNull("name", category1));
		
		List list = bookDao.get(dc);
		List<Book> listbook = new ArrayList<Book>();
		if(list!=null && list.size()>0){
			Bookcategory1 object = (Bookcategory1) list.get(0);
			Set<Book> books = object.getBooks();
			Iterator<Book> iterator = books.iterator();
			while(iterator.hasNext()){
				listbook.add(iterator.next());
			}
		}
		return listbook;
	}

	//搜索书籍，带分页方式
	@Override
	public Map<String,Object> getBookPageMap(String find_bname,Long category_id,String bsta,String is_news,String is_hots,Integer p,Integer ps) {
		//创建一个用来保存查询得到的和一些需要返回给web前端的数据的retmap集合
		Map retMap = new HashMap<>();
		//创建用户搜索相关书籍的离线的查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);//查询book类
		//设置参数
		if(find_bname == null || (find_bname).equals("")){//书本名字
			find_bname = "";
		}
		if(bsta == null ||(bsta).equals("")){//书本状态标志
			bsta = "1";
		}
		if(is_hots == null || (is_hots).equals("")){//是否是热门
			is_hots = "";
		}
		if(is_news == null ||(is_news).equals("")){//是否是最新
			is_news = "";
		}
		if(category_id == null ||(category_id).equals("")){//书本属于的分类
			category_id = 0L;
		}
		if(category_id == 0L){//不带分类的搜索
			dc.add(Restrictions.and(Restrictions.like("book_name", "%"+find_bname+"%"),Restrictions.like("statu", "%"+bsta+"%"),Restrictions.like("news", "%"+is_news+"%"),Restrictions.like("hots", "%"+is_hots+"%")));
		}else{//带分类的搜索
			dc.add(Restrictions.and(Restrictions.like("book_name", "%"+find_bname+"%"),Restrictions.like("statu", "%"+bsta+"%"),Restrictions.like("news", "%"+is_news+"%"),Restrictions.like("hots", "%"+is_hots+"%"),Restrictions.eq("bookcategory1.bookcategory1_id", category_id)));
		}
		dc.addOrder(org.hibernate.criterion.Order.desc("add_time"));//时间倒叙排序
		Integer tc = bookDao.getTotalCount(dc);//搜索结果的总数
		if(p==null || p <=0){//检查当前页数是否合法
			p = 1;
		}
		if(tc==null){//检查搜索条目总数
			tc =0;
		}if(ps ==null || ps<0){//检查每页的条目数是否合法
			ps = 20;
		}
		int tp =(int) (tc * 1.0 / ps + 0.9998) ;
		if(p > tp){//检查当前页码数是否越界
			p = tp;
		}
		//计算开始索引。
		int psta = (p-1)*ps;
		//搜索的结果数。
		int m =ps;
		//设置搜索书本结果列表到map中。
		List list = bookDao.get(dc, psta,m);
		retMap.put("page", p);
		retMap.put("total_page", tp);
		retMap.put("page_size", ps);
		retMap.put("total_count", tc);
		retMap.put("list", list);
		return retMap;
	}
	
	
	@Deprecated
	@Override
	public PageBean getPageBeanByNameAndCategory1(String search_text, String search_category1, Integer currentPage,
			Integer pageSize) {
		
		System.out.println("BookServiceImpl.getPageBeanByNameAndCategory1...........");
		//获取记录数
		//创建离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
		
		//优化查找条件
		String bookname =search_text==null ? "" : search_text;
		//设置查询条件
		dc.add(Restrictions.like("book_name", "%"+bookname+"%"));

		Integer totalCount = bookDao.getTotalCount(dc);
		//封装pb对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		
		//计算开始索引
		int start = pb.getStart();
		
		List<Book> list = new ArrayList<Book>();
		
		//查询得到集合
		List bookList = bookDao.get(dc,start,pb.getPageSize());
		List<Book> bookListByCatecary1 = null;
		if(search_category1!=null && !"".equals(search_category1)){
			bookListByCatecary1 = getBookListByCatecary1(search_category1);
		}
		
		if(bookListByCatecary1!=null){
			for (int i = 0; i < bookList.size(); i++) {
				Book book1 = (Book) bookList.get(i);
				Long book_id = book1.getBook_id();
				
				for (int j = 0; bookListByCatecary1!=null &&j < bookListByCatecary1.size(); j++) {
					Book book2 = bookListByCatecary1.get(j);
					
					Long book_id2 = book2.getBook_id();
					
					if(book_id == book_id2){
						list.add(book1);
					}
					
				}
				
				
			}
			
		}else{
			list.addAll(bookList);
		}
		
		//把集合封装到pb中
		pb.setList(list);
		return pb;
	}

	@Override
	public List<Ad> getAd(int start, int max) {
		List<Ad> list = bookDao.get(" from com.le.ebook.domain.Ad where statu=1", null, start, max);
		return list;
	}

	@Override
	public Bookcategory1 getCategoryById(Long category_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bookcategory1.class);
		dc.add(Restrictions.eq("bookcategory1_id", category_id));
		Bookcategory1 Bookcategory1 = (com.le.ebook.domain.Bookcategory1) bookDao.getOne(dc );
		return Bookcategory1;
	}
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void updBook(Object obj) {
		bookDao.updateObj(obj);
		
	}

}
