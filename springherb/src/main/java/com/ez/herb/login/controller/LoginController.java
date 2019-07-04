package com.ez.herb.login.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.member.model.MemberService;
import com.ez.herb.member.model.MemberVO;

@Controller
@RequestMapping("/login")
public class LoginController {
	private Logger logger=LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private MemberService memberService;

	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public void login_get() {
		logger.info("로그인 화면 보여주기");
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login_post(@ModelAttribute MemberVO memberVo, @RequestParam(required = false) String saveId, 
			Model model, HttpServletRequest request, HttpServletResponse response) {
		//userid와 pwd를 requestparam으로 불러와도 됨
		logger.info("로그인 처리 파라미터 vo={}, saveId={}", memberVo, saveId);
		
		int result=memberService.loginCheck(memberVo.getUserid(), memberVo.getPwd());
		
		logger.info("로그인 처리 결과 result={}",result);
		
		String msg="", url="/login/login.do";
		if(result==MemberService.LOGIN_OK) {
			msg="로그인되었습니다.";
			
			//로그인 성공
			MemberVO memVo2=memberService.selectByUserid(memberVo.getUserid());
			
			//[1] session에 저장 (request에서 받아오기)
			HttpSession session=request.getSession();
			session.setAttribute("userid", memberVo.getUserid());
			session.setAttribute("userName", memVo2.getName());
			
			//[2] 쿠키에 저장
			Cookie ck=new Cookie("ck_userid", memberVo.getUserid());
			ck.setPath("/"); //안쓰면 못찾음 => 다른경로에서 쿠키지우려고하면 접근안되서 path설정해줘야함(/로 설정하면 지금 경로와 하위경로에서 사용가능)
			
			if(saveId!=null) {//아이디 저장하기를 체크한 경우
				//checkbox => 체크하면 on, 안하면 null 
				//(equals(on)으로 하게 되면 null point exception때문에 null이 아닐때는 check, null일 때는 체크안된걸로 확인하기)
				
				ck.setMaxAge(1000*24*60*60); //유효기간 1000일
				response.addCookie(ck);
			}else {
				ck.setMaxAge(0); //쿠키 삭제
				response.addCookie(ck); 
			}
		
			msg=memVo2.getName()+"님 로그인되었습니다.";
			url="/index.do";
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
			
		}else if(result==MemberService.ID_NONE){
			msg="아이디가 존재하지 않습니다.";
			
		}else {
			msg="로그인 처리 실패.";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		
		return "common/message";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) { 
		logger.info("로그아웃 처리");
		
		//session.invalidate();
		//기존의 session데이터 다 없애면 관리자까지 없어지므로 invalidate는 X
		
		session.removeAttribute("userid");
		session.removeAttribute("userName");
		
		return "redirect:/index.do";
	}
	
	
}
