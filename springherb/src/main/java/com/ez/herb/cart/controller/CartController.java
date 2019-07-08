package com.ez.herb.cart.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.cart.model.CartService;
import com.ez.herb.cart.model.CartVO;

@Controller
@RequestMapping("/shop/cart")
public class CartController {

	private Logger logger=LoggerFactory.getLogger(CartController.class);
	@Autowired private CartService cartService;
	
	@RequestMapping("/cartAdd.do")
	public String cartAdd(@ModelAttribute CartVO cartVo, @RequestParam String mode, HttpSession session) {
		String userid=(String) session.getAttribute("userid");
		cartVo.setCustomerId(userid);
		logger.info("장바구니 담기 파라미터 cartVo={}, mode={}",cartVo,mode);
		
		int cnt=cartService.insertCart(cartVo);
		logger.info("장바구니 담기 결과 cnt={}", cnt);
		
		String resultPage="";
		if(mode.equals("cart")) {
			resultPage="/shop/cart/cartList.do";
		}else {
			resultPage="/shop/order/orderSheet.do";
		}
		
		return "redirect:"+resultPage;
	}
	
	@RequestMapping("/cartList.do")
	public String cartList(HttpSession session, Model model) {
		
		String userid=(String) session.getAttribute("userid");
		logger.info("장바구니 목록 파라미터 userid={}",userid);
		List<Map<String, Object>> list=cartService.selectCartView(userid);
		logger.info("장바구니 목록 결과, list.size={}",list.size());
		
		model.addAttribute("list",list);
		model.addAttribute("BUY_PRICE",CartService.BUY_PRICE);
		model.addAttribute("DELIVERY",CartService.DELIVERY);
		return "shop/cart/cartList";
	}
}
