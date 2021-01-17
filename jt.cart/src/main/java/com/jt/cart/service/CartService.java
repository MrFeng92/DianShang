package com.jt.cart.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.vo.SysResult;

@Service
public class CartService extends BaseService<Cart>
{
    @Autowired
    private CartMapper cartMapper;

    // 新增商品到购物车
    public SysResult saveCart(Cart cart)
    {
        try
        {
            // 1.查询要新增的商品是否在购物车
            Cart param = new Cart();
            param.setItemId(cart.getItemId());
            param.setUserId(cart.getUserId());

            List<Cart> cartList = cartMapper.select(param);

            if (cartList == null && cartList.size() == 0)
            {
                cart.setCreated(new Date());
                cart.setUpdated(cart.getCreated());
                cartMapper.insert(cart);
                return SysResult.ok();
            }
            else
            {
                // 2.如果不在新增商品到购物车
                Cart curCart = cartList.get(0);
                Integer cartNum = curCart.getNum();
                // 3.如果在，查出已存在的商品数量并加上当前商品，跟新到数据库
                Map<String, String> map = new HashMap<String, String>();
                map.put("num", String.valueOf(cartNum + cart.getNum()));
                map.put("itemId", String.valueOf(cart.getItemId()));
                map.put("userId", String.valueOf(cart.getUserId()));

                cartMapper.updateByItemIdAndUserId(map);
                return SysResult.build(202, "此商品已在你的购物车中，商品数量已修改！");
            }
        } catch (Exception e)
        {
            return SysResult.build(201, "商品添加购物车失败!");
        }
    }

    // 跟新购物车商品数量
    public SysResult updateCart(Long userId, Long itemId, Integer num)
    {
        // 直接页面上就含有之前此商品的数量，所以用户加减后直接就修改这个值
        Map<String, String> map = new HashMap<String, String>();
        map.put("num", String.valueOf(num));
        map.put("itemId", String.valueOf(itemId));
        map.put("userId", String.valueOf(userId));
        cartMapper.updateByItemIdAndUserId(map);
        return SysResult.ok();
    }

    public SysResult deleteCartByUserIdAndItemId(Long userId, Long itemId)
    {
        Cart param = new Cart();
        param.setUserId(userId);
        param.setItemId(itemId);
        cartMapper.delete(param);
        return SysResult.ok();
    }

}
