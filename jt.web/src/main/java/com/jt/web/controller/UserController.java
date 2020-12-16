package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	public static String COOKIE_NAME = "JT_TICKET";
	
	//转向注册页面
	@RequestMapping("register")
	public String register(){
		return "register";
	}
	
	//转向登录页面
	@RequestMapping("login")
	public String login(){
		return "login";
	}

	//注册/service/user/doRegister
	@RequestMapping("doRegister")
	@ResponseBody
	public SysResult doRegister(User user) throws Exception{
		return userService.doRegister(user);
	}
	
	//登录 /service/user/doLogin
	@RequestMapping("doLogin")
	@ResponseBody
	public SysResult doLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String ticket = userService.doLogin(username, password);
		
		//要把ticket值写入cookie，这样下次访问就可以从cookie中获取
		String cookieName = "JT_TICKET";		//只能写死
		CookieUtils.setCookie(request, response, cookieName, ticket);
		
		return SysResult.ok();
	}
}