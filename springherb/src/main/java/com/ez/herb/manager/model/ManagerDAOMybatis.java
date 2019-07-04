package com.ez.herb.manager.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDAOMybatis implements ManagerDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="config.mybatis.mapper.oracle.manager.";

	@Override
	public String selectPwdForAdminLogin(String userid) {
		return sqlSession.selectOne(namespace+"selectPwdForAdminLogin",userid);
	}

	@Override
	public ManagerVO selectManager(String userid) {
		return sqlSession.selectOne(namespace+"selectManager",userid);
	}

	@Override
	public List<Map<String, Object>> selectAuthorityAll() {
		return sqlSession.selectList(namespace+"selectAuthorityAll");
	}

	@Override
	public int insertManager(ManagerVO vo) {
		return sqlSession.insert(namespace+"insertManager",vo);
	}
}
