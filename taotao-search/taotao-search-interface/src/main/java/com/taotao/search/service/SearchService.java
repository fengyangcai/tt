package com.taotao.search.service;

import java.util.List;

import com.taotao.common.vo.DataGridResult;
import com.taotao.search.vo.SolrItem;

public interface SearchService {

	/**
	 * 批量新增或更新solr中数据
	 * @param solrItemList
	 * @throws Exception 
	 */
	void saveOrUpdateSolrItemList(List<SolrItem> solrItemList) throws Exception;

	/**
	 * 根据搜索关键字分页查询solr中的数据
	 * @param keyWords 搜索关键字
	 * @param page 页号
	 * @return
	 * @throws Exception 
	 */
	DataGridResult search(String keyWords, Integer page, Integer rows) throws Exception;

	/**
	 * 新增或更新solr中数据
	 * @param solrItem
	 * @throws Exception 
	 */
	void saveOrUpdateSolrItem(SolrItem solrItem) throws Exception;

	/**
	 * 根据商品id删除solr中对应的商品索引数据
	 * @param itemId
	 */
	void deleteSolrItemByItemId(Long itemId) throws Exception;

}
