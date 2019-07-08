package com.ez.herb.order.model;

import java.util.List;
import java.util.Map;

import com.ez.herb.common.DateSearchVO;

public interface OrderDAO {
	int insertOrder(OrderVO vo);
	int insertOrderDetail(OrderVO vo);
	int deleteCartByUserid(String customerId);
	List<Map<String , Object>> selectOrderDetailsView(int orderNo);
	Map<String, Object> selectOrdersView(int orderNo);
	List<OrderAllVO> selectOrderList(DateSearchVO dateSearchVo);
	int selectTotalRecord(DateSearchVO dateSearchVo);
}
