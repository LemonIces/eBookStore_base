package com.le.ebook.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.context.Theme;

import com.le.ebook.dao.UserDao;
import com.le.ebook.domain.Book;
import com.le.ebook.domain.MallCar;
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
import com.le.ebook.service.UserService;
import com.le.ebook.utils.MyEBStoCheckRandCodeUtils;
import com.le.ebook.utils.MyMD5EncodeUtil;
import com.le.ebook.utils.MyPageBean;
import com.le.ebook.utils.Rand;
import com.le.ebook.utils.ScopePutAndGetFromValStackUtil;
import com.le.ebook.utils.MySendRandCode2EamilUtil;
import com.opensymphony.xwork2.ActionContext;
//注解方式使用hibernate事务
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=true)
public class UserServiceImpl implements UserService{
	/**
	 * 验证码为空
	 */
	public static final String ERR_CHE_EMPT_MSG = "验证码为空！";
	/**
	 * 验证码错误
	 */
	public static final String ERR_CHE_MSG = "验证码错误！";
	/**
	 * 用户未登录
	 */
	public static final String ERR_UUL_MSG = "用户未登录！";
	/**
	 * 新邮箱为空
	 */
	public static final String ERR_NEMA_EMP_MSG = "新邮箱为空！";
	/**
	 * 新邮箱格式错误
	 */
	public static final String ERR_NEMA_FMA_MSG = "新邮箱格式错误！";
	
	/**
	 * 用户名为空
	 */
	public static final String ERR_UNAME_EMP_MSG = "用户名为空！";
	/**
	 * 用户不存在
	 */
	public static final String ERR_U_UEXT_MSG = "用户不存在！";
	/**
	 * 邮箱为空
	 */
	public static final String ERR_EMA_EMP_MSG = "邮箱为空！";
	/**
	 * 邮箱不匹配
	 */
	public static final String ERR_EMA_EMA1_MSG = "邮箱不匹配！";
	
	public static final String ERR_PWD_EMP_MSG = "密码为空！";
	
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findOnUserByUsernameAndPassword(User user) throws UserPwdOrUsernameWrongErrException{
		System.out.println("getUserByUsernamePassword");
		//md5
		user.setPassword(MyMD5EncodeUtil.useMd5EncodePlainText(user.getPassword()));
		User exits = userDao.findOneUser(user);
		if(exits == null){
			throw new UserPwdOrUsernameWrongErrException("用户名或密码错误！");
		}
		return exits;
	}
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
	public void addUser(User user, String check_code_back, String form_uuid_code) throws RegistParamsIllegelExcep {
		// 判断验证码
		String regist_check_code = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope("regist_check_code");
		ScopePutAndGetFromValStackUtil.remAnObjFromSessionScope("regist_check_code");
		System.out.println("regist_check_code" + regist_check_code);
		// 判断用户输入的验证码和后端保存的是否相一致
		if(MyEBStoCheckRandCodeUtils.check4LoginPageFormRandCode(regist_check_code, check_code_back)){
			// 比对成功，验证码正确
		} else {
			// 验证码错误
			// 比对失败，抛出验证码错误异常
			throw new RegistParamsIllegelExcep("验证码错误!");
		}
		// 检查用户提交的注册用户表单是否有效
		if (form_uuid_code == null && "".equals(form_uuid_code)) {
			throw new RegistParamsIllegelExcep("无效的表单！");
		} else {
			//检查用户是否是重复的提交同一个表单
			String regist_uuid_code_session = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope("regist_uuid_code_session");
			ScopePutAndGetFromValStackUtil.remAnObjFromSessionScope("regist_uuid_code_session");
			if (regist_uuid_code_session != null && form_uuid_code.equals(regist_uuid_code_session)) {
			} else {
				throw new RegistParamsIllegelExcep("请不要重复提交表单！");
			}
		}
		// 创建新的User对象，把用户信息封装进去
		User newIndatabUser = new User();
		String newIndatabUseruname = user.getUsername();
		String newIndatabUserpwd = user.getPassword();
		newIndatabUser.setUsername(newIndatabUseruname.trim());
		//密码使用md5进行加密
		newIndatabUser.setPassword(MyMD5EncodeUtil.useMd5EncodePlainText(newIndatabUserpwd.trim()));
		newIndatabUser.setNickname(newIndatabUseruname);
		newIndatabUser.setStatu("1");
		newIndatabUser.setEmail(user.getEmail());
		Date newIndatabUserdate = new Date();
		newIndatabUser.setRegist_time(sdf.format(newIndatabUserdate));
		if (newIndatabUser.checkUserErrorData() != null) {
			throw new RegistParamsIllegelExcep(user.checkUserErrorData());
		}
		// 检查用户名是否已经被注册过了
		User isexitsU = userDao.getByUsername(newIndatabUseruname);
		if (isexitsU != null) {
			throw new RegistParamsIllegelExcep("用户名已被使用！");
		}
		userDao.addNewUser(newIndatabUser);// 添加用户到数据库中
	}
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void updU(User user){
		userDao.update(user);//修改信息
	}
	
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void add_address(User_address address) throws UserAddressException{
		DetachedCriteria dc = DetachedCriteria.forClass(User_address.class);
		dc.add(Restrictions.eq("user_id", address.getUser_id()));
		List list = userDao.get(dc);
		if (list!=null && list.size() >=10) {
			throw new UserAddressException("添加失败，收货地址超过10个！");
		}
		if(address.getAddress().length() > 200){
			throw new UserAddressException("收货地址超过200个字符！");
		}
		userDao.add_address(address);
	}
	
	
	
