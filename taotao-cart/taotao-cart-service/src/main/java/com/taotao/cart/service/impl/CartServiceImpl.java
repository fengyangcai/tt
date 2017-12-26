package com.taotao.cart.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.common.service.redis.RedisService;
import com.taotao.manage.pojo.Item;

@Service
public class CartServiceImpl implements CartService {

	//用户在redis中对应的购物车前缀
	private static final String REDIS_CART_KEY = "TT_CART_";
	
	@Autowired
	private RedisService redisService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void addCartByItemIdAndUserId(Integer num, Item item, Long userId) throws Exception {
		/**
		 * 将商品加入到redis中,
		 * 如果商品已经在购物车中则购买数量叠加，
		 * 如果商品不存在在购物车中则将根据商品id查询商品并转换为购物车商品之后再添加到redis
		 */
		String key = REDIS_CART_KEY + "" + userId;
		
		String field = item.getId().toString();
		
		String cartJsonStr = redisService.hget(key, field );
		Cart cart = null;
		if(StringUtils.isNotBlank(cartJsonStr)) {
			//商品已经存在数据量叠加
			cart = MAPPER.readValue(cartJsonStr, Cart.class);
			cart.setNum(cart.getNum() + num);
			cart.setUpdated(new Date());
		} else {
			//如果商品不存在在购物车中则将根据商品id查询商品并转换为购物车商品之后再添加到redis
			cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(item.getId());
			cart.setItemTitle(item.getTitle());
			cart.setNum(num);
			cart.setItemPrice(item.getPrice());
			if(item.getImages() != null && item.getImages().length > 0) {
				cart.setItemImage(item.getImages()[0]);
			}
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
		}
		
		//保存购物车商品数据到redis
		redisService.hset(key, field, MAPPER.writeValueAsString(cart));
	}
}
