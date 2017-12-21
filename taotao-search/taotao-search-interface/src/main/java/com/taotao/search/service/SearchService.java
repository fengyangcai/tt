package com.taotao.search.service;

import java.util.List;

import com.taotao.search.vo.SolrItem;

public interface SearchService {

	/**
	 * 批量新增或更新solr中数据
	 * @param solrItemList
	 * @throws Exception 
	 */
	void saveOrUpdateSolrItemList(List<SolrItem> solrItemList) throws Exception;

}
