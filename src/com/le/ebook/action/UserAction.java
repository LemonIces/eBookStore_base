package com.le.ebook.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.le.ebook.domain.Book;
import com.le.ebook.domain.Order;
import com.le.ebook.domain.Order_item;
import com.le.ebook.domain.User;
import com.le.ebook.domain.User_address;
import com.le.ebook.exception.BookCountNotEnoughException;
import com.le.ebook.exception.BookException;
import com.le.ebook.exception.IllgalParamsExcep;
import com.le.ebook.exception.OrderException;
import com.le.ebook.exception.OrderStateErrorException;
import com.le.ebook.exception.RegistParamsIllegelExcep;
import com.le.ebook.exception.SendCodeIllegalExcep;
import com.le.ebook.exception.UserAddressException;
import com.le.ebook.exception.UserNoActiveException;
import com.le.ebook.exception.UserPwdOrUsernameWrongErrException;
import com.le.ebook.exception.UserIsUnLoginExcp;
import com.le.ebook.pagebean.JiesuanPage;
import com.le.ebook.service.BookService;
import com.le.ebook.service.UserService;
import com.le.ebook.utils.MyEBStoStrUtil;
import com.le.ebook.utils.MyMD5EncodeUtil;
import com.le.ebook.utils.ScopePutAndGetFromValStackUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	//使用模型驱动装配数据
	private User user = new User();
	
	//用户下次自动登录属性
	private String autoLogin;
	
	//确认密码
	private String password1;
	
	//结算生成订单使用的字符串
	private String products;
	
	public static final String ER = "error";
	
	public static final String IMG_ERR_MSG = "仅支持jpg,jpeg,gif,png,bmp格式！";
	
	public static final String ERR_UUL_MSG = "用户未登录！";
	
	public static final String ERR_PWD_EMPT_MSG = "密码为空！！";
	
	public static final String ERR_OPWD_EMPT_MSG = "原密码为空！";
	
	public static final String ERR_NPWD_EMPT_MSG = "新密码为空！";
	
	public static final String ERR_OPWD_MSG = "原密码错误！";
	
	public static final String ERR_PWD_PWD1_MSG = "密码与确认密码不一致！";
	
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	private Long book_id;
	
	private Integer count;
	
	private File img_file;
	
	 private String img_fileContentType; //得到文件的类型
	 private String img_fileFileName; //得到文件的名称
	
	
	
	
	public String getImg_fileContentType() {
		return img_fileContentType;
	}
	public void setImg_fileContentType(String img_fileContentType) {
		this.img_fileContentType = img_fileContentType;
	}
	public String getImg_fileFileName() {
		return img_fileFileName;
	}
	public void setImg_fileFileName(String img_fileFileName) {
		this.img_fileFileName = img_fileFileName;
	}
	public File getImg_file() {
		return img_file;
	}
	public void setImg_file(File img_file) {
		this.img_file = img_file;
	}
	public Long getBook_id() {
		return book_id;
	}
	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	private BookService bookService;
	
	public BookService getBookService() {
		return bookService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	
	private String check_code;
	/**
	 * 用户登录
	 * @return
	 */
	public String login(){
		System.out.println("login................................");
		System.out.println("user="+user+", autoLogin="+autoLogin+", check_code="+check_code);
		Map retTempMap = new HashMap<>();
		Map errorMap = new HashMap<>();
		retTempMap.put("username",user.getUsername().trim());
		retTempMap.put("check_code",check_code);
		User bookStoreloginU = null;
		String pwdt = user.getPassword();
		String login_code_check_str = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope("codeStr");
		System.out.println("codeStr"+login_code_check_str);
		//把前端用户上传的验证码和后台保存的验证码进行比对是否相一致，如果一直才进行下一步的登录操作，否则返回登录并给用户进行提示
		if(login_code_check_str!=null && check_code!=null && check_code.toLowerCase().equals(login_code_check_str.toLowerCase())){
		}else{
			errorMap.put("error", "验证码错误！");
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", errorMap);
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("tempInfoMap", retTempMap);
			return "login";
		}
		try {
			//调用login方法对用户输入的用户名和密码同后台的用户数据进行比对正误
			bookStoreloginU = userService.login(user);
		}catch (UserNoActiveException e) {
			return "login";
		} catch (Exception e) {
				errorMap.put("error", "用户名或密码错误！");
				ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", errorMap);
				ScopePutAndGetFromValStackUtil.putMsg2ValStack("tempInfoMap", retTempMap);
				return "login";
		}
		//进一步的判断是否已经存在
		if(bookStoreloginU!=null){
			//成功 把user存到session值域中
			ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("loginUser", bookStoreloginU);
			Cookie bookStoreAutoLoginCookie =null;
			//判断是否已经选择需要七天内自动登录的功能
			if(autoLogin!=null && autoLogin.length() == "autoLogin".length() && autoLogin.equals("autoLogin")){
				//用户需要七天内自动登录的功能
				bookStoreAutoLoginCookie = new Cookie("autoLogin", bookStoreloginU.getUsername()+" "+pwdt);
				bookStoreAutoLoginCookie.setMaxAge(604800);
				ScopePutAndGetFromValStackUtil.addAnCook2RespCookieScope(bookStoreAutoLoginCookie);
			}else{
				//不需要自动登录并清除自动登录信息
				bookStoreAutoLoginCookie = new Cookie("autoLogin", null);
				bookStoreAutoLoginCookie.setMaxAge(-1);
				ScopePutAndGetFromValStackUtil.addAnCook2RespCookieScope(bookStoreAutoLoginCookie);
			}
		}
		return "toHome";
	}
		
	
	//用户登出操作
	public String logout() throws Exception {	
		ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("loginUser", null);
		Cookie cookie = new Cookie("autoLogin", null);
		cookie.setMaxAge(0);
		ScopePutAndGetFromValStackUtil.addAnCook2RespCookieScope(cookie);
		return "toHome";
	}
	private String regist_uuid_code;//表单uuid
	private String checkcode;

	
	
	
	
	//regist_uuid_code
	//checkcode
	/**
	 * 用户注册Action方法，接收用户注册界面上传到服务器的数据，为后续调用服务方法
	 * @return
	 */
	public String regist1() {
		System.out.println("regist1.................. ");
		System.out.println(user);
		Map<String,String> tempMap = new HashMap<String,String>();//声明map用来保存临时的用户数据
		Map<String,String> errorMap = new HashMap<String,String>();//声明用来保存错误信息的map
		tempMap.put("username",user.getUsername().trim());
		tempMap.put("checkcode",checkcode);
		tempMap.put("password",user.getPassword());
		tempMap.put("email",user.getEmail());
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("tempInfoMap", tempMap);
		try {
			userService.addUser(user, check_code, regist_uuid_code);//调用注册用户的服务
		} catch (RegistParamsIllegelExcep e) {
			
			errorMap.put("error", e.getMessage());
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", errorMap);
			return "regist";
		}
		return "regist_success";
	}
	
	
	
	public String getRegist_uuid_code() {
		return regist_uuid_code;
	}
	public void setRegist_uuid_code(String regist_uuid_code) {
		this.regist_uuid_code = regist_uuid_code;
	}
	
	//修改用户头像
	public String updateHead_img(){
		System.out.println("updateHeadImg.....");
		User ebsLou = userService.getBookStroreLoginedUse();
		//检查用户是否已经处于登录状态
		if (ebsLou == null) {
			return "un_login_page";
		}
		Long ebslu_u_id = ebsLou.getUser_id();
		//得到指定的保存上传头像的路径
		String inCtRP=ScopePutAndGetFromValStackUtil.getRealPathFromCtextVirtPath("/upload/img/user/"+ebslu_u_id);
        File inCtRPF = new File(inCtRP);
        //如果要保存到的目标路径不存在，创建路径
        if(!inCtRPF.exists()){
        	inCtRPF.mkdirs();
        }
        try {
        	  File inCtNF = new File(inCtRP,"head_img");//生成file对象
        	  if(!inCtNF.exists()){
        		  inCtNF.mkdirs();//如果路径不存在，创建路径
        	  }
        	  String hip =  user.getHead_img();
        	  String inCtNname = MyEBStoStrUtil.subChsF(hip, MyEBStoStrUtil.getStrLaIndex0Chs(hip, "/")+1);//获得新HeadImg的文件名称
            //开始保存文件
            FileUtils.copyFile(new File(inCtRPF,inCtNname), new File(inCtNF,inCtNname));
            ebsLou.setHead_img(getHead_imgRootPath(ebslu_u_id)+inCtNname);//把新HeadImg路径和名字设置给用户
            //进行操作修改
            userService.updU(ebsLou);
        } catch (IOException e) {
        }
		return "toUsercenterPage";
	}
	//返回指定用户id的头像根目录
	public String getHead_imgRootPath(Long user_id){
		return "upload/img/user/"+user_id+"/head_img/";
	}
	
	
	
	//返回model
	@Override
	public User getModel() {
		
		return user;
	}
	
	public String addcar(){
		User login = userService.getBookStroreLoginedUse();
		
		if (login == null) {
			return "un_login_page";
		}
		
		
		if(login!= null){
			Book newBook = bookService.getBookById(book_id);
			login.getMall_books().add(newBook);
			userService.updU(login);
		}
		return null;
	}
	
	//修改用户基本信息
	public String updateInfo(){
		System.out.println("updateInfo............");
		System.out.println("userinfo:"+user);
		//从Session域中获取登录的信息，如果信息不为空登录处于的状态，否则用户未登录要求用户进行登录
		User login = userService.getBookStroreLoginedUse();
		if (login == null) {//未登录状态，返回到登录的界面
			return "un_login_page";
		}
		//用户已处于登录状态，进行下一步操作
			String username_new_form_temp = user.getUsername();
			String email_new_fonrm_temp = user.getEmail();
			String nickname_new_fonrm_temp = user.getNickname();
			String phone_new_fonrm_temp = user.getPhone();
			String gender_new_fonrm_temp = user.getGender();
			String introduce_new_fonrm_temp = user.getIntroduce();
			//不进行修改用户名
/*			if(!username_new_form_temp.equals(login.getUsername())){
				login.setUsername(username_new_form_temp);
			}*/
			//不进行修改邮箱
/*			if(!email_new_fonrm_temp.equals(login.getEmail())){
				login.setEmail(email_new_fonrm_temp);
			}*/
			if(nickname_new_fonrm_temp !=null && !nickname_new_fonrm_temp.equals(login.getNickname())){
				login.setNickname(nickname_new_fonrm_temp);//给昵称赋前端上传的值，对当前登录用户昵称进行修改
			}
			if(phone_new_fonrm_temp !=null && !phone_new_fonrm_temp.equals(login.getPhone())){
				login.setPhone(phone_new_fonrm_temp);//给手机号赋前端上传的值，对当前登录用户手机号进行修改
			}
			if(gender_new_fonrm_temp !=null && !gender_new_fonrm_temp.equals(login.getGender())){
				login.setGender(gender_new_fonrm_temp);//给性别赋前端上传的值，对当前登录用户性别进行修改
			}
			if(introduce_new_fonrm_temp !=null && !introduce_new_fonrm_temp.equals(login.getIntroduce())){
				login.setIntroduce(introduce_new_fonrm_temp);//给简介赋前端上传的值，对当前登录用户简介进行修改介
			}
			userService.updU(login);//进行修改基本信息
			System.out.println("newInfo user:"+ login);
			//更新session中的用户信息
			ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("loginUser", login);
		return "toUsercenterPage";
	}
	
	//products
	//结算生成订单
	public String jiesuan(){
		System.out.println("jiesuan..................");
		System.out.println("products="+products);
		Map<String,String> retMap = new HashMap<String,String>();
		User login2 = userService.getBookStroreLoginedUse();
		if (login2 == null) {
			retMap.put("state", "false_unlogined");
			retMap.put("error", "用户未登录");
		}else{
			UUID uuid = UUID.randomUUID();
			String ustr = uuid.toString();
				List<JiesuanPage> jiesuanList;
				try {
					jiesuanList = userService.generatorOrder(products);
					//得到收货地址列表
					List<User_address> addressList = userService.getAddressList(login2.getUser_id());
					//保存到值栈中
					ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("jiesuanList", jiesuanList);
					ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("addressList", addressList);
					ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("uu_code", ustr);
					retMap.put("state", "true");
				} catch (BookCountNotEnoughException e) {
					retMap.put("state", "false_count");
					retMap.put("error", e.getMessage());
				} catch (BookException e) {
					retMap.put("state", "false_down");
					retMap.put("error", e.getMessage());
				}
			
		}
		System.out.println(retMap);
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
			return null;
	}
	
	
	private String pay_name;
	private String pay_phone;
	private String pay_address;
	private String pay_memo;
	private String pay_way;
	private String pay_uu_code;
	
	private String pay_products;
	//jq方式
	//书籍购买支付订单
	public String pay_order1(){
		System.out.println("pay_order1..................");
		System.out.println("pay_name="+pay_name+", pay_phone="+pay_phone+", pay_address="+pay_address+", pay_memo="+pay_memo+", pay_uu_code="+pay_uu_code);
		System.out.println("pay_ptoducts="+pay_products);
		//声明一个retmap用来服务器返回给web前端的数据
		Map<String,String> retMap = new HashMap<String,String>();
				
				try {
					Order order = userService.payOrder(pay_name,pay_address,pay_phone,pay_memo,pay_way,pay_uu_code,pay_products);
					userService.addOrder(order);
					Set<Order_item> order_items = order.getItems();
					Iterator<Order_item> it = order_items.iterator();
					while(it.hasNext()){
						Order_item order_item = it.next();
						Long id = order_item.getProduct_id();
						Long mallcar_id = order_item.getMallcar_id();
						Long count = order_item.getCount();
						Book onebook = bookService.getBookById(id);
						onebook.setCount(onebook.getCount() - count);
						bookService.updBook(onebook);
						if(mallcar_id!=null && mallcar_id>0){
							userService.delete_mallcar(mallcar_id);
						}
						
					}
					retMap.put("state", "true");//标识订单成功
					retMap.put("order_no", order.getOrder_no());
				} catch (BookCountNotEnoughException e) {
					retMap.put("state", "false_count");//标识订单成功
					retMap.put("error",e.getMessage());
				} catch (BookException e) {
					retMap.put("state", "false_book");//标识订单成功
					retMap.put("error",e.getMessage());
				} catch (UserIsUnLoginExcp e) {
					retMap.put("state", "false_unlogined");//标识订单成功
					retMap.put("error",e.getMessage());
				} catch (OrderException e) {
					retMap.put("state", "false_order");//标识订单成功
					retMap.put("error",e.getMessage());
				}
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		//转发到结算页面
		return null;
	}
	
	
	
	
	public String getPay_uu_code() {
		return pay_uu_code;
	}
	public void setPay_uu_code(String pay_uu_code) {
		this.pay_uu_code = pay_uu_code;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public String getPay_products() {
		return pay_products;
	}
	public void setPay_products(String pay_products) {
		this.pay_products = pay_products;
	}
	public String getPay_name() {
		return pay_name;
	}
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	public String getPay_phone() {
		return pay_phone;
	}
	public void setPay_phone(String pay_phone) {
		this.pay_phone = pay_phone;
	}
	public String getPay_address() {
		return pay_address;
	}
	public void setPay_address(String pay_address) {
		this.pay_address = pay_address;
	}
	public String getPay_memo() {
		return pay_memo;
	}
	public void setPay_memo(String pay_memo) {
		this.pay_memo = pay_memo;
	}

	//上传头像操作
	public String uploadImg(){
		System.out.println("uploadImg.......................");
		System.out.println("img_file="+img_file+", getImg_fileFileName()="+getImg_fileFileName()+", getImg_fileContentType()="+getImg_fileContentType()+", img_file.length()="+img_file.length());
		User login = userService.getBookStroreLoginedUse();
		if (login == null) {
			return "un_login_page";
		}
		int strIndex = MyEBStoStrUtil.getStrIndex0Chs(getImg_fileFileName(), ".");
		//对用户上传的图像进行文件扩展名检查
		if(strIndex < 0){
			ScopePutAndGetFromValStackUtil.putMsg2ValStack(ER, IMG_ERR_MSG);
			return "usercenter";
		}
		String upload_user_head_img_exet = MyEBStoStrUtil.subChsF(getImg_fileFileName(), strIndex);
		//检查对用户上传到服务器的文件的扩展名是否符合要求
		if(!MyEBStoStrUtil.chs2LCase(upload_user_head_img_exet).equals( ".jpg")  &&  !MyEBStoStrUtil.chs2LCase(upload_user_head_img_exet).equals( ".jpeg")  &&  !MyEBStoStrUtil.chs2LCase(upload_user_head_img_exet) .equals( ".bmp")   &&  !MyEBStoStrUtil.chs2LCase(upload_user_head_img_exet).equals( ".gif") ){
			ScopePutAndGetFromValStackUtil.putMsg2ValStack(ER, IMG_ERR_MSG);
			return "usercenter";
		}
		long img_Length = img_file.length();
		//用户的头像仅支持图像空间占用大小为2mB及以下
		if(img_Length > (2*1024*1024)){
			ScopePutAndGetFromValStackUtil.putMsg2ValStack(ER, "图片大小超过2M！");
			return "usercenter";
		}
		Long user_id = login.getUser_id();
	        //获得要进行保存文件夹的物理路径(绝对路径)
	        String inContxRealp=ScopePutAndGetFromValStackUtil.getRealPathFromCtextVirtPath("/upload/img/user/"+user_id);
	        File iCRF = new File(inContxRealp);
	        //路径是否是真实存在检查，检查结果是不存在的话就要进行创建所有的目录路径
	        if(!iCRF.exists()){
	        	iCRF.mkdirs();	
	        }
	        try {
	            //进行保存文件的操作
	            FileUtils.copyFile(img_file, new File(iCRF,getImg_fileFileName()));
	            ScopePutAndGetFromValStackUtil.putMsg2ValStack("upload_img_temp", "upload/img/user/"+user_id+"/"+getImg_fileFileName());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return "upload_success";
	}
	
	
	
	
	//添加地址
	private String name_addrform;
	private String gender_addrform;
	private String phone_addrform;
	private String address_addrform;
	//添加地址
	public String add_address(){
		System.out.println("add_address.........");
		System.out.println("name_addrform="+name_addrform+", gender_addrform="+gender_addrform+", phone_addrform="+phone_addrform+", address_addrform="+address_addrform);
		User login2 = userService.getBookStroreLoginedUse();
		if (login2 == null) {
			return "un_login_page";
		}
			/*
			 * 
			private Long user_address_id;
			private Long user_id;
			private String name;
			private String phone;
			private String address;
			private String memo;
			private String gender;
			 */
			User_address user_address = new User_address();
			user_address.setUser_id(login2.getUser_id());
			user_address.setAddress(address_addrform);
			user_address.setGender(gender_addrform);
			user_address.setPhone(phone_addrform);
			user_address.setName(name_addrform);
			try {
				userService.add_address(user_address);
				ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("addressList",userService.getAddressList(login2.getUser_id()));
			} catch (UserAddressException e) {
				ScopePutAndGetFromValStackUtil.putMsg2ValStack("error", e.getMessage());
				return "add_address";
			}
		return "toAddressPage";
	}
	
	
	
	

	private Long user_address_id;
	
	//删除地址
	public String delete_address(){
		System.out.println("delete_address.........");
		System.out.println("user_address_id="+user_address_id);
		
		User login2 = userService.getBookStroreLoginedUse();
		if (login2!=null) {
			
			
			User_address addressOneById = userService.getAddressOneById(user_address_id);
			
			userService.delete_address(addressOneById);
			
		}
		return "toAddressPage";
	}
	
	
	//修改地址
	public String update_address(){
		System.out.println("update_address.........");
		System.out.println("name_addrform="+name_addrform+", gender_addrform="+gender_addrform+", phone_addrform="+phone_addrform+", address_addrform="+address_addrform);
		
		User login2 = userService.getBookStroreLoginedUse();
		if (login2!=null) {
			/*
			 * 
			private Long user_address_id;
			private Long user_id;
			private String name;
			private String phone;
			private String address;
			private String memo;
			private String gender;
			 */
			//TODO:
			
			User_address user_address = new User_address();
			
			user_address.setUser_address_id(user_address_id);
			user_address.setUser_id(login2.getUser_id());
			user_address.setAddress(address_addrform);
			user_address.setGender(gender_addrform);
			user_address.setPhone(phone_addrform);
			user_address.setName(name_addrform);
			
			userService.update_address(user_address);
			
		}
		return "toAddressPage";
	}
	
	
	private Long order_id;
	//显示订单详情
	public String order_detail(){
System.out.println("order_detail...............!");
		System.out.println("order_id="+order_id);
		User login2 = userService.getBookStroreLoginedUse();
		
		if(login2 == null){
			return "un_login_page";
		}	
		
		
		if (login2 != null) {
			Order order = userService.getOrder(order_id);
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("detailOrder", order);
			
		}
		
		return "order_detail";
		
	}
	
	
	//old_pwd
	//pwd
	//pwd1
	//user_id
	private String old_pwd;
	private String pwd;
	private String pwd1;
	private Long user_id;
	//用户修改密码
	public String changePwd(){
		System.out.println("changePwd........");
		//检查用户登录状态
		//得到图书商城系统登录的用户
		User loginedUser4ChangePwd = userService.getBookStroreLoginedUse();
		if(loginedUser4ChangePwd == null){
			//用户未登录状态
			return "un_login_page";
		}	
		//声明用来需要返回给前端的保存错误信息map
		Map erMap = new HashMap<>();
			//检查原始密码是否为空
			if(old_pwd == null && old_pwd.trim().equals("")){
				erMap.put(ER, ERR_OPWD_EMPT_MSG);
			}else{
				//检查新密码是否为空
				if(pwd == null && pwd.trim().equals("")){
					erMap.put("error", ERR_NPWD_EMPT_MSG);
				}//检验用户输入的新密码和确认密码两个密码是否是一致的
				else if(pwd1 == null ||!pwd.trim().equals(pwd1.trim())){
					erMap.put(ER,ERR_PWD_PWD1_MSG);
				}//比对原密码和该用户名的后台数据中保存的密码是否是一致的			
				else if(! MyMD5EncodeUtil.useMd5EncodePlainText(old_pwd.trim()).equals(loginedUser4ChangePwd.getPassword())){
					erMap.put(ER, ERR_OPWD_MSG);
				}
			}
			//是否有错误的信息
			if(erMap.size() > 0){
				ScopePutAndGetFromValStackUtil.putMsg2ValStack("tempUser", loginedUser4ChangePwd);
				ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", erMap);
				return "changepwd";
			}
			loginedUser4ChangePwd.setPassword(MyMD5EncodeUtil.useMd5EncodePlainText(pwd.trim()));
			userService.updU(loginedUser4ChangePwd);//进行修改密码
			try {
				logout();//修改成功后进行登出操作，要求用户重新登录
			} catch (Exception e) {
			}
		return "changepwd_success";
	}
	
	
	//email
	//username
	
	public String sendActiveCode(){
		System.out.println("sendActiveCode.......");
		System.out.println("email="+user.getEmail()+", username="+user.getUsername());
		
		Map<String,String> retMap = new HashMap<String,String>();
		try {
			userService.sendActiveCode(user.getEmail(), user.getUsername());
		} catch (SendCodeIllegalExcep e) {
			System.out.println(e.getMessage());
			retMap.put("error", e.getMessage());

		} catch (AddressException e) {
			retMap.put("error", "发送失败，确认邮箱是否正确！");
		} catch (MessagingException e) {
			retMap.put("error", "发送失败，请检查是否连接互联网！");
		}
		
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		
		return null;
	}
	
	
	
	
	
	//email
	//username
	
	public String sendResetPwdCode(){
		System.out.println("sendResetPwdCode.......");
		System.out.println("email="+user.getEmail()+", username="+user.getUsername());
		Map<String,String> retMap = new HashMap<String,String>();
		try {
			userService.sendResetPwdCode(user.getEmail(), user.getUsername());
			retMap.put("state", "true");
		} catch (SendCodeIllegalExcep e) {
			System.out.println(e.getMessage());

			retMap.put("error", e.getMessage());
			retMap.put("state", "false");

		}catch (AddressException e) {
			retMap.put("error", "发送失败，确认邮箱是否正确！");
			retMap.put("state", "false");
		} catch (MessagingException e) {
			retMap.put("error", "发送失败，请检查是否连接互联网！");
			retMap.put("state", "false");
		}
		JSONObject fromObject = JSONObject.fromObject(retMap);
		
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		
		return null;
	}
	
	
	
	
	
	//email
	//username
	
	public String sendRegistCheckCode(){
		System.out.println("sendRegistCheckCode.......");
		System.out.println("email="+user.getEmail()+", username="+user.getUsername());
		Map<String,String> retMap = new HashMap<String,String>();
		
		try {
			userService.sendRegistCode(user.getEmail(), user.getUsername());
			retMap.put("state", "true");
		} catch (SendCodeIllegalExcep e) {
			
			System.out.println(e.getMessage());
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());

		} catch (AddressException e) {
			retMap.put("error", "发送失败，确认邮箱是否正确！");
			retMap.put("state", "false");
		} catch (MessagingException e) {
			retMap.put("error", "发送失败，请检查是否连接互联网！");
			retMap.put("state", "false");
		}
		
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		return null;
	}
	
	
	/**
	 * 重置用户密码
	 * @return
	 */
	//username
	//email
	//checkcode
	//password
	public String resetPwd(){
		System.out.println("resetPwd.........");
		System.out.println("username="+user.getUsername()+", email="+user.getEmail()+", checkcode="+checkcode+", password="+user.getPassword());
		try {
			userService.resetUserPwd(user.getUsername(),user.getPassword(),user.getEmail(),checkcode);
		} catch (IllgalParamsExcep e) {
			Map<String,String> errorMap = new HashMap<String,String>();
			Map<String,String> infoMap = new HashMap<String,String>();
			infoMap.put("username", user.getUsername());
			infoMap.put("password", user.getPassword());
			infoMap.put("eamil", user.getEmail());
			errorMap.put("error", e.getMessage());
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", errorMap);
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("infoMap", infoMap);
			return "reset_pwd";
		}
		return "reset_pwd_success";
		
	}
	
	
	
	//email
	/**
	 * 修改邮箱，如果存在旧邮箱，将验证码发送到旧邮箱验证用户身份
	 * @return
	 */
	public String send_ChangeEmail2OldEmail(){
		System.out.println("send_ChangeEmail2OldEmail.......");
		System.out.println("email="+user.getEmail());
		Map<String,String> retMap = new HashMap<String,String>();//声明返回信息的map
		User login2 = userService.getBookStroreLoginedUse();//获取登录状态
		if(login2 == null){//用户未登录
			return "un_login_page";
		}	
		//用户已经登录，继续向下运行
		try {
			userService.sendChangeEmail2OldEmail(user.getEmail(),login2);//发送验证码到旧邮箱
			retMap.put("state", "true");
		} catch (IllgalParamsExcep e) {
			System.out.println(e.getMessage());
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		} catch (MessagingException e) {
			retMap.put("state", "false");
			retMap.put("error", "发送失败，检查是否连接互联网！");
		}
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		return null;
	}
	
	
	//email
	public String send_ChangeEmail2NewEmail(){
		System.out.println("send_ChangeEmail2NewEmail.......");
		System.out.println("email="+user.getEmail());
		
		Map<String,String> retMap = new HashMap<String,String>();
		
		User login2 = userService.getBookStroreLoginedUse();
		if(login2 == null){
			return "un_login_page";
		}	
		
		try {
			userService.sendChangeEmail2NewEmail(user.getEmail(),login2);
			retMap.put("state", "true");
			
		} catch (IllgalParamsExcep e) {
			System.out.println(e.getMessage());
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());

		} catch (MessagingException e) {
			retMap.put("state", "false");
			retMap.put("error", "发送失败，检查是否连接互联网！");
		}
		
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		
		return null;
	}
	
	//checkcode
	public String check_changeEmailOldCode(){
		System.out.println("check_changeEmailOldCode...........");
		System.out.println("checkcode"+checkcode);
		
		//声明返回的map
		Map<String,String> retMap = new HashMap<String,String>();
		
		//判断用户是否登录
		User login2 = userService.getBookStroreLoginedUse();
		try {
			userService.check_changeEmailOldCode(checkcode, login2);
			//验证通过
			retMap.put("state", "true");
		} catch (UserIsUnLoginExcp e) {
			//用户未登录
			retMap.put("error", e.getMessage());
			retMap.put("state", "false");
			return "un_login_page";
		} catch (IllgalParamsExcep e) {
			//参数错误
			retMap.put("error", e.getMessage());
			retMap.put("state", "false");
		}
		
		//返回数据
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};

		return null;
	}
	
	private String new_email;
	//checkcode
	//new_email
	public String check_changeEmailNewCodeAndChangeEmail(){
		System.out.println("check_changeEmailNewCodeAndChangeEmail...........");
		System.out.println("checkcode="+checkcode+", new_email="+new_email);
		
		//声明返回的map
		Map<String,String> retMap = new HashMap<String,String>();
		Map<String,String> errorMap = new HashMap<String,String>();
		
		//判断用户是否登录
		User login2 = userService.getBookStroreLoginedUse();
		try {
			userService.check_changeEmailNewCodeAndDoChange(checkcode,new_email, login2);
			//验证通过
			retMap.put("state", "true");
			//刷新登录用户
			userService.refreshLoginUser();
		} catch (UserIsUnLoginExcp e) {
			//用户未登录
			retMap.put("error", e.getMessage());
			retMap.put("state", "false");
			return "un_login_page";
		} catch (IllgalParamsExcep e) {
			//参数错误
			retMap.put("error", e.getMessage());
			retMap.put("state", "false");
		} catch (UserNoActiveException e) {
			errorMap.put("error", "用户信息已失效，请重新输入用户信息！");
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", errorMap);
			return "login";
		} catch (UserPwdOrUsernameWrongErrException e) {
			errorMap.put("error", "用户信息已失效，请重新输入用户信息！");
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("errorMap", errorMap);
			return "login";
		}
		
		//返回数据
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};

		return null;
	}
	
	
	//order_id
	public String go_pay_page(){
	System.out.println("order_id="+order_id);
	
		User loginUser = userService.getBookStroreLoginedUse();
		
		if (loginUser==null) {
			return "un_login_page";
		}
		
		Order order = userService.getOrder(order_id);
		
		if (order!=null && !order.getStatu().equals("0")) {
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("error", "订单已经支付过了！");
			return "error";
		}
		
			//保存到值栈中
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("order", order);
		
		return "pay_order";
	}
	
	//order_id
	//pay_way
	public String re_pay_order(){
		System.out.println("re_pay_order.......");
		System.out.println("order_id="+order_id+", pay_way="+pay_way);
		
		try {
			userService.rePayOrder(order_id,pay_way);
			Order order = userService.getOrder(order_id);
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("order", order);
			
		} catch (UserIsUnLoginExcp e) {
			return "un_login_page";
		} catch (OrderException e) {
			ScopePutAndGetFromValStackUtil.putMsg2ValStack("error", e.getMessage());
			return "error";
		}
		return "pay_order";
		
	}
	
	
	//order_id
	//state
	private String state;
	public String returnOrderGoods(){
		System.out.println("returnOrderGoods...........");
		System.out.println("order_id="+order_id+", state="+state);
		Map<String,String> retMap = new HashMap<String,String>();
		//判断用户是否登录
		User login2 = userService.getBookStroreLoginedUse();
		try {
			
				userService.returnOrderGoodSer(order_id,state);
			//验证通过
			retMap.put("state", "true");
			//刷新登录用户
		} catch (UserIsUnLoginExcp e) {
			//用户未登录
			retMap.put("error", e.getMessage());
			retMap.put("state", "false_unlogined");
		}catch (OrderStateErrorException e) {
			retMap.put("error", e.getMessage());
			retMap.put("state", "false_orderstate");
		}
		
		//返回数据
		JSONObject fromObject = JSONObject.fromObject(retMap);
		try {
			ServletActionContext.getResponse().getOutputStream().write(fromObject.toString().getBytes());
		} catch (IOException e1) {
			System.out.println("json会写出错");
		};
		
		return null;
		
	}
	
	

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getOld_pwd() {
		return old_pwd;
	}
	public void setOld_pwd(String old_pwd) {
		this.old_pwd = old_pwd;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwd1() {
		return pwd1;
	}
	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getUser_address_id() {
		return user_address_id;
	}
	public void setUser_address_id(Long user_address_id) {
		this.user_address_id = user_address_id;
	}
	public String getName_addrform() {
		return name_addrform;
	}
	public void setName_addrform(String name_addrform) {
		this.name_addrform = name_addrform;
	}
	public String getGender_addrform() {
		return gender_addrform;
	}
	public void setGender_addrform(String gender_addrform) {
		this.gender_addrform = gender_addrform;
	}
	public String getPhone_addrform() {
		return phone_addrform;
	}
	public void setPhone_addrform(String phone_addrform) {
		this.phone_addrform = phone_addrform;
	}
	public String getAddress_addrform() {
		return address_addrform;
	}
	public void setAddress_addrform(String address_addrform) {
		this.address_addrform = address_addrform;
	}
	public String getCheck_code() {
		return check_code;
	}
	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}
	public String getNew_email() {
		return new_email;
	}
	public void setNew_email(String new_email) {
		this.new_email = new_email;
	}
	/**
	 * 放入值站中
	 * @param msgKey
	 * @param msgVal
	 */
	public void putMsg2ValStack(String msgKey,Object msgVal){
		ActionContext.getContext().put(msgKey, msgVal);
	}
	
	/**
	 * 从session雨中获取对象
	 * @param objKey
	 * @return
	 */
	public Object getAnObjFromSessionScope(String objKey){
		return ServletActionContext.getRequest().getSession().getAttribute(objKey);
	}
	/**
	 * 给session域中设置一个值
	 * @param objKey
	 * @param objValue
	 */
	public void setAnObj2SessionScope(String objKey,Object objValue){
		ServletActionContext.getRequest().getSession().setAttribute(objKey,objValue);
	}
	/**
	 * 给session域中删除一个值
	 * @param objKey
	 */
	public void remAnObjFromSessionScope(String objKey){
		ServletActionContext.getRequest().getSession().removeAttribute(objKey);
	}
	
}
