package com.ez.herb.cart.model;

import java.util.List;
import java.util.Map;

public interface CartDAO {
	int insertCart(CartVO vo);
	int updateCartQty(CartVO vo);
	int selectCountCart(CartVO vo);
	List<Map<String, Object>> selectCartView(String customerId);
}
