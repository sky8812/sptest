package com.ez.herb.member.controller;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.herb.member.model.MemberService;
import com.ez.herb.member.model.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
private Logger logger=LoggerFactory.getLogger(MemberController.class);

@Autowired
private MemberService memberService;

	@RequestMapping("/agreement.do")
	public String agreement() {
		logger.info("약관 동의 화면 보여주기");
		return "/member/agreement";
	}
	
	@RequestMapping("/register.do")
	public void register() { //void 일 때: do요청과 jsp이름 같을 때 사용 (do를 jsp로 자동변환함)
		logger.info("회원가입 화면 보여주기");
		
	}
	
	@RequestMapping("/memberWrite.do")
	public String memberWrite(@ModelAttribute MemberVO vo, Model model, @RequestParam String email3) {
		logger.info("회원가입 처리, 파라미터 vo={},",vo);
		
		//hp
		if(vo.getHp2()==null || vo.getHp2().isEmpty() || vo.getHp3()==null || vo.getHp3().isEmpty()) {
			vo.setHp1(" ");
			vo.setHp2(" ");
			vo.setHp3(" ");
		}
		
		//email
		if(vo.getEmail1()==null || vo.getEmail1().isEmpty()) {
			vo.setEmail2(" ");
		}else {
			if(vo.getEmail2().equals("etc")) {
				if(email3==null || email3.isEmpty()) {
					vo.setEmail1("");
					vo.setEmail2("");
				}else {
					vo.setEmail2(email3);
					
				}
			
			}
		}
		
		int cnt=memberService.insertMember(vo);
		logger.info("회원가입 처리 결과 cnt={}",cnt);
		
		String msg="", url="/member/register.do";
		if(cnt>0) {
			msg="회원가입되었습니다.";
			url="/login/login.do";
		}else {
			msg="회원가입되었습니다.";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		
		return "common/message";
	}
	
	@RequestMapping("/checkUserid.do")
	public String checkUserid(@RequestParam String userid, Model model) {
		logger.info("아이디 중복확인 파라미터 userid={}",userid);
		
		int result=0;
		if(userid!=null && !userid.isEmpty()) {
			result=memberService.duplicateUserid(userid);
			logger.info("아이디 중복확인 결과, result={}",result);
			
		}
		
		model.addAttribute("result",result);
		model.addAttribute("USEFUL_USERID",MemberService.USEFUL_USERID);
		model.addAttribute("NON_USEFUL_USERID",MemberService.NON_USEFUL_USERID);
		
		return "member/checkUserid";
	}
	
	@RequestMapping(value="/memberEdit.do", method = RequestMethod.GET)
	public String memEdit_get(HttpSession session, Model model) {
		String userid=(String) session.getAttribute("userid");
		logger.info("회원수정 화면 파라미터 userid={}",userid);
		
		//로그인 먼저하도록
		/*
		if(userid==null || userid.isEmpty()) {
			model.addAttribute("msg","먼저 로그인하세요!");
			model.addAttribute("url", "/login/login.do");
			return "common/message";
		}
		*/
		
		MemberVO memberVo=memberService.selectByUserid(userid);
		logger.info("회원수정 조회 결과, vo={}",memberVo);
		model.addAttribute("vo",memberVo);
		return "member/memberEdit";
	}
	
	@RequestMapping(value="/memberEdit.do", method = RequestMethod.POST)
	public String memEdit_post(@ModelAttribute MemberVO memberVo, @RequestParam String email3, Model model, HttpSession session) {
		
		String userid=(String) session.getAttribute("userid");
		memberVo.setUserid(userid);
		logger.info("회원수정 처리, 파라미터 memberVo={}",memberVo);
		//hp
		String hp2=memberVo.getHp2();
		String hp3=memberVo.getHp3();
		
		if(hp2==null || hp2.isEmpty() || hp3==null || hp3.isEmpty()) {
			memberVo.setHp1("");
			memberVo.setHp2("");
			memberVo.setHp3("");
		}
		
		//email
		String email1=memberVo.getEmail1();
		String email2=memberVo.getEmail2();
		if(email1==null || email1.isEmpty()) {
			memberVo.setEmail1("");
			memberVo.setEmail2("");
		}else {
			if(email2.equals("etc") && email3!=null && !email3.isEmpty()) {
				memberVo.setEmail2(email3);
			}
		}

		String msg="", url="/member/memberEdit.do";
		int result=memberService.loginCheck(userid, memberVo.getPwd());
		if(result==MemberService.LOGIN_OK) {
			int cnt=memberService.updateMember(memberVo);
			logger.info("회원 수정 처리 결과 cnt={}",cnt);
			
			if(cnt>0) {
				msg="회원정보 수정되었습니다.";
			}else {
				msg="회원정보 수정 실패";
				
			}
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else{
			msg="비밀번호 체크 실패";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		
		return "common/message";
		 
	}
	
	@RequestMapping(value="/memberOut.do", method = RequestMethod.GET)
	public String memberOut_get() {
		logger.info("회원탈퇴 화면 보여주기");
		return "/member/memberOut";
	}
	
	@RequestMapping(value="/memberOut.do", method = RequestMethod.POST)
	public String memberOut_post(@RequestParam String pwd, HttpSession session, Model model,HttpServletResponse response) {
		String userid=(String) session.getAttribute("userid");
		logger.info("회원탈퇴 처리 파라미터 userid={}, pwd={}",userid, pwd);
		
		int result=memberService.loginCheck(userid, pwd);
		String msg="", url="/member/memberOut.do";
		if(result==MemberService.LOGIN_OK) {
			int cnt=memberService.withdrawMember(userid);
			if(cnt>0) {
				msg="회원 탈퇴처리되었습니다.";
				url="/index.do";
				
				//세션, 쿠키 정보 삭제
				session.removeAttribute("userid");
				session.removeAttribute("userName");
				
				Cookie ck=new Cookie("ck_userid",userid);
				ck.setPath("/"); // /와 하위폴더에서 사용가능
				ck.setMaxAge(0);
				response.addCookie(ck);
				
			}else {
				msg="회원 탈퇴 실패 ";
			}
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
			
		}else {
			msg="회원 탈퇴 실패 ";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		return "common/message";
	}
	
	@RequestMapping("/ajaxDupUserid.do")
	@ResponseBody
	public Boolean ajaxDupUserid(@RequestParam String userid) {
		logger.info("ajax방식-아이디 중복확인 파라미터, userid={}",userid);
		
		int cnt=memberService.duplicateUserid(userid);
		logger.info("ajax-아이디 중복확인 결과 cnt={}",cnt);
		
		Boolean bool=false;
		if(cnt==MemberService.USEFUL_USERID) {
			bool=true;
		}else if(cnt==MemberService.NON_USEFUL_USERID) {
			bool=false;
		}
		return bool;
	}
	
	
}
