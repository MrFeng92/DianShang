package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserService {
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper MAPPER = new ObjectMapper();

	//注册
	public SysResult doRegister(User user) throws Exception {
		//发起一个http请求，请求sso登录
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		String jsonData = httpClientService.doPost(url, params, "utf-8");
		//只获取返回的jsonData中的data属性值
		JsonNode node = MAPPER.readTree(jsonData);
		String data = node.get("data").asText();		//变成文本
		
		return SysResult.ok(data);
	}

	//登录
	public String doLogin(String username, String password) throws Exception {
		String url = "http://sso.jt.com/user/login";
		Map<String,String> params = new HashMap<String,String>();
		params.put("u", username);
		params.put("p", password);
		
		String jsonData = httpClientService.doPost(url, params, "utf-8");
		//将ticket值存放到cookie
		JsonNode node = MAPPER.readTree(jsonData);
		String ticket = node.get("data").asText();	//获取sso返回对象中的ticket
		
		return ticket;
	}

}
