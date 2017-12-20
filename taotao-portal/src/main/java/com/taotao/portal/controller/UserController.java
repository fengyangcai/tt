package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	//登录成功标识符存放在cookie中的key的名称
	private static final String COOKIE_TICKET = "TT_TICKET";
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		//TODO 1、调用userService将redis中的用户信息清除
		
		//2、将cookie中的ticket删除
		CookieUtils.deleteCookie(request, response, COOKIE_TICKET);
		//重定向到首页
		return "redirect:/index.html";
	}
	
	/**
	 * 登录
	 * @param user 用户信息
	 * @return
	 */
	@RequestMapping(value="/doLogin", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(User user,
			HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 500);
		try {
			//1、登录并返回登录成功标识符ticket
			String ticket = userService.login(user);
			//2、将ticket存入cookie
			CookieUtils.setCookie(request, response, COOKIE_TICKET, ticket, 3600);
			
			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 获取用户信息并将用户信息保存到单点登录系统
	 * @param user 用户信息
	 * @return
	 */
	@RequestMapping(value="/doRegister", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> register(User user){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 500);
		try {
			userService.register(user);
			
			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", e.getMessage());
		}
		
		return ResponseEntity.ok(result);
	}

}
