package com.taotao.manage.service.redis;

public interface RedisFunction<T,E> {

	public T callback(E jedis);
}
