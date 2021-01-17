package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;

@Controller    //为前台系统商品分类展现
public class WebItemCatListController
{
    @Autowired
    private ItemCatService itemCatService;

    //http://manage.jt.com/web/itemcat/all?callback=category.getDataService
    @RequestMapping("/web/itemcat/all")
    @ResponseBody
    public ItemCatResult ItemCatAll()
    {
        return itemCatService.getItemCatResult();
    }
}
