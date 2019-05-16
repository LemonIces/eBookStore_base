package com.le.ebook.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.le.ebook.domain.Order;
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
import com.le.ebook.utils.MyPageBean;

public interface UserService {
	/**
	 * 登录服务
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public User findOnUserByUsernameAndPassword(User user) throws Exception;
	void updU(User user);

	//通过字符串得到订单
	List<JiesuanPage> generatorOrder(String products) throws BookCountNotEnoughException, BookException;

	/**
	 * 添加收货地址
	 * @param address
	 * @throws UserAddressException 
	 */
	void add_address(User_address address) throws UserAddressException;

	
	//通过用户id获取地址列表
	public List<User_address> getAddressList(Long user_id);

	//获取唯一的收货地址
	public User_address getAddressOneById(Long user_address_id);

	/**
	 * 删除制定id的收货地址
	 */

	void delete_address(User_address user_address);

	/**
	 * 修改收货地址
	 * @param user_address
	 */
	public void update_address(User_address user_address);

	public void addOrder(Order order) throws BookCountNotEnoughException, OrderException;
	

	public void delete_mallcar(Long mallcar_id);

	/**
	 * 得到订单列表
	 * @param user_id
	 * @return
	 */
	public List<Order> getOrderList(Long user_id);
	List<Order> getOrderList(Long user_id, Object[] order);

	boolean checkExistsOrderByUUCode(String uu_code);
//获取订单
	Order getOrder(Long order_id);

	/**
	 * 通过用户名获取用户
	 * @param username
	 * @return
	 */
	public User getUByUname(String username);
	
	public void sendActiveCode(String toEmail,String username) throws SendCodeIllegalExcep, AddressException, MessagingException;

	public void activeUser(String username, String email, String checkcode) throws IllgalParamsExcep;

	void sendResetPwdCode(String toEmail, String username) throws SendCodeIllegalExcep, AddressException, MessagingException;

	/**
	 * 重置用户密码
	 * @param username
	 * @param password
	 * @param email
	 * @param checkcode
	 * @throws IllgalParamsExcep 
	 */
	public void resetUserPwd(String username, String password, String email, String checkcode) throws IllgalParamsExcep;

	/**
	 * 优化后的注册用户
	 * @param user
	 * @param check_code
	 * @param form_uuid_code
	 * @throws RegistParamsIllegelExcep
	 */
	void addUser(User user, String check_code, String form_uuid_code) throws RegistParamsIllegelExcep;

	/**
	 * 发送注册用户验证码
	 * @param toEmail
	 * @param username
	 * @throws SendCodeIllegalExcep
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	void sendRegistCode(String toEmail, String username) throws SendCodeIllegalExcep, AddressException, MessagingException;
	
	/**
	 *修改邮箱 发送验证码到旧的邮箱中
	 * @param email
	 * @param user
	 * @throws IllgalParamsExcep
	 * @throws MessagingException 
	 */
	void sendChangeEmail2OldEmail(String email, User user) throws IllgalParamsExcep, MessagingException;
	
	/**
	 * 修改邮箱，发送验证码到新的邮箱中
	 * @param email
	 * @param user
	 * @throws IllgalParamsExcep
	 * @throws MessagingException
	 */
	void sendChangeEmail2NewEmail(String email, User user) throws IllgalParamsExcep, MessagingException;
	
	/**
	 * 修改验证码，验证旧邮箱的验证码是否正确
	 * @param checkcode
	 * @param user
	 * @throws UserIsUnLoginExcp
	 * @throws IllgalParamsExcep
	 */
	void check_changeEmailOldCode(String checkcode, User user) throws UserIsUnLoginExcp, IllgalParamsExcep;
	
	/**
	 * 获取登录的用户
	 * @return
	 */
	User getBookStroreLoginedUse();
	
	/**
	 * 修改邮箱，验证新邮箱验证码并修改邮箱
	 * @param checkcode
	 * @param new_email
	 * @param login2
	 * @throws UserIsUnLoginExcp 
	 * @throws IllgalParamsExcep 
	 */
	public void check_changeEmailNewCodeAndDoChange(String checkcode, String new_email, User user) throws UserIsUnLoginExcp, IllgalParamsExcep;
	/**
	 * 刷新登录的用户
	 * @return
	 * @throws UserNoActiveException
	 * @throws UserIsUnLoginExcp
	 * @throws UserPwdOrUsernameWrongErrException
	 */
	User refreshLoginUser() throws UserNoActiveException, UserIsUnLoginExcp, UserPwdOrUsernameWrongErrException;
/**
 * 用户登录
 * @param user
 * @return
 * @throws UserNoActiveException
 * @throws UserPwdOrUsernameWrongErrException
 */
	User login(User user) throws UserNoActiveException, UserPwdOrUsernameWrongErrException;

	
	/**
	 * 分页的订单列表
	 * @param page
	 * @param page_size
	 * @param statu
	 * @return
	 * @throws UserIsUnLoginExcp 
	 */
	MyPageBean getOrderPageBean(Integer page, Integer page_size, String statu) throws UserIsUnLoginExcp;
	/**
	 * 订单重新支付
	 * @param order_id
	 * @param pay_way
	 * @throws UserIsUnLoginExcp 
	 * @throws OrderException 
	 */
	public void rePayOrder(Long order_id, String pay_way) throws UserIsUnLoginExcp, OrderException;
	/**
	 * 生成订单并支付
	 * @param pay_name
	 * @param pay_address
	 * @param pay_phone
	 * @param pay_memo
	 * @param pay_way
	 * @param pay_uu_code
	 * @param pay_products
	 * @param order 
	 * @param order
	 * @throws BookException 
	 * @throws BookCountNotEnoughException 
	 * @throws UserIsUnLoginExcp 
	 * @throws OrderException 
	 */
	public Order payAndSaveOrder(String pay_name, String pay_address, String pay_phone,String pay_memo, String pay_way,
			String pay_uu_code, String pay_products) throws BookCountNotEnoughException, BookException, UserIsUnLoginExcp, OrderException;
	/**
	 * 退货
	 * @param order_id
	 * @param state 
	 * @throws UserIsUnLoginExcp 
	 * @throws OrderStateErrorException 
	 */
	public void returnOrderGoodSer(Long order_id, String state) throws UserIsUnLoginExcp, OrderStateErrorException;
	Order payOrder(String pay_name, String pay_address, String pay_phone, String pay_memo, String pay_way,
			String pay_uu_code, String pay_products)
			throws BookCountNotEnoughException, BookException, UserIsUnLoginExcp, OrderException;
	


	
	
	

}
