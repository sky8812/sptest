package com.ez.herb.board.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ez.herb.common.SearchVO;

@Repository
public class ReBoardDAOMybatis implements ReBoardDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="config.mybatis.mapper.oracle.reBoard.";
	
	public int insertReBoard(ReBoardVO vo){
		int cnt=sqlSession.insert(namespace+"insertReBoard", vo);
		return cnt;
	}
	
	
	public List<ReBoardVO> selectAll(SearchVO searchVo){
		List<ReBoardVO> list 
			=sqlSession.selectList(namespace+"selectSearch", searchVo);
		return list;		
	}


	@Override
	public int selectTotalCount(SearchVO searchVo) {
		return sqlSession.selectOne(namespace+"selectTotalCount", searchVo);
	}
	
	public int updateReadCount(int no){
		return sqlSession.update(namespace+"updateReadCount", no);
	}
	
	public ReBoardVO selectByNo(int no) {
		ReBoardVO vo=sqlSession.selectOne(namespace+"selectByNo", no);
		return vo;
	}
	
	public int updateReBoard(ReBoardVO vo){
		return sqlSession.update(namespace+"updateReBoard", vo);
	}


	@Override
	public int selectPWdCheck(ReBoardVO vo) {
		return sqlSession.selectOne(namespace+"selectPWdCheck", vo);
	}
	
	public int deleteReBoard(Map<String, String> map){
		return sqlSession.delete(namespace+"deleteReBoard", map);
	}


	@Override
	public int updateDownCount(int no) {
		return sqlSession.update(namespace+"updateDownCount",no);
	}


	@Override
	public int insertReply(ReBoardVO vo) {
		return sqlSession.insert(namespace+"insertReply", vo);
	}


	@Override
	public int updateSortNo(ReBoardVO vo) {
		return sqlSession.update(namespace+"updateSortNo",vo);
	}
	
	/*
	public List<ReBoardVO> selectMainNotice() throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		List<ReBoardVO> list=new ArrayList<ReBoardVO>();
		try {
			//1,2
			con=pool.getConnection();
			
			//3.
			String sql="select no, title" + 
					" from" + 
					" (" + 
					"    select * from reBoard order by no desc" + 
					" )" + 
					" where rownum<=6";
			ps=con.prepareStatement(sql);
			
			//4
			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				String title=rs.getString("title");
				
				ReBoardVO vo=new ReBoardVO();
				vo.setNo(no);
				vo.setTitle(title);
				list.add(vo);
			}
			System.out.println("메인공지사항 조회 결과 list.size="+list.size());
			
			return list;
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}
	*/
}








