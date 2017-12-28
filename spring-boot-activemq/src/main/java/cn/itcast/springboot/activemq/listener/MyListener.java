package cn.itcast.springboot.activemq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

	@JmsListener(destination="itcast.queue")
	public void receiveQueueMsg(String msg) {
		System.out.println("接收到队列的消息：" + msg);
	}
	
	@JmsListener(destination="itcast.topic")
	public void receiveTopicMsg(String msg) {
		System.out.println("接收到主题的消息：" + msg);
	}
}
