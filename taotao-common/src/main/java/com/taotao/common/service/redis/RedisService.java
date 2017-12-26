package com.taotao.common.service.redis;

import java.util.List;

public interface RedisService {
	
	/**
	 * 设置键名为key的键值
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value);
	
	/**
	 * 设置键名为key的键值；并对key设置过期时间
	 * @param key
	 * @param seconds 过期时间；单位为秒
	 * @param value
	 * @return
	 */
	public String setex(String key, int seconds, String value);
	
	/**
	 * 对key设置过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(String key, int seconds);
	
	/**
	 * 获取key对于的键值
	 * @param key
	 * @return
	 */
	public String get(String key);
	
	/**
	 * 将key对应的键删除
	 * @param key
	 * @return
	 */
	public Long del(String key);
	
	/**
	 * 对key对应的键递增，默认步长为1
	 * @param key
	 * @return
	 */
	public Long incr(String key);
	
	//设置key对应的散列的某个域的值
	public Long hset(String key, String field, String value);
	
	//获取某个key对应的域的值
	public String hget(String key, String field);
	
	//获取某个key对应的所有域的值集合
	public List<String> hvals(String key);
	
	//删除某个key对应散列的域
	public Long hdel(String key, String field);
}
