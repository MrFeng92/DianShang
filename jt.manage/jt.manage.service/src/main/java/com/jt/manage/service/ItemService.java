package com.jt.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.service.RedisService;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import manage.pojo.Item;
import manage.pojo.ItemDesc;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService extends BaseService<Item>
{
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    //商品列表查询
    public EasyUIResult queryItemList(Integer page, Integer rows)
    {
        //分页插件
        //对pageHelper下面的第一条sql进行分页包装。注意，代码是有先后顺序
        PageHelper.startPage(page, rows);
        List<Item> itemList = itemMapper.queryItemList();
        //一页的数据
        PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);

        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }

    //商品新增
    public SysResult saveItem(Item item, String desc)
    {
        try
        {
            //初始化值
            item.setCreated(new Date());
            item.setUpdated(item.getCreated());
            itemMapper.insertSelective(item);

            //新增商品描述信息？如何拿到商品id？
            ItemDesc param = new ItemDesc();
            param.setItemId(item.getId());    //有吗？有
            param.setItemDesc(desc);
            param.setCreated(new Date());
            param.setUpdated(param.getCreated());

            itemDescMapper.insert(param);
        } catch (Exception e)
        {
            return SysResult.build(201, "商品新增错误!");
        }
        return SysResult.ok();
    }

    //商品修改
    public SysResult updateItem(Item item, String desc)
    {
        try
        {
            item.setUpdated(new Date());
            itemMapper.updateByPrimaryKeySelective(item);

            //商品描述修改
            ItemDesc param = new ItemDesc();
            param.setItemId(item.getId());
            param.setItemDesc(desc);
            param.setUpdated(new Date());

            itemDescMapper.updateByPrimaryKeySelective(param);

            //RabbitMQ方式：发送消息
            String routingKey = "jt.item.update";
            rabbitTemplate.convertAndSend(routingKey, item.getId());    //消息体要尽量小
//            //删除redis中的key
//            String ITEM_KEY = "JT_ITEM_" + item.getId();
//            redisService.del(ITEM_KEY);
        } catch (Exception e)
        {
            return SysResult.build(201, "商品修改错误!");
        }
        return SysResult.ok();
    }

    //商品删除
    public SysResult deleteItem(String[] id)
    {
        try
        {
            itemDescMapper.deleteByIDS(id);

            itemMapper.deleteByIDS(id);
        } catch (Exception e)
        {
            return SysResult.build(201, "商品删除失败!");
        }
        return SysResult.ok();
    }

    //根据itemId获取商品详细信息
    public SysResult getItemDesc(Long itemId)
    {
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return SysResult.ok(itemDesc);
    }

}
