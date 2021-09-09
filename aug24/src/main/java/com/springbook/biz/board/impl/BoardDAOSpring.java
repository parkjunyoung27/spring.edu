package com.springbook.biz.board.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
//DAO는 이렇게 붙여줍니다.
@Repository
public class BoardDAOSpring {
	@Autowired
	private JdbcTemplate jdbcTemplate; //application에서 부른것
	
	private final String BOARD_INSERT= 
			"INSERT INTO board (seq, title, writer, content) "
			+ "VALUES(?, ?, ?, ?)";
	
	private void insertBoard(/*DTO를 안만들어서 뺍니다.*/) {		
		//jdbcTemplate.update(쿼리, 값);
		jdbcTemplate.update(BOARD_INSERT,2, "title", "poseidon","blabla");
	}
	
}
