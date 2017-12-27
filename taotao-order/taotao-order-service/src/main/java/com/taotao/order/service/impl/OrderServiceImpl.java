package com.taotao.order.service.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.order.mapper.OrderMapper;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public String saveOrder(Order order) {
		//生成订单号：唯一，可读
		String orderId = order.getUserId() + "" + System.currentTimeMillis();
		order.setOrderId(orderId);
		order.setCreateTime(new Date());
		order.setUpdateTime(order.getCreateTime());
		order.setStatus(1);//未付款
		
		orderMapper.saveOrder(order);
		
		return orderId;
	}

	@Override
	public Order queryOrderByOrderId(String orderId) {
		return orderMapper.queryOrderByOrderId(orderId);
	}

	@Override
	public void autoCloseOrder() {
		orderMapper.autoCloseOrder(DateTime.now().minusDays(2).toDate());
	}
}
