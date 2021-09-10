package com.poseidon.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 2021-08-25 TestService
 * 
 */
@Service //service랑 dao랑 연결해야됨
public class TestService {
	@Autowired
	private TestDAO testDAO;
	
	public List<TestDTO> boardList(Map<String, Object> sendMap){
		return testDAO.boardList(sendMap);
	}
	
	public void write(TestDTO testDTO) {
		testDAO.write(testDTO);
	}
	//서비스 끝

	public TestDTO detail(int bno) {
		return testDAO.detail(bno);
	}
	
	public int delete(int bno){
		return testDAO.delete(bno);
	}

	public int update(TestDTO dto) {
		return testDAO.update(dto);
	}

	public String getCategory(int sb_cate) {
		return testDAO.getCategory(sb_cate);
	}

	public List<HashMap<String, Object>> categoryList() {
		return testDAO.categoryList();
	}

	public int totalList(int sb_cate) {
		return testDAO.totalList(sb_cate);
	}

	public void countUp(int sb_no) {
		testDAO.countUp(sb_no);
	}

	public void likeUp(Map<String, Object> sendMap) {
		testDAO.likeUp(sendMap);
	}

	public int exist(Map<String, Object> sendMap) {
		return testDAO.exist(sendMap);
	}

	public void likeCancle(int sb_no) {
		
	}


	
}
