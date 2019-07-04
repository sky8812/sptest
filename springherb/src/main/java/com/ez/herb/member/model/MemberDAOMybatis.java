package com.ez.herb.member.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOMybatis implements MemberDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	String namespace="config.mybatis.mapper.oracle.member.";
	
	public int insertMember(MemberVO vo) {
		return sqlSession.insert(namespace+"insertMember",vo);
		
	}
	
	
	public int duplicateUserid(String userid) {
		return sqlSession.selectOne(namespace+"dupUserid",userid);
	}
	
	
	public String selectForLogin(String userid){
		return sqlSession.selectOne(namespace+"selectPwdForLogin",userid);
	}
	
	public MemberVO selectByUserid(String userid){
		return sqlSession.selectOne(namespace+"selectMember",userid);
	}
	
	
	
	public int updateMember(MemberVO vo) {
		return sqlSession.update(namespace+"updateMember",vo);
	}
	

	public int withdrawMember(String userid) {
		return sqlSession.update(namespace+"withdrawMember",userid);
	}
	
}



