package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manage.service.ContentService;

@RequestMapping("/index")
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toIndexPage() {
		ModelAndView mv = new ModelAndView("index");
		try {
			//加载6条大广告数据
			mv.addObject("bigAdData", contentService.getPortalBigAdData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
