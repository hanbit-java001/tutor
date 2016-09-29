package com.hanbit.tutor.core.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.tutor.core.vo.MemberVO;

@Repository
public class MemberDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberDAO.class);

	@Autowired
	private SqlSession sqlSession;

	public int countMember(String email) {
		return sqlSession.selectOne("member.countMember", email);
	}

	public int insertMember(MemberVO member) {
		return sqlSession.insert("member.insertMember", member);
	}

	public int selectMemberId(String email) {
		return sqlSession.selectOne("member.selectMemberId", email);
	}

	public String selectPassword(int memberId) {
		return sqlSession.selectOne("member.selectPassword", memberId);
	}

	public int updateMember(MemberVO member) {
		return sqlSession.update("member.updateMember", member);
	}

	public int selectNextMemberId() {
		return sqlSession.selectOne("member.selectNextMemberId");
	}

}
