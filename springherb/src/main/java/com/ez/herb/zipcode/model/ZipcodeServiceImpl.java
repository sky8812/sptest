package com.ez.herb.zipcode.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZipcodeServiceImpl implements ZipcodeService{
	@Autowired
	private ZipcodeDAO zipcodeDao;

	@Override
	public List<ZipcodeVO> selectZipcode(ZipcodeVO vo) {
		return zipcodeDao.selectZipcode(vo);
	}

	@Override
	public int getTotalRecord(String dong) {
		return zipcodeDao.getTotalRecord(dong);
	}

}
