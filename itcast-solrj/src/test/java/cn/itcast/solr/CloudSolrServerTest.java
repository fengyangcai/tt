package cn.itcast.solr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.solr.vo.SolrItem;

public class CloudSolrServerTest {
	
	private CloudSolrServer cloudSolrServer;

	@Before
	public void setUp() throws Exception {
		//创建cloudSolrServer；并指定zookeeper的集群地址
		String zkHost = "192.168.12.168:3181,192.168.12.168:3182,192.168.12.168:3183";
		cloudSolrServer = new CloudSolrServer(zkHost);
		//设置默认的collection的名称
		cloudSolrServer.setDefaultCollection("taotao_collection");
	}

	/**
	 * 新增或者更新solr中的数据
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testAddAndUpdate() throws IOException, SolrServerException {
		SolrItem solrItem = new SolrItem();
		solrItem.setId(124L);
		solrItem.setTitle("222 锤子 坚果 Pro 2 酒红色 6+64GB 全网通 移动联通电信4G手机 双卡双待");
		solrItem.setSellPoint("全面屏/骁龙660/人脸识别/后置双摄/精致美颜/18W快充，温馨提醒：已激活手机不支持7天无理由退货更多详情请点击");
		solrItem.setPrice(229900L);
		solrItem.setStatus(1);
		solrItem.setImage("https://item.jd.com/5734960.html");
		cloudSolrServer.addBean(solrItem);
		
		//提交
		cloudSolrServer.commit();
	}

	/**
	 * 根据id删除
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testDeleteById() throws IOException, SolrServerException {
		cloudSolrServer.deleteById("124");
		
		//提交
		cloudSolrServer.commit();
	}
	
	/**
	 * 根据条件删除
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testDeleteByCondiction() throws IOException, SolrServerException {
		//删除全部
		cloudSolrServer.deleteByQuery("*:*");
		
		//提交
		cloudSolrServer.commit();
	}
	
	/**
	 * 根据条件查询
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testQuery() throws IOException, SolrServerException {
		List<SolrItem> solrItemList = doSearch("小米",1,10);
		
		if(solrItemList != null && solrItemList.size() > 0) {
			for (SolrItem solrItem : solrItemList) {
				System.out.println(solrItem);
			}
		}
		
	}

	/**
	 * 根据查询关键字查询solr的索引信息
	 * @param keyWords 查询关键字
	 * @param page 页号
	 * @param rows 页大小
	 * @return
	 * @throws SolrServerException 
	 */
	private List<SolrItem> doSearch(String keyWords, int page, int rows) throws SolrServerException {
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		
		if(StringUtils.isBlank(keyWords)) {
			keyWords = "*";
		}
		solrQuery.setQuery("title:"+keyWords + " AND status:1");
		
		//设置分页
		solrQuery.setStart((page-1)*rows);//起始索引号
		solrQuery.setRows(rows);//页大小
		
		//设置高亮
		boolean isHighLight = !"*".equals(keyWords);
		if(isHighLight) {
			solrQuery.setHighlight(true);
			solrQuery.addHighlightField("title");//高亮的域（在schema.xml中配置的域）
			solrQuery.setHighlightSimplePre("<em>");//高亮的起始标签
			solrQuery.setHighlightSimplePost("</em>");//高亮的结束标签
		}
		
		//查询
		QueryResponse queryResponse = cloudSolrServer.query(solrQuery);
		
		//获取总记录数
		System.out.println("符合本次查询的总记录数为：" + queryResponse.getResults().getNumFound());
		//获取查询列表
		List<SolrItem> list = queryResponse.getBeans(SolrItem.class);
		/**
		 * 处理高亮数据：格式如下：
		 *  "highlighting": {
			    "124": {
			      "title": [
			        "<em>锤子</em> 坚果 Pro 2 酒红色 6+64GB 全网通 移动联通电信4G手机 双卡双待"
			      ]
			    }
			  }
		 */
		if(isHighLight && list!= null && list.size() > 0) {
			//获取到高亮的集合
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			for (SolrItem solrItem : list) {
				solrItem.setTitle(highlighting.get(solrItem.getId().toString()).get("title").get(0));
			}
		}
		return list;
	}

}
