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
public class EditController {

	private Logger logger=LoggerFactory.getLogger(EditController.class);
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/board/edit.do", method = RequestMethod.GET)
	public String edit_get(@RequestParam(defaultValue = "0") int no, ModelMap model) {
		logger.info("글 수정 처리화면 보여주기, 파라미터 no={}",no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url","/board/detail.do?no="+no);
			return "common/message";
		}
		
		BoardVO boardVo=boardService.selectByNo(no);
		logger.info("글 수정 화면 보여주기 결과 boardVo={}",boardVo);
		
		model.addAttribute("vo", boardVo);
		
		return "board/edit";
	}
	
	@RequestMapping(value="/board/edit.do", method = RequestMethod.POST)
	public String edit_post(@ModelAttribute BoardVO boardVo, ModelMap model) {
		logger.info("글 수정 처리 파라미터 boardVo={}",boardVo);
		
		String pwd=boardService.selectPwd(boardVo.getNo());
		logger.info("실제 비밀번호 pwd={}",pwd);
		
		String msg="", url="/board/edit.do?no="+boardVo.getNo();
		if(boardService.selectPWdCheck(boardVo)) { //비밀번호 일치
			int cnt=boardService.updateBoard(boardVo);
			logger.info("update 결과 cnt={}",cnt);
			
			if(cnt>0) {
				msg="글 수정되었습니다.";
				url="/board/detail.do?no="+boardVo.getNo();
			}else {
				msg="글 수정 실패";
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "common/message";
		
		/*
		if(boardVo.getPwd()==null || boardVo.getPwd().isEmpty()) {
			model.addAttribute("msg", "비밀번호를 입력해주세요!");
			model.addAttribute("url","/board/edit.do?no="+boardVo.getNo());
			return "common/message";
		}
		if(!boardVo.getPwd().equals(pwd)) {
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다!");
			model.addAttribute("url","/board/edit.do?no="+boardVo.getNo());
			return "common/message";
		}
		
		return "redirect:/board/detail.do?no="+boardVo.getNo();
		 */
		
		
	
		
	}
	
	
	
}
