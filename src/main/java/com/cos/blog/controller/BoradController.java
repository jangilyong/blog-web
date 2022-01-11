package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@Controller
public class BoradController {
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}

}
