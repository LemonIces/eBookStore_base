package com.le.ebook.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.domain.Bookcategory1bak;
import com.le.ebook.pagebean.FindResultPage;
import com.le.ebook.service.BookService;
import com.le.ebook.service.impl.BookServiceImpl;
import com.le.ebook.utils.PageBean;
import com.le.ebook.utils.ScopePutAndGetFromValStackUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BookAction extends ActionSupport implements ModelDriven<Book> {
	// 使用模型驱动封装的book对象
	private Book book = new Book();

	private BookService bookService;

	// 当前页码
	private Integer page;
	// 每页显示的条数
	private Integer page_size;

	// 搜做文本
	private String search_text;

	public String getSearch_text() {
		return search_text;
	}

	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String list() throws Exception {
		System.out.println("BookAction.list.........");
		PageBean pageBean = bookService.getPageBean(book, page, page_size);

		System.out.println(pageBean);
		System.out.println(pageBean.getList());
		// 把pageBean放到值栈站定
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("pageBean", pageBean);

		return "home";
	}

	public String bookDetail() throws Exception {
		System.out.println("BookAction.bookDetail.........");
		Book bookDetail = bookService.getBookById(book.getBook_id());

		System.out.println(bookDetail);
		// 把pageBean放到值栈站定
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("book", bookDetail);

		return "bookDetail";
	}

	// 模型驱动封装book对象
	@Override
	public Book getModel() {
		return book;
	}


	// search_text
	// search_category1
	// page
	// page_size
	//search_category_id
	private Long  search_category_id;
	/**
	 * 用作分页查找书籍
	 * @return
	 */
	public String findBookByNameAndCategory2() {
		System.out.println("findBookByNameAndCategory2..............");
		System.out.println(search_text);
		System.out.println(search_category_id);
		System.out.println(page);
		System.out.println(page_size);
		if (page == null || page <= 0) {
			page = 1;
		}
		if (page_size == null || page_size <= 0) {
			page_size = 20;
		}
		Map<String, Object> bookMap = bookService.getBookPageMap(search_text, search_category_id, "1", "", "", page,
				page_size);
		if(search_category_id!=null && search_category_id >0){
			Bookcategory1 categoryById = bookService.getCategoryById(search_category_id);
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("category", categoryById);
		}else{
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("category", null);
		}
		List<Bookcategory1> category1List = bookService.getCategory1List();
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("bookMap", bookMap);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("search_text", search_text);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("category1List", category1List);
		System.out.println("bookMap=" + bookMap);

		return "find_book";

	}

	private String search_category1;

	public String getSearch_category1() {
		return search_category1;
	}

	public void setSearch_category1(String search_category1) {
		this.search_category1 = search_category1;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Long getSearch_category_id() {
		return search_category_id;
	}

	public void setSearch_category_id(Long search_category_id) {
		this.search_category_id = search_category_id;
	}
	/**
	 * 放入值站中
	 * @param msgKey
	 * @param msgVal
	 */
	public void putMsg2ValStack(String msgKey,Object msgVal){
		ActionContext.getContext().put(msgKey, msgVal);
	}

}
