package com.ez.herb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private Logger logger=LoggerFactory.getLogger(AdminController.class);
	
	@RequestMapping("/adminMain.do")
	public String adminMain() {
		logger.info("관리자 메인화면 보여주기");
		return "admin/adminMain";
	}
}
