package com.ez.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.board.model.BoardService;

@Controller
public class CountUpdateController {
	private Logger logger=LoggerFactory.getLogger(CountUpdateController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue = "0") int no, Model model) {
		logger.info("조회수 증가, 파라미터 no={}",no);
		
		if(no==0) {
			model.addAttribute("msg","잘못된 url입니다.");
			model.addAttribute("url","/board/list.do");
			return "common/message";
		}
		
		int cnt=boardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}",cnt);
		
		//redirect
		return "redirect:/board/detail.do?no="+no;
	}
}
