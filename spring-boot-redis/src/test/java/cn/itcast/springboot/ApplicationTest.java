package cn.itcast.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTest {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() {
		
		//设置字符串类型的键值
		redisTemplate.boundValueOps("key-a").set("value-a");
		
		//获取值
		String str = redisTemplate.boundValueOps("key-a").get();
		System.out.println(str);
	}
	
	@Test
	public void testCluster() {
		
		//设置字符串类型的键值
		for(int i = 1; i <= 10; i++) {
			redisTemplate.boundValueOps("key-a-" + i).set("value-a"+i);
		}
		
		//获取值
		String str = redisTemplate.boundValueOps("key-a-4").get();
		System.out.println(str);
	}

}
