package com.ez.herb.product.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDao;

	@Override
	public List<ProductVO> selectEventPd(String eventName) {
		return productDao.selectEventPd(eventName);
	}

	@Override
	public ProductVO selectProductByNo(int productNo) {
		return productDao.selectProductByNo(productNo);
	}

	@Override
	public List<ProductVO> selectByCategoryNo(int categoryNo) {
		return productDao.selectByCategoryNo(categoryNo);
	}

	@Override
	public int insertProduct(ProductVO vo) {
		return productDao.insertProduct(vo);
	}

	@Override
	public List<ProductVO> selectProductAll(EventProductVO vo) {
		return productDao.selectProductAll(vo);
	}

	@Override
	public int getTotalRecord(EventProductVO vo) {
		return productDao.getTotalRecord(vo);
	}

	@Override
	public int deleteProduct2(Map<String, String[]> map) {
		return productDao.deleteProduct2(map);
	}

	@Override
	@Transactional
	public int deleteProduct(List<ProductVO> list) {
		
		int cnt=0;
		try {
			for(ProductVO vo:list) {
				if(vo.getProductNo()!=0) {
					cnt=productDao.deleteProduct(vo.getProductNo());
				}
			}
		}catch(RuntimeException e) {
			e.printStackTrace();
			cnt=-1;
			
			/*
			선언적 트랜잭션에서는 런타임 예외가 발생하면 롤백한다.
			반면에 예외가 전혀 발생하지 않거나 체크 예외가 발생하면 커밋한다.
			스프링에서는 데이터 액세스 기술의 예외는 런타임 예외로 전환되서 던져지므로 
			런타임 예외만 롤백 대상으로 삼은 것이다.
			*/
		}
		return cnt;
	}

	@Override
	@Transactional
	public int inserteventProduct(List<ProductVO> list, String eventSel) {
		int cnt=0;
		try {
			
			for(ProductVO vo:list) {
				if(vo.getProductNo()!=0) {
					EventProductVO eventproductVo=new EventProductVO();
					eventproductVo.setProductNo(vo.getProductNo());
					eventproductVo.setEventName(eventSel);
					
					int count=productDao.geteventProductCount(eventproductVo);
					if(count>0) { //해당 상품이 이미 이벤트에 등록되어 있는 경우
						cnt=1; //이미 등록한 것으로 간주
					}else {
						cnt=productDao.inserteventProduct(eventproductVo);
						
					}
				}
			}
		}catch(RuntimeException e) {
			cnt=-1;
		}
		return cnt;
	}

}
