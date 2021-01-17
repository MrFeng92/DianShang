package com.jt.manage.controller;

import com.jt.manage.service.ItemCatService;
import manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("item/cat")
public class ItemCatController
{
    @Autowired
    private ItemCatService itemCatService;

    //查询所有 http://localhost:8081/itemcat/queryAll
    @RequestMapping("queryAll")
    @ResponseBody    //这个注解在返回对象后，springmvc会将其转换为json串
    public List<ItemCat> queryAll()
    {
        return itemCatService.queryAll();
    }

    @RequestMapping("list")
    @ResponseBody
    public List<ItemCat> queryListById(@RequestParam(defaultValue = "0") Long id)
    {
        //SELECT * FROM tb_item_cat WHERE parent_id=0 AND STATUS=1
        ItemCat param = new ItemCat();
        param.setParentId(id);
        param.setStatus(1);            //1正常2删除
        return itemCatService.queryListByWhere(param);
    }
}
