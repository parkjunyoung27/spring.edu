package com.poseidon.web.admin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	@Autowired
	private AdminDAO adminDAO;

	public List<HashMap<String, Object>> getCategory() {
		return adminDAO.getCategory();
	}

	public int categoryInsert(String categoryName) {
		return adminDAO.categoryInsert(categoryName);
	}

	public HashMap<String, Object> getCategory(int sc_no) {
		return adminDAO.getCategory(sc_no);
	}


	public int categoryUpdate(HashMap<String, Object> cate) {
		return adminDAO.categoryUpdate(cate);
	}

	public List<HashMap<String, Object>> list() {
		return adminDAO.list();
	}

	public int gradeUpDown(MemberDTO dto) {
		return adminDAO.gradeUpDown(dto);
	}

	public int userDelete(int sm_no) {
		return adminDAO.userDelete(sm_no);
	}


	public int categoryVisible(int sc_no) {
		return adminDAO.categoryVisible(sc_no);
	}
}
