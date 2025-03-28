package com.kh.spring.busan.model.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kh.spring.busan.model.dto.Comment;
import com.kh.spring.busan.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusanServiceImpl implements BusanService {
	
	private final CommentMapper mapper;
	private final String SERVICE_KEY = "NP3TSVUqRXLFKndGRD8VCeQpNvoC1uFkMusUSX%2BvFT2hLJ09yV3NJyA3n7sp%2BbCK6dB7jF0CdWfOEOgwtIJH8Q%3D%3D";
	
	private String apiRequest(String uriPath) {
		URI uri = null;
		
		try {
			uri = new URI(uriPath);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String apiResponseData = new RestTemplate().getForObject(uri, String.class);
		return apiResponseData;
	}
	
	@Override
	public String requestGetBusan(int pageNo) {
		if(pageNo < 1) { pageNo = 1; }
		StringBuilder sb = new StringBuilder();
		sb.append("http://apis.data.go.kr/6260000/FoodService/getFoodKr");
		sb.append("?serviceKey=" + SERVICE_KEY);
		sb.append("&pageNo=" + pageNo);
		sb.append("&numOfRows=9");
		sb.append("&resultType=json");
		
		//log.info("API서버에서 응답온거:{}", apiResponseData);
		return apiRequest(sb.toString());
	}

	// 식당 1개 조회
	@Override
	public String requestGetBusanDetail(int pk) {
		if(pk < 1) { return null; } // 예외 발생시켰어야함(부트가서 합시다)
		StringBuilder sb = new StringBuilder();
		sb.append("http://apis.data.go.kr/6260000/FoodService/getFoodKr");
		sb.append("?serviceKey=" + SERVICE_KEY);
		sb.append("&pageNo=1");
		sb.append("&numOfRows=1");
		sb.append("&resultType=json");
		sb.append("&UC_SEQ=" + pk);
		
		return apiRequest(sb.toString());
	}

	
	@Override
	public void saveComment(Comment comment) {
		mapper.saveComment(comment);
	}

	@Override
	public List<Comment> selectCommentList(Long seq) {
		return mapper.selectCommentList(seq);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
