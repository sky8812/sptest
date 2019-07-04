package com.ez.herb.cart.model;

import java.util.List;
import java.util.Map;

public interface CartService {
	//장바구니-배송비정책
	//총 구매금액이 30000원 미만이면 배송비 3000원
	public static final int BUY_PRICE=30000;
	int DELIVERY=3000;
	
	int insertCart(CartVO vo);
	List<Map<String, Object>> selectCartView(String customerId);
}
