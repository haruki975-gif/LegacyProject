package com.kh.spring.busan.model.service;

import java.util.List;

import com.kh.spring.busan.model.dto.Comment;

public interface BusanService {
	
	// 1절
	String requestGetBusan(int pageNo);
	
	// 2절
	String requestGetBusanDetail(int pk);
	
	// 3절(식당에 후기 달기)
	void saveComment(Comment comment); // 작성하기
	List<Comment> selectCommentList(Long seq); // 조회하기
}
