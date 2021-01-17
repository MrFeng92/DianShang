package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	
	//进行校验：username/phone/email
	//http://sso.jt.com/user/check/{param}/{type}
	@RequestMapping("check/{param}/{type}")
	@ResponseBody
	public SysResult check(@PathVariable String param,@PathVariable Integer type){
		return userService.check(param, type);
	}
	
	//用户注册 http://sso.jt.com/user/register
	@RequestMapping("register")
	@ResponseBody
	public SysResult register(User user){
		return userService.register(user);
	}
	
	//用户登录 http://sso.jt.com/user/login
	@RequestMapping("login")
	@ResponseBody
	public SysResult login(String u, String p) throws JsonProcessingException{
		return userService.login(u, p);
	}
	
	//按ticket进行查询 http://sso.jt.com/user/query/{ticket}
	@RequestMapping("query/{ticket}")
	@ResponseBody
	public SysResult queryByTicket(@PathVariable String ticket){
		return userService.queryByTicket(ticket);
	}
}
