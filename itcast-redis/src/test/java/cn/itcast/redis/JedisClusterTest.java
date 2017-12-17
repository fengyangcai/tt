package cn.itcast.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class JedisClusterTest {

	@Test
	public void test() {
		//设置redis集群节点集合
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.12.168", 7001));
		nodes.add(new HostAndPort("192.168.12.168", 7002));
		nodes.add(new HostAndPort("192.168.12.168", 7003));
		nodes.add(new HostAndPort("192.168.12.168", 7004));
		nodes.add(new HostAndPort("192.168.12.168", 7005));
		nodes.add(new HostAndPort("192.168.12.168", 7006));
		
		//创建jedisCluster
		JedisCluster jedisCluster = new JedisCluster(nodes);
		
		for(int i = 1; i<= 100; i++) {
			jedisCluster.set("itcast-cluster-"+i, "传智播客-"+i);
		}
		
		String str = jedisCluster.get("itcast-cluster-21");
		System.out.println(str);
		
	}

}
