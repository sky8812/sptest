package com.ez.herb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ajaxTest")
public class AjaxTestController {
	public Logger logger=LoggerFactory.getLogger(AjaxTestController.class);
	
	@RequestMapping("/ajaxTest1.do")
	public void ajaxTest1() {
		logger.info("ajaxTest1 화면!");
		
	}
	
	@RequestMapping("/search.do")
	@ResponseBody
	public String search(@RequestParam(required = false) String keyword, @RequestParam(required = false) String id) {
		logger.info("ajax-search.do 요청, 파라미터 keyword={},id={}",keyword,id);
		String result=id+","+keyword+",sk,sbs,sm";
		return result;
	}
	
	@RequestMapping("/search2.do")
	public String search2(Model model) {
		logger.info("기존방식 search2.do 요청");
		String result="sk,sbs,sm";
		model.addAttribute("res",result);
		return "ajaxTest/test";
	}
	
	@RequestMapping("/ajaxTest2.do")
	public void ajaxTest2() {
		logger.info("ajaxTest2페이지");
	}
	
	@RequestMapping("/view.do")
	@ResponseBody
	public MemoVO ajax_view() {
		//메시지 컨버터가 json으로 변환해줌(responsebody붙이면)
		
		logger.info("ajax-view.do 요청");
		MemoVO memoVo=new MemoVO(1, "홍길동", "안녕");
		
		//{"no":1,"name":"홍길동","content":"안녕"} => 이렇게 json으로 변환되서 보내줌
		
		return memoVo;
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public List<MemoVO> ajax_list(){
		logger.info("ajax-list.do 요청");
		MemoVO vo=new MemoVO(1, "김길동", "안녕하세요 ");
		MemoVO vo2=new MemoVO(2, "이길동", "안녕 ");
		MemoVO vo3=new MemoVO(3, "한길동", "하이 ");
		
		List<MemoVO> list=new ArrayList<MemoVO>();
		
		
		list.add(vo);
		list.add(vo2);
		list.add(vo3);
		
		//list가 json으로 변환되서 response body로 바로 들어감
		//[{"no":1,"name":"김길동","content":"안녕하세요 "},{"no":2,"name":"이길동","content":"안녕 "},{"no":3,"name":"한길동","content":"하이 "}]

		return list;
	}
	
	@RequestMapping("/ajaxTest3.do")
	public void ajaxTest3() {
		logger.info("ajaxTest3 페이지");
	}
	
	@RequestMapping("/view2.do")
	@ResponseBody
	public MemoVO ajaxTest3_post(@RequestParam(defaultValue = "0") int no) {
		//{"no":10,"name":"홍길동","content":"내용"}
		logger.info("ajaxTest3 페이지 파라미터 no={}",no);
		MemoVO vo=new MemoVO(no, "홍길동", "내용");
		return vo;
	}
	
	@RequestMapping("/memoWrite.do")
	@ResponseBody
	public ResultVO memoWrite(@ModelAttribute MemoVO memoVo) {
		//{"message":"메모 등록 성공","data":{"no":10,"name":"hong","content":"hi"}}
		logger.info("ajax-memoWrite.do 요청, 파라미터 memoVo={}",memoVo);
		memoVo.setNo(10);
		ResultVO resultVo=new ResultVO();
		resultVo.setMessage("메모 등록 성공");
		resultVo.setData(memoVo);
		return resultVo;
	}
	
	@RequestMapping("/apiJson.do")
	public String apiJson() {
		logger.info("apiJson.jsp 화면 보여주기");
		
		return "ajaxTest/apiSampleApplicationJSON";
	}
	
	@RequestMapping("/apiXML.do")
	public String apiXml() {
		logger.info("apiXML.jsp 화면 보여주기");
		
		return "ajaxTest/apiSampleApplicationXML";
	}
}
