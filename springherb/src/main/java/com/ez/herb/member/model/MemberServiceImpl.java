package com.ez.herb.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Service - DB작업 이외의 로직을 담는 클래스
//jsp - dao
//jsp - service - dao
@Service
public class MemberServiceImpl implements MemberService{
		
	@Autowired
	private MemberDAO memberDao;

	public int insertMember(MemberVO vo){
		int cnt=memberDao.insertMember(vo);
		return cnt;
	}

	@Override
	public int duplicateUserid(String userid) {
		int count= memberDao.duplicateUserid(userid);
		int result=0;
		
		if(count>0) {
			//이미 해당 아이디가 존재 => 사용불가
			result=NON_USEFUL_USERID;
		}else {
			result=MemberService.USEFUL_USERID;
		}
		return result;
	}
	
	public int loginCheck(String userid, String pwd) {
		String dbPwd= memberDao.selectForLogin(userid);
		int result=0;
		
		if(dbPwd==null || dbPwd.isEmpty()) {
			result=ID_NONE;
		}else { //있을 때는 같은지 다른지 확인
			if(dbPwd.equals(pwd)) {
				result=LOGIN_OK;
			}else {
				result=PWD_DISAGREE;
			}
		}
		return result;
	}
	public MemberVO selectByUserid(String userid){
		return memberDao.selectByUserid(userid);
	}
	
	@Override
	public int updateMember(MemberVO vo) {
		return memberDao.updateMember(vo);
	}

	@Override
	public int withdrawMember(String userid) {
		return memberDao.withdrawMember(userid);
	}
	
	/*
	public int duplicateUserid(String userid) throws SQLException {
		return memberDao.duplicateUserid(userid);
	}
	
	
	public MemberVO selectByUserid(String userid) throws SQLException {
		return memberDao.selectByUserid(userid);
	}
		
	public int updateMember(MemberVO vo) throws SQLException {
		return memberDao.updateMember(vo);
	}
	
	public int withdrawMember(String userid) throws SQLException {
		return memberDao.withdrawMember(userid);
	}
	*/

		
			
}


