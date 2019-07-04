package com.ez.herb.cart.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

	@Autowired private CartDAO cartDao;

	@Override
	public int insertCart(CartVO vo) {
		int count=cartDao.selectCountCart(vo);
		
		int cnt=0;
		if(count>0) { //해당상품이 이미 장바구니에 담겨 있으면 update
			cnt=cartDao.updateCartQty(vo);
		}else {
			cnt=cartDao.insertCart(vo);
		}
		return cnt;
	}

	@Override
	public List<Map<String, Object>> selectCartView(String customerId) {
		return cartDao.selectCartView(customerId);
	}
}
