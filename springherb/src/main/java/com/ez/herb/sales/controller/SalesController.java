package com.ez.herb.sales.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.common.DateSearchVO;
import com.ez.herb.sales.model.SalesService;

@Controller
@RequestMapping("/admin")
public class SalesController {
	private Logger logger=LoggerFactory.getLogger(SalesController.class);
	@Autowired private SalesService salesService;
	
	@RequestMapping("/sales/salesStats.do")
	public String salesStats(@ModelAttribute DateSearchVO dateSearchVo, Model model, @RequestParam String type) {
		logger.info("관리자-매출조회 파라미터 dateSearchVo={}",dateSearchVo);
		
		List<Map<String, Object>> list=null;
		if(type.equals("d") || type.equals("m")) { //일별, 월별
			if(dateSearchVo.getYear()!=null && !dateSearchVo.getYear().isEmpty()) {
				if(type.equals("d")) {
					list=salesService.selectSalesByDay(dateSearchVo);
				}else if(type.equals("m")) {
					list=salesService.selectSalesByMonth(dateSearchVo.getYear());
				}
				logger.info("매출조회 결과 list.size={}",list.size());
			}
			
		}else if(type.equals("t")) { //기간별 (startday)
			if(dateSearchVo.getStartDay()!=null && !dateSearchVo.getStartDay().isEmpty()) {
				list=salesService.selectSalesByTerm(dateSearchVo);
				logger.info("기간별 매출조회 결과 list.size={}",list.size());
			}
		}
		model.addAttribute("list",list);
		return "admin/sales/salesStats";
		
		
	}
}
