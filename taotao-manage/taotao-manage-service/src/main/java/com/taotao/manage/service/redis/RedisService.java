package com.taotao.manage.service.redis;

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
}
