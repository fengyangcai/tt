package com.taotao.cart.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	@Override
	public List<Cart> getCartListByUserId(Long userId) throws Exception {
		String key = REDIS_CART_KEY + userId;
		List<Cart> cartList = new ArrayList<>();
		List<String> cartJsonStrList = redisService.hvals(key);
		if(cartJsonStrList != null && cartJsonStrList.size() > 0) {
			for (String cartJsonStr : cartJsonStrList) {
				cartList.add(MAPPER.readValue(cartJsonStr, Cart.class));
			}
		}
		return cartList;
	}

	@Override
	public void updateCartNumByItemIdAndUserId(Integer num, Long itemId, Long userId) throws Exception {
		//1、查询用户购买商品并修改购买数量
		String key = REDIS_CART_KEY + "" + userId;
		String field = itemId.toString();
		String cartJsonStr = redisService.hget(key, field);
		if(StringUtils.isNotBlank(cartJsonStr)) {
			//2、将最新的购物车商品写回
			Cart cart = MAPPER.readValue(cartJsonStr, Cart.class);
			cart.setNum(num);
			cart.setUpdated(new Date());
			
			redisService.hset(key, field, MAPPER.writeValueAsString(cart));
		}
	}

	@Override
	public void deleteCartByItemIdAndUserId(Long itemId, Long userId) {
		String key = REDIS_CART_KEY + "" + userId;
		String field = itemId.toString();
		redisService.hdel(key, field);
	}
}
