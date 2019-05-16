package com.le.ebook.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.le.ebook.utils.VerifyCode;

public class VerifyCodeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			VerifyCode verifyCode = new VerifyCode();
			//生成图片
			BufferedImage codeImg = verifyCode.getImage();
			//获取图片中的文字
			String codeStr = verifyCode.getText();
			
			System.out.println(codeStr);
			req.getSession().removeAttribute("codeStr");
			req.getSession().setAttribute("codeStr", codeStr);
			
			verifyCode.output(codeImg, resp.getOutputStream());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
