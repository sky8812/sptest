package com.ez.herb.manager.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.herb.member.model.MemberService;

@Service
public class ManagerServiceImpl implements ManagerService{
	@Autowired
	private ManagerDAO managerDao;

	@Override
	public int adminLoginCheck(String userid, String pwd) {
		String dbPwd=managerDao.selectPwdForAdminLogin(userid);
		int result=0;
		if(dbPwd==null || dbPwd.isEmpty()) {
			result=MemberService.ID_NONE;
		}else {
			if(dbPwd.equals(pwd)) {
				result=MemberService.LOGIN_OK;
			}else {
				result=MemberService.PWD_DISAGREE;
			}
			
		}
		return result;
	}

	@Override
	public ManagerVO selectManager(String userid) {
		return managerDao.selectManager(userid);
	}

	@Override
	public List<Map<String, Object>> selectAuthorityAll() {
		return managerDao.selectAuthorityAll();
	}

	@Override
	public int insertManager(ManagerVO vo) {
		return managerDao.insertManager(vo);
	}
}
