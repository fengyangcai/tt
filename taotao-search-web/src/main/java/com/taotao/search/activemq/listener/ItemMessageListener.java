package com.taotao.search.activemq.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.search.service.SearchService;
import com.taotao.search.vo.SolrItem;
/**
 * 订阅商品变更的ActiveMQ主题，编写监听器监听消息；根据商品操作类型，
 * 如果是新增或者更新则根据商品id查询最新的商品数据并转换为solr可以接受的数据格式并保存到solr，
 * 如果是删除则根据商品id删除solr中数据
 *
 */
public class ItemMessageListener extends AbstractAdaptableMessageListener {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SearchService searchService;

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		if(message instanceof MapMessage) {
			//转换消息
			MapMessage mapMessage = (MapMessage)message;
			//获取消息数据
			String type = mapMessage.getString("type");//操作类型
			Long itemId = mapMessage.getLong("itemId");//商品id
			
			try {
				//根据消息处理
				if(!"delete".equals(type)) {
					//如果是新增或者更新则根据商品id查询最新的商品数据并转换为solr可以接受的数据格式并保存到solr，
					Item item = itemService.queryById(itemId);
					SolrItem solrItem = new SolrItem();
					solrItem.setId(item.getId());
					solrItem.setTitle(item.getTitle());
					solrItem.setImage(item.getImage());
					solrItem.setSellPoint(item.getSellPoint());
					solrItem.setPrice(item.getPrice());
					solrItem.setStatus(item.getStatus());
					
					searchService.saveOrUpdateSolrItem(solrItem);
				} else {
					//如果是删除则根据商品id删除solr中数据
					searchService.deleteSolrItemByItemId(itemId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
