package com.ez.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.board.model.BoardService;
import com.ez.herb.board.model.BoardVO;

@Controller
public class DeleteController {
	private Logger logger=LoggerFactory.getLogger(DeleteController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/board/delete.do", method = RequestMethod.GET)
	public String delete_get(@RequestParam(defaultValue = "0") int no, ModelMap model) {
		logger.info("글 삭제 화면 보여주기, 파라미터 no={}",no);
		
		if(no==0) {
			model.addAttribute("msg","잘못된 url입니다.");
			model.addAttribute("url","/board/list.do");
			return "common/message";
		}
		
		//글 삭제 화면 보여주기만 하면 되므로 db작업 필요X
		
		return "/board/delete";
	}
	
	@RequestMapping(value="/board/delete.do", method = RequestMethod.POST)
	public String delete_post(@ModelAttribute BoardVO boardVo, ModelMap model) {
		logger.info("글 삭제 처리 파라미터 boardVo={}",boardVo);
		
		String msg="", url="/board/delete.do?no="+boardVo.getNo();
		if(boardService.selectPWdCheck(boardVo)) { //비밀번호 일치
			int cnt=boardService.deleteBoard(boardVo.getNo());
			logger.info("글 삭제 결과 cnt={}",cnt);
			
			if(cnt>0) {
				msg="글 삭제 성공";
				url="/board/list.do";
			}else {
				msg="글 삭제 실패";
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		return "common/message";
	}
}
