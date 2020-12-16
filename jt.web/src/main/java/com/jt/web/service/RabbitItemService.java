package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.RedisService;

@Service
public class RabbitItemService {
	@Autowired
	private RedisService redisService;
	
	public void updateItem(Long itemId){
		//从redis中删除itemId商品缓存
		redisService.del("JT_ITEM_"+itemId);
	}
}
