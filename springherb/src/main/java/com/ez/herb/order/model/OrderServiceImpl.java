package com.ez.herb.order.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
	private Logger logger=LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired private OrderDAO orderDao;

	@Override
	@Transactional
	public int insertOrder(OrderVO vo) {
		logger.info("order테이블 insert전 orderVo={}",vo);
		
		int cnt=orderDao.insertOrder(vo);
		logger.info("order테이블 insert후 orderVo={}, 처리결과 cnt={}",vo, cnt); //orderNo의 값이 들어감
		
		cnt=orderDao.insertOrderDetail(vo);
		logger.info("주문 상세등록 후 cnt={}",cnt);
		
		cnt=orderDao.deleteCartByUserid(vo.getCustomerId());
		logger.info("주문 처리시 카트 삭제 후 cnt={}",cnt);
		
		return cnt;
	}

	

}