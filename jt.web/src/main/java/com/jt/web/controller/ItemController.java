package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//商品详情 http://www.jt.com/items/1861098.html
	//返回item对象需要特殊属性，图片数组，images
	@RequestMapping("{itemId}")
	public String item(@PathVariable Long itemId, Model model) throws Exception{
		//获取当前商品的信息
		Item item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		
		return "item";
	}
	
}
