package com.ez.herb.sales.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.herb.common.DateSearchVO;

@Service
public class SalesServiceImpl implements SalesService{
	@Autowired private SalesDAO salesDao;
	
	@Override
	public List<Map<String, Object>> selectSalesByDay(DateSearchVO dateSearchVo) {
		return salesDao.selectSalesByDay(dateSearchVo);
	}

	@Override
	public List<Map<String, Object>> selectSalesByMonth(String year) {
		return salesDao.selectSalesByMonth(year);
	}

	@Override
	public List<Map<String, Object>> selectSalesByTerm(DateSearchVO dateSearchVo) {
		return salesDao.selectSalesByTerm(dateSearchVo);
	}

}
