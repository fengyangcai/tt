package cn.itcast.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest {

	@Test
	public void test() {
		//创建jedisPool
		JedisPool jedisPool = new JedisPool("192.168.12.168", 6379);
		
		//Jedis jedis = new Jedis("192.168.12.168", 6379);
		Jedis jedis = jedisPool.getResource();
		
		jedis.set("itcast--pool", "传智播客--pool");
		
		String str = jedis.get("itcast--pool");
		System.out.println(str);
		
		//关闭
		jedis.close();
		
		jedisPool.close();
	}

}
