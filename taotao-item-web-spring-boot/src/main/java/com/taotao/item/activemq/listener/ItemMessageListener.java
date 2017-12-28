package com.taotao.item.activemq.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *在监听器中接受到商品变更消息：如果是新增或者更新，根据商品id查询商品基本、描述信息，
 *获取商品的freemarker模版；模版+数据=输出（特定的路径，并且静态文件的名称以商品id为名）；
 *如果是删除则根据商品id到 特定路径 下将对应的静态页面删除
 *
 */
@Component
public class ItemMessageListener {
	
	@Value("${ITEM_HTML_PATH}")
	private String ITEM_HTML_PATH;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	

	@JmsListener(destination="topic.item")
	public void onMessage(Message message, Session session) throws JMSException {
		if(message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage)message;
			//商品id
			long itemId = mapMessage.getLong("itemId");
			//操作类型
			String type = mapMessage.getString("type");
			
			try {
				if(!"delete".equals(type)) {
					//如果是新增或者更新，根据商品id查询商品基本、描述信息，
					//获取商品的freemarker模版；模版+数据=输出（特定的路径，并且静态文件的名称以商品id为名）；
					Item item = itemService.queryById(itemId);
					ItemDesc itemDesc = itemDescService.queryById(itemId);
					
					Map<String, Object> dataModel = new HashMap<String, Object>();
					dataModel.put("item", item);
					dataModel.put("itemDesc", itemDesc);
					
					//获取freemarker模版
					Configuration configuration = freeMarkerConfigurer.getConfiguration();
					Template template = configuration.getTemplate("item.ftl");
					
					//输出对象
					String filePath = ITEM_HTML_PATH + File.separator + itemId + ".html";
					FileWriter fileWriter = new FileWriter(new File(filePath));
					
					template.process(dataModel, fileWriter);
				} else {
					//如果是删除则根据商品id到 特定路径 下将对应的静态页面删除
					String filePath = ITEM_HTML_PATH + File.separator + itemId + ".html";
					File file = new File(filePath);
					if(file.exists()) {
						file.delete();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
