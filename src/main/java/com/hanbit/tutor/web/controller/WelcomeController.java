package com.hanbit.tutor.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanbit.tutor.core.session.LoginRequired;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String welcome() {

		return "welcome";
	}

	@LoginRequired
	@RequestMapping("/api/data")
	@ResponseBody
	public Map getData(String dummy) {
		Map map = new HashMap();

		map.put("name", "Hanbit");
		map.put("message", "Hello");

		if (map != null) {
			throw new RuntimeException("데이터 요청 에러");
		}

		return map;
	}

}
