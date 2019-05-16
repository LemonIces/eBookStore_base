package com.le.ebook.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Admin;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.domain.Order;
import com.le.ebook.domain.Order_item;
import com.le.ebook.domain.User;
import com.le.ebook.service.AdminService;
import com.le.ebook.service.BookService;
import com.le.ebook.service.UserService;
import com.le.ebook.utils.MyPageBean;
import com.le.ebook.utils.ScopePutAndGetFromValStackUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport{
	private AdminService adminService;
	private BookService bookService;
	private UserService userService;
	private String param;
	private String name;
	private String pwd;
	/**
	 * 管理员登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		System.out.println("login.................");
		System.out.println("name="+name+", pwd="+pwd);
		Admin admin = adminService.login(name,pwd);
		if(admin!=null){
			
			ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("admin", admin);
			return "main";
		}else{
			
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorPwd", "用户名或密码错误！");
			return "login";
			
		}
	}
	
	//显示订单
	//statu
	@Deprecated
	public String listOrder(){
		System.out.println("listOrder.........");
		System.out.println("statu="+statu);
		
		List<Order> list =  adminService.listOrder(statu);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("indentList", list);
		
		System.out.println("list"+list);
		
		return "order_list_page";
	}
	
	private String statu;
	//显示订单
	//statu
	//page
	//page_size
	/**
	 * 分页显示订单
	 * @return
	 */
	public String listOrder1(){
		System.out.println("listOrder1.........");
		System.out.println("statu="+statu+", page="+page+", page_size="+page_size+", find_username="+find_username);
		
		MyPageBean pb = adminService.listOrder(find_username,page, page_size, statu);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("order_bean", pb);
		
		System.out.println("order_bean"+pb);
		return "order_list_page";
	}
	
	private Long order_id;
	public String listOrderItemDetail(){
		System.out.println("listOrderItemDetail.........");
		System.out.println("order_id="+order_id);
		Order order =  adminService.getOrderById(order_id);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("order", order);
		System.out.println("order"+order);
		
		return "item_list_page";
	}
	
	
	private Integer page;
	//page
	//page_size
	//statu
	public String orderDispose(){
		System.out.println("orderDispose.........");
		System.out.println("order_id="+order_id);
		adminService.disposeOrder(order_id);
		
		
		return "reListOrder1";
	}
	
	
	//page
	//page_size
	//statu
	public String orderDelete(){
		System.out.println("orderDelete.........");
		System.out.println("order_id="+order_id);
		adminService.deleteOrder(order_id);
		
		
		return "reListOrder1";
	}
	
	//page
	//page_size
	//statu
	public String orderReturn(){
		System.out.println("orderReturn.........");
		System.out.println("order_id="+order_id);
		Order order = adminService.getOrderById(order_id);
		adminService.returnOrder(order);
		Iterator<Order_item> it = order.getItems().iterator();
		while(it.hasNext()){
			Order_item order_item = it.next();
			Long count = order_item.getCount();
			Long id = order_item.getProduct_id();
			Book book = adminService.getBookById(id);
			book.setCount(book.getCount()+count);
			adminService.updateBook(book);
		}
		return "reListOrder1";
	}
	
	//page
	//page_size
	//statu
	public String orderOk(){
		System.out.println("orderDelete.........");
		System.out.println("order_id="+order_id);
		adminService.orderOk(order_id);
		
		return "reListOrder1";
	}
	
	
	
	@Deprecated
	public String listUser(){
		System.out.println("listUser.........");
		List<User> list =  adminService.getUserList();
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("userList", list);
		System.out.println("list"+list);
		
		return "user_list_page";
	}
	
	
	private String find_username;
	//page
	//statu
	//page_size
	//username
	/**
	 * 分页用户列表
	 * @return
	 */
	public String listUser2(){
		System.out.println("listUser2.........");
		System.out.println("page="+page+", statu="+statu+", page_size="+page_size+", find_username="+find_username);
		MyPageBean pb = adminService.getUserList(find_username,page,page_size,statu);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("user_bean", pb);
		System.out.println("pb"+pb);
		
		return "user_list_page";
	}
	
	
	private Long user_id;
	public String userRe(){
		System.out.println("userRe.........");
		System.out.println("user_id="+user_id);
		adminService.userRePwd(user_id);
		return "re_user_list_page";
	}
	public String userUp(){
		System.out.println("userUp.........");
		System.out.println("user_id="+user_id);
		User user = adminService.getUserById(user_id);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("user", user);
		return "user_update_page";
	}
	
	

	
	@Deprecated
	public String listBook(){
		System.out.println("listBook.........");
		List<Book> list =  adminService.getBookList("1");
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("bookList", list);
		System.out.println("list"+list);
		
		return "book_list_page";
	}
	
	//page
	//statu
	//news
	//hots
	//page_size=20
	
	private String find_book_name;
	
	private String news;
	private String hots;
	private Integer page_size;
	public String listBook2(){
		System.out.println("listBook2.........");
		System.out.println("page="+page+", statu="+statu+", news="+news+",hots="+hots+", page_size="+page_size+", find_book_name="+find_book_name);
		Map<String, Object> map = adminService.getBookPageMap(find_book_name, statu, news, hots, page, page_size);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("bookMap",map);
		System.out.println("map"+map);
		
		return "book_list_page";
	}
	
	
	public String addBookPage(){
		System.out.println("addBook.........");
		List<Bookcategory1> list =  adminService.getCategory1();
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("bookcategoryList", list);
		System.out.println("list"+list);
		
		return "book_add_page";
	}
	
	private String find_category_name;
	//page
	//statu
	//page_size=20
	/**
	 * 分页分类
	 * @return
	 */
	public String listBookcategory12(){
		System.out.println("listBookcategory1.........");
		MyPageBean pb = adminService.getCategory1(find_category_name,page,page_size,statu);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("pb", pb);
		System.out.println("pb"+pb);
		
		return "list_bookcategory_page";
	}
	
	//user_id
	public String listUserAddress(){
		System.out.println("listUserAddress.........");
		System.out.println("user_id="+user_id);
		MyPageBean pb = adminService.getUserAddress(user_id);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("pb", pb);
		System.out.println("pb"+pb);
		return "user_address";
	}
	
	private String book_name;
	private File book_img;
	private String book_price;
	private String book_memo;
	private String book_author;
	private Long book_word_count;
	private Long book_total_page;
	private String book_publishing;
	private String book_publish_time;
	private Long book_bookcategory1_id;
	
	private Long book_count;
	private String book_discount;
	
	private String book_news;
	private String book_hots;
	
	 private String book_imgContentType; //得到文件的类型
	 private String book_imgFileName; //得到文件的名称
	
	public String addBook(){
		System.out.println("addBook.........");
		Bookcategory1 cate1 = adminService.getCategory1(book_bookcategory1_id).get(0);
		Book book = new Book();
		book.setAuthor(book_author);
		book.setBook_name(book_name);
		book.setBookcategory1(cate1);
		book.setPublish_time(book_publish_time);
		book.setTotal_page(book_total_page);
		book.setWord_count(book_word_count);
		book.setPublishing(book_publishing);
		book.setDiscount(String.format("%.2f", Double.valueOf(book_price)));
		book.setCount(book_count);
		book.setDiscount(String.format("%.2f", Double.valueOf(book_discount)));
		book.setMemo(book_memo);
		book.setNews(book_news);
		book.setHots(book_hots);
		book.setStatu("1");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		book.setAdd_time(sdf.format(date));
        //获取要保存文件夹的物理路径(绝对路径)
        String realPath=ServletActionContext.getServletContext().getRealPath("/upload/img/book");
        File file = new File(realPath);
        
        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        if(!file.exists())file.mkdirs();
        
        try {
            //保存文件
            FileUtils.copyFile(book_img, new File(file,date.getTime()+getBook_imgFileName()));
        	book.setImg("upload/img/book/"+date.getTime()+getBook_imgFileName());
            System.out.println("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
		adminService.addBook(book);
		return "re_book_list_page";
	}
	
	
	
	private String category_name;
	private String category_memo;
	public String addCategory(){
		System.out.println("addCategory.....");
		System.out.println("category_name="+category_name+". category_memo="+category_memo);
		Bookcategory1 bookcategory1 = new Bookcategory1();
		bookcategory1.setName(category_name);
		bookcategory1.setMemo(category_memo);
		bookcategory1.setStatu("1");
		adminService.addCategory(bookcategory1);
		
		return "re_list_bookcategory_page";
	}

	
	private String username;
	private String password;
	//管理员不添加用户
	@Deprecated
	public String addUser(){
		System.out.println("addUser..........");
		System.out.println("username="+username+", password="+password);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setStatu("1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setRegist_time(sdf.format(new Date()));
		
		adminService.addUser(user);
		return "re_user_list_page";
	}
	
	@Deprecated
	public String listAd(){
		System.out.println("listAd..........");
		List<Ad> list = adminService.getAdList();
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("adList", list);
		return "ad_list_page";
	}
	
	//page
	//page_size
	//statu
	/**
	 * 分页广告
	 * @return
	 */
	public String listAd2(){
		System.out.println("listAd2..........");
		System.out.println("page="+page+", page_size="+page_size);
		MyPageBean pb = adminService.getAdList(page,page_size);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("pb", pb);
		return "ad_list_page";
	}
	
	
	private Long book_id;
	
	public String bookUpPage(){
		System.out.println("bookUpPage.........");
		System.out.println("book_id="+book_id);

		Book book = adminService.getBookById(book_id);
		
		List<Bookcategory1> list =  adminService.getCategory1();
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("book", book);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("bookcategoryList", list);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("bookcategory", book.getBookcategory1());
		System.out.println("list"+list);
		
		return "book_update_page";
	}
	
	
	/*
	 * 	private String book_name;
	private File book_img;
	private String book_price;
	private String book_memo;
	private String book_author;
	private String book_word_count;
	private String book_total_page;
	private String book_publishing;
	private String book_publish_time;
	private Long book_bookcategory1_id;
		private String book_news;
	private String book_hots;
	
	 private String book_imgContentType; //得到文件的类型
	 private String book_imgFileName; //得到文件的名称
	 */
	public String bookUpdate(){
		System.out.println("bookUpdate.........");
		Bookcategory1 cate1 = adminService.getCategory1(book_bookcategory1_id).get(0);
		Book book = adminService.getBookById(book_id);
		book.setAuthor(book_author);
		book.setBook_name(book_name);
		book.setBookcategory1(cate1);
		book.setPublish_time(book_publish_time);
		book.setPrice(String.format("%.2f", Double.valueOf(book_price)));
		book.setCount(book_count);
		book.setDiscount(String.format("%.2f", Double.valueOf(book_discount)));
		book.setTotal_page(book_total_page);
		book.setWord_count(book_word_count);
		book.setPublishing(book_publishing);
		book.setMemo(book_memo);
		book.setNews(book_news);
		book.setHots(book_hots);
		
		if(book_img!=null && book_img.length() > 0){
		Date date = new Date();
        //获取要保存文件夹的物理路径(绝对路径)
        String realPath=ServletActionContext.getServletContext().getRealPath("/upload/img/book");
        File file = new File(realPath);
        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        if(!file.exists())file.mkdirs();
        try {
            //保存文件
            FileUtils.copyFile(book_img, new File(file,date.getTime()+getBook_imgFileName()));
            book.setImg("upload/img/book/"+date.getTime()+getBook_imgFileName());
            System.out.println("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
		adminService.updateBook(book);
		return "re_book_list_page";
	}
	
	/*
	 * private Long book_id
	 */
	public String bookDelete(){
		System.out.println("bookDelete.........");
		System.out.println("book_id="+book_id);
		
		adminService.deleteBook(book_id);
		
		return "re_book_list_page";
	}
	
	
	private String ad_title;
	private String ad_loc;
	private File ad_img;
	private String ad_statu;
	
	 private String ad_imgContentType; //得到文件的类型
	 private String ad_imgFileName; //得到文件的名称
	
	public String adAdd(){
		System.out.println("adAdd..........");
		Ad ad = new Ad();
		ad.setAd_loc(ad_loc);
		ad.setAd_title(ad_title);
		ad.setStatu("2");
		if(ad_statu!=null && ad_statu.trim().length()>0){
			ad.setStatu(ad_statu);
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
		ad.setAd_time(sdf.format(date));
		
		 //获取要保存文件夹的物理路径(绝对路径)
        String realPath=ServletActionContext.getServletContext().getRealPath("/upload/ad");
        File file = new File(realPath);
        
        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        if(!file.exists())file.mkdirs();
        
        try {
            //保存文件
            FileUtils.copyFile(ad_img, new File(file,date.getTime()+getAd_imgFileName()));
        	ad.setAd_img("upload/ad/"+date.getTime()+getAd_imgFileName());
            System.out.println("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
		adminService.addAd(ad);
		
		return "re_ad_list_page";
	}
	
	private Long ad_id;
	public String adUpPage(){
		System.out.println("adUpPage.........");
		System.out.println("ad_id="+ad_id);
		
		Ad ad = adminService.getAdById(ad_id);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("ad", ad);
		
		return "ad_update_page";
	}
	
	//private Long ad_id;
	/*ad_img
	 * ad_title
	 * ad_loc
	 * ad_statu
	 * 	private String ad_imgContentType; //得到文件的类型
	 *	private String ad_imgFileName; //得到文件的名称
	 */
	
	public String updateAd(){
		System.out.println("updateAd.........");
		System.out.println("ad_id="+ad_id);
		Ad ad = adminService.getAdById(ad_id);
		ad.setAd_loc(ad_loc);
		ad.setAd_title(ad_title);
		ad.setStatu("2");
		if(ad_statu!=null && ad_statu.trim().length()>0){
			ad.setStatu(ad_statu);
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
		ad.setAd_time(sdf.format(date));
		
		if(ad_img!=null && ad_img.length()>0){
		
		 //获取要保存文件夹的物理路径(绝对路径)
        String realPath=ServletActionContext.getServletContext().getRealPath("/upload/ad");
        File file = new File(realPath);
        
        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        if(!file.exists())file.mkdirs();
        
        try {
            //保存文件
            FileUtils.copyFile(ad_img, new File(file,date.getTime()+getAd_imgFileName()));
        	ad.setAd_img("upload/ad/"+date.getTime()+getAd_imgFileName());
            System.out.println("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		}
		adminService.updateAd(ad);
		
		return "re_ad_list_page";
	}
	
	
	//ad_id
	public String adDelete(){
		System.out.println("adDelete.........");
		System.out.println("ad_id="+ad_id);
		adminService.deleteAd(ad_id);
		return "re_ad_list_page";
	}
	
	//user_id
	public String userDelete(){
		System.out.println("userDelete.........");
		System.out.println("user_id"+user_id);
		adminService.deleteUser(user_id);
		return "re_user_list_page";
	}
	
	
	
	//user_id
	public String userReStatu(){
		System.out.println("userReStatu.........");
		System.out.println("user_id"+user_id);
		
		adminService.reStatueUser(user_id);
		return "re_user_list_page";
	}
	
	
	
	//book_id
	//flag
	private String flag;
	public String bookSet(){
		System.out.println("bookSet.........");
		System.out.println("book_id="+book_id);
		System.out.println("flag="+flag);
		if(flag.equals("21")){
			
			adminService.setBookNews(book_id,"1");
			
		}	else	if(flag.equals("22")){
			adminService.setBookNews(book_id,"2");
		}	else	if(flag.equals("11")){
			adminService.setBookhots(book_id,"1");
		}	else	if(flag.equals("12")){
			adminService.setBookhots(book_id,"2");
		}
		return "re_book_list_page";
	}
	
	private Long bookcategory1_id;
	//bookcategory1_id
	public String categoryUp(){
		System.out.println("categoryUp.........");
		System.out.println("bookcategory1_id="+bookcategory1_id);
		List<Bookcategory1> list = adminService.getCategory1(bookcategory1_id);
		if(list!=null && list.size() >=1){
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("category", list.get(0));
		}
		return "category_update";
	}
	
	
	
	
	
	
	//category_name
	//category_memo
	//bookcategory1_id
	public String categoryUpdate(){
		System.out.println("categoryUpdate.....");
		System.out.println("category_name="+category_name+". category_memo="+category_memo+", bookcategory1_id="+bookcategory1_id);
		 List<Bookcategory1> list = adminService.getCategory1(bookcategory1_id);
		 
		 if(list!=null && list.size() >0){
			 Bookcategory1 bookcategory1 = list.get(0);
				bookcategory1.setName(category_name);
				bookcategory1.setMemo(category_memo);
				adminService.updateCategory(bookcategory1);
		 }

		return "re_list_bookcategory_page";
	}
	
	//bookcategory1_id
	public String categoryDelete(){
		System.out.println("categoryDelete.....");
		System.out.println("bookcategory1_id="+bookcategory1_id);
		adminService.deleteCategory(bookcategory1_id);
		return "re_list_bookcategory_page";
	}
	
	//user_address_id
	public String addrDelete(){
		return "";
	}
	
	
	public Long getBookcategory1_id() {
		return bookcategory1_id;
	}

	public void setBookcategory1_id(Long bookcategory1_id) {
		this.bookcategory1_id = bookcategory1_id;
	}

	public Long getAd_id() {
		return ad_id;
	}



	public void setAd_id(Long ad_id) {
		this.ad_id = ad_id;
	}



	public String getBook_news() {
		return book_news;
	}



	public void setBook_news(String book_news) {
		this.book_news = book_news;
	}



	public String getBook_hots() {
		return book_hots;
	}



	public void setBook_hots(String book_hots) {
		this.book_hots = book_hots;
	}



	public String getFlag() {
		return flag;
	}



	public void setFlag(String flag) {
		this.flag = flag;
	}



	public String getAd_title() {
		return ad_title;
	}



	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}



	public String getAd_loc() {
		return ad_loc;
	}



	public void setAd_loc(String ad_loc) {
		this.ad_loc = ad_loc;
	}



	public File getAd_img() {
		return ad_img;
	}



	public void setAd_img(File ad_img) {
		this.ad_img = ad_img;
	}



	public String getAd_statu() {
		return ad_statu;
	}



	public void setAd_statu(String ad_statu) {
		this.ad_statu = ad_statu;
	}



	public String getAd_imgContentType() {
		return ad_imgContentType;
	}



	public void setAd_imgContentType(String ad_imgContentType) {
		this.ad_imgContentType = ad_imgContentType;
	}



	public String getAd_imgFileName() {
		return ad_imgFileName;
	}



	public void setAd_imgFileName(String ad_imgFileName) {
		this.ad_imgFileName = ad_imgFileName;
	}



	public Long getBook_id() {
		return book_id;
	}



	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getCategory_name() {
		return category_name;
	}



	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}



	public String getCategory_memo() {
		return category_memo;
	}



	public void setCategory_memo(String category_memo) {
		this.category_memo = category_memo;
	}



	public Long getUser_id() {
		return user_id;
	}



	public File getBook_img() {
		return book_img;
	}



	public void setBook_img(File book_img) {
		this.book_img = book_img;
	}



	public String getBook_imgContentType() {
		return book_imgContentType;
	}



	public void setBook_imgContentType(String book_imgContentType) {
		this.book_imgContentType = book_imgContentType;
	}



	public String getBook_imgFileName() {
		return book_imgFileName;
	}



	public void setBook_imgFileName(String book_imgFileName) {
		this.book_imgFileName = book_imgFileName;
	}



	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	
	


	public String getBook_name() {
		return book_name;
	}



	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}





	public String getBook_price() {
		return book_price;
	}



	public void setBook_price(String book_price) {
		this.book_price = book_price;
	}



	public String getBook_memo() {
		return book_memo;
	}



	public void setBook_memo(String book_memo) {
		this.book_memo = book_memo;
	}



	public String getBook_author() {
		return book_author;
	}



	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}





	public String getBook_publishing() {
		return book_publishing;
	}



	public void setBook_publishing(String book_publishing) {
		this.book_publishing = book_publishing;
	}



	public String getBook_publish_time() {
		return book_publish_time;
	}



	public void setBook_publish_time(String book_publish_time) {
		this.book_publish_time = book_publish_time;
	}

	public Long getBook_word_count() {
		return book_word_count;
	}



	public void setBook_word_count(Long book_word_count) {
		this.book_word_count = book_word_count;
	}



	public Long getBook_total_page() {
		return book_total_page;
	}



	public void setBook_total_page(Long book_total_page) {
		this.book_total_page = book_total_page;
	}



	public Long getBook_bookcategory1_id() {
		return book_bookcategory1_id;
	}



	public void setBook_bookcategory1_id(Long book_bookcategory1_id) {
		this.book_bookcategory1_id = book_bookcategory1_id;
	}







	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public String getStatu() {
		return statu;
	}


	public void setStatu(String statu) {
		this.statu = statu;
	}


	public Long getOrder_id() {
		return order_id;
	}




	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}




	public Long getBook_count() {
		return book_count;
	}

	public void setBook_count(Long book_count) {
		this.book_count = book_count;
	}

	public String getBook_discount() {
		return book_discount;
	}

	public void setBook_discount(String book_discount) {
		this.book_discount = book_discount;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public AdminService getAdminService() {
		return adminService;
	}
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	public BookService getBookService() {
		return bookService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public String getFind_username() {
		return find_username;
	}

	public void setFind_username(String find_username) {
		this.find_username = find_username;
	}

	public String getFind_book_name() {
		return find_book_name;
	}

	public void setFind_book_name(String find_book_name) {
		this.find_book_name = find_book_name;
	}

	public String getNews() {
		return news;
	}



	public void setNews(String news) {
		this.news = news;
	}



	public String getHots() {
		return hots;
	}



	public void setHots(String hots) {
		this.hots = hots;
	}



	public Integer getPage_size() {
		return page_size;
	}
	
	



	public String getParam() {
		return param;
	}



	public String getFind_category_name() {
		return find_category_name;
	}

	public void setFind_category_name(String find_category_name) {
		this.find_category_name = find_category_name;
	}

	public void setParam(String param) {
		this.param = param;
	}



	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
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
