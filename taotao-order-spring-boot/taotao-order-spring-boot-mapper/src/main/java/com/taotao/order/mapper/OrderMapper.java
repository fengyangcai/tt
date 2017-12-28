package com.taotao.order.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.taotao.order.pojo.Order;

@Mapper
public interface OrderMapper {

	void saveOrder(Order order);

	Order queryOrderByOrderId(@Param("orderId")String orderId);

	void autoCloseOrder(@Param("date")Date date);

}
