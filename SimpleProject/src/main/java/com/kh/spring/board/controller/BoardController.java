package com.kh.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// ?page=1
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name="page", defaultValue="1") int page, 
							  Model model) {
		
		// 한 페이지에 몇 개 보여줄까? == 5
		// 버튼 몇 개 보여줄까? 	 == 5
		// 총 게시글 == SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS='Y'
		if(page < 1) {
			throw new InvalidParameterException("페이지 없음");
		}
		Map<String, Object> map = boardService.selectBoardList(page);
		
		model.addAttribute("map", map);
		
		return "board/board_list";
	}
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv
								,BoardDTO board
								,MultipartFile upfile
								,HttpSession session) {
		
		log.info("게시글 정보 잘 넘어옴? : {} / 파일 정보는? : {}", board, upfile);
		
		// 첨부파일의 존재유무
		// => 차이점 => MultipartFile타입의 filename필드값으로 확인을 하겠다.
		
		// INSERT INTO TB_SPRING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER, CHANGE_NAME)
		// VALUES(#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName})
		// 1. 권한있는 요청인가? (writer가 맞는지)
		// 2. 값들이 유효성 있는 값인가
		// 3. 전달된 파일이 존재할 경우 파일명을 수정 후 서버에 올리고 BoardDTO의 changeName 필드에 값을 대입
		
		boardService.insertBoard(board, upfile, session);
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글이 등록되었습니다.");
		return mv;
	}
	
	
	@GetMapping("boards/{id}")
	public ModelAndView goBoard(@PathVariable(name="id") int boardNo, ModelAndView mv) {
		//log.info("게시글번호 : {}", boardNo);
		
		if(boardNo < 1) {
			throw new InvalidParameterException("비정상적인 접근입니다.");
		}
		BoardDTO board = boardService.selectBoard(boardNo);
		mv.addObject("board", board).setViewName("board/board_detail");
		return mv;
	}
	
	// 게시판 검색 search
	@GetMapping("search")
	public ModelAndView doSearch(
			@RequestParam(name="condition") String condition, 
			@RequestParam(name="keyword") String keyword,
			@RequestParam(name="page", defaultValue="1") int page,
			ModelAndView mv) {
		
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		// int -> String으로 변환 > String.valueOf()
		map.put("currentPage", String.valueOf(page));
		
		Map<String, Object> list = boardService.doSearch(map);
		// model로 condition과 keyword를 넘겨줘야함(검색 후 페이징 처리 유지)
		list.put("condition", condition);
		list.put("keyword", keyword);
		
		// 포워딩 작업
		mv.addObject("map", list).setViewName("board/board_list");
		return mv;
	}
	
	
	
	
	
	
	
	
	

}