	public List<Book> getMallBooks(User u){
		return null;
		
	}
	
	
	@Override
	public List<JiesuanPage> generatorOrder(String products) throws BookCountNotEnoughException, BookException{
		System.out.println("UserServiceImpl.generatorOrder...................");
			String [] product_items = products.split("@");
			List<JiesuanPage> list = new ArrayList<JiesuanPage>();			
		for (int i = 0; product_items!=null&& i < product_items.length; i++) {
			//获取每一条目中的数据【0】是商品id，【1】是数量，【3】是购物车id（可以为空）
			String [] infos = product_items[i].split(",");
			JiesuanPage item = new JiesuanPage();
			//product_id
			item.setProduct_id(Long.valueOf(infos[0]));
			//count
			item.setCount(Long.valueOf(infos[1]));
			//mallcar_id
			if(infos.length >= 3){
				item.setMallcar_id(Long.valueOf(infos[2]));
			}
			//把条目信息添加到order中
			DetachedCriteria dc = DetachedCriteria.forClass(Book.class);
			dc.add(Restrictions.eq("book_id", item.getProduct_id()));
			//得到相应的id的信息
			Book one = (Book) userDao.getOne(dc);
			if(one == null){
				throw new BookException("书本已经下架！");
			}
			//判断库存
			if (one.getCount() == null || item.getCount() > one.getCount()) {
				throw new BookCountNotEnoughException( "书本："+one.getBook_name()+"，库存不足！");
			}
			item.setPrice(one.getPrice());
			item.setDiscount(one.getDiscount());
			item.setProduct_name(one.getBook_name());
			item.setAuthor(one.getAuthor());
			item.setImg(one.getImg());
			item.setPublishing(one.getPublishing());
			item.calMoney();
			list.add(item);
		}
		System.out.println("list"+list.toString());
		return list;
	}

	@Override
	public List<User_address> getAddressList(Long user_id) {
		
		String hql = "from com.le.ebook.domain.User_address where user_id=?";
		
		List<User_address> list = userDao.get(hql, new Object[]{user_id});
		
		return list;
	}

