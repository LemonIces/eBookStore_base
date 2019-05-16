package com.le.ebook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.le.ebook.domain.Ad;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.Bookcategory1;
import com.le.ebook.domain.Order;
import com.le.ebook.domain.User;
import com.le.ebook.domain.User_address;
import com.le.ebook.exception.UserNoActiveException;
import com.le.ebook.exception.UserPwdOrUsernameWrongErrException;
import com.le.ebook.exception.UserIsUnLoginExcp;
import com.le.ebook.service.BookService;
import com.le.ebook.service.UserService;
import com.le.ebook.utils.MyPageBean;
import com.le.ebook.utils.PageBean;
import com.le.ebook.utils.ScopePutAndGetFromValStackUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class TestAction extends ActionSupport {

	private BookService bookService;

	private UserService userService;
	
	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute() {
		System.out.println("success!");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> map = context.getParameters();
		System.out.println(map);
		for (Entry entry : map.entrySet()) {
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			System.out.println(key + "=" + value);

		}
		return NONE;
	}
	
	/**
	 * 转到登录界面
	 * @return
	 */
	public String loginPage() {
		System.out.println("loginPage!");
		
		return "login";
	}
	
	/**
	 * 转到注册界面
	 * @return
	 */
	public String registPage() {
		System.out.println("loginPage!");
		
		String regist_uuid_code = UUID.randomUUID().toString();
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("regist_uuid_code", regist_uuid_code);
		ServletActionContext.getRequest().getSession().setAttribute("regist_uuid_code_session", regist_uuid_code);
		
		return "regist";
	}
	
	
	public String usercenterPage(){
		System.out.println("usercenterPage!");
		User login2 = userService.getBookStroreLoginedUse();
		if(login2 == null){
			return "un_login_page";
		}	
		return "usercenter";
	}
	
	
	@Deprecated
	public String usercenter_order_listPage(){
		System.out.println("usercenter_order_listPage!");
		
		User login2 =userService.getBookStroreLoginedUse();
		
		if(login2 == null){
			return "un_login_page";
		}	
		
		
		List<Order> list = null;
		if (login2 != null) {
			list = userService.getOrderList(login2.getUser_id());
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("orderList",list);
			
		}
		
		return "usercenter_order_list";
	}
	
	
	//page
	//page_size
	//statu
	
	
	private Integer page;
	private Integer page_size;
	private String statu;
	/**
	 * 分页显示订单
	 * @return
	 */
	public String usercenter_order_listPage1(){
		System.out.println("usercenter_order_listPage1!......");
		try {
			MyPageBean pb = userService.getOrderPageBean(page, page_size, statu);
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("order_bean", pb);
			System.out.println(pb);
		} catch (UserIsUnLoginExcp e) {
			return "un_login_page";
		}
		return "usercenter_order_list";
	}
	
	
	public String addressPage(){
		System.out.println("addressPage!");
		
		User login2 = userService.getBookStroreLoginedUse();
		//shifou yijing  denglu
		if(login2 == null){
			return "un_login_page";
		}	

		if (login2!=null) {
			List<User_address> list = userService.getAddressList(login2.getUser_id());
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("addressList", list);
		}
		
		
		
		return "address";
	}
	
	
	
	
	private Long user_address_id;
	
	//跳转到修改地址界面
	public String changeAddressPage(){
		System.out.println("changeAddressPage!");
		System.out.println("user_address_id="+user_address_id);
		User login2 = userService.getBookStroreLoginedUse();
		
		//shifou yijing  denglu
		if(login2 == null){
			return "un_login_page";
		}	
		
		if (login2!=null) {
			
			User_address address = userService.getAddressOneById(user_address_id);
			
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("userAddress", address);
			
			
		}
		
		return "change_address";
	}
	
	
	
	
	
	public Long getUser_address_id() {
		return user_address_id;
	}

	public void setUser_address_id(Long user_address_id) {
		this.user_address_id = user_address_id;
	}

	
	public String add_addressPage(){
		System.out.println("add_addressPage!");
		User login2 = userService.getBookStroreLoginedUse();
		//shifou yijing  denglu
		if(login2 == null){
			return "un_login_page";
		}	
		
		return "add_address";
	}
	
	

	public String index() {
		System.out.println("index........!");
		
		//getAd
		int start = 0;
		int max = 5;
		List<Ad> listAd = bookService.getAd(start,max);
		
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("listAd", listAd);
		
		/*
		 * 经典著作 社会科学 自然哲学 历史地理 中国文学 美术雕塑 摄影影视 书法篆刻 医药卫生 建筑工程 生活休闲 少儿读物
		 */
		/*
		 * String[] page2categoty = new String[] { "经典著作", "社会科学", "自然哲学",
		 * "历史地理", "中国文学 ", "美术雕塑", "摄影影视", "书法篆刻 "
		 * , "医药卫生 ", "建筑工程", "生活休闲", "少儿读物 " };
		 * long[] page2categoty_id = new long[] { 1, 2, 3, 4, 5, 6, 7,8 , 9, 10,
		 * 11, 12 };
		 */
		List<Bookcategory1> category1List = bookService.getCategory1List(0,12);
		PageBean pageBean = bookService.getNewBookPageBean(new Book(), 1,2*12);
		List<PageBean> listPage2 = new ArrayList<PageBean>();
		for (int i = 0; i < category1List.size(); i++) {
			PageBean pb = bookService.getHotBoosByCategoryById(category1List.get(i).getBookcategory1_id(), 1, 24,null);
			listPage2.add(pb);
		}
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("pageBean", pageBean);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("pageBean2", listPage2);
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("category1List", category1List);
		System.out.println(pageBean.getList());
		//检查用户是否登录
		User loginUser = userService.getBookStroreLoginedUse();
		System.out.println("loginUser="+loginUser);
		if (loginUser == null) {
			Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			User nUser = null;
			for (int i = 0; cookies != null && i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("autoLogin") && cookie.getValue() != null
						&& !cookie.getValue().equals("")) {
					String[] values = cookie.getValue().split(" ");
					nUser = new User();
					nUser.setUsername(values[0]);
					nUser.setPassword(values[1]);
					break;
				}
			}
			if (nUser != null) {
				try {
					User user = userService.login(nUser);
					ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("loginUser", user);
					System.out.println("--------------------------------------------"+user);
				} catch (UserNoActiveException | UserPwdOrUsernameWrongErrException e) {
					System.out.println("用户信息已失效");
				}
			}

		}
		System.out.println("--------------------------------------------");
		return "home";
	}
	
	//currentPage
	//pageSize
	private Integer currentPage;
	private Integer pageSize;
	public String changeNewsBookPage(){
		System.out.println("changeNewsBookPage.......");
		System.out.println("currentPage="+currentPage+", pageSize="+pageSize);
		if(pageSize== null || pageSize <=0){
			pageSize = 12;
		}
		if(currentPage == null || currentPage<=0){
			currentPage = 1;
		}
		
		PageBean newBookPageBean = bookService.getNewBookPageBean(null, currentPage, pageSize);
		System.out.println(newBookPageBean);
		
		JSONObject jsonObject = JSONObject.fromObject(newBookPageBean);
		String jsonStr = jsonObject.toString();
		System.out.println("jsonStr="+jsonStr);
		try {
			ServletActionContext.getResponse().getOutputStream().write(jsonStr.getBytes());
		} catch (IOException e) {
			System.out.println("json会写出错");
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String changepwdPage(){
		System.out.println("changepwdPage!");
		User login2 = userService.getBookStroreLoginedUse();
		//shifou yijing  denglu
		if(login2 == null){
			return "un_login_page";
		}	
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("tempUser", login2);
		return "changepwd";
	}
	
	
	public String change_email_Page(){
		System.out.println("change_email_Page!");
		User login2 = userService.getBookStroreLoginedUse();
		//shifou yijing  denglu
		if(login2 == null){
			return "un_login_page";
		}	
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("user", login2);
		return "change_email";
	}
	
	public String verifyCodeServlet(){
		System.out.println("verifyCodeServlet...........");
		return "verifyCodeServlet";
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
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
