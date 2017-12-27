package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/order")
@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	/**
	 * 根据订单id查询订单并转发到页面显示
	 * @param orderId 订单id
	 * @return
	 */
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView toSuccessPage(@RequestParam(value = "id")String orderId) {
		ModelAndView mv = new ModelAndView("success");
		try {
			//加载订单信息
			mv.addObject("order", orderService.queryOrderByOrderId(orderId));
			//设置送达时间，默认订单创建之后的2天
			mv.addObject("date", DateTime.now().plusDays(2).toString("MM月dd日"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * 保存订单的基本、物理、商品到订单系统
	 * @param order 订单信息
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveOrder(Order order, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 500);
		
		try {
			User user = (User)request.getAttribute("user");
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			
			String orderId = orderService.saveOrder(order);
			if(StringUtils.isNotBlank(orderId)) {
				result.put("data", orderId);
				result.put("status", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
	}

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
