package com.taotao.manage.controller;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination topicDestination;
	
	//TODO 删除
	//TODO 上架
	//TODO 下架
	
	/**
	 * 根据商品标题模糊分页查询并按照更新时间降序排序列表
	 * @param title 商品标题
	 * @param page 页号
	 * @param rows 页大小
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> queryItemListByPage(@RequestParam(value = "title", required =false)String title,
			@RequestParam(value = "page", defaultValue="1")Integer page, 
			@RequestParam(value = "rows", defaultValue="30")Integer rows){
		try {
			DataGridResult dataGridResult = itemService.queryItemListByPage(title, page, rows);
			
			return ResponseEntity.ok(dataGridResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 更新商品基本和描述信息
	 * @param item 基本信息
	 * @param desc 描述信息
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam(value="desc", required = false)String desc){
		
		try {
			itemService.updateItem(item, desc);
			//发送mq消息
			sendMQMsg(item.getId(), "update");
			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//返回状态码为500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	/**
	 * 保存商品基本和描述信息
	 * @param item 基本信息
	 * @param desc 描述信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam(value="desc", required = false)String desc){
		
		try {
			Long itemId = itemService.saveItem(item, desc);
			//发送mq消息
			sendMQMsg(itemId, "insert");
			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//返回状态码为500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 发送MQ消息
	 * @param itemId 商品id
	 * @param type 操作类型；如：insert/update/delete
	 */
	private void sendMQMsg(final Long itemId, final String type) {
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
				mapMessage.setString("type", type);
				mapMessage.setLong("itemId", itemId);
				
				return mapMessage;
			}
		});
	}
}
