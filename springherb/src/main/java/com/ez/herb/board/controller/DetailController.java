package com.ez.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.board.model.BoardService;
import com.ez.herb.board.model.BoardVO;

@Controller
public class DetailController {
private Logger logger=LoggerFactory.getLogger(DetailController.class);

@Autowired
private BoardService boardService;


	@RequestMapping("/board/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no, ModelMap model) { //String 의미 : view페이지 리턴
		logger.info("글 상세보기, 파라미터 no={}",no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/board/list.do");
			
			return "/common/message";
		}
		
		BoardVO boardVo=boardService.selectByNo(no); 
		logger.info("상세보기 조회 결과 vo={}",boardVo);
		model.addAttribute("vo", boardVo);
		
		return "board/detail";
		
	}
}
