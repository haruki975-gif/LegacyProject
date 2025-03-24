package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {
	
	// 게시글 작성(파일첨부)
	// 문자열 + 파일형 > 멀티파트파일 (같이 보냄)
	void insertBoard(BoardDTO board, MultipartFile file, HttpSession session); 
	/*
	 * 뭐하는지+뭔지
	 * insertBoard();
	 * save();
	 */
	
	// 게시글 목록 조회
	Map<String, Object> selectBoardList(int currentPage); 
	/*
	 * selectBoardList();
	 * selectAll();
	 * findAll();
	 * currentPage > 페이징처리
	 */
	
	// 게시글 상세보기(댓글도 같이 조회) --> 새로운 멋있는 기술쓸거임...
	BoardDTO selectBoard(int boardNo);
	/*
	 * selectBoard();
	 * findByXXXX();
	 */
	
	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	/*
	 * updateBoard();
	 * updateByXXX();
	 */
	
	// 게시글 삭제(딜리트인척하고 업데이트할것 STATUS컬럼값 N으로 바꿀것)
	void deleteBoard(int boardNo);
	
	// ----------------1절
	// 게시글 검색 기능 (페이징 처리도 해야 해서 반환 타입을 Map으로 받음)
	Map<String, Object> doSearch(Map<String, String> map);
	
	// 댓글 작성
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
