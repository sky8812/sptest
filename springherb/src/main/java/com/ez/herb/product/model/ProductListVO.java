package com.ez.herb.product.model;

import java.util.List;

//커맨드 객체로 List받기
//- 스프링 MVC는 List 타입의 프로퍼티에 대한 바인딩도 처리해줌
public class ProductListVO {
	private List<ProductVO> pdItems;

	public List<ProductVO> getPdItems() {
		return pdItems;
	}

	public void setPdItems(List<ProductVO> pdItems) {
		this.pdItems = pdItems;
	}

	@Override
	public String toString() {
		return "ProductListVO [pdItems=" + pdItems + "]";
	}
	
}
