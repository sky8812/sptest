package com.ez.herb.product.model;

import java.util.List;
import java.util.Map;

public interface ProductDAO {
	public List<ProductVO> selectEventPd(String eventName);
	public ProductVO selectProductByNo(int productNo);
	public List<ProductVO> selectByCategoryNo(int categoryNo);
	public int insertProduct(ProductVO vo);
	List<ProductVO> selectProductAll(EventProductVO vo);
	int getTotalRecord(EventProductVO vo);
	int deleteProduct2(Map<String, String[]> map);
	int deleteProduct(int productNo);
	
	
	int inserteventProduct(EventProductVO vo);
	int geteventProductCount(EventProductVO vo);
	
}
