package com.poseidon.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.util.FileSave;
import com.poseidon.web.log.LogDTO;
import com.poseidon.web.log.LogService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller //Controller설정 필수
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private Util util;
	@Autowired
	private LogService logService;
	@Autowired
	private FileSave fileSave;
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("menu");
		List<HashMap<String, Object>> categoryList = testService.categoryList();
		mv.addObject("categoryList",categoryList);
		return mv;
	}
	
	//맵핑작업
	//@RequestMapping(value="{/board","/board2"}, method=RequestMethod.GET) 여러곳 매핑 하고싶을때
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public ModelAndView board(HttpServletRequest request) {
		
		//객체생성
		ModelAndView mv = new ModelAndView("board");//jsp
			
		//파일업로드
		//mv페이지
		//검색
		
		int sb_cate = 1; //없으면 기본값 설정해주기
		//sb_cate에 값이 있다면 / 숫자로 변환 가능하다면
		if(request.getParameter("sb_cate") != null && 
				util.str2Int(request.getParameter("sb_cate"))){
		//	sb_cate = Integer.parseInt(request.getParameter("sb_cate"));
			sb_cate = util.str2Int2(request.getParameter("sb_cate")); // 중요
		}
		//데이터베이스로 보낼 map 만들기
		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.put("sb_cate", sb_cate);
		
		///////////////////////////////
		//페이지네이션인포 만들어주겠습니다.
		PaginationInfo paginationInfo = new PaginationInfo();
		int pageNo = 1; // 현재 페이지 번호
		int listScale = 10;// 한 페이지에 나올 글 수 
		int pageScale = 10; // 하단에 나타낼 페이지 개수
		
		if(request.getParameter("pageNo") != null) {
			pageNo = util.str2Int2(request.getParameter("pageNo"));
		}
		
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);
		
		int startPage = paginationInfo.getFirstRecordIndex();//시작페이지
		int lastPage =paginationInfo.getRecordCountPerPage();//마지막페이지
		
		sendMap.put("startPage", startPage);
		sendMap.put("lastPage", lastPage);
		
		/////////////////////////////////////
		
		//System.out.println("들어온 카테고리 : " + sb_cate);
		
		// 데이터베이스에 접근해서 값 가져오고
		// DAO / DTO(VO) Service
		
		// user -> Controller -> Serice -> DAO -> DB
		//						DTO(VO)에 담아서 보내줍니다.
		
		//----->service에게 일 시키기
		
		//보드리스트 가져오기 
		List<TestDTO> boardList = testService.boardList(sendMap);
		
		//카테고리 리스트 가져오기 
		//List<HashMap<String, Object>> categoryList = testService.categoryList();
		
		//카테고리 찍기
		//String category = testService.getCategory(sb_cate);
