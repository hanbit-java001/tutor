package com.hanbit.tutor.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanbit.tutor.core.service.SecurityService;
import com.hanbit.tutor.core.session.Session;
import com.hanbit.tutor.core.session.SessionHelpler;
import com.hanbit.tutor.core.vo.MemberVO;

@Controller
public class SecurityController {

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value="/api/security/login", method=RequestMethod.POST)
	@ResponseBody
	public Map login(@RequestParam("email") String email,
			@RequestParam("password") String password) {

		MemberVO member = securityService.getValidMember(email, password);

		Session session = SessionHelpler.getSession();
		session.setLoggedIn(true);
		session.setMemberId(member.getMemberId());
		session.setEmail(email);
		session.setName(member.getName());

		Map result = new HashMap();
		result.put("name", member.getName());

		return result;
	}

	@RequestMapping("/api/security/isLoggedIn")
	@ResponseBody
	public Map isLoggedIn() {

		Map result = new HashMap();
		Session session = SessionHelpler.getSession();

		if (!session.isLoggedIn()) {
			result.put("name", "");
		}
		else {
			result.put("name", session.getName());
		}

		return result;
	}

	@RequestMapping("/security/logout")
	public void logout(HttpServletResponse response) throws Exception {

		Session session = SessionHelpler.getSession();
		session.logout();

		response.sendRedirect("/");
	}

}
