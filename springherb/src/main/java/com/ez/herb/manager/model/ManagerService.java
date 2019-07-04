package com.ez.herb.manager.model;

import java.util.List;
import java.util.Map;

public interface ManagerService {
	public int adminLoginCheck(String userid, String pwd);
	 ManagerVO selectManager(String userid);
	 List<Map<String, Object>> selectAuthorityAll();
	 int insertManager(ManagerVO vo);
}
