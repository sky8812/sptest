package com.ez.herb.board.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ez.herb.common.SearchVO;

@Repository
public class BoardDAOMybatis implements BoardDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="config.mybatis.mapper.oracle.board.";
	
	public int insertBoard(BoardVO vo){
		int cnt=sqlSession.insert(namespace+"insertBoard",vo);
		return cnt;
	}
	
	
	public List<BoardVO> selectAll(SearchVO searchVo) {
	
		List<BoardVO> list=sqlSession.selectList(namespace+"selectSearch",searchVo);
		return list;
	}


	@Override
	public int selectTotalCount(SearchVO searchVo) {
		return sqlSession.selectOne(namespace+"selectTotalCount", searchVo);
	}
	
	public int updateReadCount(int no){
		return sqlSession.update(namespace+"updateReadCount", no);
	}
	
	public BoardVO selectByNo(int no){
		return sqlSession.selectOne(namespace+"selectByNo",no);
	}
	
	public int updateBoard(BoardVO vo){
		return sqlSession.update(namespace+"updateBoard",vo);
	}
	
	public String selectPwd(int no) {
		return sqlSession.selectOne(namespace+"selectPwd",no);
	}
	
	public int selectPWdCheck(BoardVO vo) {
		return sqlSession.selectOne(namespace+"selectPWdCheck",vo);
	}
	
	public int deleteBoard(int no){
		return sqlSession.delete(namespace+"deleteBoard",no);
	}
	
	public List<BoardVO> selectMainNotice(){
		return sqlSession.selectList(namespace+"selectMainNotice");
	}
	
	/*
	
	public List<BoardVO> selectMainNotice() throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<BoardVO> list=new ArrayList<BoardVO>();
		try {
			con=pool.getConnection();
			String sql="select no, title from (select * from board order by no desc)" + 
					" where rownum<=5";
			ps=con.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				list.add(vo);
			}
			
			System.out.println("메인 공지사항 조회 결과 list.size="+list.size());
			return list;
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}
	*/
}








