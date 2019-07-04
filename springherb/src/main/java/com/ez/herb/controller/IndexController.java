package com.ez.herb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
private Logger logger=LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/index.do")
	public String index() {
		logger.info("index페이지 보여주기");
		return "index";
	}
}
