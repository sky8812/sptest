package com.ez.herb.order.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.cart.model.CartService;
import com.ez.herb.common.DateSearchVO;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.WebUtility;
import com.ez.herb.member.model.MemberService;
import com.ez.herb.member.model.MemberVO;
import com.ez.herb.order.model.OrderAllVO;
import com.ez.herb.order.model.OrderService;
import com.ez.herb.order.model.OrderVO;

@Controller
@RequestMapping("/shop/order")
public class OrderController {
	private Logger logger=LoggerFactory.getLogger(OrderController.class);
	@Autowired private CartService cartService;
	@Autowired private MemberService memberService;
	@Autowired private OrderService orderService;
	
	
	@RequestMapping(value="/orderSheet.do", method = RequestMethod.GET)
	public String orderSheet(HttpSession session, Model model) {
		String userid=(String)session.getAttribute("userid");
		logger.info("주문 페이지 파라미터 userid={}",userid);
		MemberVO memberVo=memberService.selectByUserid(userid);
		logger.info("주문 페이지, 회원조회 결과 vo={}",memberVo );
		List<Map<String , Object>> list=cartService.selectCartView(userid);
		logger.info("주문 페이지, 장바구니 목록 결과 list.size={}",list.size() );
		
		model.addAttribute("memberVo", memberVo);
		model.addAttribute("cartList",list);
		return "shop/order/orderSheet";
	}
	
	@RequestMapping(value="/orderSheet.do", method = RequestMethod.POST)
	public String orderSheet_post(@ModelAttribute OrderVO orderVo, HttpSession session) {
		String userid=(String)session.getAttribute("userid");
		logger.info("주문처리 파라미터, orderVo={}",orderVo);
		orderVo.setCustomerId(userid);
		
		int cnt=orderService.insertOrder(orderVo);
		logger.info("주문처리 결과 cnt={}",cnt);
		
		return "redirect:/shop/order/orderComplete.do?orderNo="+orderVo.getOrderNo();
	}
	
	@RequestMapping("/orderComplete.do")
	public String ordercomplete(@RequestParam(defaultValue = "0") int orderNo, Model model) {
		logger.info("주문완료 페이지 파라미터 orderNo={}",orderNo);
		
		List<Map<String, Object>> list=orderService.selectOrderDetailsView(orderNo);
		logger.info("주문 완료 - 주문 상세내역 조회 결과, list.size={}",list.size());
		Map<String, Object> map=orderService.selectOrdersView(orderNo);
		logger.info("주문 완료 - 주문 내역 조회 결과, map={}",map);
		
		model.addAttribute("list",list);
		model.addAttribute("map",map);
		
		return "shop/order/orderComplete";
	}
	
	@RequestMapping("/orderList.do")
	public String orderList(@ModelAttribute DateSearchVO dateSearchVo,Model model, HttpSession session) {
		String userid=(String) session.getAttribute("userid");
		dateSearchVo.setCustomerId(userid);
		logger.info("주문내역 조회 파라미터 vo={}",dateSearchVo);
		
		//시작일이 null일 경우 현재일자 세팅
		if(dateSearchVo.getStartDay()==null || dateSearchVo.getStartDay().isEmpty()) {
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");	
			dateSearchVo.setStartDay(sdf.format(d));
			dateSearchVo.setEndDay(sdf.format(d));
			logger.info("현재 일자 setting 후 vo={}",dateSearchVo);
		}
		
		//
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(WebUtility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(dateSearchVo.getCurrentPage());
		
		dateSearchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		dateSearchVo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
		
		List<OrderAllVO> list=orderService.selectOrderList(dateSearchVo);
		logger.info("주문내역 조회 결과, list.size={}",list.size());
		
		//주문상세내역
		/*
		for(int i=0;i<list.size();i++) {
			OrderAllVO orderallVo=list.get(i);
			OrderVO orderVo=orderallVo.getOrderVo();
			List<Map<String, Object>> orderlist=orderService.selectOrderDetailsView(orderVo.getOrderNo());
			orderallVo.setOrderDetailsList(orderlist);
		}
		*/
		
		int totalRecord=orderService.selectTotalRecord(dateSearchVo);
		pagingInfo.setTotalRecord(totalRecord);
		logger.info("주문내역 - 전체 레코드 조회, totalRecord={}",totalRecord);
		
		model.addAttribute("list",list);
		model.addAttribute("pagingInfo", pagingInfo);
		return "shop/order/orderList";
		
	}
	
	@RequestMapping("/bestProduct.do")
	public String best(@RequestParam(defaultValue = "0") int productNo, Model model) {
		logger.info("카테고리별 판매 많은 상품 조회, 파라미터 productNo={}",productNo);
		
		List<Map<String, Object>> list=orderService.selectBestProduct(productNo);
		logger.info("카테고리별 판매 많은 상품 조회 결과 list.size={}",list.size());
		
		model.addAttribute("list",list);
		return "inc/bestProduct";
		
	}
}
