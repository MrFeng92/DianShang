package com.jt.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.web.controller.UserController;
import com.jt.web.pojo.User;
import com.jt.web.threadlocal.UserThreadlocal;

//拦截所有/cart/**请求
public class CartInterceptor implements HandlerInterceptor{
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper MAPPER = new ObjectMapper();

	@Override	//在controller处理之前
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从cookie中获取ticket
		String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_NAME);
		if(null != ticket){
			//访问SSO的业务接口
			String url = "http://sso.jt.com/user/query/"+ticket;
			String jsonData = httpClientService.doGet(url);	//当前user对象json值
			if(null == jsonData){
				UserThreadlocal.set(null);
			}else{
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				JsonNode userJsonNode = jsonNode.get("data");
				
				User curUser = MAPPER.readValue(userJsonNode.asText(), User.class);	//转换json串为当前user对象
				UserThreadlocal.set(curUser);	//cookie,redis都有值，才设置到ThreadLocal对象中
			}
		}else{
			UserThreadlocal.set(null);
		}
		
		return true;	//true放行，false不放行
	}

	@Override	//在contrller处理之后
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override	//在controller处理后并完成渲染
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
