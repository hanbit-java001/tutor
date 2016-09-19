package com.hanbit.tutor.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String welcome(Model model) {

		model.addAttribute("name", "Hanbit");

		return "welcome";
	}

	@RequestMapping("/api/data")
	@ResponseBody
	public Map getData() {
		Map map = new HashMap();

		map.put("name", "Hanbit");
		map.put("message", "Hello");

		return map;
	}

}
