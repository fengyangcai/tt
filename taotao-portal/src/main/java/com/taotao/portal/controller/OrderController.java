package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/order")
@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;

	/**
	 * 查询当前登录用户的购物车数据并转发到订单结算页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView toOrderCartPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("order-cart");
		try {
			/*String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET);
			User user = userService.queryUserByTicket(ticket);*/
			User user = (User)request.getAttribute("user");
			//获取购物车列表数据
			List<Cart> carts = cartService.getCartListByUserId(user.getId());
			
			mv.addObject("carts", carts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
