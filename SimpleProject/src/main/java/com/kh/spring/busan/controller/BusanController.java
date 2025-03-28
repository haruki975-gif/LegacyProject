package com.kh.spring.busan.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.busan.model.dto.Comment;
import com.kh.spring.busan.model.service.BusanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// controller, mapping, 롬복 관련 애노테이션
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value="busans", produces="application/json; charset=UTF-8")
@RequiredArgsConstructor
public class BusanController {
	
	private final BusanService busanService;
	
	@GetMapping
	public ResponseEntity<String> getBusanFoods(@RequestParam(name="pageNo", defaultValue="1")int pageNo) {
		//log.info("페이지넘버 : {}", pageNo);
		String responseData = busanService.requestGetBusan(pageNo);
		return ResponseEntity.ok(responseData);
	}
	
	// /busans/abc
	// /busans/ddd
	// @GetMapping("/ddd")
	
	// 2절하기 상세조회
	@GetMapping("/{id}")
	public ResponseEntity<String> getBusanDetail(@PathVariable(name="id") int id){
		log.info("넘어온 id : {}", id);
		String response = busanService.requestGetBusanDetail(id);
		return ResponseEntity.ok(response); // 상태 코드 200 전달
	}
	
	// 3절하기 식장에 댓글달기 및 조회
	@PostMapping("/comments") // 식당번호, 내용 => 가공
	public ResponseEntity<String> save(@RequestBody Comment comment){
		log.info("근데 이게... {}", comment);
		busanService.saveComment(comment);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<List<Comment>> getComment(@PathVariable(name="id") Long id){
		List<Comment> comments = busanService.selectCommentList(id);
		return ResponseEntity.ok(comments);
	}
	
	// 4절하기 뒷단을 부트로 바꿔서 돌리기
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
