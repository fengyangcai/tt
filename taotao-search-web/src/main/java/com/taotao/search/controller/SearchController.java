package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.vo.DataGridResult;
import com.taotao.search.service.SearchService;

@RequestMapping("/search")
@Controller
public class SearchController {
	
	//默认的页大小
	private static final Integer ROWS = 40;
	
	@Autowired
	private SearchService searchService;

	/**
	 * 根据搜索关键字分页查询solr中的数据
	 * @param keyWords 搜索关键字
	 * @param page 页号
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = "q", required = false)String keyWords,
			@RequestParam(value = "page", defaultValue = "1")Integer page) {
		ModelAndView mv = new ModelAndView("search");
		try {
			if(StringUtils.isNotBlank(keyWords)) {
				keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
			}
			//搜索关键字
			mv.addObject("query", keyWords);
			DataGridResult dataGridResult = searchService.search(keyWords, page, ROWS);
			//商品列表
			mv.addObject("itemList", dataGridResult.getRows());
			//页号
			mv.addObject("page", page);
			//总页数
			//方式1：总页数 = （总记录数%页大小==0）?(总记录数/页大小):(总记录数/页大小  + 1)
			//方式2：总页数 = （总记录数 + 页大小 - 1）/页大小
			long totalPages = (dataGridResult.getTotal() + ROWS -1)/ROWS;
			mv.addObject("totalPages", totalPages);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
