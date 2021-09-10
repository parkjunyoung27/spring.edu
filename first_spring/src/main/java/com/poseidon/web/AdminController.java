package com.poseidon.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.web.admin.AdminService;
import com.poseidon.web.admin.MemberDTO;
import com.poseidon.web.log.LogDTO;
import com.poseidon.web.log.LogService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/admin") //admin 하위로 들어오는 곳은 모두 이곳으로 매핑된다.
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired 
	private LogService logService;
	@Autowired
	private Util util;
	
	//@RequestMapping(value= {"/category", "/admin"})
	//http://localhost:8080/web/admin/category
	@RequestMapping(value = "/category", method=RequestMethod.GET)
	public ModelAndView category(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/category");
		
		//scategory 내용을 다 가져와 주세요. sc_no, sc_category, sc_date
		List<HashMap<String, Object>> cate = adminService.getCategory();//hashmap은 카페24에 올리면 에러가 난다.
		//mv에 붙여주세요.
		mv.addObject("category", cate);
		//jsp에 찍어주세요.
		return mv;
	}
	
	//@RequestMapping(value="/admin/category" method=RequestMethod.POST)
	@PostMapping("/category")
	public String categoryInsert(HttpServletRequest request) {
		String categoryName = request.getParameter("categoryName");
		System.out.println(categoryName);
		//로직...
		//서비스 -> DAO -> sqlSession -> DB 
		//결과 받고싶으면 result 지정
		int result = adminService.categoryInsert(categoryName);
		System.out.println("저장 결과 : " + result);
		return "redirect:/admin/category"; //get다시 호출
	}
	
	@GetMapping("/categoryUpdate")
	public ModelAndView categoryUpdate(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
		System.out.println("들어온 sc_no " + sc_no );
		
		ModelAndView mv = new ModelAndView("/admin/categoryUpdate");
		
		HashMap<String, Object> cate = adminService.getCategory(sc_no);
		System.out.println(cate);
		
		mv.addObject("cate", cate);
		
		return mv;
	}
	
	@PostMapping("/categoryUpdate")
	public String categoryUpdate2(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
		String category = request.getParameter("category");
		
		System.out.println("sc_no: " + sc_no);
		System.out.println("category: " + category);
		
		//DB로 보내기
		HashMap<String, Object> cate = new HashMap<String, Object>();
		cate.put("sc_no", sc_no);
		cate.put("category", category);
		int result = adminService.categoryUpdate(cate);
		
		System.out.println("수정 결과 : " + result);
		
		return "redirect:/admin/category";
	}
	
	//전체 회원리스트 단, 비밀번호는 안 보이게;;
	// admin/meber member.jsp만들기
	@GetMapping("/member")
	public ModelAndView member() {
		ModelAndView mv = new ModelAndView(); //페이지 없이 생성
		mv.setViewName("admin/member"); //추후 페이지 넣어주기

		//멤버리스트 가져오기
		List<HashMap<String, Object>> list = adminService.list();
		//mv에 붙여주세요.
		mv.addObject("list", list);
		
		return mv;
	}
	
	//회원 등급 조정 down up , 두개 다 처리하기
	//이중매핑 **중요**
	@GetMapping({"/gradeDown", "/gradeUp"})
	public String gradeDown(
			@RequestParam("sm_no") int sm_no, 
			@RequestParam("grade") int sm_grade) {
		System.out.println("sm_no : " + sm_no + " grade : "+ sm_grade);
		//이 메소드 정상으로 동작하지 않습니다.
		//고치는 방법은?
		MemberDTO dto = new MemberDTO();
		dto.setSm_no(sm_no);
		dto.setSm_grade(sm_grade);
		
		int result=adminService.gradeUpDown(dto);
		System.out.println("등급 업데이트 결과 : " + result);
		return "redirect:/admin/member";
	}
	
	@GetMapping("/userDelete")
	public String userDelete(@RequestParam("sm_no") int sm_no) {
		//로직
		int result = adminService.userDelete(sm_no);
		
		return "redirect:/admin/member";
	}
	
	@GetMapping("/categoryVisible")
	public String categoryPublicPrivate(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
		System.out.println("들어온 sc_no " + sc_no );
		
		int result = adminService.categoryVisible(sc_no);
				
		return "redirect:/admin/category";	
	}
	
	@GetMapping("/log")
	public ModelAndView log(HttpServletRequest request) {
			
		ModelAndView mv = new ModelAndView("admin/log");		
	
		String searchName= request.getParameter("searchName");
		String search = request.getParameter("search");
		System.out.println("searchName : " + searchName);
		System.out.println("search : " + search);
		
		//페이지네이션인포 만들기
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

		Map<String, Object> sendMap = new HashMap<String, Object>();
				
		int startPage = paginationInfo.getFirstRecordIndex();//시작페이지
		int lastPage =paginationInfo.getRecordCountPerPage();//마지막페이지
				
		sendMap.put("startPage", startPage);
		sendMap.put("lastPage", lastPage);
		//검색 값 map에 붙이기 
		if(searchName != null) {
			sendMap.put("searchName", searchName);
			sendMap.put("search", search);			
			mv.addObject("searchName", searchName);
			mv.addObject("search", search);		
		}
		
		List<LogDTO> loglist = logService.loglist(sendMap);
		int totalCount = logService.logTotalList(sendMap); //전체 글의 수
		
		paginationInfo.setTotalRecordCount(totalCount);// 전체 글 수 저장
		
		mv.addObject("paginationInfo", paginationInfo); // 페이징도 보내기
		mv.addObject("pageNo", pageNo); // 현 페이지 번호
		mv.addObject("totalCount", totalCount); // 전체 글 수
		mv.addObject("list", loglist);
		return mv;		
	}
		
}


