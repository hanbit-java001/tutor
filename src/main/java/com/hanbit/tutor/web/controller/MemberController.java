package com.hanbit.tutor.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class MemberController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("/member/join")
	public String join() {

		return "member/join";
	}

	@RequestMapping(value="/api/member/join", method=RequestMethod.POST)
	@ResponseBody
	public Map doJoin(MultipartHttpServletRequest request) {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Iterator<String> paramNames = request.getFileNames();

		while (paramNames.hasNext()) {
			String paramName = paramNames.next();

			MultipartFile file = request.getFile(paramName);
			LOGGER.debug(file.getOriginalFilename() + "/" + file.getSize());
		}

		return new HashMap();
	}

}
