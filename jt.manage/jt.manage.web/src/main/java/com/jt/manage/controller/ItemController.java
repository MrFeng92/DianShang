package com.jt.manage.controller;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ItemService;
import manage.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("item")
public class ItemController
{
    @Autowired
    private ItemService itemService;

    //商品列表 item/query
    @RequestMapping("query")
    @ResponseBody    //EasyUIDataGrid要求返回结构
    public EasyUIResult queryItemList(Integer page, Integer rows)
    {        //分页
        return itemService.queryItemList(page, rows);
    }

    //商品新增 /item/save
    @RequestMapping("save")
    @ResponseBody
    public SysResult save(Item item,String desc)
    {
        return itemService.saveItem(item,desc);
    }

    //商品修改 /item/update
    @RequestMapping("update")
    @ResponseBody
    public SysResult update(Item item,String desc)
    {
        return itemService.updateItem(item,desc);
    }

    //商品删除 	/item/delete
    @RequestMapping("delete")
    @ResponseBody
    public SysResult delete(String ids)
    {
        String[] id = ids.split(",");
        return itemService.deleteItem(id);
    }

    //获取某个itemId的商品详情信息 /item/query/item/desc/'+data.id
    @RequestMapping("query/item/desc/{itemId}")
    @ResponseBody
    public SysResult getItemDesc(@PathVariable Long itemId)
    {
        return itemService.getItemDesc(itemId);
    }
}
