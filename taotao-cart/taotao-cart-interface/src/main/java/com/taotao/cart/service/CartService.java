package com.taotao.cart.service;

import java.util.List;

import com.taotao.cart.pojo.Cart;
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
	
	
	/**
	 * 根据用户id获取redis中该用户对应的购物车商品列表
	 * @param userId 用户id
	 * @return
	 * @throws Exception 
	 */
	List<Cart> getCartListByUserId(Long userId) throws Exception;


	/**
	 * 修改用户在redis中的对应商品的购买数量
	 * @param num 最新的购买数量
	 * @param itemId 商品id
	 * @param userId 用户id
	 * @throws Exception 
	 */
	void updateCartNumByItemIdAndUserId(Integer num, Long itemId, Long userId) throws Exception;


	/**
	 * 删除用户在购物车列表中的商品
	 * @param itemId 商品id
	 * @param userId 用户id
	 */
	void deleteCartByItemIdAndUserId(Long itemId, Long userId);

}
