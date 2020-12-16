package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.threadlocal.UserThreadlocal;

@Controller
@RequestMapping("cart")
public class CartController
{
    @Autowired
    private CartService cartService;

    // 购物车列表 show.html
    @RequestMapping("show")
    public String showList(Model model) throws Exception
    {
	Long userId = UserThreadlocal.getUserId();
	if (userId == null)
	{
	    return "login";// 转向登录页面
	}

	List<Cart> cartList = cartService.show(userId);
	model.addAttribute("cartList", cartList);
	return "cart";
    }

    // 添加商品到购物车
    @RequestMapping("{itemId}")
    public String addToCart(@PathVariable Long itemId, @RequestParam("buy-num") Integer num, Model model)
	    throws Exception
    {
	Long userId = UserThreadlocal.getUserId();
	if (userId == null)
	{
	    return "login";// 转向登录页面
	}

	List<Cart> cartList = cartService.addItemToCart(userId, itemId, num);
	model.addAttribute("cartList", cartList);
	return "cart";

    }

    // 修改商品数量 http://www.jt.com/service/cart/update/num/562379/13
    @RequestMapping("update/num/{itemId}/{num}")
    public String updateCartNum(Long itemId, Integer num) throws Exception
    {
	Long userId = UserThreadlocal.getUserId();
	if (userId == null)
	{
	    return "login";// 转向登录页面
	}
	cartService.updateCartNum(userId, itemId, num);
	return "";
    }

    // 删除商品 /cart/delete/562379.html
    @RequestMapping("delete/{itemId}")
    public String delete(@PathVariable Long itemId) throws Exception
    {
	Long userId = UserThreadlocal.getUserId();
	if (null == userId)
	{
	    return "login"; // 转向登录页面
	}

	cartService.delete(userId, itemId);

	return "redirect:/cart/show.html";
    }
}
