package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.vo.DataGridResult;
import com.taotao.search.service.SearchService;
import com.taotao.search.vo.SolrItem;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private CloudSolrServer cloudSolrServer;

	@Override
	public void saveOrUpdateSolrItemList(List<SolrItem> solrItemList) throws Exception {
		cloudSolrServer.addBeans(solrItemList);
		cloudSolrServer.commit();
	}

	@Override
	public DataGridResult search(String keyWords, Integer page, Integer rows) throws Exception {
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		if(StringUtils.isBlank(keyWords)) {
			keyWords = "*";
		}
		solrQuery.setQuery("title:"+keyWords + " AND status:1");
		
		//设置分页
		solrQuery.setStart((page -1 )*rows);//起始索引号
		solrQuery.setRows(rows);//页大小
		
		//设置高亮
		boolean isHighLight = !"*".equals(keyWords);
		if(isHighLight) {
			solrQuery.setHighlight(true);
			solrQuery.addHighlightField("title");
			solrQuery.setHighlightSimplePre("<em>");//高亮的起始标签
			solrQuery.setHighlightSimplePost("</em>");//高亮的结束标签
		}
		
		//查询
		QueryResponse queryResponse = cloudSolrServer.query(solrQuery);
		
		//获取总记录数
		long total = queryResponse.getResults().getNumFound();
		//获取记录列表
		List<SolrItem> list = queryResponse.getBeans(SolrItem.class);
		
		//处理高亮标题
		if(isHighLight && list != null && list.size() > 0) {
			for (SolrItem solrItem : list) {
				//获取高亮标题集合
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				solrItem.setTitle(highlighting.get(solrItem.getId().toString()).get("title").get(0));
			}
		}
		
		return new DataGridResult(total, list);
	}
}
