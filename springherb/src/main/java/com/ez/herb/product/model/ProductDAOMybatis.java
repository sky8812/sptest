package com.ez.herb.product.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOMybatis implements ProductDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="config.mybatis.mapper.oracle.product.";
	
	@Override
	public List<ProductVO> selectEventPd(String eventName) {
		return sqlSession.selectList(namespace+"selectEventPd",eventName);
	}

	@Override
	public ProductVO selectProductByNo(int productNo) {
		return sqlSession.selectOne(namespace+"selectProductByNo", productNo);
	}

	@Override
	public List<ProductVO> selectByCategoryNo(int categoryNo) {
		return sqlSession.selectList(namespace+"selectByCategoryNo", categoryNo);
	}

	@Override
	public int insertProduct(ProductVO vo) {
		return sqlSession.insert(namespace+"insertProduct",vo);
	}

	@Override
	public List<ProductVO> selectProductAll(EventProductVO vo) {
		return sqlSession.selectList(namespace+"selectProductAll",vo);
	}

	@Override
	public int getTotalRecord(EventProductVO vo) {
		return sqlSession.selectOne(namespace+"getTotalRecord",vo);
	}

	@Override
	public int deleteProduct2(Map<String, String[]> map) {
		return sqlSession.delete(namespace+"deleteProduct2",map);
	}

	@Override
	public int deleteProduct(int productNo) {
		return sqlSession.delete(namespace+"deleteProduct",productNo);
	}

	@Override
	public int inserteventProduct(EventProductVO vo) {
		return sqlSession.insert(namespace+"inserteventProduct",vo);
	}

	@Override
	public int geteventProductCount(EventProductVO vo) {
		return sqlSession.selectOne(namespace+"geteventProductCount",vo);
	}
	
}
