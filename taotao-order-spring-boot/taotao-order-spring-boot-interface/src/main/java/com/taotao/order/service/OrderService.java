package com.taotao.order.service;

import com.taotao.order.pojo.Order;

public interface OrderService {

	/**
	 * 保存订单的基本、物理、商品到订单系统
	 * @param order 订单信息
	 * @return
	 */
	String saveOrder(Order order);

	/**
	 * 根据订单id查询订单并转发到页面显示
	 * @param orderId 订单id
	 * @return
	 */
	Order queryOrderByOrderId(String orderId);
	
	
	/**
	 * 当用户提交订单后；如果超过2天的在线支付还未付款的话，
	 * 那么将此类订单的状态标识为 交易关闭 状态。
	 * 同时；需要更新交易关闭时间、交易完成时间、订单更新时间。
	 */
	void autoCloseOrder();

}
