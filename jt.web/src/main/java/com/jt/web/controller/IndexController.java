package com.jt.web.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.service.HttpClientService;

@Controller
public class IndexController {
	//声明一个log对象
	private static Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	private HttpClient httpClient;
	
	@Autowired
	private HttpClientService httpClientService;
	
	//转向index.jsp 
	/*
	 * 浏览器请求url http://www.jt.com/index.html
	 * springmvc处理完映射路径：/index
	 * 
	 * 浏览器请求url http://www.jt.com/service/save/100
	 * springmvc处理完映射路径：/save/100
	 */
	
	@RequestMapping("/index")
	public String index() throws Exception{
		//HttpGet get = new HttpGet("http://www.baidu.com");
		//httpClient.execute(get);
		
//		String url = "http://www.baidu.com";
//		String result = httpClientService.doGet(url);
//		log.debug(result);
		
		return "index";
	}
}
