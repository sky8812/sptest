package com.ez.herb.cart.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOMybatis implements CartDAO{
	private String namespace="config.mybatis.mapper.oracle.cart.";
	@Autowired private SqlSessionTemplate sqlSession;
	
	@Override
	public int insertCart(CartVO vo) {
		return sqlSession.insert(namespace+"insertCart",vo);
	}
	
	@Override
	public int updateCartQty(CartVO vo) {
		return sqlSession.update(namespace+"updateCartQty",vo);
	}
	
	@Override
	public int selectCountCart(CartVO vo) {
		return sqlSession.selectOne(namespace+"selectCountCart",vo);
	}

	@Override
	public List<Map<String, Object>> selectCartView(String customerId) {
		return sqlSession.selectList(namespace+"selectCartView",customerId);
	}
	
	
}
