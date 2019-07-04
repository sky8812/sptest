package com.ez.herb.order.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOMybatis implements OrderDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="config.mybatis.mapper.oracle.order.";
	@Override
	public int insertOrder(OrderVO vo) {
		return sqlSession.insert(namespace+"insertOrder",vo);
	}
	@Override
	public int insertOrderDetail(OrderVO vo) {
		return sqlSession.insert(namespace+"insertOrderDetail",vo);
	}
	@Override
	public int deleteCartByUserid(String customerId) {
		return sqlSession.delete(namespace+"deleteCartByUserid",customerId);
	}
	
	

}
