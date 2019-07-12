package com.ez.herb.sales.model;

import java.util.List;
import java.util.Map;

import com.ez.herb.common.DateSearchVO;

public interface SalesDAO {
	List<Map<String, Object>> selectSalesByDay(DateSearchVO dateSerchVo);
	List<Map<String, Object>> selectSalesByMonth(String year);
	List<Map<String, Object>> selectSalesByTerm(DateSearchVO dateSerchVo);

}
