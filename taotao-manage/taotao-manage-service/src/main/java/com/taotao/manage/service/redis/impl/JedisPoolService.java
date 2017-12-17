package com.taotao.manage.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.manage.service.redis.RedisFunction;
import com.taotao.manage.service.redis.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolService implements RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	private <T> T execute(RedisFunction<T,Jedis> fun) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return fun.callback(jedis);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String set(final String key, final String value) {
		return execute(new RedisFunction<String, Jedis>() {

			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	@Override
	public String setex(final String key, final int seconds, final String value) {
		return execute(new RedisFunction<String, Jedis>() {

			@Override
			public String callback(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	public Long expire(final String key, final int seconds) {
		return execute(new RedisFunction<Long, Jedis>() {

			@Override
			public Long callback(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	@Override
	public String get(final String key) {
		return execute(new RedisFunction<String, Jedis>() {

			@Override
			public String callback(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	@Override
	public Long del(final String key) {
		return execute(new RedisFunction<Long, Jedis>() {

			@Override
			public Long callback(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}

	@Override
	public Long incr(final String key) {
		return execute(new RedisFunction<Long, Jedis>() {

			@Override
			public Long callback(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}

}
