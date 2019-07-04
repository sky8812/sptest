package com.ez.herb.admin.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.manager.model.ManagerService;
import com.ez.herb.manager.model.ManagerVO;
import com.ez.herb.member.model.MemberService;

@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {

	private Logger logger=LoggerFactory.getLogger(AdminLoginController.class);
	@Autowired private ManagerService managerService;
	
	@RequestMapping(value="/adminLogin.do", method = RequestMethod.GET)
	public String adminLogin_get() {
		logger.info("관지라 로그인 화면 보여주기");
		return "admin/login/adminLogin";
	}
	
	@RequestMapping(value="/adminLogin.do", method = RequestMethod.POST)
	public String adminLogin_post(@RequestParam String userid ,@RequestParam String pwd, @RequestParam(required = false) String saveId, Model model, 
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("관리자 로그인 처리 파라미터 userid={}, pwd={}",userid, pwd);
		logger.info("saveId={}",saveId);
		
		int result=managerService.adminLoginCheck(userid, pwd);
		
		String msg="", url="/admin/login/adminLogin.do";
		if(result==MemberService.LOGIN_OK) {
			//세션
			ManagerVO managerVo=managerService.selectManager(userid);
			HttpSession session=request.getSession();
			session.setAttribute("adminUserName", managerVo.getName());
			session.setAttribute("adminUserid", userid);
			
			//쿠키
			Cookie ck=new Cookie("ck_admin_userid", userid);
			ck.setPath("/");
			if(saveId!=null) {
				ck.setMaxAge(1000*24*60*60);
				response.addCookie(ck);
			}else {
				ck.setMaxAge(0);
				response.addCookie(ck);
			}
			
			msg=managerVo.getName()+"님 관리자 로그인되었습니다.";
			url="/admin/adminMain.do";
		}else if(result==MemberService.PWD_DISAGREE){
			msg="비밀번호가 일치하지 않습니다.";
		}else if(result==MemberService.ID_NONE){ 
			msg="해당 아이디가 존재하지 않습니다.";
		}else {
			msg="로그인 실패";
			
		}
			model.addAttribute("msg",msg);
			model.addAttribute("url",url);
			return "common/message";
	}
	
	@RequestMapping("/logout.do")
	public String adminLogout(HttpSession session) {
		logger.info("관리자 로그아웃 처리");
		//세션 삭제
		session.removeAttribute("adminUserid");
		session.removeAttribute("adminUserName");
		
		return "redirect:/admin/login/adminLogin.do";
	}
	
}
