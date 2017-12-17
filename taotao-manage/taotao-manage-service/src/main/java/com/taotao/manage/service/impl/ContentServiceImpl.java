package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

	@Value("${PORTAL_INDEX_BIG_ADD_NUMBER}")
	private Integer PORTAL_INDEX_BIG_ADD_NUMBER;

	@Value("${CONTENT_CATEGORY_BIG_AD_ID}")
	private Long CONTENT_CATEGORY_BIG_AD_ID;
	
	@Autowired
	private ContentMapper contentMapper;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public DataGridResult queryContentListByPage(Long categoryId, Integer page, Integer rows) {
		//根据内容分类id分页查询该分类下的内容列表并根据更新时间降序排序
		Example example = new Example(Content.class);
		
		//设置查询条件
		example.createCriteria().andEqualTo("categoryId", categoryId);
		
		//设置排序
		example.orderBy("updated").desc();
		
		//设置分页
		PageHelper.startPage(page, rows);
		
		List<Content> list = contentMapper.selectByExample(example);
		
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

	@Override
	public String getPortalBigAdData() throws Exception {
		String resultStr = "";
		//1、查询6条最新的大广告数据
		DataGridResult dataGridResult = queryContentListByPage(CONTENT_CATEGORY_BIG_AD_ID, 1, PORTAL_INDEX_BIG_ADD_NUMBER);
		
		//2、将6条大广告数据转换为符合格式的字符串
		if(dataGridResult.getRows() != null && dataGridResult.getRows().size() > 0) {
			List<Content> contentList = (List<Content>) dataGridResult.getRows();
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> map = null;
			for (Content content : contentList) {
				map = new HashMap<>();
				map.put("alt", content.getTitle());
				map.put("height", 240);
				map.put("heightB", 240);
				map.put("href", content.getUrl());
				map.put("src", content.getPic());
				map.put("srcB", content.getPic2());
				map.put("width", 670);
				map.put("widthB", 550);
				
				resultList.add(map);
			}
			//3、返回json格式字符串
			resultStr = MAPPER.writeValueAsString(resultList);
		}
		
		return resultStr;
	}

}
