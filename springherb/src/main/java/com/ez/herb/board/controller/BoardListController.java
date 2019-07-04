package com.ez.herb.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ez.herb.board.model.BoardService;
import com.ez.herb.board.model.BoardVO;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.SearchVO;
import com.ez.herb.common.WebUtility;

@Controller
public class BoardListController {
	private Logger logger=LoggerFactory.getLogger(BoardListController.class);
	@Autowired
	private BoardService boardService;

	@RequestMapping("/board/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model) {
		//1.
		logger.info("글목록 파라미터 searchVo={}",searchVo);
		
		//2.
		// [1] PaginationInfo 객체 먼저 생성
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(WebUtility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		// [2] SearchVo에 페이징 관련 변수 셋팅
		searchVo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("셋팅 후 searchVo={}",searchVo);
		
		// [3] 조회처리
		List<BoardVO> list=boardService.selectAll(searchVo);
		logger.info("글목록 결과, list.size={}",list.size());
		
		// [4] 전체 레코드 개수 조회
		int totalRecord=0;
		totalRecord=boardService.selectTotalCount(searchVo);
		logger.info("전체 레코드 조회결과, totalRecord={}",totalRecord);
		
		// [5] PaginationInfo에 totalRecord 값 셋팅
		pagingInfo.setTotalRecord(totalRecord);
		
		
		//3.
		model.addAttribute("list",list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "board/list";
	}
}
