package com.jt.web.service;

import com.jt.common.service.RedisService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

@Service
public class ItemService {
	@Autowired
	private HttpClientService httpClientService;

	@Autowired
	private RedisService redisService;
	//jackson json提供工具类
	private static ObjectMapper MAPPER = new ObjectMapper();

	@PropertyConfig                //spring注入
	private String MANAGE_URL;	//这个名字必须和properties文件中定义key一致
	
	//根据itemId获取当前商品信息
	public Item getItemById(Long itemId) throws Exception {
		//发起http请求，请求后台系统 http://manage.jt.com/web/item/19384383
				String url = MANAGE_URL+"/web/item/"+itemId;

				//Redis1.从缓存中获取数据
				String ITEM_KEY = "JT_ITEM_"+itemId;	//redis中key
				String redisData = redisService.get(ITEM_KEY);
				if(!StringUtils.isNullOrEmpty(redisData)){
					Item item = MAPPER.readValue(redisData, Item.class);
					return item;	//如果缓存中有数据，直接返回
				}

				String jsonData = httpClientService.doGet(url, "utf-8");
				//将一个json串转换成一个Java对象
				try{
					Item item = MAPPER.readValue(jsonData, Item.class);

					//Redis2.写缓存
					redisService.set(ITEM_KEY, jsonData);

					return item;
				}catch(Exception e){
					return null;
				}
	}

}
