package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
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
	 * 在登录或未登录时删除购物车购物商品
	 * @param itemId 商品id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete/{itemId}", method = RequestMethod.GET)
	public String deleteCart(@PathVariable Long itemId, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//判断当前用户是否已经登录
			//获取cookie中的ticket登录成功标识符
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET);
			//到单点登录系统获取用户信息；如果存在则说明已经登录，如果不存在则说明没有登录
			User user = userService.queryUserByTicket(ticket);
			if(user != null) {//已登录，删除redis中的购买商品
				cartService.deleteCartByItemIdAndUserId(itemId, user.getId());
			} else {//未登录，删除cookie中的购买商品
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//重定向到我的购物车列表页面
		return "redirect:/cart/show.html";
	}
	
	/**
	 * 在登录或者未登录 修改购物车中的购买商品的购买数量
	 * @param itemId 商品id
	 * @param num 购买数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update/num/{itemId}/{num}", method=RequestMethod.POST)
	public ResponseEntity<Void> updateCartNum(@PathVariable Long itemId, @PathVariable Integer num, 
			HttpServletRequest request, HttpServletResponse response){
		try {
			//判断当前用户是否已经登录
			//获取cookie中的ticket登录成功标识符
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET);
			//到单点登录系统获取用户信息；如果存在则说明已经登录，如果不存在则说明没有登录
			User user = userService.queryUserByTicket(ticket);
			if(user != null) {//已登录，修改redis中的购买数量
				cartService.updateCartNumByItemIdAndUserId(num, itemId, user.getId());
			} else {//未登录，修改cookie中的购买数量
				
			}
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	

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
	
	/**
	 * 获取购物车数据并显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("cart");
		try {
			//判断当前用户是否已经登录
			//获取cookie中的ticket登录成功标识符
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET);
			//到单点登录系统获取用户信息；如果存在则说明已经登录，如果不存在则说明没有登录
			User user = userService.queryUserByTicket(ticket);
			List<Cart> cartList = null;
			if(user != null) {//已登录，从redis中获取购物车列表数据
				cartList = cartService.getCartListByUserId(user.getId());
			} else {//未登录，从cookie中获取购物车列表数据
				
			}
			mv.addObject("cartList", cartList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
