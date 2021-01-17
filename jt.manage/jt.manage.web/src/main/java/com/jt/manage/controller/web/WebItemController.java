package com.jt.manage.controller.web;

import manage.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemService;

@Controller
public class WebItemController
{
    @Autowired
    private ItemService itemService;

    //根据商品id来获取商品信息 "http://manage.jt.com/web/item/19384383"
    @RequestMapping("/web/item/{itemId}")
    @ResponseBody
    public Item getItemById(@PathVariable Long itemId)
    {

        return itemService.queryById(itemId);
    }
}
