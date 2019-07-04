package com.ez.herb.zipcode.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.WebUtility;
import com.ez.herb.zipcode.model.ZipcodeService;
import com.ez.herb.zipcode.model.ZipcodeVO;

@Controller
public class ZipcodeController {
	private Logger logger=LoggerFactory.getLogger(ZipcodeController.class);
	
	@Autowired
	private ZipcodeService zipcodeService;
	
	@RequestMapping("/zipcode/zipcode.do")
	public String zipcode(@ModelAttribute ZipcodeVO vo, Model model) {
		logger.info("zipcode화면 파라미터 vo={}",vo);
		//db작업
		
		//페이징 처리
		//[1] PaginationInfo
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(WebUtility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(WebUtility.ZIPCODE_RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(vo.getCurrentPage());
		
		//[2] SearchVO => ZipcodeVO
		vo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(pagingInfo.getRecordCountPerPage());
		
		
		List<ZipcodeVO>	list=null;
		int totalRecord=0;
		if(vo.getDong()!=null && !vo.getDong().isEmpty()) {
			list=zipcodeService.selectZipcode(vo);
			logger.info("우편번호 조회 결과 list.size={}",list.size());
		
			totalRecord=zipcodeService.getTotalRecord(vo.getDong());
			logger.info("전체 레코드 개수={}",totalRecord);
		}
		
		pagingInfo.setTotalRecord(totalRecord);
		model.addAttribute("pagingInfo",pagingInfo);
		
		model.addAttribute("list",list);
		return "zipcode/zipcode";
	}
	
	
	
	
	
	
}
