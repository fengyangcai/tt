package cn.itcast.springboot.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/info")
@RestController
public class InfoController {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ActiveMQQueue activeMQQueue;
	
	@Autowired
	private ActiveMQTopic activeMQTopic;

	/**
	 * 发送队列消息
	 * @param msg 要发送的消息
	 * @return
	 */
	@RequestMapping("/send/queue/{msg}")
	public String sendQueueMsg(@PathVariable String msg) {
		jmsTemplate.convertAndSend(activeMQQueue, msg);
		return "发送队列消息：" + msg + " 已完成。";
	}
	
	/**
	 * 发送主题消息
	 * @param msg 要发送的消息
	 * @return
	 */
	@RequestMapping("/send/topic/{msg}")
	public String sendTopicMsg(@PathVariable String msg) {
		jmsTemplate.convertAndSend(activeMQTopic, msg);
		return "发送主题消息：" + msg + " 已完成。";
	}
}
