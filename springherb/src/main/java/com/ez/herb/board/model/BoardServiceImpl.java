package com.ez.herb.board.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.herb.common.SearchVO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDao;
	
	public int insertBoard(BoardVO vo){
		return boardDao.insertBoard(vo);
	}
	
	public List<BoardVO> selectAll(SearchVO searchVo){
		return boardDao.selectAll(searchVo);
	}

	@Override
	public int selectTotalCount(SearchVO searchVo) {
		return boardDao.selectTotalCount(searchVo);
	}

	public int updateReadCount(int no) {
		return boardDao.updateReadCount(no);
	}
	public BoardVO selectByNo(int no){
		return boardDao.selectByNo(no);
	}
	
	public int updateBoard(BoardVO vo){
		return boardDao.updateBoard(vo);
	}
	
	public String selectPwd(int no) {
		return boardDao.selectPwd(no);
	}
	
	public boolean selectPWdCheck(BoardVO vo) {
		boolean result=false;
		
		int count= boardDao.selectPWdCheck(vo);
		if(count>0) {
			result=true; 	//비밀번호 일치
		}
		return result;
	}
	
	public int deleteBoard(int no){
		return boardDao.deleteBoard(no);
	}

	@Override
	public List<BoardVO> selectMainNotice() {
		return boardDao.selectMainNotice();
	}

	/*
	public List<BoardVO> selectMainNotice() throws SQLException{
		return boardDao.selectMainNotice();
	}
	public BoardVO selectByNo(int no) throws SQLException {
		return boardDao.selectByNo(no);
	}
	
	public int updateBoard(BoardVO vo) throws SQLException {
		return boardDao.updateBoard(vo);
	}
	
	public int updateReadCount(int no) throws SQLException {
		return boardDao.updateReadCount(no);
	}
	
	public int deleteBoard(int no, String pwd) throws SQLException {
		return boardDao.deleteBoard(no, pwd);
	}
	*/
}
