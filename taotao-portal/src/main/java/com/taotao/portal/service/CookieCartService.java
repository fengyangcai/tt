package com.taotao.portal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.portal.util.CookieUtils;

@Service
public class CookieCartService {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	//用户在未登录时购物车数据在cookie中的key
	private static final String COOKIE_CART = "TT_CART";
	//用户在未登录时购物车数据在cookie中的key的过期时间为：1周
	private static final int COOKIE_CART_MAX_AGE = 60*60*24*7;
	
	@Autowired
	private ItemService itemService;

	public void addCartByItemId(Integer num, Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 从cookie中获取购物车列表
		 * 查询当前添加的商品是否存在购物车列表
		 * 如果存在则购买商品数据叠加
		 * 如果不存在则根据商品id查询商品并转换为cart并保存到cookie；并设置过期时间：1周
		 */
		List<Cart> cartList = getCartList(request);
		
		boolean isDeal = false;
		if(cartList != null && cartList.size()>0) {
			for (Cart cart : cartList) {
				if(itemId.equals(cart.getItemId())) {
					cart.setNum(cart.getNum() + num);
					cart.setUpdated(new Date());
					isDeal = true;
					break;
				}
			}
		}
		
		if(!isDeal) {
			//如果没有更新数量，则新增
			if(cartList == null) {
				cartList = new ArrayList<>();
			}
			//根据商品id查询商品
			Item item = itemService.queryById(itemId);
			
			Cart cart = new Cart();
			cart.setItemId(item.getId());
			cart.setItemTitle(item.getTitle());
			cart.setNum(num);
			cart.setItemPrice(item.getPrice());
			if(item.getImages() != null && item.getImages().length > 0) {
				cart.setItemImage(item.getImages()[0]);
			}
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			
			cartList.add(cart);
		}
		
		//将数据写回到cookie
		CookieUtils.setCookie(request, response, COOKIE_CART, MAPPER.writeValueAsString(cartList), 
				COOKIE_CART_MAX_AGE, true);
	}

	public List<Cart> getCartList(HttpServletRequest request) throws Exception {
		//从cookie中将购物车字符串数据转换成列表
		String cartListStr = CookieUtils.getCookieValue(request, COOKIE_CART, true);
		if(StringUtils.isNotBlank(cartListStr)) {
			return MAPPER.readValue(cartListStr, 
					MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
		}
		return null;
	}

	public void updateCartNumByItemId(Integer num, Long itemId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<Cart> cartList = getCartList(request);
		if(cartList != null && cartList.size() > 0) {
			
			for (Cart cart : cartList) {
				if(cart.getItemId().equals(itemId)) {
					cart.setNum(num);
					cart.setUpdated(new Date());
					break;
				}
			}
			
			//将数据写回到cookie
			CookieUtils.setCookie(request, response, COOKIE_CART, MAPPER.writeValueAsString(cartList), 
					COOKIE_CART_MAX_AGE, true);
		}
	}

	public void deleteCartByItemId(Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Cart> cartList = getCartList(request);
		if(cartList != null && cartList.size() > 0) {
			
			Iterator<Cart> iterator = cartList.iterator();
			while (iterator.hasNext()) {
				Cart cart = iterator.next();
				if(cart.getItemId().equals(itemId)) {
					cartList.remove(cart);
					break;
				}
				
			}
			
			if(cartList.size() > 0) {
				//将数据写回到cookie
				CookieUtils.setCookie(request, response, COOKIE_CART, MAPPER.writeValueAsString(cartList), 
						COOKIE_CART_MAX_AGE, true);
			} else {
				//将数据写回到cookie
				CookieUtils.deleteCookie(request, response, COOKIE_CART);
			}
		}
	}

}
