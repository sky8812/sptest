package com.ez.herb.order.model;

public interface OrderDAO {
	int insertOrder(OrderVO vo);
	int insertOrderDetail(OrderVO vo);
	int deleteCartByUserid(String customerId);
}
