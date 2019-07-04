package com.ez.herb.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.product.model.ProductService;
import com.ez.herb.product.model.ProductVO;

@Controller
@RequestMapping("/shop/product")
public class ProductController {

	private Logger logger=LoggerFactory.getLogger(ProductController.class);
	@Autowired private ProductService productService;
	
	@RequestMapping("/productByEvent.do")
	public String pdByEvent(@RequestParam String eventName, Model model) {
		//
		logger.info("이벤트별 조회 파라미터 eventName={}",eventName);
		
		List<ProductVO> list=productService.selectEventPd(eventName);
		logger.info("이벤트별 조회 결과, list.size={}",list.size());
		
		model.addAttribute("list",list);
		return "shop/product/productByEvent";
		
	}
	
	@RequestMapping("/productDetail.do")
	public String pdDetail(@RequestParam(defaultValue = "0") int productNo, Model model) {
		logger.info("상품 상세보기 파라미터 productNo={}",productNo);
		
		if(productNo==0) {
			model.addAttribute("msg","잘못된 url입니다.");
			model.addAttribute("url","/index.do");
			return "common/message";
			
		}
		
		ProductVO vo=productService.selectProductByNo(productNo);
		model.addAttribute("vo",vo);
		return "shop/product/productDetail";
	}
	
	@RequestMapping("/bigImage.do")
	public String pdDetail_bigimg(@RequestParam String imageURL, Model model) {
		return "shop/product/bigImage";
	}
	
	@RequestMapping("/productByCategory.do")
	public String productByCategory(@RequestParam int categoryNo, String categoryName, Model model) {
		logger.info("카테고리별 상품 이미지 조회 파라미터 categoryNo={}, categoryName={}");
		List<ProductVO> list=productService.selectByCategoryNo(categoryNo);
		model.addAttribute("list",list);
		return "shop/product/productByCategory";
	}
}
