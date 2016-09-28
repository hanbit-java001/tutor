package com.hanbit.tutor.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.tutor.core.dao.MemberDAO;
import com.hanbit.tutor.core.vo.MemberVO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;

	public String joinMember(MemberVO member) {

		return null;
	}

	public boolean modifyMember(MemberVO member) {

		return false;
	}

}
