package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Slf4j : println() => log.info("", aa)로그로 출력
@Slf4j
@Controller
@RequiredArgsConstructor
// => 의존성주입 생성자를 생성해주는 애노테이션, 롬복에 달림 (@Autowired 선언 없어도 됨)
public class MemberController {
	
	// 필드로 타입만 선언해둠
	// 1번 필드 주입
	// @Autowired
	private final MemberService memberService;
	
	/* 2번 setter 주입
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	// 3번 생성자 주입, 기본 생성자 생성 불가, final 선언 가능(불변)
	/*
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	
	/* 1번
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
		//System.out.println("로그인 요청 오면 출동함");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		//System.out.println("id : " + id + ", pw : " + pw);
		log.info("id : {}, pw : {}", id, pw);
		//INFO : com.kh.spring.member.controller.MemberController - id : admin, pw : 1234
		return "main_page";
	}
	*/
	
	/* 2번
	@RequestMapping("login")
	public String login(@RequestParam(value="id", defaultValue="abcde") String id,
						@RequestParam(value="pw") String pw) {
		log.info("이렇게도 넘어오나요? id : {} / pw : {}", id, pw);
		return "main_page";
	}
	*/
	
	/* 3번
	@PostMapping("login")
	public String login(String id, String pw) {
		log.info("이렇게도 넘어옴? id : {} / pw : {}", id, pw);
		
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		return "main_page";
	}
	*/
	
	/**
	 * 커맨드 객체 방식
	 * 
	 * 1. 매개변수 자료형에 반드시 기본 생성자가 존재할 것
	 * 2. 전달되는 키값과 객체의 필드명이 동일할 것
	 * 3. 필드의 setter메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해 생성한 후 내부적으로 
	 * setter메서드를 찾아서 요청 시 전달값을 해당 필드에 대입해준다.
	 * (Setter Injection)
	 */
	/*@PostMapping("login")
	public String login(MemberDTO member, 
						HttpSession session, 
						Model model) {
		
		// log.info("지금도 넘어옴?? {}", member);
		
		 데이터가공 => 패스
		 * 요청처리 => 
		 * 서비스를 호출하려면 메모리에 올려야함 => 메모리를 올리려면 객체를 생성해야함
		 * 응답화면지정
		 
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			log.info("로그인 성공");
		} else {
			log.info("로그인 실패");
		}
		
		if(loginMember != null) { // 성공했을 때
			// sessionScope에 로그인정보를 담아줌
			session.setAttribute("login", loginMember);
			// 그 다음에 메인페이지로 넘어가야함
			// /WEB-INF/views/
			// .jsp
			// => 포워딩
			// sendRedirect
			
			// localhost/spring   /->리다이렉트는 이것만 붙여서 보내주면 됨
			return "redirect:/";
			
		}else { // 실패했을 때
			// error_page
			// requestScope에 에러문구를 담아서 포워딩
			// spring에서는 Model객체를 이용해서 RequestScope에 값을 담음
			model.addAttribute("message", "로그인 실패!");
			
			// forwarding
			// /WEB-INF/views/
			// include/error_page
			// .jsp
			// (문자열)논리적인 경로를 가지고 (실제위치)물리적인 경로를 찾아간다 
			// ㄴ> JSP에 위임
			 return "include/error_page";
		}
		
		// return "main_page";
	}
*/	
	
	
	// 두 번째 방법 반환타입 ModelAndView로 돌아가기
	@PostMapping("login")
	public ModelAndView login(MemberDTO member,
			HttpSession session,
			ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/"); 
		} else {
			mv.addObject("message", "로그인 실패!")
			.setViewName("include/error_page");
		}
		return mv;
	}
	
	
	// 로그아웃
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session,
						ModelAndView mv) {
		
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		return mv;
	}
	
		
	@GetMapping("signup-form")
	public String signupForm() {
		// /WEB-INF/views/member/signup-form.jsp
		return "member/signup-form";
	}
	
	
	/**
	 * @param member id, pw, name, email
	 * @return 성공 시 main~ 실패하면 error~
	 */
	@PostMapping("signup")
	public String join(MemberDTO member, HttpServletRequest request) {
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		// log.info("멤버 필드 찍어보기 : {}", member);
		memberService.signUp(member);
		return "main_page";
	}
	
	
	// 마이페이지
	@GetMapping("my-page")
	public String myPage() {
		return "member/my_page";
	}
	
	@PostMapping("update-member")
	public String update(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		/*
		 * 1_1) 404 발생 : mapping값 잘못 적음
		 * org.springframework.web.servlet.PageNotFound
		 * - No mapping for POST / spring/update-member
		 * 
		 * 1_2) 405 발생 : GET으로 요청을 보내놓고 메서드와 맞지 않은 애노테이션을 사용했을 때 
		 * Request method 'POST' not supported
		 * 
		 * 1_3) 필드에 값이 들어오지 않는 경우 : name속성값 에러
		 */
		log.info("사용자가 입력한 값 : {}", member);
		
		// 2. 이번에 실행할 SQL문을 생각
		// UPDATE문 => KH_MEMBER(MEMBER_ID)
		// ID, PW, NAME, EMAIL, DATE => 내가 PK값을 가져왔나? 확인.
		// 2_1) 매개변수 MemberDTO타입의 memberId 필드값
		// 2_2) SessionScope에 loginMember 키값에 memberId필드값
		// 넘겨주어야겠구나 + 
		
		// 값들이 유요한 값인지 체크하기
		// MemberId가 존재하는 아이디인지 체크하기
		
		// UPDATE KH_MEMBER SET MEMBER_NAME = 사용자가 입력한 이름, 
		// 						EMAIL = 사용자가 입력한 이메일,
		// 						WHERE MEMBER_ID = 사용자가 입력한 아이디
		// UPDATE 수행의 결과 => PK를 조건으로 수행함 => 0 / 1
		
		// 수행에 성공했을 경우 => 
		// my-page.jsp로 이동시켜서 갱신된 사용자의 정보 출력
		
		// id를 가지고 다시 조회 => login메서드 재활용
		
		// 수행에 실패했을 경우 =>
		// message를 담아서 error_page로 포워딩
		// 예외 발생 => 예외처리기로 위임
		
		memberService.update(member, session);
		return "redirect:my-page";
	}
	
	
	// 탈퇴구현 숙제...
	// 비밀번호 입력받음
	// 비밀번호 맞는지 검증 => 예외 발생시키기
	// Delete 성공했는지
	
	@PostMapping("delete-member")
	public String delete(MemberDTO member) {
		
		log.info("사용자가 입력한 비밀번호 : {}", member);
		
		memberService.delete(member);
		
		return "main_page";
	}
	
	@ResponseBody
	@GetMapping("id-check")
	public String idCheck(@RequestParam(name="memberId") String memberId) {
		// 응답을 어떻게 돌려줄 것인가?
		// 조회 결과가 있다/없다
		//     NNNNY/NNNNN
		
		return memberService.idCheck(memberId);
		// SELECT DECODE(MEMBER_ID) FROM KH_MEMBER WHERE MEMBER_ID = 사용자가입력한아이디
	}
	
	
	
	
	
	
	
	
	
	
}
