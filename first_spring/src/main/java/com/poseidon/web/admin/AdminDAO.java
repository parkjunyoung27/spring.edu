package com.poseidon.web.admin;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {
	@Autowired
	private SqlSession sqlSession;

	public List<HashMap<String, Object>> getCategory() {
		return sqlSession.selectList("admin.getCategory");
	}
	
	public int categoryInsert(String categoryName) {
		return sqlSession.insert("admin.categoryInsert",categoryName);
	}

	public HashMap<String, Object> getCategory(int sc_no) {
		return sqlSession.selectOne("admin.getCategory", sc_no);
	}

	public int categoryUpdate(HashMap<String, Object> cate) {
		return sqlSession.update("admin.categoryUpdate", cate); //hashmap이 알아서 해줌 
	}

	public List<HashMap<String, Object>> list() {
		return sqlSession.selectList("admin.memberList");
	}

	public int gradeUpDown(MemberDTO dto) {
		return sqlSession.update("admin.gradeUpDown", dto);
	}

	public int userDelete(int sm_no) {
		return sqlSession.update("admin.userDelete", sm_no);
	}

	public int categoryVisible(int sc_no) {
		return sqlSession.update("admin.categoryVisible", sc_no);
	}

}
