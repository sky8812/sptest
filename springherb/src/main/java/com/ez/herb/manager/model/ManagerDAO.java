package com.ez.herb.manager.model;

import java.util.List;
import java.util.Map;

public interface ManagerDAO {
	public String selectPwdForAdminLogin(String userid);
	 ManagerVO selectManager(String userid);
	 List<Map<String, Object>> selectAuthorityAll();
	 int insertManager(ManagerVO vo);
}
