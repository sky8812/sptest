package com.ez.herb.board.model;

import java.util.List;

import com.ez.herb.common.SearchVO;

public interface BoardDAO {
	public int insertBoard(BoardVO vo);
	public List<BoardVO> selectAll(SearchVO searchVo);
	public int selectTotalCount(SearchVO searchVo);
	public int updateReadCount(int no);
	public BoardVO selectByNo(int no);
	public int updateBoard(BoardVO vo);
	
	public String selectPwd(int no);
	public int selectPWdCheck(BoardVO vo);
	
	public int deleteBoard(int no);
	public List<BoardVO> selectMainNotice();
}
