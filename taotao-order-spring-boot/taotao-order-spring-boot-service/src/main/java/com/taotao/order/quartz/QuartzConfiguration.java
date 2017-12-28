package com.taotao.order.quartz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.taotao.order.service.OrderService;

@Configuration
public class QuartzConfiguration {
	
	//任务详细信息bean
	@Bean("orderJobDetail")
	public MethodInvokingJobDetailFactoryBean getJobDetail(OrderService orderService) {
		MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
		//设置执行对象
		jobDetailFactoryBean.setTargetObject(orderService);
		//设置执行对象中对应的方法
		jobDetailFactoryBean.setTargetMethod("autoCloseOrder");
		//设置是否可并发
		jobDetailFactoryBean.setConcurrent(false);
		return jobDetailFactoryBean;
	}
	
	//任务调度触发器bean
	@Bean("orderCronTrigger")
	public CronTriggerFactoryBean getTrigger(MethodInvokingJobDetailFactoryBean jobDetail,
			@Value("${quartz.cronExpression}")String cronExpression) {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		//任务详细信息
		cronTriggerFactoryBean.setJobDetail(jobDetail.getObject());
		//设置执行时机，每隔5秒
		cronTriggerFactoryBean.setCronExpression(cronExpression);
		return cronTriggerFactoryBean;
	}
	
	//任务触发器调度工厂bean
	@Bean("orderSchedulerFactoryBean")
	public SchedulerFactoryBean getScheduler(CronTriggerFactoryBean trigger) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		//设置触发器们
		schedulerFactoryBean.setTriggers(trigger.getObject());
		return schedulerFactoryBean;
	}
	
}