	@Override
	public User_address getAddressOneById(Long user_address_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(User_address.class);
		
		dc.add(Restrictions.eq("user_address_id", user_address_id));
				
		return (User_address) userDao.getOne(dc);
	}


	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void delete_address(User_address user_address) {
		
		userDao.deleteObj(user_address);
		
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void update_address(User_address user_address) {
		
		userDao.updateObj(user_address);
		
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void addOrder(Order order) throws BookCountNotEnoughException, OrderException {
		userDao.addOrder(order);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void delete_mallcar(Long mallcar_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(MallCar.class);
		dc.add(Restrictions.eq("mallcar_id", mallcar_id));
		MallCar one = (MallCar) userDao.getOne(dc);
		if(one!=null)
		userDao.deleteObj(one);
		
	}

	@Deprecated
	@Override
	public List<Order> getOrderList(Long user_id) {
		return this.getOrderList(user_id, null);
	}
	
	
	@Override
	public MyPageBean getOrderPageBean(Integer page,Integer page_size,String statu) throws UserIsUnLoginExcp {
		User user = getBookStroreLoginedUse();
		if(user == null){
			throw new UserIsUnLoginExcp("用户未登录");
		}
		if(statu == null || "".equals(statu)){
			statu = "";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.and(Restrictions.like("statu", "%"+statu+"%"), Restrictions.eq("user_id", user.getUser_id())));
		dc.addOrder(org.hibernate.criterion.Order.desc("time"));
		dc.setProjection(Projections.rowCount());
		//获取总条目数
		Integer totalCount = userDao.getTotalCount(dc);
		MyPageBean pageBean = new MyPageBean(page, page_size, totalCount);
		List list = userDao.get(dc, pageBean.getStartIndex(),pageBean.getPage_size());
		pageBean.setList(list);
		return pageBean;		
	}

	@Override
	public Order getOrder(Long order_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.eq("order_id", order_id));	
		return (Order) userDao.getOne(dc);
	}
	
	@Override
	public List<Order> getOrderList(Long user_id,Object[] order) {
		String hql = " from com.le.ebook.domain.Order where user_id=?";
		
		if(order!=null && order.length>=2){
			hql+=" order by "+order[0].toString()+" "+order[1].toString();
		}else{
			hql+=" order by time desc";
		}
		
		List<Order> list = userDao.get(hql, new Object[]{user_id});
		
		return list;
	}

	@Override
	public boolean checkExistsOrderByUUCode(String uu_code) {
		String hql = " from com.le.ebook.domain.Order where uu_code=?";
		List<Object> list = userDao.get(hql, new Object[]{uu_code});
		if (list!=null && list.size() >= 1) {
			return true;
		}
		return false;
		
	}

	@Override
	public User getUByUname(String username) {
	String hql = "from com.le.ebook.domain.User where username=?";
		
		List<User> list = userDao.get(hql,new Object[]{username});
		
		User u = null;
		
		if(list!=null && list.size() >= 1){
			u = list.get(0);
		}
		return u;
	}

	@Override
	public void sendActiveCode(String toEmail, String username) throws SendCodeIllegalExcep, AddressException, MessagingException {
		System.out.println("sendActiveCode.......");
		if(username ==null ||"".equals(username)){
			throw new SendCodeIllegalExcep("用户名为空");
		}
		if(toEmail == null || "".equals(toEmail)){
			throw new SendCodeIllegalExcep("邮箱为空");
		}
		
		User user = getUByUname(username);
		
		if(user == null){
			throw new SendCodeIllegalExcep("用户不存在");
		}
		
		if(user!=null && toEmail.equals(user.getEmail())){
			String str = Rand.genRandCodeStr();
			String content = "用户："+username+"\n您好！您的激活验证码是"+str+",请您妥善保管，不要告诉他人，以免个人信息的泄露！";
			System.out.println("conten="+content);
			MySendRandCode2EamilUtil.sendANewMsg(toEmail, content, "网上图书商城用户激活验证码");
			
			ScopePutAndGetFromValStackUtil.setAnObj2SessionScope(username+"_active_check_code", str);
			
		}else{
			throw new SendCodeIllegalExcep("邮箱不匹配");
		}
		
	}
	@Override
	public void sendResetPwdCode(String chapwd2ema, String chapwd_uname) throws SendCodeIllegalExcep, AddressException, MessagingException {
		System.out.println("sendResetPwdCode.......");
		//检查用户名。
		if(chapwd_uname ==null ||(chapwd_uname).equals("")){
			throw new SendCodeIllegalExcep(ERR_UNAME_EMP_MSG);
		}
		//检查邮箱
		if(chapwd2ema == null ||(chapwd2ema).equals("")){
			throw new SendCodeIllegalExcep(ERR_EMA_EMP_MSG);
		}
		//获得用户。
		User chpwdU = getUByUname(chapwd_uname);
		//用户是否存在。
		if(chpwdU == null){
			throw new SendCodeIllegalExcep(ERR_U_UEXT_MSG);
		}
		if(chpwdU!=null && chapwd2ema.equals(chpwdU.getEmail())){
			//取到6位随机数。
			String ranCo = Rand.genRandCodeStr();
			String content = "用户："+chapwd_uname+"\n您好！您重置密码的验证码是"+ranCo+",请您妥善保管，不要告诉他人，以免个人信息的泄露！";
			MySendRandCode2EamilUtil.sendANewMsg(chapwd2ema, content, "网上图书商城用户重置验证码");
			ScopePutAndGetFromValStackUtil.setAnObj2SessionScope(chapwd_uname+"_reset_pwd_check_code", ranCo);
		}else{
			throw new SendCodeIllegalExcep(ERR_EMA_EMA1_MSG);
		}
	}
	

	
	public void sendRegistCode(String toEmail, String username) throws SendCodeIllegalExcep, AddressException, MessagingException {
		System.out.println("sendRegistCode.......");
		if(toEmail == null || "".equals(toEmail)){
			throw new SendCodeIllegalExcep("邮箱为空");
		}
		User user = getUByUname(username);
		if(user != null){
			throw new SendCodeIllegalExcep("用户已存在");
		}
			String str = Rand.genRandCodeStr();
			String content ="您好！您注册用户的验证码是"+str+",请您妥善保管，不要告诉他人，以免个人信息的泄露！";
			System.out.println("conten="+content);
			MySendRandCode2EamilUtil.sendANewMsg(toEmail, content, "网上图书商城用户注册验证码");
			ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("regist_check_code", str);
	}
	
	
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void activeUser(String username, String email, String checkcode) throws IllgalParamsExcep {
		
		System.out.println("sendActiveCode.......");
		
		if(username ==null ||"".equals(username)){
			throw new IllgalParamsExcep("用户名为空");
		}
		if(email == null || "".equals(email)){
			throw new IllgalParamsExcep("邮箱为空");
		}
		
		User user = getUByUname(username);
		
		if(user == null){
			throw new IllgalParamsExcep("用户不存在");
		}
		
	
		if(user!=null && email.equals(user.getEmail())){
			
		}else{
			throw new IllgalParamsExcep("邮箱不匹配");
		}
		
		String active_check_code = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope(username+"_active_check_code");
		 ScopePutAndGetFromValStackUtil.remAnObjFromSessionScope(username+"_active_check_code");
		if(active_check_code!= null && active_check_code.equals(checkcode)){
			userDao.update(user);
		}else{
			throw new IllgalParamsExcep("验证码不正确");
		}
		
		
	}

	//验证邮箱后进行重置密码
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void resetUserPwd(String reset_pwd_uname, String reset_ped_new_password, String reser_pwd_ema, String rpwdchcode) throws IllgalParamsExcep {
		System.out.println("resetUserPwd.......");
		//检查从浏览器上传的数据是否合法
		if(reset_pwd_uname ==null ||(reset_pwd_uname).equals("")){
			throw new IllgalParamsExcep(ERR_UNAME_EMP_MSG);
		}
		if(reser_pwd_ema == null || (reser_pwd_ema).equals("")){
			throw new IllgalParamsExcep(ERR_EMA_EMP_MSG);
		}
		if(reset_ped_new_password ==null ||(reset_ped_new_password).equals("")){
			throw new IllgalParamsExcep(ERR_PWD_EMP_MSG);
		}
		//通过用户名查找用户
		User ubyuname = getUByUname(reset_pwd_uname);
		//判断是否存在该用户
		if(ubyuname == null){
			throw new IllgalParamsExcep(ERR_U_UEXT_MSG);
		}
		//检查用户输入的邮箱和后台用户数据中的邮箱是否匹配
		if(ubyuname!=null && reser_pwd_ema.equals(ubyuname.getEmail())){
			
		}else{
			throw new IllgalParamsExcep(ERR_EMA_EMA1_MSG);
		}
		//获取session中的验证码和用户输入的验证码进行比较
		String reset_code_ses = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope(reset_pwd_uname+"_reset_pwd_check_code");
		 ScopePutAndGetFromValStackUtil.remAnObjFromSessionScope(reset_pwd_uname+"_reset_pwd_check_code");
		if(reset_code_ses!= null && reset_code_ses.equals(rpwdchcode)){
			//验证码正确进行重置密码操作
			//加密密码，使用MD5方式加密
			ubyuname.setPassword(MyMD5EncodeUtil.useMd5EncodePlainText(reset_ped_new_password));
			//重置密码操作
			userDao.update(ubyuname);
		}else{
			//验证码不匹配，抛出异常
			throw new IllgalParamsExcep(ERR_CHE_MSG);
		}
	}
	//发送验证码到旧邮箱中，如果存在旧邮箱的话
	@Override
	public void sendChangeEmail2OldEmail(String email, User user) throws IllgalParamsExcep ,MessagingException{
		if(!user.getEmail().equals(email)){//检查输入的邮箱和后台保存的旧邮箱是否相匹配
			//两个邮箱，抛出邮箱不匹配
			throw new IllgalParamsExcep("邮箱不匹配");
		}
		String ranCode = Rand.genRandCodeStr();//生成随机6位数
		String uname = user.getUsername();
		String ema = user.getEmail();
		//定义一个需要发送到邮箱的内容的字符串
		String content = "用户："+uname+"\n您好！您的修改邮箱验证码是"+ranCode+",请您妥善保管，不要告诉他人，以免个人信息的泄露！";
		//发送这个随机验证码消息到用户旧邮箱中
		MySendRandCode2EamilUtil.sendANewMsg(ema, content, "网上图书商城用户修改邮箱验证码");
		//把验证码放到session域中，用户进行下一步的比较
		ScopePutAndGetFromValStackUtil.setAnObj2SessionScope(uname+"_change_email_2old_email_code", ranCode);
	}
	//发送验证码到新邮箱中，验证邮箱是否正确和邮箱是否归属于 用户
	@Override
	public void sendChangeEmail2NewEmail(String ema_addr, User user) throws IllgalParamsExcep ,MessagingException{
		if(ema_addr == null || ema_addr.equals("")){//进行新的邮箱的格式的检测,这是邮箱为空执行接下来的操作
			throw new IllgalParamsExcep("邮箱为空！");//邮箱是空值，抛出异常
		}
		else if(ema_addr!=null && ema_addr.matches(MySendRandCode2EamilUtil.BOOK_STORE_CORRECT_EMAIL_REG )){
		//邮箱格式正确且不为空，可以执行发送随机的验证码到新邮箱的操作
		}else{
			//邮箱格式错误，抛出邮箱格式不正确的异常
			throw new IllgalParamsExcep("邮箱格式错误!");
		}
		String uname = user.getUsername();
		String ranCode = Rand.genRandCodeStr();//获得随机6位数码
		//拼接需要发送随机的验证码消息到新邮箱的内容字符串
		String content = "用户："+uname+"\n您好！您的修改邮箱新邮箱验证码是"+ranCode+",请您妥善保管，不要告诉他人，以免个人信息的泄露！";
		//发送随机生成的验证码消息到用户的新邮箱中
		MySendRandCode2EamilUtil.sendANewMsg(ema_addr, content, "网上图书商城用户新邮箱验证码");
		//把随机的验证码放到session域中，用户进行下一步的比较
		ScopePutAndGetFromValStackUtil.setAnObj2SessionScope(uname+"_change_email_2new_email_code", ranCode);
	}
	
	@Override
	public void check_changeEmailOldCode(String checkcode, User user)
			throws UserIsUnLoginExcp, IllgalParamsExcep {
		if (user == null) {
			throw new UserIsUnLoginExcp("用户未登录！");
		}
		// 判断验证码是否为空
		if (checkcode == null || "".equals(checkcode)) {
			throw new IllgalParamsExcep("验证码为空!");
		}
		// 验证码不为空
		// 获取session中的验证码，用来和用户上传的验证码比较
		String native_code = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope(user.getUsername() + "_change_email_2old_email_code");
		ScopePutAndGetFromValStackUtil.remAnObjFromSessionScope(user.getUsername() + "_change_email_2old_email_code");
		// 两个验证码是否相等
		if (!checkcode.equals(native_code)) {
			// 不等
			throw new IllgalParamsExcep("验证码不正确!");
		}
		//相等

	}
	
	
	//获取登录的用户
	@Override
	public User getBookStroreLoginedUse(){
		User logined = (User) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope("loginUser");
		return logined;
		
	}
	
	
	//刷新登录用户信息
	@Override
	public User refreshLoginUser() throws UserNoActiveException, UserIsUnLoginExcp, UserPwdOrUsernameWrongErrException{
		User logined = getBookStroreLoginedUse();
		User tempUser = null;
		if(logined==null){
			throw new UserIsUnLoginExcp("用户未登录!");
		}
		tempUser = findOnUserByUsernameAndPassword(logined);
		ScopePutAndGetFromValStackUtil.setAnObj2SessionScope("loginUser", tempUser);
		return tempUser;
		
	}

	//修改用户邮箱方法
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void check_changeEmailNewCodeAndDoChange(String c_code_user_in, String book_store_cha_ema__new_email, User user) throws UserIsUnLoginExcp, IllgalParamsExcep {
		if (user == null) {
			throw new UserIsUnLoginExcp(ERR_UUL_MSG);
		}
		// 判断用户输入的验证码是否是空值null或空字符串
		if (c_code_user_in == null || c_code_user_in.equals("")) {
			throw new IllgalParamsExcep(ERR_CHE_EMPT_MSG);
		}
		// 用户输入的验证码是不为空值null或空字符串的
		// 获取系统后台保存在session中的随机验证码，用来和用户上传的验证码进行比较
		String native_code = (String) ScopePutAndGetFromValStackUtil.getAnObjFromSessionScope(user.getUsername() + "_change_email_2new_email_code");
		ScopePutAndGetFromValStackUtil.remAnObjFromSessionScope(user.getUsername() + "_change_email_2new_email_code");
		// 进行检验两个验证码是否是相一致的
		if (!c_code_user_in.equals(native_code)) {
			// 进入到这里代表是不一致的
			throw new IllgalParamsExcep(ERR_CHE_MSG);
		}
		//相一致
		//接下来检查用户输入的新邮箱格式的正确性
		if(book_store_cha_ema__new_email == null || book_store_cha_ema__new_email.equals("")){
			//检查用户输入的邮箱的格式是错误的，邮箱为空
			throw new IllgalParamsExcep(ERR_NEMA_EMP_MSG);
		}
		else if(book_store_cha_ema__new_email!=null && book_store_cha_ema__new_email.matches(MySendRandCode2EamilUtil.BOOK_STORE_CORRECT_EMAIL_REG)){
			//邮箱格式是正确的格式
		}else{//邮箱格式错误
			throw new IllgalParamsExcep(ERR_NEMA_FMA_MSG);
		}
		//格式正确，可以进行下一步的操作
		//接下来要进行修改邮箱操作
		user.setEmail(book_store_cha_ema__new_email);
		updU(user);
	}
	
	//用户登录service
	@Override
	public User login(User user) throws UserPwdOrUsernameWrongErrException{
		User exist_user_logined = null;
		exist_user_logined = findOnUserByUsernameAndPassword(user);//检查用户名和密码是否正确
		return exist_user_logined;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void rePayOrder(Long order_id, String pay_way) throws UserIsUnLoginExcp, OrderException {
		User user = getBookStroreLoginedUse();
		if (user == null) {
			throw new UserIsUnLoginExcp("用户未登录！");
		}
		
		Order order = getOrder(order_id);
		//查看订单是否存在
		if (order == null) {
			throw new OrderException("订单不存在！");
		}
		
		if(!"0".equals(order.getStatu())){
			throw new OrderException("订单已经支付过了！");
		}
		if (pay_way == null || "".equals(pay_way)) {
			
		}else{
			order.setPay_way(pay_way);
		}
		order.setStatu("1");
		userDao.updateObj(order);
		//支付成功
		
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

	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public Order payOrder(String pay_name, String pay_address, String pay_phone,String pay_memo, String pay_way,
			String pay_uu_code, String pay_products) throws BookCountNotEnoughException, BookException, UserIsUnLoginExcp, OrderException {
		User bokkStoreLoginedUser =getBookStroreLoginedUse();//获取登录用户转台
		if (bokkStoreLoginedUser == null) {
			//用户未登录
		throw new UserIsUnLoginExcp("用户未登录！");
		}
		if (pay_uu_code == null || pay_uu_code.length() == 0) {
			throw new OrderException("无效的订单！");
		}
		if(checkExistsOrderByUUCode(pay_uu_code)){
			throw new OrderException("请不要重复提交订单！");
		}
		//声明新order对象
		Order order = new Order();
		order.setName(pay_name);//订单收货人姓名
		order.setPhone(pay_phone);//订单收货人联系电话
		order.setAddress(pay_address);//订单收货人地址
		order.setUu_code(pay_uu_code);//订单唯一的表单码
		order.setUsername(bokkStoreLoginedUser.getUsername());//订单用户名
		List<JiesuanPage> list;
			list = generatorOrder(pay_products);
			for (int i = 0; list!=null &&i < list.size(); i++) {
				JiesuanPage page = list.get(i);
				Order_item oi = new Order_item();//每一项订单条目
				oi.setCount(page.getCount());//每个书籍数量
				oi.setMallcar_id(page.getMallcar_id());
				oi.setProduct_id(page.getProduct_id());//书籍id
				oi.setPrice(page.getPrice());//书籍单价
				oi.setDiscount(page.getDiscount());
				oi.calMoney();//书籍总价小计
				oi.setOrder(order);//设置给订单
				oi.setProduct_name(page.getProduct_name());
				oi.setProduct_img(page.getImg());//书籍图片
				order.getItems().add(oi);
			}
		
			Date bookStorePayOrderTime = new Date();
			order.setOrder_no(bokkStoreLoginedUser.getUser_id()+""+bookStorePayOrderTime.getTime());
			order.setTime(sdf.format(bookStorePayOrderTime));
			order.setMomo(pay_memo);//设置给卖家留言
			order.setUser_id(bokkStoreLoginedUser.getUser_id());
			order.setPay_way(pay_way);//设置支付方式
			order.calSumMoney();//计算订单总价
			order.calCount();//计算书籍总数量
			order.setStatu("1");
			order.setStatu0("1");
			return order;
	}

	
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public Order payAndSaveOrder(String pay_name, String pay_address, String pay_phone,String pay_memo, String pay_way,
			String pay_uu_code, String pay_products) throws BookCountNotEnoughException, BookException, UserIsUnLoginExcp, OrderException {
		User bokkStoreLoginedUser =getBookStroreLoginedUse();//获取登录用户转台
		if (bokkStoreLoginedUser == null) {
			//用户未登录
		throw new UserIsUnLoginExcp("用户未登录！");
		}
		if (pay_uu_code == null || pay_uu_code.length() == 0) {
			throw new OrderException("无效的订单！");
		}
		if(checkExistsOrderByUUCode(pay_uu_code)){
			throw new OrderException("请不要重复提交订单！");
		}
		Order order = new Order();//声明新order对象
		order.setName(pay_name);//订单收货人姓名
		order.setPhone(pay_phone);//订单收货人联系电话
		order.setAddress(pay_address);//订单收货人地址
		order.setUu_code(pay_uu_code);//订单唯一的表单码
		order.setUsername(bokkStoreLoginedUser.getUsername());//订单用户名
		List<JiesuanPage> list;
			list = generatorOrder(pay_products);
			for (int i = 0; list!=null &&i < list.size(); i++) {
				JiesuanPage page = list.get(i);
				Order_item oi = new Order_item();//每一项订单条目
				oi.setCount(page.getCount());//每个条目想的书籍数量
				oi.setMallcar_id(page.getMallcar_id());
				oi.setProduct_id(page.getProduct_id());//书籍id
				oi.setPrice(page.getPrice());//书籍单价
				oi.setDiscount(page.getDiscount());
				oi.calMoney();//书籍总价小计
				oi.setOrder(order);//设置给订单
				oi.setProduct_name(page.getProduct_name());
				oi.setProduct_img(page.getImg());//书籍图片
				order.getItems().add(oi);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date bookStorePayOrderDate = new Date();
			order.setOrder_no(bokkStoreLoginedUser.getUser_id()+""+bookStorePayOrderDate.getTime());
			order.setTime(sdf.format(bookStorePayOrderDate));
			order.setMomo(pay_memo);//设置给卖家留言
			order.setUser_id(bokkStoreLoginedUser.getUser_id());
			order.setPay_way(pay_way);//设置支付方式
			order.calSumMoney();//计算订单总价
			order.calCount();//计算书籍总数量
			order.setStatu("1");
			order.setStatu0("1");
			userDao.addOrder(order);
			return order;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void returnOrderGoodSer(Long order_id,String state) throws UserIsUnLoginExcp, OrderStateErrorException{
		User bookStoreLoginedUser = getBookStroreLoginedUse();
		//check 用户是否已经登录了
		if(bookStoreLoginedUser == null){
			throw new  UserIsUnLoginExcp("用户未登录！");
		}
		if(!"5".equals(state) && !"6".equals(state)){
			throw new OrderStateErrorException("订单状态错误，您的操作有误！");
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.eq("order_id", order_id));
		Order one = (Order) userDao.getOne(dc);
		if("6".equals(state)){
			one.setStatu(one.getStatu0());
		}
		if("5".equals(state)){
			one.setStatu("5");
		}
		userDao.updateObj(one);
	}
}
