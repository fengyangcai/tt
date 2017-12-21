package com.taotao.search.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.search.service.SearchService;
import com.taotao.search.vo.SolrItem;

public class ItemImport2Solr {
	
	private SearchService searchService;
	
	private ItemService itemService;

	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		searchService = context.getBean(SearchService.class);
		itemService = context.getBean(ItemService.class);
	}

	@Test
	public void test() throws Exception {
		int page = 1;
		int rows = 500;
		List<Item> itemList = null;
		List<SolrItem> solrItemList = null;
		SolrItem solrItem = null;
		do {
			System.out.println("正在导入第" + page +"页...");
			//1、分页获取商品数据
			itemList = itemService.queryByPage(page, rows);
			solrItemList = new ArrayList<>();
			//2、将商品数据转换为搜索系统可以接受的数据（solrItem）
			for (Item item : itemList) {
				solrItem = new SolrItem();
				solrItem.setId(item.getId());
				solrItem.setTitle(item.getTitle());
				solrItem.setImage(item.getImage());
				solrItem.setSellPoint(item.getSellPoint());
				solrItem.setPrice(item.getPrice());
				solrItem.setStatus(item.getStatus());
				solrItemList.add(solrItem);
			}
			//3、调用搜索系统的方法保存数据到solr
			searchService.saveOrUpdateSolrItemList(solrItemList);
			System.out.println("导入第" + page +"页完成。");
			page++;
			rows = itemList.size();
		}while(rows == 500);
	}

}
