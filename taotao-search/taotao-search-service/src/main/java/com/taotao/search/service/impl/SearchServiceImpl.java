package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
