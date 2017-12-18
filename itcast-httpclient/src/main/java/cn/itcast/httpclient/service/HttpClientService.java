package cn.itcast.httpclient.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.itcast.httpclient.vo.HttpResult;

public class HttpClientService {
	
	private CloseableHttpClient httpClient;
	
	public HttpClientService() {
		//创建httpClient对象
		httpClient = HttpClients.createDefault();
	}
	
	/**
	 * 执行get请求不携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
		
		return doGet(url, null);
	}
	
	/**
	 * 执行get请求携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url, Map<String, Object> param) throws URISyntaxException, ClientProtocolException, IOException {
		//1、处理请求地址和参数
		URIBuilder uriBuilder = new URIBuilder(url);
		if(param != null) {
			Set<Entry<String, Object>> entrySet = param.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}
		
		//2、创建http（get/post/put/delete）对象
		HttpGet http = new HttpGet(uriBuilder.build());
		
		//3、利用httpClient执行http请求
		CloseableHttpResponse httpResponse = null;
		
		try {
			httpResponse = httpClient.execute(http);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return new String(EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
			}
		} finally {
			if(httpResponse != null) {
				httpResponse.close();
			}
			httpClient.close();
		}
		//4、返回数据
		return null;
	}
	
	/**
	 * 执行post请求不携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPost(String url) throws URISyntaxException, ClientProtocolException, IOException {
		return doPost(url, null);
	}
	
	/**
	 * 执行post请求携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, Object> param) throws URISyntaxException, ClientProtocolException, IOException {
		//1、创建http（get/post/put/delete）对象
		HttpPost http = new HttpPost(url);
		
		//2、处理请求参数
		if(param != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, Object>> entrySet = param.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			
			//将参数设置到请求对象
			http.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		}
		
		//3、利用httpClient执行http请求
		CloseableHttpResponse httpResponse = null;
		
		try {
			httpResponse = httpClient.execute(http);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(httpResponse.getEntity() != null) {
				return new HttpResult(statusCode, 
						EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
			}
			//4、返回数据
			return new HttpResult(statusCode);
		} finally {
			if(httpResponse != null) {
				httpResponse.close();
			}
			httpClient.close();
		}
		
	}
	
	/**
	 * 执行put请求不携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPut(String url) throws URISyntaxException, ClientProtocolException, IOException {
		return doPut(url, null);
	}
	
	
	/**
	 * 执行put请求携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPut(String url, Map<String, Object> param) throws URISyntaxException, ClientProtocolException, IOException {
		//1、创建http（get/post/put/delete）对象
		HttpPut http = new HttpPut(url);
		
		//2、处理请求参数
		if(param != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, Object>> entrySet = param.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			
			//将参数设置到请求对象
			http.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		}
		
		//3、利用httpClient执行http请求
		CloseableHttpResponse httpResponse = null;
		
		try {
			httpResponse = httpClient.execute(http);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(httpResponse.getEntity() != null) {
				return new HttpResult(statusCode, 
						EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
			}
			//4、返回数据
			return new HttpResult(statusCode);
		} finally {
			if(httpResponse != null) {
				httpResponse.close();
			}
			httpClient.close();
		}
		
	}
	
	
	/**
	 * 执行delete请求不携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doDelete(String url) throws URISyntaxException, ClientProtocolException, IOException {
		//1、创建http（get/post/put/delete）对象
		HttpDelete http = new HttpDelete(url);
		
		//2、利用httpClient执行http请求
		CloseableHttpResponse httpResponse = null;
		
		try {
			httpResponse = httpClient.execute(http);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(httpResponse.getEntity() != null) {
				return new HttpResult(statusCode, 
						EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
			}
			//3、返回数据
			return new HttpResult(statusCode);
		} finally {
			if(httpResponse != null) {
				httpResponse.close();
			}
			httpClient.close();
		}
		
	}
	
	/**
	 * 执行delete请求不携带参数
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doDelete(String url, Map<String, Object> param) throws URISyntaxException, ClientProtocolException, IOException {
		if(param == null) {
			param = new HashMap<String, Object>();
		}
		param.put("_method", "delete");
		
		return doPost(url, param);
	}
	
}
