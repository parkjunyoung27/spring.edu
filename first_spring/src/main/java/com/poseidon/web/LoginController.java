package com.poseidon.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poseidon.web.log.LogDTO;
import com.poseidon.web.log.LogService;
import com.poseidon.web.login.LoginDTO;
import com.poseidon.web.login.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;
	@Autowired
	private Util util;
	
	/*
	 * 화면 만들기
	 * 데이터 받기 
	 * 데이터베이스에 질의하기 
	 * 결과 판별하기
	 * 세션 만들기
	 * 페이지 이동  
	 */
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		
		//ip불러오기
		HttpSession session = request.getSession();
		String ip = util.getUserIp(request);
		String target= "login";
		String data = "로그인";
		LogDTO log = null;
		
		//로그 기록 입력 2021-09-07
		//세션이 있으면 세션도 넣기 = sl_id
		if(session.getAttribute("sm_id") != null) {
			String id = (String)session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);
		}else {
			log = new LogDTO(ip, target, data);
		}
		logService.writeLog(log);
		
		return "login";
		//http://localhost:8080/web/login
	}
	
	@PostMapping("/")
	public String loginAction(HttpServletRequest request) {
		//@RequestParam("네임") 타입 변수명
	//데이터 베이스 보내고
	//올바른 로그인이면 세션 만들어서 -> board
	//잘못된 로그인이면 로그인 페이지로 -> login
	//@RequestParam("id") String id, @RequestParam("pw") String pw	
		
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	System.out.println("들어온 id : " + id + " pw : " + pw);
		
	//LoginDTO를 만들어주세요. 거기에 넣어주세요.
	LoginDTO dto = new LoginDTO();
	dto.setSm_id(id);
	dto.setSm_pw(pw);
	
	//service -> DAO -> mapper.xml
	//리턴타입 변수명 = 값; 
	LoginDTO result = loginService.login(dto);
	
	if(result != null) {
		System.out.println(result.getSm_no());		
		System.out.println(result.getSm_name());
		//세션은 여기에
		HttpSession session = request.getSession();
		session.setAttribute("sm_name", result.getSm_name());
		session.setAttribute("sm_id", result.getSm_id());
		
		//ip불러오기
		String ip = util.getUserIp(request);
		String target= "login";
		String data = "로그인성공";
		LogDTO log = null;
		
		//로그 기록 입력 2021-09-07
		//세션이 있으면 세션도 넣기 = sl_id
		log = new LogDTO(ip, target, id, data);
		logService.writeLog(log);
		
	
		return "redirect:/board"; //여긴 정상 로그인
	} else {
		//redirect 수정했습니다.
		
		//ip불러오기
		String ip = util.getUserIp(request);
		String target= "login";
		String data = "로그인실패";
		LogDTO log = null;
		
		//로그 기록 입력 2021-09-07
		//세션이 있으면 세션도 넣기 = sl_id
		if(request.getParameter("sm_id") != null) {
			log = new LogDTO(ip, target, id, data);
		}else {
			log = new LogDTO(ip, target, data);
		}
		logService.writeLog(log);
		
		return "redirect:/?error=loginError"; //비정상 로그인
	}
	
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		//session.invalidate();
		if(session.getAttribute("sm_id")!=null) {
			session.removeAttribute("sm_id");
		}
		if(session.getAttribute("sm_name")!=null) {
			session.removeAttribute("sm_name");
		}
		
		//ip불러오기
		String ip = util.getUserIp(request);
		String target= "logout";
		String data = "로그아웃";
		LogDTO log = null;
		
		//로그 기록 입력 2021-09-07
		//세션이 있으면 세션도 넣기 = sl_id
		if(session.getAttribute("sm_id") != null) {
			String id = (String)session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);
		}else {
			log = new LogDTO(ip, target, data);
		}
		logService.writeLog(log);
		
		
		return "redirect:/";
	}
	
	 //가입하기 join 
	 @GetMapping("/join")
	 public String join(HttpServletRequest request) {
		 
			HttpSession session = request.getSession();

			//ip불러오기
			String ip = util.getUserIp(request);
			String target= "login";
			String data = "로그인";
			LogDTO log = null;
			
			//로그 기록 입력 2021-09-07
			//세션이 있으면 세션도 넣기 = sl_id
			if(session.getAttribute("sm_id") != null) {
				String id = (String)session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);
			}else {
				log = new LogDTO(ip, target, data);
			}
			logService.writeLog(log);
			
		 return "join";
	 }
	 
	 //가입하기 post
	 @PostMapping("/join")
	 public String joinAction(HttpServletRequest request) {
		 
		 //오는 데이터 잡아주세요.
		 String id = request.getParameter("id");
		 String pw = request.getParameter("pw1");
		 String name = request.getParameter("name");
		 String email = request.getParameter("email");
		 
		 System.out.println("id : " + id);
		 System.out.println("pw : " + pw);
		 System.out.println("name : " + name);
		 System.out.println("email : " + email);
		 
		 //데이터베이스 저장
		 //데이터베이스로 보낼때 객체에 담기
		 Map<String, Object> login = new HashMap<String, Object>();
		 login.put("id", id);
		 login.put("pw", pw);
		 login.put("name", name);
		 login.put("email", email);
		 
		 int result = loginService.join(login);
		 System.out.println(result);
		 
			HttpSession session = request.getSession();

			//ip불러오기
			String ip = util.getUserIp(request);
			String target= "join";
			String data = "회원가입";
			LogDTO log = null;
			
			//로그 기록 입력 2021-09-07
			//세션이 있으면 세션도 넣기 = sl_id
			if(session.getAttribute("sm_id") != null) {
				 id = (String)session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);
			}else {
				log = new LogDTO(ip, target, data);
			}
			logService.writeLog(log);
		 
		 return "redirect:/";
	 }
	 
	 //ajax id check
	 @PostMapping("/checkID") //responsebody는 내보낼때 결과값이 순수하게 날라가게함 
	 public @ResponseBody String checkID(HttpServletRequest request) {
		 String id = request.getParameter("id");
		 System.out.println("들어온 ID는 : " + id);
		 String check = "1";
		 
		 //데이터베이스에게 질의하기
		 check = loginService.checkID(id);
		 System.out.println(check);
		 
		 return check;
	 }

}
