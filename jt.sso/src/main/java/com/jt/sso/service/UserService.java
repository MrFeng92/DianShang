package com.jt.sso.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.SysResult;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisService redisService;
	private static ObjectMapper MAPPER = new ObjectMapper();
	
	//检查用户名、电话、邮箱唯一性
	public SysResult check(String param, Integer type) {
		String typename = "";
		if(type==1){
			typename = "username";
		}else if(type==2){
			typename = "phone";
		}else if(type==3){
			typename = "email";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typename", typename);
		map.put("param", param);
		
		int countNum = userMapper.check(map);
		if(countNum>0){
			return SysResult.ok(true);
		}else{
			return SysResult.ok(false);
		}
	}
	
	//用户注册
	public SysResult register(User user){
		String password = user.getPassword();
		user.setPassword(DigestUtils.md5Hex(password));	//MD5加密
		user.setEmail(user.getPhone());	//把其值设置一下。
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		try{
			userMapper.insertSelective(user);
			return SysResult.ok(true);
		}catch(Exception e){
			return SysResult.ok(false);
		}
	}
	
	//用户登录
	public SysResult login(String username, String password) throws JsonProcessingException{
		User param = new User();
		param.setUsername(username);	//习惯权限获取时只按username查询
		
		List<User> userList = userMapper.select(param);
		//获取当某个用户
		if(userList!=null && userList.size()>0){
			User curUser = userList.get(0);
			password = DigestUtils.md5Hex(password);	//md5加密
			if(curUser.getPassword().equals(password)){
				//构建一个ticket
				String JT_TICKET = DigestUtils.md5Hex("JT_TICKET_" + username + System.currentTimeMillis());
				//将ticket写入到redis中
				redisService.set(JT_TICKET, MAPPER.writeValueAsString(curUser));
				
				return SysResult.ok(JT_TICKET);
			}
		}
		return SysResult.build(201, "查询此用户不存在，用户名或密码不正确!");
	}
	
	//按ticket从redis中获取当前用户json串
	public SysResult queryByTicket(String ticket){
		String userJson = redisService.get(ticket);
		return SysResult.ok(userJson);
	}
}
