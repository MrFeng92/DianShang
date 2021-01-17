package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("cart")
public class CartController
{
    @Autowired
    private CartService cartService;

    // 保存商品到购物车中 http://cart.jt.com/cart/save
    @RequestMapping("save")
    @ResponseBody
    public SysResult save(Cart cart)
    {
        return cartService.saveCart(cart);
    }

    // 根据userId来查询某人的购物车数据 http://cart.jt.com/cart/query/{userId}
    @RequestMapping("query/{userId}")
    @ResponseBody
    public SysResult queryByUserId(@PathVariable Long userId)
    {
        Cart param = new Cart();
        param.setUserId(userId);

        List<Cart> cartList = cartService.queryListByWhere(param);
        return SysResult.ok(cartList);
    }

    // 更新商品数量 http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
    @RequestMapping("update/num/{userId}/{itemId}/{num}")
    @ResponseBody
    public SysResult update(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num)
    {
        return cartService.updateCart(userId, itemId, num);
    }

    // 商品删除 http://cart.jt.com/cart/delete/{userId}/{itemId}
    @RequestMapping("delete/{userId}/{itemId}")
    @ResponseBody
    public SysResult deleteCart(@PathVariable Long userId, @PathVariable Long itemId)
    {
        return cartService.deleteCartByUserIdAndItemId(userId, itemId);
    }

}
