package com.ez.herb.order.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ez.herb.common.DateSearchVO;

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
	@Override
	public List<Map<String, Object>> selectOrderDetailsView(int orderNo) {
		return sqlSession.selectList(namespace+"selectOrderDetailsView",orderNo);
	}
	@Override
	public Map<String, Object> selectOrdersView(int orderNo) {
		return sqlSession.selectOne(namespace+"selectOrdersView",orderNo);
	}
	@Override
	public List<OrderAllVO> selectOrderList(DateSearchVO dateSearchVo) {
		return sqlSession.selectList(namespace+"selectOrderList",dateSearchVo);
	}
	@Override
	public int selectTotalRecord(DateSearchVO dateSearchVo) {
		return sqlSession.selectOne(namespace+"selectTotalRecord",dateSearchVo);
	}
	
	

}
