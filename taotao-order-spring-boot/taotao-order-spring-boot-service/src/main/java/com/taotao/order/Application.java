package com.taotao.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * @ImportResource 当遇到spring boot没有对应的启动器的时候，又必须需要使用spring整合的时候
 * 可以利用该注解添加额外的spring 配置文件
 *
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:spring/applicationContext-dubbo.xml"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
