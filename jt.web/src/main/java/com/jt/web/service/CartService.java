package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Item;
import org.springframework.stereotype.Service;

@Service
public class CartService
{
    @Autowired
    private HttpClientService httpClientService;
    private static ObjectMapper MAPPER = new ObjectMapper();

    public List<Cart> show(Long userId) throws Exception
    {
	// 1.根据userId去购物车系统查询商品信息
	String url = "http://cart.jt.com/cart/query/" + userId;
	String jsonData = httpClientService.doGet(url);
	// 2.对返回的json串进行处理得到cartList
	JsonNode nodeTree = MAPPER.readTree(jsonData);
	List<Cart> cartList = MAPPER.readValue(nodeTree.get("data").traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
	return cartList;
    }

    public List<Cart> addItemToCart(Long userId, Long itemId, Integer num) throws Exception
    {
	// 1.根据商品ID去后台查询商品详情
	String url = "http://manage.jt.com/web/item/" + itemId;
	String jsonData = httpClientService.doGet(url);
	Item item = MAPPER.readValue(jsonData, Item.class);

	// 2.将查出来的商品信息封装到map中到购物车系统保存
	url = "http://cart.jt.com/cart/save";
	Map<String, String> params = new HashMap<String, String>();
	params.put("userId", String.valueOf(userId));
	params.put("itemId", String.valueOf(itemId));
	params.put("itemTitle", item.getTitle());
	params.put("itemImage", item.getImage().split(",") [ 0 ]);
	params.put("itemPrice", String.valueOf(item.getPrice()));
	params.put("num", String.valueOf(num));

	httpClientService.doPost(url, params, "utf-8");

	// 3.根据用户ID查询当前用户的购物车信息返回
	url = "http://cart.jt.com/cart/query/" + userId;
	String jsonCartData = httpClientService.doGet(url);
	JsonNode jsonNode = MAPPER.readTree(jsonData);
	List<Cart> cartList = MAPPER.readValue(jsonNode.get("data").traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
	return cartList;
    }

    public void updateCartNum(Long userId, Long itemId, Integer num) throws Exception
    {
	String url = "http://cart.jt.com/cart/update/num/" + userId + "/" + itemId + "/" + num;
	httpClientService.doGet(url);
    }

    // 商品删除
    public void delete(Long userId, Long itemId) throws Exception
    {
	String url = "http://cart.jt.com/cart/delete/" + userId + "/" + itemId;
	httpClientService.doGet(url);
    }

}
