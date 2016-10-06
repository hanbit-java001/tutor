package com.hanbit.tutor.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanbit.tutor.core.service.SecurityService;
import com.hanbit.tutor.core.vo.MemberVO;

@Controller
public class SecurityController {

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value="/api/security/login", method=RequestMethod.POST)
	@ResponseBody
	public Map login(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest request) {

		MemberVO member = securityService.getValidMember(email, password);

		HttpSession session = request.getSession();
		session.setAttribute("email", email);
		session.setAttribute("loggedIn", true);

		Map result = new HashMap();
		result.put("name", member.getName());

		return result;
	}

	@RequestMapping("/security/logout")
	public void logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		session.invalidate();

		response.sendRedirect("/");
	}

}
