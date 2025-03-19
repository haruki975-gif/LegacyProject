package com.kh.spring.member.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.dto.MemberDTO;

// 저장소 == Repository
@Repository
public class MemberDAO {
	
	// 로그인
	public MemberDTO login(SqlSessionTemplate sqlSession, MemberDTO member) {
		return sqlSession.selectOne("memberMapper.login", member);
	}
	
	// 아이디중복체크
	public int checkId(SqlSessionTemplate sqlSession, String memberId) {
		return sqlSession.selectOne("memberMapper.checkId", memberId);
	}
	
	// 회원가입
	public int signUp(SqlSessionTemplate sqlSession, MemberDTO member) {
		return sqlSession.insert("memberMapper.signUp", member);
	}
	
	
}
