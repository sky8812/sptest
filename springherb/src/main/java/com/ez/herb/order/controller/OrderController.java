package com.ez.herb.order.controller;

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

import com.ez.herb.cart.model.CartService;
import com.ez.herb.member.model.MemberService;
import com.ez.herb.member.model.MemberVO;
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
		
		return "redirect:/shop/order/orderComplete.do";
	}
}
