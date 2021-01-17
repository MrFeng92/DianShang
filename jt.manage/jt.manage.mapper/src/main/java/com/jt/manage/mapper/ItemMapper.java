package com.jt.manage.mapper;

import com.jt.common.mapper.base.mapper.SysMapper;
import manage.pojo.Item;

import java.util.List;


public interface ItemMapper extends SysMapper<Item>
{
	public List<Item> queryItemList();
}
