package com.le.ebook.utils;
/*
 * javaMail
 * 
 */

import java.util.Date;


import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MySendRandCode2EamilUtil {
	public static String BOOK_STORE_CORRECT_EMAIL_REG = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
	
	public static final String CTNT_TP_HTM_UT8 =  "text/html;charset=utf-8";
	public static final String HOST = "smtp.163.com";
    public static final String PROTOCOL = "smtp";  
    public static final int P0RT = 25;  //ssl :465/994  非ssl: 25
    public static final String FR0M_EMA = "lemon_room@163.com";//发件人的email
    public static final String PWD = "lemonzishan163";//网易邮箱的客户端授权密码

    /**
     * 获取Session
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.store.protocol" , PROTOCOL);//设置协议
        props.put("mail.smtp.port", P0RT);//设置端口
        props.put("mail.smtp.auth" , true);

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FR0M_EMA, PWD);
            }

        };
        
        Session session = Session.getDefaultInstance(props , authenticator);
        session.setDebug(true);

        return session;
    }
    /**
     * 发送信息到邮箱中
     * @param send2EmailAddr 目标邮箱
     * @param ctent 需要发送的内容
     * @param send2EmallTitle 邮件的标题
     * @throws AddressException 邮箱地址错误异常
     * @throws MessagingException 发送邮件异常
     */
    public static void sendANewMsg(String send2EmailAddr , String ctent,String send2EmallTitle) throws AddressException, MessagingException {
        	//获取发送邮件对象。且对象已有来源。
    		Message msg = newMsg();
            //需要发送的邮件的邮箱。
            msg.setRecipients(Message.RecipientType.TO, getIAddr(send2EmailAddr));
            //设置邮件的标题。
            msg.setSubject(send2EmallTitle);
            //设置发送邮件的时间
            msg.setSentDate(new Date());
            //设置邮件的内容
            msg.setContent(ctent ,CTNT_TP_HTM_UT8);
            //开始发送邮件
            Transport.send(msg);
    }
    
    /**
     * 获取Message对象
     * @return
     * @throws MessagingException 
     * @throws AddressException 
     */
    public static Message newMsg() throws AddressException, MessagingException{
    	Session sendMsg2EmailSession = getSession();
        // 初始化发送邮件对象
        Message msg = new MimeMessage(sendMsg2EmailSession);
        msg.setFrom(new InternetAddress(FR0M_EMA));
        return msg;
    }
    
    /**
     * 封装发送toEmail地址
     * @param addr
     * @return
     * @throws AddressException
     */
    public static InternetAddress[] getIAddr(String addr) throws AddressException{
    	  InternetAddress[] addr4SendMsg2Emails = {new InternetAddress(addr)};
        return addr4SendMsg2Emails;
    }
}



