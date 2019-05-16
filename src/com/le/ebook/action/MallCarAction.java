package com.le.ebook.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.struts2.ServletActionContext;
import org.dom4j.DocumentFactory;
import org.springframework.beans.BeanUtils;

import com.le.ebook.domain.Book;
import com.le.ebook.domain.MallCar;
import com.le.ebook.domain.User;
import com.le.ebook.exception.BookException;
import com.le.ebook.exception.MallCarException;
import com.le.ebook.pagebean.MallCarPage;
import com.le.ebook.service.BookService;
import com.le.ebook.service.MallCarService;
import com.le.ebook.service.UserService;
import com.le.ebook.utils.MyEBSJS0NUtil;
import com.le.ebook.utils.ScopePutAndGetFromValStackUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;

public class MallCarAction  extends ActionSupport implements ModelDriven<MallCar>{
	//
	private MallCar mallCar = new MallCar(); 
	//服务
	private UserService userService;
	private MallCarService mallCarService;
	
	/**
	 * error
	 */
	public static final String ER = "error";
	/**
	 * state
	 */
	public static final String STA = "state";
	
	private Integer page_size;
	private Integer page;
	//page_size
	//page
	//statu
	//
	/**
	 * 显示用户的购物车列表
	 * @return
	 */
	public String list(){
		System.out.println("list................");
		System.out.println("page_size="+page_size+", page="+page);
		//获取用户登录状态
		User isLoUse = userService.getBookStroreLoginedUse();//获取登录用户
		if (isLoUse == null) {//用户未登录状态
			System.out.println("用户未登录");
			return "un_login_page";
		}
		//用户已登录状态
		//通过用户名查询得到用户的购物车列表
		Map map = mallCarService.getByUsernameForList(isLoUse.getUsername(),page,page_size);
		//把查询到购物车列表放到值栈中
		ScopePutAndGetFromValStackUtil.putMsg2ValStack("mallMap", map);
		return "mallCar";
	}
	
	//product_id
	//count
	/**
	 * 添加书籍到购物车
	 * @return
	 */
	public String add(){
		System.out.println("add.........");
		System.out.println(mallCar);
		Map retMap = new HashMap<>();//声明map用来返回
		User bookStoreLoginedUser = userService.getBookStroreLoginedUse();	//从系统session中获取用户登录的状态
		if(bookStoreLoginedUser == null){//未登录状态
			retMap.put(STA, "false_unlogined");//标识添加失败状态
			retMap.put(ER, "用户未登录");
		}else{//已登录状态
			mallCar.setUsername(bookStoreLoginedUser.getUsername());//购物车数据从属的用户名
			mallCar.setUser_id(bookStoreLoginedUser.getUser_id());//用户的用户id
			try {
				mallCarService.addMallCar(mallCar);
				retMap.put(STA, "true");//返回添加成功状态
			} catch (BookException e) {
				retMap.put(ER, e.getMessage());
				retMap.put(STA, "false_book");//标识添加失败状态
			} catch (MallCarException e) {
				retMap.put(ER, e.getMessage());
				retMap.put(STA, "false_mall");//标识添加失败状态
			}//添加到购物车
		}
	JSONObject rJObj = MyEBSJS0NUtil.jObjFromAMap(retMap);//封装成JSON
	try {
		//进行回写数据
		MyEBSJS0NUtil.wriJObj2Resp(rJObj);
	} catch (IOException e1) {
		System.out.println("json回写出错");
	};
		return null;
	}
	
	//mallcar_id
	/**
	 * 删除购物车中指定的书籍
	 * @return
	 */
	public String deleteOne(){
		System.out.println("deleteOne.....");
		System.out.println("mallcar_id="+mallCar.getMallcar_id());
		User user = userService.getBookStroreLoginedUse();
		if (user == null) {
			System.out.println("用户未登录");
			return "un_login_page";
		}
		mallCarService.delete(mallCar.getMallcar_id());
		return "re_mallcar";
	}
	
	private String mallcar_ids;
	//mallcar_ids 
	// 1,2,3,4
	/**
	 * 批量删除购物车中的书籍
	 * @return
	 */
	public String deleteMulti(){
		System.out.println("mallcar_ids="+mallcar_ids);
		User isLUse = userService.getBookStroreLoginedUse();
		if (isLUse == null) {
			System.out.println("用户未登录");
			return "un_login_page";
		}
		//循环删除指定id的购物车列表数据
		if(mallcar_ids != null && mallcar_ids.length() > 0){
			String[] ids = mallcar_ids.split(",");
			//循环ids获取每一项id值
			for (int i = 0; i < ids.length; i++) {
				//得到每一项的id
				Long mid = Long.parseLong(ids[i]);
				mallCarService.delete(mid);//删除购物车数据
			}
		}
		return "re_mallcar";
	}
	

	
	public String getMallcar_ids() {
		return mallcar_ids;
	}

	public void setMallcar_ids(String mallcar_ids) {
		this.mallcar_ids = mallcar_ids;
	}

	@Override
	public MallCar getModel() {
		return mallCar;
	}
	
	/**
	 * 修改购物车书籍数量
	 * @return
	 */
	public String updateCount(){
		System.out.println("updateCount.................................");
		//检查用户登录状态
		User isLUse = userService.getBookStroreLoginedUse();
		if (isLUse == null) {//用户未登录
			return "un_login_page";
		}
		//用户已登录
		mallCarService.update(mallCar);//修改购物车书籍数量
		return "toListAction";
	}
	
	public MallCarService getMallCarService() {
		return mallCarService;
	}

	public void setMallCarService(MallCarService mallCarService) {
		this.mallCarService = mallCarService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
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
