package com.poseidon.web.log;

public class LogDTO {
	
	private int sl_no;
	private String sl_ip, sl_target, sl_id, sl_data, sl_date;

	//constructor
	public LogDTO(String sl_ip, String sl_target, String sl_id, String sl_data) {
		this(sl_ip, sl_target, sl_data); //생성자 파트 중요 
		this.setSl_id(sl_id);
	}
	
	//constructor2
	public LogDTO(String sl_ip, String sl_target, String sl_data) {
		this.setSl_ip(sl_ip);
		this.setSl_target(sl_target);
		this.setSl_data(sl_data);
	}
	
	//constructor3
	public LogDTO() {};
	
	public int getSl_no() {
		return sl_no;
	}
	public void setSl_no(int sl_no) {
		this.sl_no = sl_no;
	}
	public String getSl_ip() {
		return sl_ip;
	}
	public void setSl_ip(String sl_ip) {
		this.sl_ip = sl_ip;
	}
	public String getSl_target() {
		return sl_target;
	}
	public void setSl_target(String sl_target) {
		this.sl_target = sl_target;
	}
	public String getSl_id() {
		return sl_id;
	}
	public void setSl_id(String sl_id) {
		this.sl_id = sl_id;
	}
	public String getSl_data() {
		return sl_data;
	}
	public void setSl_data(String sl_data) {
		this.sl_data = sl_data;
	}
	public String getSl_date() {
		return sl_date;
	}
	public void setSl_date(String sl_date) {
		this.sl_date = sl_date;
	}
	
}
