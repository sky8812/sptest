package com.ez.herb.board.model;

import java.util.List;
import java.util.Map;

import com.ez.herb.common.SearchVO;

public interface ReBoardService {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	public int selectTotalCount(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public boolean selectPWdCheck(ReBoardVO vo);
	public int updateReBoard(ReBoardVO vo);
	public int deleteReBoard(Map<String, String> map);
	public int updateDownCount(int no);
	public int insertReply(ReBoardVO vo);
}
