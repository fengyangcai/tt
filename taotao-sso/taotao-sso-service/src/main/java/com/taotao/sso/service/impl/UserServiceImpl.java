package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

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

	@Override
	public void register(User user) {
		//将用户信息保存到数据库；密码加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		userMapper.insertSelective(user);
	}

	@Override
	public String login(User user) throws Exception {
		String ticket = "";
		//1、根据用户名和密码查询用户
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		List<User> list = userMapper.select(user);
		if(list != null && list.size()>0) {
			//2、获取用户
			User tmp = list.get(0);
			//3、将用户转换为json格式字符串存入redis，设置过期时间为1小时
			
			ticket = DigestUtils.md5Hex(user.getUsername() + System.currentTimeMillis());//值要唯一
			
			String key = TICKET_PREFIX + ticket;
			
			redisService.setex(key, 3600, MAPPER.writeValueAsString(tmp));
		}
		//4、返回redis中用户信息对应的部分key，--- ticket
		return ticket;
	}

}
