package com.hanbit.tutor.core.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.tutor.core.vo.MemberVO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession sqlSession;

	public int countMember(String email) {

		return 0;
	}

	public int insertMember(MemberVO member) {

		return 0;
	}

	public String selectPassword(int memberId) {

		return null;
	}

	public int updateMember(MemberVO member) {

		return 0;
	}

}
