package com.taotao.manage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;

import tk.mybatis.mapper.entity.Example;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public Long saveItem(Item item, String desc) {
		// 1、保存商品基本信息
		saveSelective(item);

		// 2、保存商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.insertSelective(itemDesc);

		return item.getId();
	}

	@Override
	public void updateItem(Item item, String desc) {
		// 1、更新商品基本信息
		updateSelective(item);

		// 2、保存商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public DataGridResult queryItemListByPage(String title, Integer page, Integer rows) {
		
		Example example = new Example(Item.class);
		
		//设置根据标题模糊查询
		try {
			if(StringUtils.isNotBlank(title)) {
				title = URLDecoder.decode(title, "utf-8");
				
				example.createCriteria().andLike("title", "%" + title + "%");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//设置排序，根据更新时间降序排序
		example.orderBy("updated").desc();
		
		//设置分页
		PageHelper.startPage(page, rows);
		
		List<Item> list = itemMapper.selectByExample(example);
		
		//转换为分页信息对象
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
