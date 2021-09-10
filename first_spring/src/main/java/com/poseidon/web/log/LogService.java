package com.poseidon.web.log;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
	@Autowired
	private LogDAO logDAO;

	public void writeLog(LogDTO log) {
		logDAO.writeLog(log);
	}

	public List<LogDTO> loglist(Map<String, Object> sendMap) {
		return logDAO.loglist(sendMap);
	}

	public int logTotalList(Map<String, Object> sendMap) {
		return logDAO.logTotalList(sendMap);
	}	
}
