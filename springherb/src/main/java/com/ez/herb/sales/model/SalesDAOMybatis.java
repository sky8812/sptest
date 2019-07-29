package com.ez.herb.sales.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ez.herb.common.DateSearchVO;

@Repository
public class SalesDAOMybatis implements SalesDAO{
	String namespace="config.mybatis.mapper.oracle.sales.";
	@Autowired private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Map<String, Object>> selectSalesByDay(DateSearchVO dateSearchVo) {
		return sqlSession.selectList(namespace+"selectSalesByDay",dateSearchVo);
	}
	@Override
	public List<Map<String, Object>> selectSalesByMonth(String year) {
		return sqlSession.selectList(namespace+"selectSalesByMonth",year);
	}
	@Override
	public List<Map<String, Object>> selectSalesByTerm(DateSearchVO dateSearchVo) {
		return sqlSession.selectList(namespace+"selectSalesByMonth",dateSearchVo);
	}

}
