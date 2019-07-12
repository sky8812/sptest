package com.ez.herb.email;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailController {
	private Logger logger=LoggerFactory.getLogger(EmailController.class);
	@Autowired private EmailSender emailSender;
	
	@RequestMapping("/email/send.do")
	public String send() {
		logger.info("이메일 발송 화면 처리");
		
		String subject="안녕하세요 허브몰입니다.";
		String content="오늘은 금요일!! 내일은 토요일!!";
		String recipient="8sky812@gmail.com";
		String sender="peoplejob@admin.com";
		try {
			emailSender.sendEmail(subject, content, recipient, sender);
			logger.info("이메일 발송 성공");
		} catch (MessagingException e) {
			logger.info("이메일 발송 실패");
			e.printStackTrace();
		}
		
		return "redirect:/index.do";
		
	}
	
	@RequestMapping("/email/emailTest.do")
	public String emailshow() {
		logger.info("이메일 발송 화면 보여주기");
		return "email/emailTest";
	}
}
