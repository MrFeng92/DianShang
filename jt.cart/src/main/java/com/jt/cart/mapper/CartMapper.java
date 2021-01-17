package com.jt.cart.mapper;

import java.util.Map;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.base.mapper.SysMapper;

public interface CartMapper extends SysMapper<Cart>
{

    void updateByItemIdAndUserId(Map<String, String> map);

}
