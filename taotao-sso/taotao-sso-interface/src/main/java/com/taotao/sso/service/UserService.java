package com.taotao.sso.service;

import com.taotao.sso.pojo.User;

public interface UserService {

	/**
	 * 检查数据是否可用
	 * @param param 校验数据
	 * @param type 参数类型，可选参数1、2、3分别代表username、phone、email
	 * @return
	 */
	Boolean check(String param, Integer type);

	/**
	 * 根据ticket查询用户信息
	 * @param ticket 登录标识符
	 * @return
	 */
	String queryUserStrByTicket(String ticket);

	/**
	 * 将用户信息保存到数据库
	 * @param user
	 */
	void register(User user);

}
