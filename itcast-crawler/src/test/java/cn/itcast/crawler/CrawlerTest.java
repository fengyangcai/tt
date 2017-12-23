package cn.itcast.crawler;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CrawlerTest {
	
	//数据来源
	private static final String FETCH_URL = "https://list.jd.com/list.html?cat=9987,653,655&page=";

	@Test
	public void test() throws Exception {
		//1、获取总页数
		Integer totalPages = 0;
		String html = doGet(FETCH_URL+"1");
		Document document = Jsoup.parse(html);
		
		String totalPagesStr = document.select(".f-pager i").text();
		
		totalPages = Integer.parseInt(totalPagesStr);
		//2、遍历每页数据
		for(int i = 1; i <= totalPages; i++) {
			html = doGet(FETCH_URL+i);
			document = Jsoup.parse(html);//每页数据
			Elements itemElements = document.select(".gl-item");
			for (Element itemEle : itemElements) {
				//获取商品标题
				String title = itemEle.select(".p-name em").text();
				System.out.println(title);
			}
			
			if(i == 1) {
				break;
			}
		}
	}

	
	public static String doGet(String url) throws Exception {
		//创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建httpGet连接
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			//利用httpClient执行httpGet请求
			response = httpClient.execute(httpGet);
			//处理结果
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} finally {
			if(response != null){
				response.close();
			}
			httpClient.close();
		}
		return null;
	}
}
