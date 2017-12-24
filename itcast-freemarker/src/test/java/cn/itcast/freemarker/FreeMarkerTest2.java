package cn.itcast.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.itcast.freemarker.vo.Employee;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest2 {
	
	/**
	 * 输出到某个文件
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest2.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("comman.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("emp", new Employee(123L, "传智播客", false, 10000.00, new Date(), 0));
		dataModel.put("empList", Arrays.asList(new Employee(121L, "传智播客1", false, 10000.00, new Date(), 1),
				new Employee(122L, "传智播客2", true, 10000.00, new Date(), 2),
				new Employee(123L, "传智播客3", false, 10000.00, new Date(), 3)));
		
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\comman.html"));
		//输出
		template.process(dataModel, fileWriter);
	}
	/**
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest2.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("assign.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\assign.html"));
		//输出
		template.process(dataModel, fileWriter);
	}
	/**
	 * @throws Exception
	 */
	@Test
	public void test03() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest2.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("include.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\include.html"));
		//输出
		template.process(dataModel, fileWriter);
	}
	/**
	 * @throws Exception
	 */
	@Test
	public void test04() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest2.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("empty.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\empty.html"));
		//输出
		template.process(dataModel, fileWriter);
	}
	/**
	 * @throws Exception
	 */
	@Test
	public void test05() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest2.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("listAndMap.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\listAndMap.html"));
		//输出
		template.process(dataModel, fileWriter);
	}
	/**
	 * @throws Exception
	 */
	@Test
	public void test06() throws Exception {
		//创建freemarker配置对象
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//设置模块路径
		configuration.setClassForTemplateLoading(FreeMarkerTest2.class, "/ftl");
		//获取模版
		Template template = configuration.getTemplate("macro.ftl");
		//获取数据
		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		//输出对象
		FileWriter fileWriter = new FileWriter(new File("D:\\itcast\\test\\macro.html"));
		//输出
		template.process(dataModel, fileWriter);
	}

}
