package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.portal.controller.UserController;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 *根据ticket到单点登录系统获取用户信息，如果存在用户信息说明已经登录，如果不存在则说明没有登录）如果登录的则放行，
 *如果没有登录的则跳转到登录页面；在spring mvc的配置文件中只拦截订单相关的访问路径 /order/**
 *
 */
public class OrderHandlerInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断是否已经登录
		String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET);
		User user = userService.queryUserByTicket(ticket);
		if(user != null) {
			//设置用户信息
			request.setAttribute("user", user);
			//如果已经登录，放行
			return true;	
		} 
		
		//获取本来要跳转的地址
		String redirectUrl = request.getRequestURL().toString();
		
		//如果未登录；跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/page/login.html?redirectUrl="+redirectUrl);
			
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
