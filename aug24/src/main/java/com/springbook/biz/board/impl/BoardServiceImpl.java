package com.springbook.biz.board.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.biz.common.LogAdvice;

@Service("boardService")
public class BoardServiceImpl {
	@Autowired
	private LogAdvice log;
	
	public BoardServiceImpl() {
		log = new LogAdvice();
	}
	
	public void insertBoard() {
		log.printLog();
		//해당 작업 진행하기
	}
	
	public void updateBoard() {
		log.printLog();		
		//해당 작업 진행하기
	}
	public void deleteBoard() {
		log.printLog();		
		//해당 작업 진행하기
	}
	public void getBoard() {
		log.printLog();		
		//해당 작업 진행하기
	}
	public void getBoardList() {
		log.printLog();		
		//해당 작업 진행하기
	}
	
	

}
