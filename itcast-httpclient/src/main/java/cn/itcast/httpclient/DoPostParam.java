package cn.itcast.httpclient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class DoPostParam {

	public static void main(String[] args) throws Exception {
		//创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建httpPost连接
		HttpPost httpPost = new HttpPost("https://www.oschina.net");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36");
		
		//https://www.oschina.net/search?scope=project&q=java
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("scope", "project"));
		nvps.add(new BasicNameValuePair("q", "java"));
		
		//设置表单项
		httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		
		CloseableHttpResponse response = null;
		try {
			//利用httpClient执行httpGet请求
			response = httpClient.execute(httpPost);
			//处理结果
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String content = EntityUtils.toString(response.getEntity(), "utf-8");
				//System.out.println(content);
				FileUtils.writeStringToFile(new File("D:\\itcast\\test.html"), content);
			}
		} finally {
			if(response != null){
				response.close();
			}
			httpClient.close();
		}

	}

}
