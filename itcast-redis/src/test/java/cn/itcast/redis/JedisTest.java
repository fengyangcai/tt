package cn.itcast.redis;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	@Test
	public void test() {
		//创建jedis
		Jedis jedis = new Jedis("192.168.12.168", 6379);
		jedis.set("itcast", "传智播客");
		
		String str = jedis.get("itcast");
		System.out.println(str);
		
		//关闭
		jedis.close();
	}

}
