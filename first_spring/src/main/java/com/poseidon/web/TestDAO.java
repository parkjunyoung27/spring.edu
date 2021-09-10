package com.poseidon.web;
/*
 * 2021-08-25
 * mybatis와 연결하겠습니다.
 */
// Sercive - DAO - sqlSession - DB

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
/* 컨트롤러 @Controller
 * 서비스 @Service
 * DAO @repository
 * 그 외 @Component
 */
import org.springframework.stereotype.Repository;

@Repository
public class TestDAO {
	@Autowired //sqlsession을 dao에서 가져온다 
	private SqlSession sqlSession;
	
	//실제 데이터를 불러오겠습니다. select 흐름을 봐주세요.
	public List<TestDTO> boardList(Map<String, Object> sendMap){
		return sqlSession.selectList("test.boardList", sendMap); //네임스페이스.id
	}

	public void write(TestDTO testDTO) {
		sqlSession.insert("test.write", testDTO);
	}

	public TestDTO detail(int bno) {
		return sqlSession.selectOne("test.detail", bno);
	}
	
	public int delete(int bno) {
		return sqlSession.delete("test.delete", bno);
	}

	public int update(TestDTO dto) {
		return sqlSession.update("test.update", dto);
	}

	public String getCategory(int sb_cate) {
		return sqlSession.selectOne("test.getCategory", sb_cate);
	}

	public List<HashMap<String, Object>> categoryList() {
		return sqlSession.selectList("test.categoryList");
	}

	public int totalList(int sb_cate) {
		return sqlSession.selectOne("test.totalList", sb_cate);
	}

	public void countUp(int sb_no) {
		sqlSession.update("test.countUp", sb_no);
	}

	public void likeUp(Map<String, Object> sendMap) {
		sqlSession.insert("test.likeUp", sendMap);
	}

	public int exist(Map<String, Object> sendMap) {
		return sqlSession.selectOne("test.exist", sendMap);
	}


	
}
