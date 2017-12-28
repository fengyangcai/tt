package cn.itcast.springboot.solr.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/solr")
@RestController
public class SearchController {
	
	@Autowired
	private SolrClient solrClient;

	@RequestMapping("/search/{keyWords}")
	public List<String> search(@PathVariable String keyWords) throws Exception{
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery("title:" + keyWords);
		
		//判断当前是否为集群版
		if(solrClient instanceof CloudSolrClient) {
			//集群版需要设置collection的名称
			solrQuery.set("collection", "taotao_collection");
		}
		
		//查询
		QueryResponse queryResponse = solrClient.query(solrQuery);
		
		List<String> list = new ArrayList<String>();
		
		SolrDocumentList results = queryResponse.getResults();
		for (SolrDocument solrDocument : results) {
			list.add(solrDocument.get("title").toString());
		}
		
		return list;
	}
	
	@RequestMapping("/save/{id}/{title}")
	public String save(@PathVariable Long id, @PathVariable String title) throws Exception{
		//创建文档对象
		SolrInputDocument doc = new SolrInputDocument();
		//如下设置的域名称必须要在solr的schema.xml中配置过
		doc.setField("id", id);
		doc.setField("title", title);
		
		//保存
		//判断当前是否为集群版
		if(solrClient instanceof CloudSolrClient) {
			//集群版需要设置collection的名称
			solrClient.add("taotao_collection", doc);
			solrClient.commit("taotao_collection");
		}else {
			//单机版
			solrClient.add(doc);
			solrClient.commit();
		}
		
		
		return "保存数据到solr成功。";
	}
}
