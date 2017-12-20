package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 检查数据是否可用；需要支持jsonp；true：数据可用，false：数据不可用
	 * @param param 校验数据
	 * @param type 参数类型，可选参数1、2、3分别代表username、phone、email
	 * @return
	 */
	@RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<String> check(@PathVariable String param, @PathVariable Integer type){
		try {
			if(0 < type && type < 4) {
				Boolean bool = userService.check(param, type);
				
				return ResponseEntity.ok(bool.toString());
			} else {//参数不合法
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 根据ticket查询用户信息；需要支持jsonp
	 * @param ticket 登录标识符
	 * @return
	 */
	@RequestMapping(value = "/{ticket}", method = RequestMethod.GET)
	public ResponseEntity<String> queryUserByTicket(@PathVariable String ticket){
		try {
			if(StringUtils.isNotBlank(ticket)) {
				String userStr = userService.queryUserStrByTicket(ticket);
				return ResponseEntity.ok(userStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	} 
}
