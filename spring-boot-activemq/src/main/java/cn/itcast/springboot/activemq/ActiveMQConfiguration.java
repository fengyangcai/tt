package cn.itcast.springboot.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfiguration {

	//队列
	@Bean
	public ActiveMQQueue getQueue() {
		return new ActiveMQQueue("itcast.queue");
	}
	
	//主题
	@Bean
	public ActiveMQTopic getTopic() {
		return new ActiveMQTopic("itcast.topic");
	}

}
