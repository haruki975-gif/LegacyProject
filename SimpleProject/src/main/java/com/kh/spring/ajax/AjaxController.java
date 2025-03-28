package com.kh.spring.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {
	
	@GetMapping("ajax")
	public String forward() {
		return "ajax/ajax";
	}
	
	
	/*
	 * 응답할 데이터를 문자열로 반환
	 * ModelAndView의 viewName필드에 return한 스트링 값이 대입
	 * => DispatcherServlet
	 * => ViewResolver
	 * 
	 * 내가 반환하는 String타입의 값이 View의 정보가 아니다. 응답데이터다!!
	 * => MessageConverter로 이동하게끔
	 * 
	 * @ResponseBody
	 */
	@ResponseBody
	@GetMapping(value="test", produces="text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input") String value) {
		log.info("AJAX요청을 통해 넘어온 value값 : {}", value);
		
		String returnValue = value.equals("admin") ? "아이디 있어요" : "아이디 없어요";
		
		return returnValue;
	}
	
	
	
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@ResponseBody
	@GetMapping(value="study", produces="application/json; charset=UTF-8")
	public ResponseEntity<BoardDTO> ajaxStudy(@RequestParam("replyNo") int boardNo) {
		
		/*
		 * DTO의 목적 == 테이블의 행에 있는 컬럼의 값을 필드에 담아옴
		 * 
		 * BoardDTO
		 * 
		 * boardTitle == 머시기
		 * boardContent == 머시기
		 * boardWriter == 머시기
		 * * 자바스크립트에서 값을 사용하려면 객체 형태여야함.
		 * 
		 * <boardTitle>제목입니당</boardTitle> => XML
		 * 
		 * {
		 * 	"boardTitle" : "제목",
		 * 	"boardContent" : "내용",
		 *  "boardWriter" : "글쓴이"
		 * }
		 * => JSON
		 */
		BoardDTO board = boardService.selectBoard(boardNo);
		//log.info("게시글 봐야지 : {}", board);
		return ResponseEntity.ok(board);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
