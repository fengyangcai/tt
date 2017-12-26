package com.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.cart.service.CartService;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/cart")
@Controller
public class CartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	

	/**
	 * 登录与未登录时候将商品加入到购物车系统中
	 * @param itemId 商品id
	 * @param num 购买数量
	 * @return
	 */
	@RequestMapping(value = "/{itemId}/{num}", method = RequestMethod.GET)
	public String addCart(@PathVariable Long itemId, @PathVariable Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//判断当前用户是否已经登录
			//获取cookie中的ticket登录成功标识符
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET);
			//到单点登录系统获取用户信息；如果存在则说明已经登录，如果不存在则说明没有登录
			User user = userService.queryUserByTicket(ticket);
			if(user != null) {//已登录，将购物车数据存入到购物车系统redis
				Item item = itemService.queryById(itemId);
				cartService.addCartByItemIdAndUserId(num, item, user.getId());
			} else {//未登录，将购物车数据存入到cookie
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//重定向到购物车列表
		return "redirect:/cart/show.html";
	}
}
