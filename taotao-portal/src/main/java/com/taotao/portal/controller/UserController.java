package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
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
