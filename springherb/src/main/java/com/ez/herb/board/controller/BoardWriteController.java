package com.ez.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ez.herb.board.model.BoardService;
import com.ez.herb.board.model.BoardVO;

@Controller
public class BoardWriteController {
	private Logger logger=LoggerFactory.getLogger(BoardWriteController.class);
	@Autowired private BoardService boardService;
	
	@RequestMapping(value="/board/write.do", method = RequestMethod.GET)
	public String write_get() { 	//modelandView는 객체생성 해야하므로, String으로 뷰페이지 리턴
		//1.
		logger.info("글쓰기 화면 보여주기");
		
		//2.
		//3. 뷰페이지 리턴
		return "board/write";
		
	}
	
	@RequestMapping(value="/board/write.do", method = RequestMethod.POST)
	public String write_post(@ModelAttribute BoardVO vo, Model model) { //command객체
		//1.
		logger.info("글쓰기 처리 파라미터 vo={}",vo);
		//logger.info("글쓰기 처리 파라미터 vo={}, n={}",vo, n); =>2개가지만 가능 (slf4j.Logger만 {}같은 format형태 가능)
		
		//2.
		int cnt=boardService.insertBoard(vo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		//3. model에 결과 저장 (request에 저장하듯이)
		String msg="", url="";
		
		if(cnt>0) {
			msg="글쓰기 처리되었습니다.";
			url="/board/list.do";
		}else {
			msg="글쓰기 처리 실패";
			url="/board/write.do";
			
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		//4. 뷰페이지 리턴
		return "common/message";
	}
}
