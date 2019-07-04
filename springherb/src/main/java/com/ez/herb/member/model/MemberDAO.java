package com.ez.herb.member.model;

public interface MemberDAO {
	public int insertMember(MemberVO vo);
	public int duplicateUserid(String userid);
	public String selectForLogin(String userid);
	public MemberVO selectByUserid(String userid);
	public int updateMember(MemberVO vo);
	public int withdrawMember(String userid);
	
}
