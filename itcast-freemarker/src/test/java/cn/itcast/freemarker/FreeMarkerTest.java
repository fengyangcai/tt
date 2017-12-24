package cn.itcast.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {

	/**
	 * 将输出到控制台
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("hello.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("msg", "hello itcast.");
		//输出
		template.process(dataModel, new PrintWriter(System.out));
	}
	
	/**
	 * 输出到某个文件
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("hello.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("msg", "hello itcast.");
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\hello.html"));
		//输出
		template.process(dataModel, fileWriter);
	}

}
