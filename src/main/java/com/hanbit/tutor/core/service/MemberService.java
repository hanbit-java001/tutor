package com.hanbit.tutor.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanbit.tutor.core.dao.MemberDAO;
import com.hanbit.tutor.core.vo.MemberVO;

@Service
public class MemberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

	PasswordEncoder encoder = new StandardPasswordEncoder();

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private SecurityService securityService;

	@Transactional
	public String joinMember(MemberVO member) {
		int countMember = memberDAO.countMember(member.getEmail());

		if (countMember > 0) {
			throw new RuntimeException("이미 가입된 이메일입니다.");
		}

		int memberId = memberDAO.selectNextMemberId();
		member.setMemberId(memberId);

		String encryptedPassword = securityService.encryptPassword(member.getPassword());
		member.setPassword(encryptedPassword);

		memberDAO.insertMember(member);

		return member.getName();
	}

	@Transactional
	public boolean modifyMember(MemberVO member) {
		String passwordFromDB = memberDAO.selectPassword(member.getMemberId());
		String passwordCurrent = member.getCurrentPassword();
		String encryptedPasswordCurrent = securityService.encryptPassword(passwordCurrent);

		if (!securityService.matchPassword(passwordFromDB, encryptedPasswordCurrent)) {
			throw new RuntimeException("현재 패스워드를 잘못 입력하셨습니다.");
		}

		String encryptedPassword = securityService.encryptPassword(member.getPassword());
		member.setPassword(encryptedPassword);

		int countUpdate = memberDAO.updateMember(member);

		return countUpdate > 0;
	}

	public MemberVO getMember(int memberId) {
		return memberDAO.selectMember(memberId);
	}

	public List<MemberVO> getMembers(int page) {
		List<MemberVO> members = memberDAO.selectMembers(page);

		return members;
	}

	public int getTotalMembers() {
		return memberDAO.countMembers();
	}

}
