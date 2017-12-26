package com.taotao.cart.service;

import com.taotao.manage.pojo.Item;

public interface CartService {

	/**
	 * 将购物车数据存入到redis中   
	 * @param num 购买数量
	 * @param itemId 商品id
	 * @param userId 用户id
	 * @throws Exception 
	 */
	void addCartByItemIdAndUserId(Integer num, Item itemId, Long userId) throws Exception;

}
