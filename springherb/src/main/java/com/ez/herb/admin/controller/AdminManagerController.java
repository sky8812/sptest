package com.ez.herb.admin.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ez.herb.manager.model.ManagerService;
import com.ez.herb.manager.model.ManagerVO;

@Controller
@RequestMapping("/admin/manager")
public class AdminManagerController {
	private Logger logger=LoggerFactory.getLogger(AdminManagerController.class);
	@Autowired
	private ManagerService managerService;

	@RequestMapping(value="/register.do", method = RequestMethod.GET)
	public String register(Model model) {
		logger.info("관리자 등록화면 보여주기");
			List<Map<String, Object>> list=managerService.selectAuthorityAll();
			logger.info("관리자 등록화면-권한 조회 결과 list.size={}",list.size());
			
			model.addAttribute("list",list);
			
		return "admin/manager/register";
	}
	
	@RequestMapping(value="/register.do", method = RequestMethod.POST)
	public String register_post(@ModelAttribute ManagerVO managerVo, Model model) {
		logger.info("관리자 등록 처리 파라미터 managerVo={}",managerVo);
		int cnt=managerService.insertManager(managerVo);
		logger.info("관리자 등록 결과 cnt={}",cnt);
		String msg="", url="/admin/manager/register.do";
		
		if(cnt>0) {
			msg="관리자 등록되었습니다.";
		}else{
			msg="관리자 등록 실패!";
			
		}
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		return "common/message";
	}
}
