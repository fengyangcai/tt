package cn.itcast.httpclient.service;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.httpclient.vo.HttpResult;

public class HttpClientServiceTest {
	
	private HttpClientService httpClientService;

	@Before
	public void setUp() throws Exception {
		httpClientService = new HttpClientService();
	}

	@Test
	public void testDoGetString() throws ClientProtocolException, URISyntaxException, IOException {
		//根据商品id查询商品
		String url = "http://manage.taotao.com/rest/item/interface/43";
		String itemJsonStr = httpClientService.doGet(url);
		System.out.println(itemJsonStr);
	}

	@Test
	public void testDoPostStringMapOfStringObject() throws ClientProtocolException, URISyntaxException, IOException {
		String url = "http://manage.taotao.com/rest/item/interface";
		Map<String, Object> param = new HashMap();
		param.put("title", "利用接口从代码中添加的商品");
		param.put("sellPoint", "我会Java");
		param.put("num", 12);
		param.put("price", 20000L);
		param.put("cid", "123");
		param.put("status", "1");
		//新增商品
		HttpResult httpResult = httpClientService.doPost(url, param);
		System.out.println(httpResult);
	}

	@Test
	public void testDoPutStringMapOfStringObject() throws ClientProtocolException, URISyntaxException, IOException {
		String url = "http://manage.taotao.com/rest/item/interface";
		Map<String, Object> param = new HashMap();
		param.put("title", "22222利用接口从代码中添加的商品");
		param.put("sellPoint", "我精通Java");
		param.put("num", 12);
		param.put("price", 30000L);
		param.put("cid", 123L);
		param.put("status", "1");
		param.put("id", 43L);
		//新增商品
		HttpResult httpResult = httpClientService.doPut(url, param);
		System.out.println(httpResult);
	}

	@Test
	public void testDoDeleteString() throws ClientProtocolException, URISyntaxException, IOException {
		String url = "http://manage.taotao.com/rest/item/interface?ids=44";
		HttpResult httpResult = httpClientService.doDelete(url);
		System.out.println(httpResult);
	}

	@Test
	public void testDoDeleteStringMapOfStringObject() throws ClientProtocolException, URISyntaxException, IOException {
		String url = "http://manage.taotao.com/rest/item/interface";
		Map<String, Object> param = new HashMap();
		param.put("ids", 43L);
		
		HttpResult httpResult = httpClientService.doDelete(url, param);
		System.out.println(httpResult);
	}

}
