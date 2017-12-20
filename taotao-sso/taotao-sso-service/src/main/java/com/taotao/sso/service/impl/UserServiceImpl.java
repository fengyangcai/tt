package com.taotao.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.service.redis.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	//用户的ticket存储在redis中的前缀
	private static final String TICKET_PREFIX = "TT_TICKET_";

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;

	@Override
	public Boolean check(String param, Integer type) {
		//根据查询条件查询是否存在用户数据，如果存在则返回false；否则返回true
		
		User user = new User();
		
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;

		default:
			user.setEmail(param);
			break;
		}
		
		int count = userMapper.selectCount(user);
		
		return count==0;
	}

	@Override
	public String queryUserStrByTicket(String ticket) {
		//根据ticket到redis获取用户信息json格式字符串；表示当前用户处于活跃状态
		String key = TICKET_PREFIX + ticket;
		String userJsonStr = redisService.get(key);
		if(StringUtils.isNotBlank(userJsonStr)) {
			//重新设置该信息的过期时间，1小时
			redisService.expire(key, 3600);
			
			return userJsonStr;
		}
		return "";
	}

}