//		String category = (String) categoryList.get(0).get("sc_category");
//		for(HashMap<String, Object> list : categoryList){
//			if((int)(list.get("sc_no")) == sb_cate) { //이부분 수정해야합니다.
//				category = (String) list.get("sc_category");
//			}
// 		}
		
		//////////////////////////////////////////
		int totalList = testService.totalList(sb_cate); //전체 글의 수
		
		paginationInfo.setTotalRecordCount(totalList);// 전체 글 수 저장
		mv.addObject("paginationInfo", paginationInfo); // 페이징도 보내기
		mv.addObject("pageNo", pageNo); // 현 페이지 번호
		mv.addObject("totalList", totalList); // 전체 글 수
		//////////////////////////////////////////
		
		/*
		System.out.println(boardList.get(0).getSb_title());
		System.out.println(boardList.get(1).getSb_title());
		System.out.println(boardList.get(2).getSb_title());
		System.out.println(boardList.get(3).getSb_title());
		*/
		
		//mv추가
		mv.addObject("list", boardList);
		String category = null;
		if(boardList.size() >0) {
			category = boardList.get(0).getSc_category();
			mv.addObject("category", category);			
		}
		//mv.addObject("category", category);
		//mv.addObject("categorylist", categoryList);
		mv.addObject("sb_cate", sb_cate);
		//그 값을 mv에 붙이고
		//board에 찍어주기
		
		//ip불러오기
		String ip = util.getUserIp(request);
		LogDTO log = null;
		String target= "board?sb_cate?"+sb_cate;
		String data = "게시판 읽음";
		
		//로그 기록 입력 2021-09-07
		//세션이 있으면 세션도 넣기 = sl_id
		HttpSession session = request.getSession();
		if(session.getAttribute("sm_id") != null) {
			String id = (String)session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);
		}else {
			log = new LogDTO(ip, target, data);
		}
		
		logService.writeLog(log);	
		//0. 이 로그 저장을 return 위쪽으로 이동 o 
		//1. 모든 곳에 로그 저장 명령을 넣어주세요 = aop
		//2. 130개 이상 만들어주세요.
		//admin/log라고 만들어주세요. + 페이징 + 검색

		return mv;
	}
	
	//write 글쓰기 
	//@RequestMapping(value="/write	", method=RequestMethod.GET)
	@GetMapping("/write") //get요청으로 들어올 수 있게 
	public String write(HttpServletRequest request) {
		//위에서 잡은 sb_cate를 MV에 붙여서 write.jsp로 보내기 
		//그럼 form 태그 안에서 붙여서 글쓰기 
		//sb_cate값까지 가져갈 수 있음.
		
		//로그인 한 사람이 확인
		HttpSession session = request.getSession();
		
		//ip불러오기
		String ip = util.getUserIp(request);
		String target= "write";
		String data = "글쓰기";
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
		
		if(session.getAttribute("sm_name") != null 
				&& session.getAttribute("sm_id") != null) {
			return "write"; //정상로그인 한사람이면 글쓰기 화면으로 
		}else { //로그인 안 한 사람
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/write")
	public String write2( HttpServletRequest request, MultipartFile file) throws IOException { // 자동으로 매칭
		
		//session에 있는 id값 넣어주기 
		HttpSession session = request.getSession();
		
		//ip불러오기
		TestDTO testDTO = new TestDTO();
		String id = request.getParameter("sm_id");
		String ip = util.getUserIp(request);
		String target= "write";
		String data = request.getParameter("sb_content");
		LogDTO log = null;
		
		//세션이 있으면 세션도 넣기 = sl_id
		if(session.getAttribute("sm_id") != null && session.getAttribute("sm_id") != "") {
			//데이터베이스로 보낼 dto만들기
			testDTO.setSb_title(request.getParameter("sb_title"));
			testDTO.setSb_content(request.getParameter("sb_content"));
			testDTO.setSb_cate(util.str2Int2(request.getParameter("sb_cate")));
			testDTO.setSm_id((String)session.getAttribute("sm_id"));
			
			System.out.println("title : " + testDTO.getSb_title());
			System.out.println("content : " + testDTO.getSb_content());
			System.out.println("cate : " + testDTO.getSb_cate());
			
			//file/
			testDTO.setSb_orifile(file.getOriginalFilename());
			
			//실제로 파일을 저장시키기 -> 실제 저장된 파일 이름 -> 아래에
		
			//실제 경로 **중요**
			//톰캣에 가상정보로 빠졌지만 실제 호스팅시에는 wepaap에 리소스로 이동한다.
			//지금 upfile에 넣어버리면 나중에 압축풀때 경로가 틀어지기 때문에 지금 가상서버에 넣어둔다.
			String realPath = servletContext.getRealPath("resources/upfile/");
			//실제 올리는 파일 파일 저장 과정
			//String upfile = fileSave.save(realPath, file);
			String upfile = fileSave.save2(realPath, file);
			
			testDTO.setSb_file(upfile);
			
			System.out.println("file : " + testDTO.getSb_orifile());
			System.out.println("upfile : " + testDTO.getSb_file());
			
			System.out.println("path : " + realPath);			
			
			//System.out.println("file : " + file.getOriginalFilename());
			//System.out.println("file size : " + file.getSize());
			
			
			testService.write(testDTO); // 자동으로 맵핑됨 						

			log = new LogDTO(ip, target, id, data);
		}else {
			log = new LogDTO(ip, target, data);
		}
		logService.writeLog(log);	
		
		return "redirect:/board?sb_cate="+testDTO.getSb_cate();//board로 메소드로 다시 돌아가
	}
	
	@GetMapping("/detail") //값도 싣어 보내야되기때문에 mv
	public ModelAndView deatil(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		//System.out.println("sb_no : "+ sb_no);
		testService.countUp(sb_no); // detail에 바로 나타남
		
		//데이터베이스에서 값 가져오기
		//SELECT * FROM boardview WHERE bno=bno
		//TestDTO 나옴
		TestDTO dto = testService.detail(sb_no);

		//MV
		ModelAndView mv = new ModelAndView("detail");
		//값 붙이기
		mv.addObject("dto", dto);

		//ip불러오기
		HttpSession session = request.getSession();
		String ip = util.getUserIp(request);
		String target= "detail?sb_no="+sb_no;
		String data = dto.getSb_content();
		LogDTO log = null;
		
		//로그 기록 입력 2021-09-07
		//세션이 있으면 세션도 넣기 = sl_id
		if(session.getAttribute("sm_id") != null) {
			String id = (String)session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);
			mv.addObject("sm_id", id);
		}else {
			log = new LogDTO(ip, target, data);
		}
		logService.writeLog(log);
		
		//여기에 읽음 수 sb_count+1 해주기
		//service -> dao -> sqlSession -> DB 
		
		return mv;
	}
	
	 @GetMapping("/delete")
	 public String delete(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		 //데이터베시으로 bno 보내기
		 int result = testService.delete(sb_no);
		 System.out.println("결과 : " + result);
		 
			//ip불러오기
			HttpSession session = request.getSession();
			String ip = util.getUserIp(request);
			String target= "delete?sb_no="+sb_no;
			String data = sb_no+"번 게시물 삭제";
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
		 
		 return "redirect:/board"; //sb_cate 받아서 해당 경로로 이동하게
	 }
	 
	 @GetMapping("/update")
	 public ModelAndView update(HttpServletRequest request) {
		 ModelAndView mv = new ModelAndView("update");
		 
		 //글 번호 가져오기
		 int bno = Integer.parseInt(request.getParameter("sb_no"));
		//System.out.println("sb_no : "+ sb_no);
		
		 
		 TestDTO dto = testService.detail(bno);
		 
		 mv.addObject("dto", dto);
		
			//ip불러오기
			HttpSession session = request.getSession();
			String ip = util.getUserIp(request);
			String target= "update?bno="+bno;
			String data = bno+"번 게시물 수정 전";
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
			
		 return mv;
		 
	 }
	 
	 @PostMapping("/update")
	 public String update(TestDTO dto , HttpServletRequest request) {//name이 똑같다면 거기에 저장, 객체로저장하는방법, requestparam으로 받는방법 등등이 있다.
		 //System.out.println(dto.getSb_title());
		 //System.out.println(dto.getSb_content());
		 //System.out.println(dto.getSb_no());
		 
		 //데이터 베이스로 보내서 저장시켜주세요
		 int result = testService.update(dto);
		 System.out.println("수정결과 : " + result);
		 
			//ip불러오기
			HttpSession session = request.getSession();
			String ip = util.getUserIp(request);
			String target= "update?sb_no="+dto.getSb_no();
			String data = dto.getSb_no()+"번 게시물 수정 후";
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
				 
		 return "redirect:/detail?sb_no="+dto.getSb_no();
		 
	 } 
	 
	 @RequestMapping("/temp")
	 public String temp() {
		 return "temp";
	 }
	
	 //좋아요 기능을 넣는다면?
	 //1.데이터 베이스에 컬럼을 추가해주세요. O 
	 //2.DTO에 sb_like를 추가해주시고, getter/setter를 만들어주세요.
	 //3.jsp화면에 그림하나 넣어주시고, db에서 해당 값을 받아와 출력해주세요.
	 //4.그림을 누르면 페이지 전환 -> sb_like+1시켜주세요. -> 페이지 전환
	 
	 @GetMapping("/like")
	 public String like(@RequestParam("sb_no") int sb_no, 
			 HttpServletRequest request, HttpServletResponse response,
			 Model model) throws IOException{
		 
		 HttpSession session = request.getSession();
		 
		 if(session.getAttribute("sm_id") != null) {
			 //보낼 값이 2개 sb_no, sm_id
			 Map<String, Object> sendMap = new HashMap<String, Object>();
			 sendMap.put("sb_no", sb_no);
			 sendMap.put("sm_id", session.getAttribute("sm_id"));			 		 
			 
			 int result = testService.exist(sendMap);
			
			 if(result == 0) {
				 testService.likeUp(sendMap);				 				 				 
				 return "redirect:/detail?sb_no="+sb_no;			 
			 }else {
				 response.setContentType("text/html; charset=euc-kr");
				 PrintWriter pw = response.getWriter();
				
				 pw.println("<script>"
				 		+ "var result = confirm('이미 누르셨습니다. 좋아요를 취소하시겠습니까?'); "
				 		+ "if(result == true){location.href='./likeCancle?sb_no="+sb_no+"';} "
				 		+ "else{window.history.back();}</script>");
				 pw.flush();	
				 pw.close();
			 }
		 }else {		
			model.addAttribute("msg", "로그인을 해야합니다.");
			model.addAttribute("url", "./login");
			return "redirect:/alert";
		 }	 		 
		 return "redirect:/detail?sb_no="+sb_no;			 
	 }
	 
	 @GetMapping("/alert")
	 public ModelAndView alert(HttpServletRequest request) {
		 ModelAndView mv = new ModelAndView("alert");
		 String msg = request.getParameter("msg");
		 String url = request.getParameter("url");
		 
		 mv.addObject("msg", msg);
		 mv.addObject("url", url);
		 
		 return mv;
	 }
	 
	 @GetMapping("/likeCancle")
	 public String likeCancle(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		 
		 testService.likeCancle(sb_no);
		 
		 return "redirect:/detail?sb_no="+sb_no;			 
	 }
}


	

















