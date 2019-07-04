package com.ez.herb.board.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.herb.common.SearchVO;

@Service
public class ReBoardServiceImpl implements ReBoardService{
	@Autowired
	private ReBoardDAO reBoardDao;
	
	public int insertReBoard(ReBoardVO vo){
		return reBoardDao.insertReBoard(vo);
	}

	public List<ReBoardVO> selectAll(SearchVO searchVo){
		return reBoardDao.selectAll(searchVo);		
	}

	@Override
	public int selectTotalCount(SearchVO searchVo) {
		return reBoardDao.selectTotalCount(searchVo);
	}
	
	public int updateReadCount(int no){
		return reBoardDao.updateReadCount(no);
	}
	
	public ReBoardVO selectByNo(int no){
		return reBoardDao.selectByNo(no);	
	}
	
	public int updateReBoard(ReBoardVO vo) {
		return reBoardDao.updateReBoard(vo);
	}

	@Override
	public boolean selectPWdCheck(ReBoardVO vo) {
		boolean result=false;
		int count=reBoardDao.selectPWdCheck(vo);
		if(count>0) {
			result=true; //비밀번호 일치
		}
		
		return result;
	}
	
	public int deleteReBoard(Map<String, String> map){
		return reBoardDao.deleteReBoard(map);
	}

	@Override
	public int updateDownCount(int no) {
		return reBoardDao.updateDownCount(no);
	}

	@Override
	@Transactional
	public int insertReply(ReBoardVO vo) {
		int cnt=reBoardDao.updateSortNo(vo); //sortNo 자리확보
		cnt=reBoardDao.insertReply(vo); //답변달기
		return cnt;
	}
	
	/*
	public List<ReBoardVO> selectMainNotice() throws SQLException{
		return reBoardDao.selectMainNotice();
	}
		
	*/
}
