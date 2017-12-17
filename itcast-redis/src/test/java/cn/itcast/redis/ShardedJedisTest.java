package cn.itcast.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class ShardedJedisTest {

	@Test
	public void test() {
		//创建shardedJedis；分片式伪集群；如果在一个redis找不到数据则到其它里面去查找
		List<JedisShardInfo> shards = new ArrayList<>();
		shards.add(new JedisShardInfo("192.168.12.168", 6379));
		shards.add(new JedisShardInfo("192.168.12.168", 6379));
		
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("itcast", "传智播客2");
		
		String str = shardedJedis.get("itcast");
		System.out.println(str);
		
		//关闭
		shardedJedis.close();
	}

}
