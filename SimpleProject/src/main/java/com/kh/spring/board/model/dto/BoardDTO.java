package com.kh.spring.board.model.dto;

import lombok.NoArgsConstructor;

import java.util.List;

import com.kh.spring.reply.model.dto.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// ValueObject == 불변성, 기본생성자X + 필드값이 동일하면 동일한 객체로 인식, 이퀄스와 해시코드를 오버라이딩 해야함
// DTO > setter가 빠져야함

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BoardDTO {
	private int boardNo;
	private String boardTitle; 
	private String boardContent; 
	private String boardWriter; 
	private int count;
	private String createDate;
	private String changeName;
	private String status;
	private List<ReplyDTO> replyList;
}
